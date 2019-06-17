package com.krm.web.sys.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.krm.common.base.BaseController;
import com.krm.common.base.CommonEntity;
import com.krm.common.base.Result;
import com.krm.common.constant.Constant;
import com.krm.common.utils.StringUtil;
import com.krm.web.sys.model.SysRole;
import com.krm.web.sys.model.SysUser;
import com.krm.web.sys.service.SysRoleService;
import com.krm.web.sys.service.SysUserService;

@Controller
@RequestMapping("sysuser")
public class SysUserController extends BaseController{

	public static final String BASE_URL = "sysuser";
	private static final String BASE_PATH = "sys/user/";

	@Override
	protected String getBaseUrl() {
		return BASE_URL;
	}

	@Override
	protected String getBasePath() {
		return BASE_PATH;
	}

	@Override
	protected String getBasePermission() {
		return "sysuser";
	}
	
	@Resource
	private SysUserService sysUserService;
	@Resource
	private SysRoleService sysRoleService;
	
	/**
	 * 跳转到用户管理
	* @return
	 */
	@RequestMapping
	public String toSysUser(Model model){
		logger.info("跳转到用户管理页面");
		checkPermission("query");
		return BASE_PATH + "user";
	}
	
	
	/**
	 * 跳转到在线用户管理
	 * @return
	 */
	@RequestMapping(value = "online")
	public String toUserOnline(Model model){
		logger.info("跳转到在线用户管理页面");
		checkPermission("query");
		return BASE_PATH + "user-online";
	}
	
	
	/**
	 * 保存用户
	* @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public @ResponseBody Result save(@ModelAttribute SysUser sysUser,HttpServletRequest request){
		logger.info("开始保存/更新用户");
		if(StringUtil.isEmpty(sysUser.getId())){
			checkPermission("add");
		}else{
			checkPermission("update");
		}
		int count = sysUserService.saveSysUser(sysUser);
		if(count > 0){
			return Result.successResult();
		}
		return Result.errorResult();
	}
	
	/**
	 * 删除用户
	* @param id 用户id
	* @return
	 */
	@RequestMapping(value="delete",method = RequestMethod.POST)
	public @ResponseBody Result del(String id){
		logger.info("开始删除用户");
		checkPermission("delete");
		int count = sysUserService.deleteUser(id);
		if(count == -1){
			return new Result(0, "不能删除管理员用户！");
		}else if(count == -2){
			return new Result(0, "不能删除自己！");
		}else if(count > 0){
			return Result.successResult();
		}
		return Result.errorResult();
	}
	
	/**
	 * 用户列表
	* @param params
	* @param model
	* @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.POST)
	@ResponseBody
	public PageInfo<CommonEntity> list(@RequestParam Map<String, Object> params,String[] roles,Model model){
		logger.info("进入用户列表页面");
		checkPermission("query");
		if(roles != null){
			String str = "";
			for (int i = 0; i < roles.length; i++) {
				str = i == roles.length -1?str + "'"+roles[i]+"'":str + "'"+roles[i]+"',";
			}
			params.put("roles", str);
		}
		PageInfo<CommonEntity> page = sysUserService.findPageInfo(params);
		return page;
	}
	
	/**
	 * 弹窗
	* @param id 用户id
	* @param mode 模式
	* @return
	 */
	@RequestMapping(value="{mode}/showlayer",method=RequestMethod.POST)
	public String showLayer(String id,@PathVariable("mode") String mode, Model model){
		SysUser user = null;
		List<SysRole> roles = null;
		Map<String, SysRole> rolesMap = Maps.newHashMap(),findUserRoleMap = null;
		if(StringUtils.equals("detail", mode)){
			logger.info("跳转到用户详情页面");
			checkPermission("query");
			user = sysUserService.selectByPrimaryKey(id);
			roles = sysRoleService.findUserRoleListByUserId(id);
			model.addAttribute("roles", roles)
				 .addAttribute("user", user);
		}
		if(StringUtils.equals("add", mode)){
			logger.info("跳转到添加用户页面");
			checkPermission("add");
			return BASE_PATH + "user-add";
		}
		if(StringUtils.equals("edit", mode)){
			logger.info("跳转到修改用户页面");
			checkPermission("update");
			user = sysUserService.selectByPrimaryKey(id);
			findUserRoleMap = sysRoleService.findUserRoleMapByUserId(id);
			rolesMap.putAll(sysRoleService.findCurUserRoleMap());
			rolesMap.putAll(findUserRoleMap);
			model.addAttribute("rolesMap", rolesMap)
				.addAttribute("user", user)
				.addAttribute("findUserRoleMap", findUserRoleMap);
			return BASE_PATH + "user-update";
		}
		return BASE_PATH + "user-detail";
	}
	
	/**
	 * 验证用户名是否存在
	* @param param
	* @return
	 */
	@RequestMapping(value="checkname",method=RequestMethod.POST)
	public @ResponseBody boolean checkName(@ModelAttribute SysUser sysUser){
		logger.info("开始验证用户名是否存在");
		SysUser result = null;
		try {
			sysUser.setDelFlag(Constant.DEL_FLAG_NORMAL);
			result = sysUserService.selectOne(sysUser);
		} catch (Exception e) {
			logger.info("此登录名太抢手了,请换一个!");
			return false;
		}
		if(result == null){
			return true;
		}else{
			//id没有值的时候，说明是添加操作,有值则是修改操作，并排除自己
			if(sysUser.getId() != null && !sysUser.getId().equals("undefined") && !sysUser.getId().equals("")){
				String name = result.getUsername();
				if(name.trim().equals(sysUser.getUsername().trim())){
					logger.info("校验通过!");
					return true;
				}
			}
		}
		logger.info("此登录名太抢手了,请换一个!");
		return false;
	}
	
}
