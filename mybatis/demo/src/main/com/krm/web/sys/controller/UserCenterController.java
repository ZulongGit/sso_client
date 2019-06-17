package com.krm.web.sys.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.krm.common.base.BaseController;
import com.krm.common.base.Result;
import com.krm.common.utils.encrypt.PasswordEncoder;
import com.krm.web.sys.model.SysUser;
import com.krm.web.sys.service.SysUserCenterService;
import com.krm.web.sys.service.SysUserService;
import com.krm.web.util.SysUserUtils;

@Controller
@RequestMapping("userCenter")
public class UserCenterController extends BaseController{

	public static final String BASE_URL = "userCenter";
	private static final String BASE_PATH = "sys/userCenter/";
	
	@Override
	protected String getBaseUrl() {
		return BASE_URL;
	}

	@Override
	protected String getBasePath() {
		return BASE_PATH;
	}
	
	@Resource
	private SysUserCenterService sysUserCenterService;
	@Resource
	private SysUserService sysUserService;

	@RequestMapping
	public String viewInfo(Model model) {
		SysUser sysUser = sysUserCenterService.getSysUserInfo();
		model.addAttribute("sysUser", sysUser);
		return BASE_PATH + "userCenter";
	}
	
	/**
	 * 更新用户信息
	 * @param sysUser
s	 */
	@RequestMapping(value = "updateInfo",method = RequestMethod.POST)
	public @ResponseBody Integer updateSysuserInfo(@ModelAttribute SysUser sysUser){
		Integer count = sysUserCenterService.updateSysuserInfo(sysUser);
		if(count>0){
			SysUserUtils.clearCacheUser(SysUserUtils.getCacheLoginUser().getId());
			SysUserUtils.getSession().stop();
		}
		return count;
	}
	
	@RequestMapping("conversation")
	public String conversation(){
		return BASE_PATH + "conversation";
	}

	
	/**
	 * 跳转到修改密码页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="toEditPasswd",method=RequestMethod.POST)
	public String toEditPasswd(@RequestParam Map<String,Object> params, HttpServletRequest request,Model model){
		model.addAttribute("user", SysUserUtils.getSessionLoginUser());
		return BASE_PATH + "user_password_edit";
	}
	
	/**
	 * 检验密码，包括有效期和初始密码
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("checkPwdExpiration")
	@ResponseBody
	public Result checkPwdExpiration(@RequestParam Map<String,Object> params){
		/*params.put("id", SysUserUtils.getSessionLoginUser().getId());
		Map<String, Object> pwd = ptBasUserService.selectUserPwd(params);
		String updateTime = null;
		//有修改日期，则判断密码有效期
		if(pwd.containsKey("updateTime")){
			try {
				Integer userPwdExpiration = Integer.parseInt(PropertiesUtils.getValue(SysContants.USER_PASSWORD_EXPIRATION));
				if(userPwdExpiration != 0){
					updateTime = pwd.get("updateTime").toString();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String now = sdf.format(new Date());
					
					Calendar c1 = Calendar.getInstance();
			        Calendar c2 = Calendar.getInstance();
			        c1.setTime(sdf.parse(updateTime));
			        c2.setTime(sdf.parse(now));

					int inteval = (c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR))*12 + c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);
					if(inteval >= userPwdExpiration){
						return new Result(2, "您的密码已过期，请修改密码！", null);
					}
					
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		//没有修改日期，则是初始密码
		}else{
			return new Result(3, "您的密码是管理员设置的初始密码，请修改密码！", null);
		}*/
		return Result.successResult();
	}
	
	/**
	 * 验证密码是否正确
	 * @param params
	 * @return
	 */
	@RequestMapping("valiPwd")
	@ResponseBody
	public Map<String, String> valiPwd(@RequestParam Map<String,Object> params) {
		logger.info("开始校验用户密码");
		//用户输入的密码
		String password = params.get("param").toString();
		password = PasswordEncoder.encrypt(password, SysUserUtils.getSessionLoginUser().getUsername());
		SysUser sysUser = new SysUser();
		SysUser user = null;
		sysUser.setUsername(SysUserUtils.getSessionLoginUser().getUsername());
		List<SysUser> users = sysUserService.select(sysUser);
		if(users != null && users.size() == 1 && users.get(0) != null) {
			user = users.get(0);
		}
		Map<String, String> map = new HashMap<String, String>();
		if (password.equals(user.getPassword())) {
			map.put("info", "您输入的密码正确");
			map.put("status", "y");
		} else {
			map.put("info", "您输入的密码不正确!");
			map.put("status", "n");
		}
		logger.info("校验用户密码结束");
		return map;  	
	}
	
	/**
	 * 验证密码是否正确
	 * @param params
	 * @return
	 */
	@RequestMapping("valiNewPwd")
	@ResponseBody
	public Map<String, String> valiNewPwd(@RequestParam Map<String,Object> params) {
		logger.info("开始校验用户密码");
		Map<String, String> map = new HashMap<String, String>();
		String password = params.get("param").toString();
		//8到32位，且至少由字母、数字和特殊字符其中两种进行组合
		String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)(?![!@#$%^&*\\-_]+$)[0-9a-zA-Z!@#$%^&*\\-_]{8,32}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(password);
		
		//是否包含不在特定范围内的特殊字符
		String regex1 = "^((?=[0-9a-zA-Z!@#$%^&*\\-_]).){1,}$";
		Pattern pattern1 = Pattern.compile(regex1);
		Matcher matcher1 = pattern1.matcher(password);
		password = PasswordEncoder.encrypt(password, SysUserUtils.getSessionLoginUser().getUsername());
		SysUser sysUser = new SysUser();
		SysUser user = null;
		sysUser.setUsername(SysUserUtils.getSessionLoginUser().getUsername());
		List<SysUser> users = sysUserService.select(sysUser);
		if(users != null && users.size() == 1 && users.get(0) != null) {
			user = users.get(0);
		}
		if (password.equals(user.getPassword())) {
			map.put("info", "新密码与旧密码不能相同");
			map.put("status", "n");
		}else{
			if(matcher.matches()){
				map.put("info", "新密码可用!");
				map.put("status", "y");
			}else if(!matcher1.matches()){
				map.put("info", "您的密码含有非法字符！");
				map.put("status", "n");
			}else{
				map.put("info", "您的密码太过简单<br>至少由字母、数字和特殊字符其中两种进行组合");
				map.put("status", "n");
			}
		}
		logger.info("校验用户密码结束");
		return map;  	
	}
	
	/**
	 * 修改用户密码
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "updatePwd",method = RequestMethod.POST)
	@ResponseBody
	public Result updatePwd(@RequestParam Map<String,Object> params) {
		logger.info("开始修改用户密码");
		String password = params.get("password").toString();
		String passwordConfirm = params.get("passwordConfirm").toString();
		if(password.trim().equals(passwordConfirm.trim())){
			SysUser user = new SysUser();
			user.setPassword(password);
			Integer count = sysUserCenterService.updateSysuserInfo(user);
			if(count > 0){
				return Result.successResult();
			}
		}
		logger.info("修改用户密码结束");
		return Result.errorResult();  	
	}

}
