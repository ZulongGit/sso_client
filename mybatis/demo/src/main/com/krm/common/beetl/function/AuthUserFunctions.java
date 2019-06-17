package com.krm.common.beetl.function;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.krm.common.beetl.utils.BeetlUtils;
import com.krm.common.constant.Constant;
import com.krm.web.sys.model.SysMenu;
import com.krm.web.sys.model.SysUser;
import com.krm.web.util.SysUserUtils;

@Component
public class AuthUserFunctions {

	/**
	 * 判断用户是否具有指定权限
	 */
	public boolean hasPermission(String url) {
		Map<String, SysMenu> allRes = BeetlUtils
				.getBeetlSharedVars(Constant.CACHE_ALL_MENU);
		SysMenu sysMenu = allRes.get(url);
		if (sysMenu == null
				|| Constant.MENU_COMMON.equals(sysMenu.getCommon())) {
			return true;
		}

		Map<String, SysMenu> userRes = SysUserUtils.getUserResources();
		if (userRes.containsKey(url)) return true;
		return false;
	}
	
	/**
	 * 登录用户
	* @return
	 */
	public SysUser getLoginUser(){
		return SysUserUtils.getCacheLoginUser();
	}
	
	/**
	 * 是否为超级管理员
	* @return
	 */
	public boolean isSuper(){
		return getLoginUser().getUserType().equals(Constant.SUPER_ADMIN)?true:false;
	}
	
	/**
	 * 是否持有所有数据(数据范围，可认为是总管理)
	 */
	public boolean hasAllDataScope(){
		return SysUserUtils.getUserDataScope().contains(Constant.DATA_SCOPE_ALL);
	}

}
