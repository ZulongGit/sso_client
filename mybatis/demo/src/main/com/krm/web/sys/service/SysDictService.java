

package com.krm.web.sys.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Table;
import com.krm.common.base.ServiceMybatis;
import com.krm.web.sys.mapper.SysAreaMapper;
import com.krm.web.sys.mapper.SysDictMapper;
import com.krm.web.sys.model.SysArea;
import com.krm.web.sys.model.SysDict;
import com.krm.web.sys.model.SysOrgan;
import com.krm.web.sys.model.SysRole;

/**
 * 
 * @author
 */

@Service("sysDictService")
@CacheConfig(cacheNames = "sysDict_cache")
public class SysDictService extends ServiceMybatis<SysDict> {

	@Resource
	private SysDictMapper sysDictMapper;

	@Resource
	private SysAreaMapper sysAreaMapper;

	/**
	 * 分页展示
	 * @param params
	 * @return
	 */
	public PageInfo<SysDict> findPageInfo(Map<String, Object> params) {
		logger.info("分页查询【字典】");
		PageHelper.startPage(params);
		List<SysDict> list = sysDictMapper.list(params);
		return new PageInfo<SysDict>(list);
	}
	
	/**
	 * 保存或更新
	 * 
	 * @param sysDict
	 * @return
	 */
	@CacheEvict(allEntries = true)
	public int saveSysdict(SysDict sysDict) {
		return this.saveOrUpdate(sysDict);
	}

	/**
	 * 删除
	* @param sysDict
	* @return
	 */
	@CacheEvict(allEntries = true)
	public int deleteSysDict(SysDict sysDict) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("type", sysDict.getValue());
		if(sysDict.getType().equals("sys_area_type")){
			int areaCount = this.beforeDelete(SysArea.class,params);
			if(areaCount<0) return -1;
		}
		if(sysDict.getType().equals("sys_organ_type")){
			int organCount = this.beforeDelete(SysOrgan.class,params);
			if(organCount<0) return -1;
		}
		if(sysDict.getType().equals("sys_data_scope")){
			int roleCount = this.beforeDelete(SysRole.class, params);
			if(roleCount<0) return -1;
		}
		return this.updateDelFlagToDelStatusById(SysDict.class, sysDict.getId());
	}

	@Cacheable(key="'allDictTable'")
	public Table<String,String, SysDict> findAllDictTable(){
		List<SysDict> dictList = this.select(new SysDict(), "sort");
		Table<String,String, SysDict> tableDicts = HashBasedTable.create();
		for(SysDict dict : dictList){
			tableDicts.put(dict.getType(), dict.getValue(), dict);
		}
		return tableDicts;
	}
	
	@Cacheable(key="'allDictMultimap'")
	public Multimap<String, SysDict> findAllMultimap(){
		List<SysDict> dictList = this.select(new SysDict(), "sort");
		Multimap<String, SysDict> multimap = ArrayListMultimap.create();
		for(SysDict dict : dictList){
			multimap.put(dict.getType(), dict);
		}
		return multimap;
	}

	@Cacheable(key="'allDicts'")
	public Multimap<String, SysDict> getAllDicts(){
		List<SysDict> dictList = this.select(new SysDict(), "sort");
		Multimap<String, SysDict> multimap = ArrayListMultimap.create();
		for (SysDict dict : dictList)
		{
			multimap.put(dict.getDescription(), dict);
		}
		
		return multimap;
	}
	
	@Cacheable(key="'list'")
	public List<SysDict> list(Map<String, Object> params){
		logger.info("分页查询【字典】");
		return sysDictMapper.list(params);
	}
	
	public Map<String, String> findAllType(){
		List<SysDict> dictList = this.select(new SysDict(), "sort");
		Map<String, String> multimap = Maps.newHashMap();
		for (SysDict dict : dictList)
		{
			multimap.put(dict.getType(), dict.getDescription());
		}
		return multimap;
	}
}
