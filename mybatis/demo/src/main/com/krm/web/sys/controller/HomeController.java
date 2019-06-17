package com.krm.web.sys.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.krm.common.base.BaseController;
import com.krm.common.base.CommonEntity;
import com.krm.common.base.Result;
import com.krm.common.captcha.ValidateCode1;
import com.krm.common.constant.Constant;
import com.krm.common.constant.SysConstant;
import com.krm.common.utils.encrypt.PasswordEncoder;
import com.krm.web.sys.model.SysUser;
import com.krm.web.sys.service.SysUpdateLogService;
import com.krm.web.sys.service.SysUserService;
import com.krm.web.tools.service.EmailService;
import com.krm.web.util.SysUserUtils;

/**
 * 
 * @author Parker
 * 首页控制层
 * 2017-12-27
 */
@Controller
@RequestMapping("home")
public class HomeController extends BaseController {
	
	public static final String BASE_URL = "home";
	private static final String BASE_PATH = "";
	
	@Resource
	private SysUpdateLogService sysUpdateLogService;
	@Resource
	private EmailService emailService;
	@Resource
	private SysUserService sysUserService;
	
	@Override
	protected String getBaseUrl() {
		return BASE_URL;
	}

	@Override
	protected String getBasePath() {
		return BASE_PATH;
	}
	
	@RequestMapping(value="getUser", method=RequestMethod.POST)
	@ResponseBody
	public Result getUser(@RequestParam Map<String, Object> params){
		logger.info("开始获取当前用户信息");
		return Result.successResult(SysUserUtils.getCacheLoginUser());
	}
	
	@RequestMapping(value="updateLogYear", method=RequestMethod.POST)
	@ResponseBody
	public Result updateLogYear(@RequestParam Map<String, Object> params){
		logger.info("开始获取系统升级日志年份");
		List<CommonEntity> list = sysUpdateLogService.updateLogYear(params);
		return Result.successResult(list);
	}
	
	@RequestMapping(value="updateLog", method=RequestMethod.POST)
	@ResponseBody
	public Result updateLog(@RequestParam Map<String, Object> params){
		logger.info("开始获取系统升级日志内容");
		params.put("sortC", "year");
		params.put("order", "desc");
		List<CommonEntity> list = sysUpdateLogService.allUpdateLog(params);
		return Result.successResult(list);
	}
	
	/**
	 * 预览图片
	 * @param type
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @throws IOException
	 */
	@RequestMapping("view/{type}")
	public void download(@PathVariable String type, HttpServletRequest request, HttpServletResponse response) throws IOException {
		String fileName = request.getParameter("fileName");
		fileName = new String((fileName).getBytes("ISO-8859-1"),"UTF-8");
		fileName = java.net.URLDecoder.decode(fileName, "UTF-8");
		String doctType = fileName.substring(fileName.lastIndexOf(".")+1, fileName.length());
		logger.info("开始预览图片：{}！",fileName);
		String targetDirectory = null;
		if(type.equals("QR")){
			targetDirectory = SysConstant.getValue("upload.rootPath") + File.separator + type +
					File.separator + request.getServerName() + File.separator + request.getServerPort();
		}else{
			targetDirectory = SysConstant.getValue("upload.rootPath") + File.separator + type;
		}
		File file = new File(targetDirectory + File.separator + fileName);
		if(file.exists()){
			//读取本地图片输入流  
			FileInputStream inputStream = new FileInputStream(file.getAbsolutePath());  
			int i = inputStream.available();  
			//byte数组用于存放图片字节数据  
			byte[] buff = new byte[i];  
			inputStream.read(buff);  
			//记得关闭输入流  
			inputStream.close();  
			response.setHeader("Accept-Ranges", "bytes");
			response.setContentType("image/"+doctType);
			OutputStream out = response.getOutputStream();
			out.write(buff);  
			//关闭响应输出流  
			out.close();  
		}else{
			logger.warn("请求的文件不存在:{}",file.getAbsolutePath());
		}
	}
	
