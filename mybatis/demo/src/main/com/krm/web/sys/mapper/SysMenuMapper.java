

package com.krm.web.sys.mapper;


import java.util.List;
import java.util.Map;

import com.krm.common.base.CommonEntity;
import com.krm.web.sys.model.SysMenu;

import tk.mybatis.mapper.common.Mapper;

/**
 * 
 * @author 
 */

public interface SysMenuMapper extends Mapper<SysMenu>{
	
	public int updateParentIds(SysMenu sysMenu);
	
	public int deleteIdsByRootId(String id);
   
	public List<CommonEntity> findPageInfo(Map<String, Object> params);
	
	//删除前验证
	public int beforeDeleteMenu(String resourceId);
	
	//根据userId获得持有的权限
	public List<SysMenu> findUserMenuByUserId(String userId);
	
}
