package com.krm.web.util;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.primitives.Ints;
import com.krm.common.base.CommonEntity;
import com.krm.common.beetl.utils.BeetlUtils;
import com.krm.common.constant.Constant;
import com.krm.common.spring.utils.SpringContextHolder;
import com.krm.common.utils.CacheUtils;
import com.krm.common.utils.TreeUtils;
import com.krm.web.sys.model.SysDict;
import com.krm.web.sys.model.SysMenu;
import com.krm.web.sys.model.SysOrgan;
import com.krm.web.sys.model.SysRole;
import com.krm.web.sys.model.SysUser;
import com.krm.web.sys.security.UsernamePasswordToken;
import com.krm.web.sys.service.SysDictService;
import com.krm.web.sys.service.SysMenuCategoryService;
import com.krm.web.sys.service.SysMenuService;
import com.krm.web.sys.service.SysOrganService;
import com.krm.web.sys.service.SysRoleService;
import com.krm.web.sys.service.SysUserService;

/**
 * 
 * @author Parker
 *
 */
public class SysUserUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(SysUserUtils.class);

	static SysMenuService sysMenuService = SpringContextHolder.getBean("sysMenuService");
	static SysUserService sysUserService = SpringContextHolder.getBean("sysUserService");
	static SysRoleService sysRoleService = SpringContextHolder.getBean("sysRoleService");
	static SysOrganService sysOrganService = SpringContextHolder.getBean("sysOrganService");
	static SysDictService sysDictService = SpringContextHolder.getBean("sysDictService");
	static SysMenuCategoryService sysMenuCategoryService = SpringContextHolder.getBean("sysMenuCategoryService");
	static DefaultWebSessionManager  sessionManager = SpringContextHolder.getBean("sessionManager");
	/**
	 * 设置用户的认证
	 */
	public static void setUserAuth() {
		//菜单树
		getUserMenus();
		//角色列表
		getUserRoles();
		//用户机构列表
		getUserOrgan();
		//用户持有的数据范围
		getUserDataScope();
	}
	
	/**
	 * 登录用户持有的资源
	* @return
	 */
	public static Map<String, SysMenu> getUserResources(){
		SysUser sysUser = getCacheLoginUser();
		Map<String, SysMenu> userMenus = CacheUtils.get(
				Constant.CACHE_SYS_MENU, Constant.CACHE_USER_MENU
						+ sysUser.getId());
		if (userMenus == null) {
			if (sysUser.isAdmin()) {
				userMenus = BeetlUtils.getBeetlSharedVars(Constant.CACHE_ALL_MENU);
			} else {
				List<SysMenu> userRes = sysMenuService.findUserMenuByUserId(sysUser);
				userMenus = new LinkedHashMap<String, SysMenu>();
				for(SysMenu res : userRes){
					if(StringUtils.isBlank(res.getUrl())){
						userMenus.put(res.getId().toString(), res);
					}else{
						userMenus.put(res.getUrl(), res);
					}
				}
			}
			CacheUtils.put(Constant.CACHE_SYS_MENU,
					Constant.CACHE_USER_MENU + sysUser.getId(),
					userMenus);
		}
		return userMenus;
	}
	
	/**
	 * 登录用户菜单
	 */
	public static List<CommonEntity> getUserMenus(){
		SysUser sysUser = getCacheLoginUser();
		List<CommonEntity> userMenus = null;
//		List<CommonEntity> userMenus = CacheUtils.get(
//				Constant.CACHE_SYS_MENU,
//				Constant.CACHE_USER_MENU_TREE + sysUser.getId());
		if (userMenus == null || userMenus.size() == 0) {
			Map<String, SysMenu> userResources = getUserResources();
			List<SysMenu> menus = Lists.newArrayList();
//			List<CommonEntity> list = sysMenuCategoryService.commonList(params);
			for(SysMenu res : userResources.values()){
				if(Constant.MENU_TYPE.equals(res.getType())){
					menus.add(res);
				}
			}
			userMenus = TreeUtils.toTreeNodeList(menus,SysMenu.class);
			CacheUtils.put(Constant.CACHE_SYS_MENU,
					Constant.CACHE_USER_MENU_TREE + sysUser.getId(), userMenus);
		}
		return userMenus;
	}
	
	/**
	 * 登录用户的角色
	 */
	public static List<SysRole> getUserRoles(){
		SysUser sysUser = getCacheLoginUser();
		List<SysRole> userRoles = CacheUtils.get(
				Constant.CACHE_SYS_ROLE,
				Constant.CACHE_USER_ROLE + sysUser.getId());
		if(userRoles == null){
			if(sysUser.isAdmin()){
				userRoles = sysRoleService.select(new SysRole());
			}else{
				userRoles = sysRoleService.findUserRoleListByUserId(sysUser.getId());
			}
			CacheUtils.put(Constant.CACHE_SYS_ROLE,
						Constant.CACHE_USER_ROLE + sysUser.getId(), userRoles);
		}
		return userRoles;
	}
	

	/**
	 * 登录用户的角色map形式
	 */
	public static Map<String, SysRole> getUserRolesMap(){
		List<SysRole> list = SysUserUtils.getUserRoles();
		Map<String, SysRole> userRolesMap = Maps.uniqueIndex(list, new Function<SysRole, String>() {
			@Override
			public String apply(SysRole sysRole) {
				return sysRole.getId();
			}
		});
		return userRolesMap;
	}
	
	
	/**
	 * 登录用户的机构
	* @return
	 */ 
	public static List<SysOrgan> getUserOrgan(){
		SysUser sysUser = getCacheLoginUser();
		List<SysOrgan> userOrgans = CacheUtils.get(
				Constant.CACHE_SYS_ORGAN,
				Constant.CACHE_USER_ORGAN + sysUser.getId());
		if(userOrgans == null){
			SysOrgan organ = new SysOrgan();
			if(sysUser.isAdmin()){
				userOrgans = sysOrganService.select(organ);
			}else{
				organ.setUserDataScope(SysUserUtils.dataScopeFilterString(null, null));
				userOrgans = sysOrganService.findEntityListByDataScope(organ);
			}
			CacheUtils.put(Constant.CACHE_SYS_ORGAN,
					Constant.CACHE_USER_ORGAN + sysUser.getId(), userOrgans);
		}
		return userOrgans;
	}
	
	
	/**
	 * 获取字典
	* @return
	 */ 
	public static List<SysDict> getDictList(){
		List<SysDict> sysDicts = CacheUtils.get(Constant.CACHE_SYS_DICT,Constant.CACHE_SYS_DICT);
		if(sysDicts == null){
			SysDict dict = new SysDict();
			sysDicts = sysDictService.select(dict);
			CacheUtils.put(Constant.CACHE_SYS_DICT,
					Constant.CACHE_SYS_DICT,sysDicts);
		}
		return sysDicts;
	}
	
	/**
	 * 用户持有的数据范围,包含数据范围小的
	 */
	public static List<String> getUserDataScope(){
		SysUser sysUser = getCacheLoginUser();
		List<String> dataScope = CacheUtils.get(Constant.CACHE_SYS_ORGAN, 
				Constant.CACHE_USER_DATASCOPE+sysUser.getId());
		if(dataScope == null){
			dataScope = Lists.newArrayList();
			if(!sysUser.isAdmin()){
				List<Integer> dc = Lists.transform(getUserRoles(), new Function<SysRole, Integer>() {
					@Override
					public Integer apply(SysRole sysRole) {
						return Integer.parseInt(sysRole.getDataScope());
					}
				});
				int[] dataScopes = Ints.toArray(dc);
				if(dataScopes.length == 0) return dataScope;
				int min = Ints.min(dataScopes);
				for(int i = min,len = Integer.parseInt(Constant.DATA_SCOPE_CUSTOM);i<=len;i++){
					dataScope.add(i+"");
				}
			}else{
				dataScope = Constant.DATA_SCOPE_ADMIN;
			}
			CacheUtils.put(Constant.CACHE_SYS_ORGAN, 
				Constant.CACHE_USER_DATASCOPE+sysUser.getId(), dataScope);
		}
		return dataScope;
	}
	
	//针对按明细设置自动赋权机构,仅且只有按明细设置的角色
	public static String autoAddOrganToRole(){
		List<String> userScope = getUserDataScope();
		int count = 0 ;
		for(String s : userScope){
			if(StringUtils.equals(s, Constant.DATA_SCOPE_CUSTOM)){
				count++;
			}
		}
		return count == userScope.size()?getUserRoles().get(0).getId():null;
	} 
	
	/**
	 * 数据范围过滤
	 * @param user 当前用户对象
	 * @param organAlias 机构表别名
	 * @param userAlias 用户表别名，传递空，忽略此参数
	 * @param field field[0] 用户表id字段名称 为了减少中间表连接
	 * @return (so.organ id=... or .. or)
	 */
	public static String dataScopeFilterString(String organAlias, String userAlias,String... field){
		SysUser sysUser = getCacheLoginUser();
		if(StringUtils.isBlank(organAlias)) organAlias = "sys_organ";
		//用户持有的角色
		List<SysRole> userRoles = getUserRoles();
		//临时sql保存
		StringBuilder tempSql = new StringBuilder();
		//最终生成的sql
		String dataScopeSql = "";
		if(!sysUser.isAdmin()){
			for(SysRole sr : userRoles){
				if(StringUtils.isNotBlank(organAlias)){
					boolean isDataScopeAll = false;
					if (Constant.DATA_SCOPE_ALL.equals(sr.getDataScope())){
						isDataScopeAll = true;
					}
					else if (Constant.DATA_SCOPE_ORGAN_AND_CHILD.equals(sr.getDataScope())){
						//so.code=1 or so.parentIds like '0,1,%'
						tempSql.append(" or "+organAlias+".code = '"+sysUser.getOrganId() + "'");
						SysOrgan sysOrgan = sysOrganService.getOrganByCode(sysUser.getOrganId());
						tempSql.append(" or "+organAlias+".parent_ids like '"+sysOrgan.getParentIds()+sysOrgan.getCode()+",%'");
					}
					else if (Constant.DATA_SCOPE_ORGAN.equals(sr.getDataScope())){
						//or so.code=1 or (so.parent_id=1 and so.type=2)
						tempSql.append(" or "+organAlias+".code = '"+sysUser.getOrganId() + "'");
						tempSql.append(" or ("+organAlias+".parent_id = '"+sysUser.getOrganId() + "'");
						tempSql.append(" and "+organAlias+".type=2)");
					}
					else if (Constant.DATA_SCOPE_DEPT_AND_CHILD.equals(sr.getDataScope())){
						//or so.code=5 or so.parentIds like '0,1,5,%'
						tempSql.append(" or "+organAlias+".code = "+sysUser.getDeptId() + "'");
						SysOrgan sysOrgan = sysOrganService.getOrganByCode(sysUser.getDeptId());
						tempSql.append(" or "+organAlias+".parent_ids like '"+sysOrgan.getParentIds()+sysOrgan.getCode()+",%'");
					}
					else if (Constant.DATA_SCOPE_DEPT.equals(sr.getDataScope())){
						//or so.code=5
						tempSql.append(" or "+organAlias+".code = '"+sysUser.getDeptId() + "'");
					}
					else if (Constant.DATA_SCOPE_CUSTOM.equals(sr.getDataScope())){
						//or so.code in (1,2,3,4,5)
						List<String> organs = sysOrganService.findUserDataScopeByUserId(sysUser.getId());
						if(organs.size() == 0) organs.add("-1");
						tempSql.append(" or "+organAlias+".code in ("+StringUtils.join(organs, ",")+")");
					}
					if (!isDataScopeAll){
						if (StringUtils.isNotBlank(userAlias)){
							// or su.id=22
							if(field==null || field.length==0) field[0] = "id";
							tempSql.append(" or "+userAlias+"."+field[0]+" = '"+sysUser.getId() + "'");
						}else {
							tempSql.append(" or "+organAlias+".code is null");
						}
					}else{
						// 如果包含全部权限，则去掉之前添加的所有条件，并跳出循环。
						tempSql.delete(0, tempSql.length());
						break;
					}
				}
			}// for end
			
			if(StringUtils.isNotBlank(tempSql)){
				dataScopeSql = "("+tempSql.substring(tempSql.indexOf("or")+2, tempSql.length())+")";
			}
		}
		return dataScopeSql;
	}
	/**
	 * 数据范围过滤
	 * @param user 当前用户对象
	 * @param organAlias 机构表别名
	 * @param userAlias 用户表别名，传递空，忽略此参数
	 * @param field field[0] 用户表id字段名称 为了减少中间表连接
	 * @return (so.organ code=... or .. or)
	 */
	public static String dataScopeFilterString1(String organAlias, String userAlias, String menuUrl, String... field){
		SysUser sysUser = getCacheLoginUser();
		Map<String, SysMenu> map = getUserResources();
		//获取当前点击的菜单
		SysMenu resource = map.get(menuUrl);
//		if(StringUtils.isBlank(organAlias)) organAlias = "sys_organ";
		//用户持有的角色
		List<SysRole> userRoles = getUserRoles();
		//临时sql保存
		StringBuilder tempSql = new StringBuilder();
		//最终生成的sql
		String dataScopeSql = "";
		if(!sysUser.isAdmin()){
			for(SysRole sr : userRoles){
				//通过角色获取菜单id
				List<String> resourceIds = sysRoleService.findMenuIdsByRoleId(sr.getId());
				if(resourceIds.contains(resource.getId())){
					boolean isDataScopeAll = false;
					//所有数据范围
					if (Constant.DATA_SCOPE_ALL.equals(sr.getDataScope())){
						isDataScopeAll = true;
					}
					if(StringUtils.isNotBlank(organAlias)){
						//所在机构及下级机构
						if (Constant.DATA_SCOPE_ORGAN_AND_CHILD.equals(sr.getDataScope())){
							//so.code=1 or so.parentIds like '0,1,%'
							tempSql.append(" or "+organAlias+".code = '"+sysUser.getOrganId() + "'");
							SysOrgan sysOrgan = sysOrganService.getOrganByCode(sysUser.getOrganId());
							tempSql.append(" or "+organAlias+".parent_ids like '"+sysOrgan.getParentIds()+sysOrgan.getCode()+",%'");
						}
						//所在机构
						else if (Constant.DATA_SCOPE_ORGAN.equals(sr.getDataScope())){
							//or so.code=1 or (so.parent_id=1 and so.type=2)
							tempSql.append(" or "+organAlias+".code = '"+sysUser.getOrganId() + "'");
							tempSql.append(" or ("+organAlias+".parent_id = '"+sysUser.getOrganId() + "'");
							tempSql.append(" and "+organAlias+".type=2)");
						}
						//所在部门及下级
						else if (Constant.DATA_SCOPE_DEPT_AND_CHILD.equals(sr.getDataScope())){
							//or so.code=5 or so.parentIds like '0,1,5,%'
							tempSql.append(" or "+organAlias+".code = '"+sysUser.getDeptId() + "'");
							SysOrgan sysOrgan = sysOrganService.getOrganByCode(sysUser.getDeptId());
							tempSql.append(" or "+organAlias+".parent_ids like '"+sysOrgan.getParentIds()+sysOrgan.getCode()+",%'");
						}
						//所在部门
						else if (Constant.DATA_SCOPE_DEPT.equals(sr.getDataScope())){
							//or so.code=5
							tempSql.append(" or "+organAlias+".code = '"+sysUser.getDeptId() + "'");
						}
						//明细
						else if (Constant.DATA_SCOPE_CUSTOM.equals(sr.getDataScope())){
							//or so.code in (1,2,3,4,5)
							List<String> organs = sysOrganService.findUserDataScopeByUserId(sysUser.getId());
							if(organs.size() == 0) organs.add("-1");
							tempSql.append(" or "+organAlias+".code in ("+StringUtils.join(organs, ",")+")");
							if (StringUtils.isNotBlank(userAlias)){
								// or su.code=22
								if(field==null || field.length==0) field[0] = "id";
								tempSql.append(" or "+userAlias+"."+field[0]+" = '"+sysUser.getId() + "'");
							}
						}
//						if (!isDataScopeAll){
//						}else{
//							// 如果包含全部权限，则去掉之前添加的所有条件，并跳出循环。
//							tempSql.delete(0, tempSql.length());
//							break;
//						}
					}
					//没有配置机构关联字段，且个人数据权限时，进入
					if(StringUtils.isNotBlank(userAlias)){
						if (Constant.DATA_SCOPE_SELF.equals(sr.getDataScope())){
							if(field==null || field.length==0) field[0] = "id";
							tempSql.append(" "+userAlias+"."+field[0]+" = '"+sysUser.getId() + "'");
						}
					}
					if(StringUtils.isNotBlank(tempSql)){
						dataScopeSql = "("+tempSql.substring(tempSql.indexOf("or")+2, tempSql.length())+")";
					}
				}// for end
			}
		}
		return dataScopeSql;
	}
	
	/**
	 * 清除缓存中用户认证
	 */
	public static void clearAllCachedAuthorizationInfo(List<String> userIds) {
		if(CollectionUtils.isNotEmpty(userIds)){
			for (String userId : userIds) {
				boolean evictRes = CacheUtils.remove(Constant.CACHE_SYS_MENU,
						Constant.CACHE_USER_MENU + userId);
				
				boolean evictMenu = CacheUtils.remove(Constant.CACHE_SYS_MENU,
						Constant.CACHE_USER_MENU_TREE + userId);
				
				boolean evictRole = CacheUtils.remove(Constant.CACHE_SYS_ROLE,
						Constant.CACHE_USER_ROLE + userId);
				
				boolean evictOrgan = CacheUtils.remove(Constant.CACHE_SYS_ORGAN,
						Constant.CACHE_USER_ORGAN + userId);
				
				boolean evictScope = CacheUtils.remove(Constant.CACHE_SYS_ORGAN, 
						Constant.CACHE_USER_DATASCOPE+userId);
				
				if(evictRes&&evictMenu&&evictRole&&evictOrgan&&evictScope){
					logger.debug("用户"+userId+"的菜单、资源、角色、机构、数据范围缓存全部删除");
				}
			}
		}
	}
	
	public static void clearCacheMenu(){
		CacheUtils.clear(Constant.CACHE_SYS_MENU);
	}
	
	/**
	 * 清除缓存中的用户
	 */
	public static void clearCacheUser(String userId){
		CacheUtils.evict(Constant.CACHE_SYS_USER,userId.toString());
	}
	
	/**
	 * 清除用户机构
	 */
	public static void clearCacheOrgan(List<String> userIds){
		for (String userId : userIds) {
			CacheUtils.evict(Constant.CACHE_SYS_ORGAN,
					Constant.CACHE_USER_ORGAN + userId);
		}
	}
	
	/**
	 * 缓存登录用户,默认设置过期时间为20分钟,与session存活时间的同步
	 */