	/**
	 * 生成登录验证码
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("validateCode")
	public void validateCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setHeader("Accept-Ranges", "bytes");
		response.setContentType("image/png");
//		String code = ValidateCode.createCode(response.getOutputStream());
		String code = ValidateCode1.generateVerifyCode(5);
		ValidateCode1.outputImage(158, 60, response.getOutputStream(), code);
		SysUserUtils.getSession().setAttribute("captcha", code.toUpperCase());
	}
	
	/**
	 * 反显验证码，偷懒专用
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("getCode")
	@ResponseBody
	public Result getCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
		return Result.successResult(SysUserUtils.getSession().getAttribute("captcha").toString());
	}
	
	/**
	 * 进入注册页面
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="enterRegistered", method = RequestMethod.GET)
	public String enterRegistered(HttpServletRequest request,HttpServletResponse response, Model model) throws IOException{
		logger.info("进入注册页面");
		return "registered";
	} 
	
	/**
	 * 账号注册
	 * @param username
	 * @param password
	 * @param captcha
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("registered")
	@ResponseBody
	public Result registered(String username, String password, String captcha, HttpServletRequest request) throws IOException {
		/* 验证码校验  */
		Session session = SecurityUtils.getSubject().getSession();
		String code = (String)session.getAttribute("captcha");
		if (captcha == null || !captcha.toUpperCase().equals(code)){
			return new Result(0, "验证码错误!");
		}
		/* 账号重复校验  */
		SysUser param = new SysUser();
		param.setUsername(username);
		List<SysUser> users = sysUserService.select(param);
		if(users != null && users.size() == 1 && users.get(0) != null) {
			SysUser user = users.get(0);
			if(user.getStatus().equals(Constant.USER_STATUS_NORMAL)){
				return new Result(0, "账号已存在，请换一个吧！");
			}else if(user.getStatus().equals(Constant.USER_STATUS_NONACTIVATED)){
				return new Result(0, "账号已注册，尚未激活，请前往邮箱进行激活吧！");
			}
		}
		/* 数据库存在多个相同账号，账号异常  */
		if(users != null && users.size() > 1) {
			return new Result(0, "账号异常，请换一个吧！");
		}
		/* 是否包含不在特定范围内的特殊字符 */
		String regex1 = "^((?=[0-9a-zA-Z!@#$%^&*\\-_]).){1,}$";
		Pattern pattern1 = Pattern.compile(regex1);
		Matcher matcher1 = pattern1.matcher(password);
		if(!matcher1.matches()){
			return new Result(0, "您的密码含有非法字符！");
		}
		/* 8到32位，且至少由字母、数字和特殊字符其中两种进行组合 */
		String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)(?![!@#$%^&*\\-_]+$)[0-9a-zA-Z!@#$%^&*\\-_]{8,32}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(password);
		if(!matcher.matches()){
			return new Result(0, "您的密码太过简单<br>tip: 至少由字母、数字和特殊字符其中两种进行组合！");
		}
		
		String id = sysUserService.generateId();
		SysUser user = new SysUser();
		user.setId(id);
		user.setUsername(username);
		String encryptPwd = PasswordEncoder.encrypt(password, username);
		user.setPassword(encryptPwd);
		user.setName(username.substring(0, username.indexOf("@")));
		user.setOrganId("1");
		user.setDeptId("1");
		/**
		 * 由于Linux暂时无法发送邮件，Linux版本取消激活操作
		 * user.setStatus(Constant.USER_STATUS_NONACTIVATED);
		 */
		user.setStatus(Constant.USER_STATUS_NORMAL);
		/* 注册用户默认角色  */
		String [] roleIds = {"1", "2"};
		user.setRoleIds(roleIds);
		sysUserService.saveRegisteredUser(user);
		/**
		 * String url = request.getScheme() + "://"+ request.getServerName()+ ":"+ request.getServerPort() +request.getContextPath() + "/home/activateAccount/" + id;
		 * emailService.sendRegisterEmail(username, url);
		 * return new Result(1, "恭喜您注册成功，请前往邮箱进行账号激活吧！");
		 */
		return new Result(1, "恭喜您注册成功！");
	}
	
	
	/**
	 * 账号激活
	 * @param id
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="activateAccount/{id}", method = RequestMethod.GET)
	public String activateAccount(@PathVariable String id, Model model) throws IOException{
		logger.info("开始激活账号");
		SysUser user = new SysUser();
		user.setId(id);
		user.setStatus(Constant.USER_STATUS_NORMAL);
		sysUserService.updateByPrimaryKeySelective(user);
		SysUserUtils.getSession().setAttribute("msg", "您的账号已激活，请登录！");
		return "login";
	} 
}
