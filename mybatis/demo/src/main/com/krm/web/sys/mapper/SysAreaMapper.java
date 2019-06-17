

package com.krm.web.sys.mapper;

import java.util.List;
import java.util.Map;

import com.krm.web.sys.model.SysArea;

import tk.mybatis.mapper.common.Mapper;

/**
 * 
 * @author 
 */

public interface SysAreaMapper extends Mapper<SysArea>{
	
	public int updateParentIds(SysArea sysArea);
	
	public int deleteAreaByRootId(String id);

	public List<SysArea> findSysAreaList(Map<String, Object> params);
	
}
