

package com.krm.web.sys.service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.krm.common.base.ServiceMybatis;
import com.krm.common.beetl.sql.MySQLManager;
import com.krm.common.constant.Constant;
import com.krm.common.utils.BeanUtils;
import com.krm.common.utils.StringUtil;
import com.krm.web.sys.mapper.SysRoleMapper;
import com.krm.web.sys.model.SysMenu;
import com.krm.web.sys.model.SysRole;
import com.krm.web.sys.model.SysRolePermission;
import com.krm.web.sys.model.SysUser;
import com.krm.web.util.SysUserUtils;

/**
 * 
 * @author 
 */

@Service("sysRoleService")
@CacheConfig(cacheNames="sysRole")
public class SysRoleService extends ServiceMybatis<SysRole> {

	@Resource
	private SysRoleMapper sysRoleMapper;
	@Resource
	private MySQLManager mySQLManager;
	
	/**
	 *新增或更新SysRole
	 */
	public int saveSysRole(SysRole sysRole){
		int count = 0;
		if(StringUtil.isEmpty(sysRole.getId())){
			String id = generateId();
			sysRole.setId(id);
			BeanUtils.setProperty(sysRole, "createBy", SysUserUtils.getCacheLoginUser().getId(), false);
			BeanUtils.setProperty(sysRole, "createDate", new Date(), false);
			BeanUtils.setProperty(sysRole, "delFlag", Constant.DEL_FLAG_NORMAL, false);
			logger.info("添加角色");
			count = sysRoleMapper.saveRole(sysRole);
		}else{
			logger.info("根据角色删除角色-菜单配置信息");
			sysRoleMapper.deleteRoleMenuByRoleId(sysRole.getId());
			logger.info("根据角色删除角色-机构配置信息");
			sysRoleMapper.deleteRoleOrganByRoleId(sysRole.getId());
			count = this.updateByPrimaryKeySelective(sysRole);
			//清除缓存
			logger.info("根据角色获取分配的用户");
			List<String> userIds = sysRoleMapper.findUserIdsByRoleId(sysRole.getId());
			logger.info("清除缓存");
			SysUserUtils.clearAllCachedAuthorizationInfo(userIds);
		}
		if(sysRole.getMenuIds().length>0){
			logger.info("添加角色-菜单配置信息");
			sysRoleMapper.insertRoleMenu(sysRole);
		}
		if(sysRole.getOrganIds().length>0 && ("9").equals(sysRole.getDataScope())){		
			logger.info("添加角色-机构配置信息");
			sysRoleMapper.insertRoleOrgan(sysRole);
		}
	    return count;
	}
	
	/**
	 * 删除角色
	* @param id
	 */
	public int deleteSysRole(String id){
		logger.info("根据角色获取分配的用户");
		List<String> userIds = sysRoleMapper.findUserIdsByRoleId(id);
		logger.info("根据角色删除角色-用户配置信息");
		sysRoleMapper.deleteUserRoleByRoleId(id);
		logger.info("根据角色删除角色-机构配置信息");
		sysRoleMapper.deleteRoleOrganByRoleId(id);
		logger.info("根据角色删除角色-菜单配置信息");
		sysRoleMapper.deleteRoleMenuByRoleId(id);
		logger.info("根据角色删除角色-操作权限配置信息");
		Map<String, Object> params = Maps.newHashMap();
		params.put("roleId", id);
		sysRoleMapper.deleteRolePermissionByRoleId(params);
		int count = this.deleteByPrimaryKey(id);
		//清除缓存
		logger.info("清除缓存");
		SysUserUtils.clearAllCachedAuthorizationInfo(userIds);
		return count;
	}
	
	/**
	 * 添加角色绑定的人员
	* @param sysRole
	* @return
	 */
	public int saveUserRole(SysRole sysRole){
		//旧的绑定的人员id
		logger.info("根据角色获取分配的用户");
		List<String> userIds = sysRoleMapper.findUserIdsByRoleId(sysRole.getId());
		//当前的要绑定的人员id
		List<String> curUserIds = Lists.newArrayList(sysRole.getUserIds());
		userIds.addAll(curUserIds);
		ImmutableList<String> mergeList = ImmutableSet.copyOf(userIds).asList();
		logger.info("根据角色删除角色-用户配置信息");
		sysRoleMapper.deleteUserRoleByRoleId(sysRole.getId());
		if(sysRole.getUserIds().length>0) {
			logger.info("添加角色-用户配置信息");
			sysRoleMapper.insertUserRoleByRoleId(sysRole);
		}
		//清除缓存
		logger.info("清除缓存");
		SysUserUtils.clearAllCachedAuthorizationInfo(mergeList);
		return 1;
	}
	
	
	/**
	 * 根据条件分页查询SysRole列表
	 * @param {"pageNum":"页码","pageSize":"条数","isCount":"是否生成count sql",......}
	 */
	public PageInfo<SysRole> findPageInfo(Map<String,Object> params) {
		logger.info("分页查询角色");
		params.put(Constant.CACHE_USER_DATASCOPE, SysUserUtils.dataScopeFilterString("so", "sur","user_id"));
        PageHelper.startPage(params);
        List<SysRole> list = sysRoleMapper.findPageInfo(params); 
        return new PageInfo<SysRole>(list);
	}
	
	/**
	 * 根据角色id查询拥有的资源id集合
	* @param roleId
	* @return
	 */
	public List<String> findMenuIdsByRoleId(String roleId){
		logger.info("根据角色获取菜单ID");
		return sysRoleMapper.findMenuIdsByRoleId(roleId);
	}
	
	/**
	 * 根据角色id查询拥有的机构id集合
	* @param roleId
	* @return
	 */
	public List<String> findOrganIdsByRoleId(String roleId){
		logger.info("根据角色获取机构号");
		return sysRoleMapper.findOrganIdsByRoleId(roleId);
	}
	
