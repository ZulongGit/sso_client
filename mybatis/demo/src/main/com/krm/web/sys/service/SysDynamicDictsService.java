package com.krm.web.sys.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.krm.common.base.CommonEntity;
import com.krm.common.base.ServiceMybatis;
import com.krm.common.beetl.sql.MySQLManager;
import com.krm.web.sys.mapper.SysDynamicDictsMapper;
import com.krm.web.sys.model.SysDict;
import com.krm.web.sys.model.SysDynamicDicts;



/**
     动态数据字典业务类
   2017-10-17
*/

@Service("sysDynamicDictsService")
public class SysDynamicDictsService extends ServiceMybatis<SysDynamicDicts>{

	@Resource
	private SysDynamicDictsMapper sysDynamicDictsMapper;
	@Resource
	private MySQLManager mySQLManager;
	
	/**
	 * 分页展示(可带条件查询)
	 * 返回的是通用实体，不受实体属性限制，相当于map
	 * @param params
	 * @return
	 */
	public PageInfo<CommonEntity> queryPageInfo(Map<String, Object> params) {
		List<CommonEntity> list = null;
		try {
			logger.info("#=================开始分页查询【动态数据字典】数据，带动态权限========================#");
			PageHelper.startPage(params);
			list = sysDynamicDictsMapper.queryPageInfo(params);
			logger.info("#=================动态权限查询成功！=================================#");
		} catch (Exception e) {
			logger.info("#=================动态权限查询出错，原因如下：========================#");
			logger.info("#                 1、此表没有和机构或者用户相关联的字段                               #");
			logger.info("#                 2、角色配置不正确                                                                     #");
			logger.info("#=================系统默认处理机制：查询所有数据======================#");
			params.remove("dynamicSQL");
			PageHelper.startPage(params);
			list = sysDynamicDictsMapper.queryPageInfo(params);
		}
		return new PageInfo<CommonEntity>(list);
	}
	
	/**
	 * 列表(可带条件查询)
	 * 返回的是通用实体，不受实体属性限制，相当于map
	 * @param params
	 * @return List<CommonEntity>
	 */
	public List<CommonEntity> commonList(Map<String, Object> params) {
		logger.info("#=================开始根据不同条件查询【动态数据字典】列表数据，返回通用对象========================#");
		List<CommonEntity> list = sysDynamicDictsMapper.queryPageInfo(params);
		return list;
	}
	
	/**
	 * 列表(可带条件查询)
	 * 返回的是实体list
	 * @param params
	 * @return List<SysDynamicDicts>
	 */
	public List<SysDynamicDicts> entityList(Map<String, Object> params) {
		logger.info("#=================开始根据不同条件查询【动态数据字典】列表数据，返回实体对象========================#");
		List<SysDynamicDicts> list = sysDynamicDictsMapper.entityList(params);
		return list;
	}
	
	/**
	 * 列表(查询所有，不带条件)
	 * @return
	 */
	public List<SysDynamicDicts> list() {
		List<SysDynamicDicts> list = this.selectAll();
		return list;
	}
	
	/**
	 * 根据主键查询
	 * @param id
	 * @return
	 */
	public SysDynamicDicts queryById(String id){
		return this.selectByPrimaryKey(id);
	}
	
	/**
	 * 根据不同条件查询一条数据
	 * @param params
	 * @return
	 */
	public SysDynamicDicts queryOne(Map<String, Object> params){
		logger.info("#=================开始根据不同条件查询一条【动态数据字典】数据，返回实体对象========================#");
		return sysDynamicDictsMapper.queryOne(params);
	}
	
	/**
	 * 根据不同条件查询一条数据
	 * @param record
	 * @return
	 */
	public SysDynamicDicts queryOne(SysDynamicDicts record){
		return this.selectOne(record);
	}
	
	/**
	 * 保存操作
	 * @param sysDynamicDicts
	 * @return
	 */
	public int save(SysDynamicDicts sysDynamicDicts){
		return this.insertSelective(sysDynamicDicts);
	}
	
	/**
	 * 更新操作
	 * @param sysDynamicDicts
	 * @return
	 */
	public int update(SysDynamicDicts sysDynamicDicts){
		return this.updateByPrimaryKeySelective(sysDynamicDicts);
	}
	
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public int deleteTableDicts(String id){
		return this.updateDelFlagToDelStatusById(SysDynamicDicts.class, id);
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	public Integer deleteTableDicts(String[] ids) {
		int count = 0;
		for (String id : ids) {
			count += this.updateDelFlagToDelStatusById(SysDynamicDicts.class, id);
		}
		return count;
	}
	
	/**
	 * 执行动态sql
	 * @param params
	 * @return
	 */
	public List<SysDict> exeuteSql(Map<String, Object> params){
		List<SysDict> dictList = Lists.newArrayList();
		try {
			String keyName = "lable";
			String valueName = "value";
			List<CommonEntity> list = this.exeuteDynamicSql(params);
			for (Map<String, Object> map : list) {
				SysDict dict = new SysDict();
				dict.setLabel(map.containsKey(keyName)?map.get(keyName).toString():"");
				dict.setValue(map.containsKey(valueName)?map.get(valueName).toString():"");
				dictList.add(dict);
			}
		} catch (Exception e) {
			logger.info(e.toString());
		}
		return dictList;
	}

	public List<CommonEntity> allTable(Map<String, Object> params) {
		return sysDynamicDictsMapper.allTable(params);
	}

	public List<CommonEntity> selectFields(Map<String, Object> params) {
		return sysDynamicDictsMapper.selectFields(params);
	}
}
