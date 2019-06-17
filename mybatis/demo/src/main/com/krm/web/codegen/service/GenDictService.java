package com.krm.web.codegen.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.krm.common.base.CommonEntity;
import com.krm.common.base.ServiceMybatis;
import com.krm.common.beetl.sql.MySQLManager;
import com.krm.web.codegen.model.GenDict;
import com.krm.web.codegen.mapper.GenDictMapper;



/**
 * 
 * @author Parker
 * 枚举类数据字典业务层
 * 2018-05-28
 */
@Service("genDictService")
public class GenDictService extends ServiceMybatis<GenDict>{

	@Resource
	private GenDictMapper genDictMapper;
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
			logger.info("#=================开始分页查询【枚举类数据字典】数据，带动态权限========================#");
			PageHelper.startPage(params);
			list = genDictMapper.queryPageInfo(params);
			logger.info("#=================动态权限查询成功！=================================#");
		} catch (Exception e) {
			logger.info("#=================动态权限查询出错，原因如下：========================#");
			logger.info("#                 1、此表没有和机构或者用户相关联的字段                               #");
			logger.info("#                 2、角色配置不正确                                                                     #");
			logger.info("#                 3、SQL本身语法错误                                                                   #");
			logger.info("#=================系统默认处理机制：查询所有数据======================#");
			params.remove("dynamicSQL");
			PageHelper.startPage(params);
			list = genDictMapper.queryPageInfo(params);
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
		logger.info("#=================开始根据不同条件查询【枚举类数据字典】列表数据，返回通用对象========================#");
		List<CommonEntity> list = genDictMapper.queryPageInfo(params);
		return list;
	}
	
	/**
	 * 列表(可带条件查询)
	 * 返回的是实体list
	 * @param params
	 * @return List<GenDict>
	 */
	public List<GenDict> entityList(Map<String, Object> params) {
		logger.info("#=================开始根据不同条件查询【枚举类数据字典】列表数据，返回实体对象========================#");
		List<GenDict> list = genDictMapper.entityList(params);
		return list;
	}
	
	/**
	 * 列表(查询所有，不带条件)
	 * @return
	 */
	public List<GenDict> list() {
		List<GenDict> list = this.selectAll();
		return list;
	}
	
	/**
	 * 根据主键查询
	 * @param id
	 * @return
	 */
	public GenDict queryById(String id){
		return this.selectByPrimaryKey(id);
	}
	
	/**
	 * 根据不同条件查询一条数据
	 * @param params
	 * @return
	 */
	public GenDict queryOne(Map<String, Object> params){
		logger.info("#=================开始根据不同条件查询一条【枚举类数据字典】数据，返回实体对象========================#");
		return genDictMapper.queryOne(params);
	}
	
	/**
	 * 根据不同条件查询一条数据
	 * @param record
	 * @return
	 */
	public GenDict queryOne(GenDict record){
		return this.selectOne(record);
	}
	
	 /**
	 * 查询单条数据,返回的是通用实体，不受实体属性限制，相当于map
	 * @param params
	 * @return
	 */
	public CommonEntity queryOneCommon(Map<String, Object> params){
	logger.info("#=================开始根据不同条件查询一条【枚举类数据字典】数据，返回通用对象========================#");
		return genDictMapper.queryOneCommon(params);
	}
	
	/**
	 * 保存操作
	 * @param genDict
	 * @return
	 */
	public int save(GenDict genDict){
		return this.insertSelective(genDict);
	}
	
	/**
	 * 批量保存操作
	 * @param list
	 * @return
	 */
	public int insertBatch(List<GenDict> list){
		return genDictMapper.insertBatch(list);
	}
	
	/**
	 * 更新操作
	 * @param genDict
	 * @return
	 */
	public int update(GenDict genDict){
		return this.updateByPrimaryKeySelective(genDict);
	}
	
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public int deleteGenDict(String id){
		return this.updateDelFlagToDelStatusById(GenDict.class, id);
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	public Integer deleteGenDict(String[] ids) {
		int count = 0;
		for (String id : ids) {
			count += this.updateDelFlagToDelStatusById(GenDict.class, id);
		}
		return count;
	}
	
	/**
	 * 根据不同组合条件删除
	 * @param params
	 * @return
	 */
	public int deleteByParams(Map<String, Object> params){
		logger.info("#=================开始根据不同条件删除【枚举类数据字典】数据========================#");
		return genDictMapper.deleteByParams(params);
	}

	public List<CommonEntity> getDicts(Map<String, Object> params) {
		return genDictMapper.getDicts(params);
	}
}