	/**
	 * 根据角色id查询拥有的资源 
	* @param roleId
	* @return
	 */
	public List<SysMenu> findMenuByRoleId(String roleId){
		logger.info("根据角色获取菜单");
		return sysRoleMapper.findMenuByRoleId(roleId);
	}
	
	/**
	 * 根据角色id查询拥有此角色的用户
	* @param roleId
	* @return
	 */
	public List<SysUser> findUserByRoleId(String roleId){
		logger.info("根据角色获取用户");
		return sysRoleMapper.findUserByRoleId(roleId);
	}
	
	/**
	 * 当前登录用户的可见的角色
	 */
	public List<SysRole> findCurUserRoleList(){
		logger.info("查询当前登录用户的可见的角色");
		Map<String, Object> params = Maps.newHashMap();
		params.put(Constant.CACHE_USER_DATASCOPE, SysUserUtils.dataScopeFilterString("so", "sur","user_id"));
		List<SysRole> list = sysRoleMapper.findPageInfo(params);
		return list;
	}
	
	/**
	 * 当前登录用户的可见的角色map形式 
	 */
	public Map<String, SysRole> findCurUserRoleMap(){
		logger.info("当前登录用户的可见的角色map形式 ");
		List<SysRole> list = this.findCurUserRoleList();
		Map<String, SysRole> map = Maps.uniqueIndex(list, new Function<SysRole, String>() {
			@Override
			public String apply(SysRole sysRole) {
				return sysRole.getId();
			}
		});
		return map;
	}
	
	
	/**
	 * 用户的角色List列表
	* @param userId
	* @return userRoleList
	 */
	public List<SysRole> findUserRoleListByUserId(String userId){
		logger.info("根据用户id查询角色列表");
		List<SysRole> userRoles = sysRoleMapper.findUserRoleListByUserId(userId);
		return userRoles;
	}
	
	/**
	 * 用户的角色Map
	* @param userId
	* @return userRoleMap
	 */
	public Map<String, SysRole> findUserRoleMapByUserId(String userId){
		logger.info("根据用户id查询角色列表，map形式");
		List<SysRole> roleList = this.findUserRoleListByUserId(userId);
		Map<String, SysRole> userRoleMap = Maps.uniqueIndex(roleList, new Function<SysRole, String>() {
			@Override
			public String apply(SysRole sysRole) {
				return sysRole.getId();
			}
		});
		return userRoleMap;
	}
	
	/**
	 * 删除角色-操作权限配置信息
	 * @param params
	 * @return
	 */
	public int deleteRolePermissionByRoleId(Map<String, Object> params){
		logger.info("根据角色ID删除角色-操作权限配置信息");
		return sysRoleMapper.deleteRolePermissionByRoleId(params);
	}
	
	/**
	 * 查询角色-操作权限配置信息
	 * @param params
	 * @return
	 */
	public List<SysRolePermission> queryRolePermissions(Map<String, Object> params){
		logger.info("根据角色ID查询角色-操作权限配置信息");
		return sysRoleMapper.queryRolePermissions(params);
	}
	
	/**
	 * 保存角色-操作权限配置信息
	 * @param list
	 * @return
	 */
	public int saveRolePermission(List<SysRolePermission> list){
		logger.info("根据角色ID保存角色-操作权限配置信息");
		int count = sysRoleMapper.insertRolePermission(list);
		return count;
	}
	
	public int updateRolePermission(List<SysRolePermission> list){
		int count = mySQLManager.updateByIdBatch(list).length;
		return count;
	}
	
	/**
	 * 获取当前用户拥有的所有的操作权限
	 * @return
	 */
	public Set<String> getUserPermissions(){
		Set<String> permissions = new HashSet<String>();
		List<SysRolePermission> roleMenus = Lists.newArrayList();
		List<SysRole> roles = SysUserUtils.getUserRoles();
		for (SysRole sysRole : roles) {
			Map<String, Object> params = Maps.newHashMap();
			params.put("roleId", sysRole.getId());
			logger.info("根据角色ID查询角色-操作权限配置信息");
			roleMenus.addAll(sysRoleMapper.queryRolePermissions(params));
		}
		if(roleMenus.size() != 0){
			for (SysRolePermission roleMenu : roleMenus) {
				if(roleMenu.getBasePermission() == null || roleMenu.getBasePermission().equals("") || roleMenu.getBasePermission().equals("undefined")){
					continue;
				}
				String basePermission = roleMenu.getBasePermission().replaceAll("/", ":") + ":";
				if(roleMenu.getCanAdd() != null && roleMenu.getCanAdd().equals("1")){
					permissions.add(basePermission + "add");
				}
				if(roleMenu.getCanDelete() != null && roleMenu.getCanDelete().equals("1")){
					permissions.add(basePermission + "delete");
				}
				if(roleMenu.getCanUpdate() != null && roleMenu.getCanUpdate().equals("1")){
					permissions.add(basePermission + "update");
				}
				if(roleMenu.getCanQuery() != null && roleMenu.getCanQuery().equals("1")){
					permissions.add(basePermission + "query");
				}
				if(roleMenu.getCanImport() != null && roleMenu.getCanImport().equals("1")){
					permissions.add(basePermission + "import");
				}
				if(roleMenu.getCanExport() != null && roleMenu.getCanExport().equals("1")){
					permissions.add(basePermission + "export");
				}
			}
		}
		Map<String, SysMenu> menus = SysUserUtils.getUserResources();
		for (String key : menus.keySet()) {
			String permission = menus.get(key).getPermissionStr();
			if(permission != null && !permission.equals("")){
				permissions.add(permission);
			}
		}
		return permissions;
	}
}