//	public static void cacheLoginUser(SysUser sysUser){
//		CacheUtils.put(Constant.CACHE_SYS_USER, sysUser.getId().toString(), 
//				sysUser,new Long(getSession().getTimeout()).intValue());
//	}
	
	/**
	 * 从缓存中取登录的用户
	 */
	public static SysUser getCacheLoginUser(){
//		try {
//			if (getSessionLoginUser() != null) {
//				return CacheUtils.get(Constant.CACHE_SYS_USER,
//						getSessionLoginUser().getId().toString());
//			}
//		} catch (Exception e) {
//		}
		return getSessionLoginUser();
	}

	/**
	 * 得到当前session
	 */
	public static Session getSession() {
		Session session = SecurityUtils.getSubject().getSession();
		return session;
	}
	
	/**
	 * 得到当前session
	 */
	public static HttpSession getHttpSession() {
		HttpSession session = getCurRequest().getSession();
		return session;
	}
	
	/**
	 * session中的用户
	 */
	public static SysUser getSessionLoginUser(){
		return (SysUser) getSession().getAttribute(Constant.SESSION_LOGIN_USER);
	}
	
	/**
	 * @Title: getCurRequest
	 * @Description:(获得当前的request) 
	 * @param:@return 
	 * @return:HttpServletRequest
	 */
	public static HttpServletRequest getCurRequest(){
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		if(requestAttributes != null && requestAttributes instanceof ServletRequestAttributes){
			ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)requestAttributes;
			return servletRequestAttributes.getRequest();
		}
		return null;
	}
	
	
	public static  boolean  setUserSession(String username,HttpServletRequest request){
	    
	    SysUser  sysuser=(SysUser)request.getSession().getAttribute(Constant.SESSION_LOGIN_USER);
	    if(null==sysuser){
	        sysuser=new SysUser();
	        sysuser.setUsername(username);
	       List<SysUser> listuser =sysUserService.select(sysuser);
	       if(listuser.size()>0){
	           sysuser=listuser.get(0);
	           UsernamePasswordToken token = new UsernamePasswordToken(username, sysuser.getPassword().toCharArray(), true, null, ""); 
	           token.setRememberMe(true);  
	           Subject subject = SecurityUtils.getSubject();  
//	           Session session=subject.getSession();
//	         long out_time=   System.currentTimeMillis()  -  subject.getSession().getLastAccessTime().getTime();
//	           if(out_time>=session.getTimeout()){
//	        	   ThreadContext.remove(ThreadContext.SUBJECT_KEY);//移除线程里面的subject
//	                sessionDAO.delete(subject.getSession());//移除失效的session 
//	                subject = new Subject.Builder().buildSubject(); 
//	           }
	           Session session = subject.getSession(false);
	           if(session != null){
	        	   System.out.println("准备清楚本次的session信息====="+session.getId());
	        	SessionDAO sessiond=   sessionManager.getSessionDAO();
	        	sessiond.delete(session);
	        	
	           }
	        //   subject.login(token); 
	           request.getSession().setAttribute(Constant.SESSION_LOGIN_USER, sysuser);
	           //setUserAuth();
	       }else{
	           return false;
	       }
	    }
	    
	    return true;

	}
	
	
	
	
	

}
