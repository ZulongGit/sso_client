package com.krm.web.sys.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.krm.common.base.CommonEntity;
import com.krm.common.base.ServiceMybatis;
import com.krm.common.beetl.sql.MySQLManager;
import com.krm.web.sys.model.SysMenuCategory;
import com.krm.web.sys.mapper.SysMenuCategoryMapper;



/**
 * 
 * @author Parker
 * 菜单分类业务层
 * 2018-08-02
 */
@Service("sysMenuCategoryService")
@CacheConfig(cacheNames = "sysDict_cache")
public class SysMenuCategoryService extends ServiceMybatis<SysMenuCategory>{

	@Resource
	private SysMenuCategoryMapper sysMenuCategoryMapper;
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
			logger.info("#=================开始分页查询【菜单分类】数据，带动态权限========================#");
			PageHelper.startPage(params);
			list = sysMenuCategoryMapper.queryPageInfo(params);
			logger.info("#=================动态权限查询成功！=================================#");
		} catch (Exception e) {
			logger.info("#=================动态权限查询出错，原因如下：========================#");
			logger.info("#                 1、此表没有和机构或者用户相关联的字段                               #");
			logger.info("#                 2、角色配置不正确                                                                     #");
			logger.info("#                 3、SQL本身语法错误                                                                   #");
			logger.info("#=================系统默认处理机制：查询所有数据======================#");
			params.remove("dynamicSQL");
			PageHelper.startPage(params);
			list = sysMenuCategoryMapper.queryPageInfo(params);
		}
		return new PageInfo<CommonEntity>(list);
	}
	
	/**
	 * 列表(可带条件查询)
	 * 返回的是通用实体，不受实体属性限制，相当于map
	 * @param params
	 * @return List<CommonEntity>
	 */
	@Cacheable(key="'commonList'")
	public List<CommonEntity> commonList(Map<String, Object> params) {
		logger.info("#=================开始根据不同条件查询【菜单分类】列表数据，返回通用对象========================#");
		List<CommonEntity> list = sysMenuCategoryMapper.queryPageInfo(params);
		return list;
	}
	
	/**
	 * 列表(可带条件查询)
	 * 返回的是实体list
	 * @param params
	 * @return List<SysMenuCategory>
	 */
	@Cacheable(key="'entityList'")
	public List<SysMenuCategory> entityList(Map<String, Object> params) {
		logger.info("#=================开始根据不同条件查询【菜单分类】列表数据，返回实体对象========================#");
		List<SysMenuCategory> list = sysMenuCategoryMapper.entityList(params);
		return list;
	}
	
	/**
	 * 列表(查询所有，不带条件)
	 * @return
	 */
	@Cacheable(key="'list'")
	public List<SysMenuCategory> list() {
		List<SysMenuCategory> list = this.selectAll();
		return list;
	}
	
	/**
	 * 根据主键查询
	 * @param id
	 * @return
	 */
	public SysMenuCategory queryById(String id){
		return this.selectByPrimaryKey(id);
	}
	
	/**
	 * 根据不同条件查询一条数据
	 * @param params
	 * @return
	 */
	public SysMenuCategory queryOne(Map<String, Object> params){
		logger.info("#=================开始根据不同条件查询一条【菜单分类】数据，返回实体对象========================#");
		return sysMenuCategoryMapper.queryOne(params);
	}
	
	/**
	 * 根据不同条件查询一条数据
	 * @param record
	 * @return
	 */
	public SysMenuCategory queryOne(SysMenuCategory record){
		return this.selectOne(record);
	}
	
	 /**
	 * 查询单条数据,返回的是通用实体，不受实体属性限制，相当于map
	 * @param params
	 * @return
	 */
	public CommonEntity queryOneCommon(Map<String, Object> params){
	logger.info("#=================开始根据不同条件查询一条【菜单分类】数据，返回通用对象========================#");
		return sysMenuCategoryMapper.queryOneCommon(params);
	}
	
	/**
	 * 保存操作
	 * @param sysMenuCategory
	 * @return
	 */
	@CacheEvict(allEntries = true)
	public int save(SysMenuCategory sysMenuCategory){
		return this.insertSelective(sysMenuCategory);
	}
	
	/**
	 * 批量保存操作
	 * @param list
	 * @return
	 */
	public int insertBatch(List<SysMenuCategory> list){
		return sysMenuCategoryMapper.insertBatch(list);
	}
	
	/**
	 * 更新操作
	 * @param sysMenuCategory
	 * @return
	 */
	@CacheEvict(allEntries = true)
	public int update(SysMenuCategory sysMenuCategory){
		return this.updateByPrimaryKeySelective(sysMenuCategory);
	}
	
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@CacheEvict(allEntries = true)
	public int deleteSysMenuCategory(String id){
		return this.deleteByPrimaryKey(id);
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@CacheEvict(allEntries = true)
	public Integer deleteSysMenuCategory(String[] ids) {
		int count = 0;
		for (String id : ids) {
			count += deleteSysMenuCategory(id);
		}
		return count;
	}
	
	/**
	 * 根据不同组合条件删除
	 * @param params
	 * @return
	 */
	@CacheEvict(allEntries = true)
	public int deleteByParams(Map<String, Object> params){
		logger.info("#=================开始根据不同条件删除【菜单分类】数据========================#");
		return sysMenuCategoryMapper.deleteByParams(params);
	}
	
	
}