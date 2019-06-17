package com.krm.common.constant;

import java.util.List;

import com.google.common.collect.Lists;

public class Constant {

	// 删除标记（0：正常；1：删除；2：审核；）
	public static final String FIELD_DEL_FLAG = "delFlag";
	public static final String DEL_FLAG_NORMAL = "0";
	public static final String DEL_FLAG_DELETE = "1";
	public static final String DEL_FLAG_AUDIT = "2";

	// 资源
	public static final String MENU_STATUS_NORMAL = "0"; // 正常
	public static final String MENU_STATUS_DISABLE = "1"; // 禁用
	public static final String MENU_TYPE = "0"; // 菜单类型
	public static final String MENU_TYPE_BUTTON = "1"; // 按钮类型
	public static final String MENU_COMMON = "1"; // 公共资源

	// 用户
	public static final String SESSION_LOGIN_USER = "loginUser"; // session中的用户key
	public static final String SUPER_ADMIN = "1"; // 超级管理员

	// 缓存key
	public static final String CACHE_SYS_MENU = "sysMenu_cache"; // 资源的缓存名称
	public static final String CACHE_SYS_ORGAN = "sysOrgan_cache"; //机构的缓存名称
	public static final String CACHE_SYS_ROLE = "sysRole_cache"; //角色的缓存名称
	public static final String CACHE_SYS_USER = "sysUser_cache"; //用户缓存名称
	public static final String CACHE_SYS_DICT = "sysDict_cache"; //字典
	
	public static final String CACHE_ALL_MENU = "allSysMenu"; // 全部资源key
	public static final String CACHE_USER_MENU = "userSysMenu"; // 用户权限
	public static final String CACHE_USER_MENU_TREE = "userMenuTree"; // 用户菜单树
	public static final String CACHE_USER_ROLE = "userRole"; // 用户角色
	public static final String CACHE_USER_DATASCOPE = "userDataScope"; //用户数据范围
	public static final String CACHE_USER_ORGAN = "userOrgan"; //用户机构

	// 数据范围（1：所有数据；2：所在机构及以下数据；3：所在机构数据；4：所在部门及以下数据；5：所在部门数据；8：仅本人数据；9：按明细设置）
	public static final String DATA_SCOPE_ALL = "1";
	public static final String DATA_SCOPE_ORGAN_AND_CHILD = "2";
	public static final String DATA_SCOPE_ORGAN = "3";
	public static final String DATA_SCOPE_DEPT_AND_CHILD = "4";
	public static final String DATA_SCOPE_DEPT = "5";
	public static final String DATA_SCOPE_SELF = "8";
	public static final String DATA_SCOPE_CUSTOM = "9";
	public static final List<String> DATA_SCOPE_ADMIN = Lists.newArrayList("1","2","3","4","5","8","9");
	

	// 显示/隐藏
	public static final String SHOW = "1";
	public static final String HIDE = "0";

	// 是/否
	public static final String YES = "1";
	public static final String NO = "0";
	
	//update or add or delete
	public static final Integer SUCCESS = 1;
	
	public static final String MSG = "msg";
	
	public static final String USER_STATUS_NORMAL = "1";	//正常
	public static final String USER_STATUS_NONACTIVATED = "2";	//未激活
	public static final String USER_STATUS_LOCKED = "3";	//已锁定
	public static final String USER_STATUS_INVALID = "4";	//失效用户

}
