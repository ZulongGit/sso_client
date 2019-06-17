

package com.krm.web.sys.mapper;


import java.util.List;
import java.util.Map;

import com.krm.common.base.CommonEntity;
import com.krm.web.sys.model.SysUser;

import tk.mybatis.mapper.common.Mapper;

/**
 * 
 * @author 
 */

public interface SysUserMapper extends Mapper<SysUser>{
	
	public List<CommonEntity> findPageInfo(Map<String, Object> params);
	
	public int saveUser(SysUser sysUser);
}
