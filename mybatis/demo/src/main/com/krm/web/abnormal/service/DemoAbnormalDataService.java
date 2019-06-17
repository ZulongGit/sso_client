package com.krm.web.abnormal.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.krm.common.base.CommonEntity;
import com.krm.common.base.ServiceMybatis;
import com.krm.common.beetl.sql.MySQLManager;
import com.krm.web.abnormal.model.DemoAbnormalData;
import com.krm.web.abnormal.mapper.DemoAbnormalDataMapper;



/**
 * 
 * @author Parker
 * 异常规则数据业务层
 * 2018-08-22
 */
@Service("demoAbnormalDataService")
public class DemoAbnormalDataService extends ServiceMybatis<DemoAbnormalData>{

	@Resource
	private DemoAbnormalDataMapper demoAbnormalDataMapper;
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
			logger.info("#=================开始分页查询【异常规则数据】数据，带动态权限========================#");
			PageHelper.startPage(params);
			list = demoAbnormalDataMapper.queryPageInfo(params);
			logger.info("#=================动态权限查询成功！=================================#");
		} catch (Exception e) {
			logger.info("#=================动态权限查询出错，原因如下：========================#");
			logger.info("#                 1、此表没有和机构或者用户相关联的字段                               #");
			logger.info("#                 2、角色配置不正确                                                                     #");
			logger.info("#                 3、SQL本身语法错误                                                                   #");
			logger.info("#=================系统默认处理机制：查询所有数据======================#");
			params.remove("dynamicSQL");
			PageHelper.startPage(params);
			list = demoAbnormalDataMapper.queryPageInfo(params);
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
		logger.info("#=================开始根据不同条件查询【异常规则数据】列表数据，返回通用对象========================#");
		List<CommonEntity> list = demoAbnormalDataMapper.queryPageInfo(params);
		return list;
	}
	
	/**
	 * 列表(可带条件查询)
	 * 返回的是实体list
	 * @param params
	 * @return List<DemoAbnormalData>
	 */
	public List<DemoAbnormalData> entityList(Map<String, Object> params) {
		logger.info("#=================开始根据不同条件查询【异常规则数据】列表数据，返回实体对象========================#");
		List<DemoAbnormalData> list = demoAbnormalDataMapper.entityList(params);
		return list;
	}
	
	/**
	 * 列表(查询所有，不带条件)
	 * @return
	 */
	public List<DemoAbnormalData> list() {
		List<DemoAbnormalData> list = this.selectAll();
		return list;
	}
	
	/**
	 * 根据主键查询
	 * @param id
	 * @return
	 */
	public DemoAbnormalData queryById(String id){
		return this.selectByPrimaryKey(id);
	}
	
	/**
	 * 根据不同条件查询一条数据
	 * @param params
	 * @return
	 */
	public DemoAbnormalData queryOne(Map<String, Object> params){
		logger.info("#=================开始根据不同条件查询一条【异常规则数据】数据，返回实体对象========================#");
		return demoAbnormalDataMapper.queryOne(params);
	}
	
	/**
	 * 根据不同条件查询一条数据
	 * @param record
	 * @return
	 */
	public DemoAbnormalData queryOne(DemoAbnormalData record){
		return this.selectOne(record);
	}
	
	 /**
	 * 查询单条数据,返回的是通用实体，不受实体属性限制，相当于map
	 * @param params
	 * @return
	 */
	public CommonEntity queryOneCommon(Map<String, Object> params){
	logger.info("#=================开始根据不同条件查询一条【异常规则数据】数据，返回通用对象========================#");
		return demoAbnormalDataMapper.queryOneCommon(params);
	}
	
	/**
	 * 保存操作
	 * @param demoAbnormalData
	 * @return
	 */
	public int save(DemoAbnormalData demoAbnormalData){
		return this.insertSelective(demoAbnormalData);
	}
	
	/**
	 * 批量保存操作
	 * @param list
	 * @return
	 */
	public int insertBatch(List<DemoAbnormalData> list){
		return demoAbnormalDataMapper.insertBatch(list);
	}
	
	/**
	 * 更新操作
	 * @param demoAbnormalData
	 * @return
	 */
	public int update(DemoAbnormalData demoAbnormalData){
		return this.updateByPrimaryKeySelective(demoAbnormalData);
	}
	
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public int deleteDemoAbnormalData(String id){
		return this.deleteByPrimaryKey(id);
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	public Integer deleteDemoAbnormalData(String[] ids) {
		int count = 0;
		for (String id : ids) {
			count += deleteDemoAbnormalData(id);
		}
		return count;
	}
	
	/**
	 * 根据不同组合条件删除
	 * @param params
	 * @return
	 */
	public int deleteByParams(Map<String, Object> params){
		logger.info("#=================开始根据不同条件删除【异常规则数据】数据========================#");
		return demoAbnormalDataMapper.deleteByParams(params);
	}
	
	
}