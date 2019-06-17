

package com.krm.web.sys.mapper;

import java.util.List;
import java.util.Map;

import com.krm.web.sys.model.SysMenu;
import com.krm.web.sys.model.SysRole;
import com.krm.web.sys.model.SysRolePermission;
import com.krm.web.sys.model.SysUser;

import tk.mybatis.mapper.common.Mapper;

/**
 * 
 * @author
 */

public interface SysRoleMapper extends Mapper<SysRole> {

	public int insertRoleOrgan(SysRole sysRole);

	public int insertRoleMenu(SysRole sysRole);

	public int insertUserRoleByRoleId(SysRole sysRole);

	public int insertUserRoleByUserId(SysUser sysUser);
	
	public int insertRolePermission(List<SysRolePermission> list);

	public int deleteRoleOrganByRoleId(String roleId);

	public int deleteRoleMenuByRoleId(String roleId);

	public int deleteUserRoleByRoleId(String roleId);

	public int deleteUserRoleByUserId(String userId);

	public List<String> findOrganIdsByRoleId(String roleId);

	public List<String> findMenuIdsByRoleId(String roleId);
	
	public List<String> findUserIdsByRoleId(String userId);

	public List<SysMenu> findMenuByRoleId(String roleId);

	public List<SysUser> findUserByRoleId(String roleId);

	public List<SysRole> findUserRoleListByUserId(String userId);

	public List<SysRole> findPageInfo(Map<String, Object> params);
	
	public int saveRole(SysRole sysRole);

	public int deleteRolePermissionByRoleId(Map<String, Object> params);
	
	public List<SysRolePermission> queryRolePermissions(Map<String, Object> params);
}
