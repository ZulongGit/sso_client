package com.krm.common.beetl.function;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.krm.common.constant.Constant;
import com.krm.common.utils.CacheUtils;
import com.krm.web.sys.model.SysDict;
import com.krm.web.sys.model.SysOrgan;
import com.krm.web.sys.service.SysDictService;
import com.krm.web.sys.service.SysOrganService;
import com.krm.web.util.SysUserUtils;

@Component
public class OrganFunctions {
	
	@Resource
	private SysOrganService sysOrganService;
	@Resource
	private SysDictService sysDictService;
	
	/**
	 * 全部机构 key:机构id  value:机构对象
	* @return
	 */
	public Map<String, SysOrgan> getAllOrganMap(){
		Map<String, SysOrgan> allOrganMap = CacheUtils.get(Constant.CACHE_SYS_ORGAN, "allOrgan");
		if(allOrganMap == null){
			allOrganMap = Maps.newHashMap();
			List<SysOrgan> list = sysOrganService.select(new SysOrgan());
			if(list!=null && list.size()>0){
				for(SysOrgan so : list){
					allOrganMap.put(so.getId(), so);
				}
			}
			CacheUtils.put(Constant.CACHE_SYS_ORGAN, "allOrgan", allOrganMap);
		}
		return allOrganMap;
	}
	
	/**
	 * 根据部门id拼接所属机构层级字符
	* @param organId 部门id
	* @param organs 全部机构map
	* @return
	 */
	public String getOrganStrByOrganId(String organId){
		Map<String, SysOrgan> organs = getAllOrganMap();
		String str = "";
		if(organs!=null){
			if(organs.get(organId) != null){
				
				String pid = organs.get(organId).getParentId();
				while(pid != null){
					SysOrgan so = (SysOrgan)organs.get(pid);
					if(so!=null)str+=so.getName()+" - ";
					pid = organs.get(pid) == null ? null : organs.get(pid).getParentId();
				}
				
				//mysql 有这个字段，oracle中不需要这个字段
//				String[] pids = ((SysOrgan)organs.get(organId)).getParentIds().split(",");
//				for(String id : pids){
//					SysOrgan so = (SysOrgan)organs.get(StringConvert.toLong(id));
//					if(so!=null)str+=so.getName()+" - ";
//				}
				SysOrgan so = (SysOrgan)organs.get(organId);
				str+=so.getName();
			}
		}
		return str;
	}
	
	/**
	 * 得到用户机构
	* @return
	 */
	public List<SysOrgan> getUserOrganList(){
		return SysUserUtils.getUserOrgan();
	}
	
	/**
	 * 用户持有数据范围
	 */
	public List<SysDict> getUserDataScope(){
		List<String> values = SysUserUtils.getUserDataScope();
		Collection<SysDict> dicts = sysDictService.findAllMultimap().get("sys_data_scope");
		List<SysDict> resultList = Lists.newArrayList();
		for(SysDict dict : dicts){
			if(values.contains(dict.getValue())){
				resultList.add(dict);
			}
		}
		return resultList;
	}
	
}
