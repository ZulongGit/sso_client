package com.krm.web.sys.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.krm.common.base.CommonEntity;
import com.krm.common.base.ServiceMybatis;
import com.krm.common.beetl.sql.MySQLManager;
import com.krm.web.sys.model.SysUpdateLog;
import com.krm.web.sys.mapper.SysUpdateLogMapper;



/**
 * 
 * @author Parker
 * 系统升级日志业务层
 * 2017-12-27
 */
@Service("sysUpdateLogService")
public class SysUpdateLogService extends ServiceMybatis<SysUpdateLog>{

	@Resource
	private SysUpdateLogMapper sysUpdateLogMapper;
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
			logger.info("#=================开始分页查询【系统升级日志】数据，带动态权限========================#");
			PageHelper.startPage(params);
			list = sysUpdateLogMapper.queryPageInfo(params);
			logger.info("#=================动态权限查询成功！=================================#");
		} catch (Exception e) {
			logger.info("#=================动态权限查询出错，原因如下：========================#");
			logger.info("#                 1、此表没有和机构或者用户相关联的字段                               #");
			logger.info("#                 2、角色配置不正确                                                                     #");
			logger.info("#                 3、SQL本身语法错误                                                                   #");
			logger.info("#=================系统默认处理机制：查询所有数据======================#");
			params.remove("dynamicSQL");
			PageHelper.startPage(params);
			list = sysUpdateLogMapper.queryPageInfo(params);
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
		logger.info("#=================开始根据不同条件查询【系统升级日志】列表数据，返回通用对象========================#");
		List<CommonEntity> list = sysUpdateLogMapper.queryPageInfo(params);
		return list;
	}
	
	/**
	 * 列表(可带条件查询)
	 * 返回的是实体list
	 * @param params
	 * @return List<SysUpdateLog>
	 */
	public List<SysUpdateLog> entityList(Map<String, Object> params) {
		logger.info("#=================开始根据不同条件查询【系统升级日志】列表数据，返回实体对象========================#");
		List<SysUpdateLog> list = sysUpdateLogMapper.entityList(params);
		return list;
	}
	
	/**
	 * 列表(查询所有，不带条件)
	 * @return
	 */
	public List<SysUpdateLog> list() {
		List<SysUpdateLog> list = this.selectAll();
		return list;
	}
	
	/**
	 * 根据主键查询
	 * @param id
	 * @return
	 */
	public SysUpdateLog queryById(String id){
		return this.selectByPrimaryKey(id);
	}
	
	/**
	 * 根据不同条件查询一条数据
	 * @param params
	 * @return
	 */
	public SysUpdateLog queryOne(Map<String, Object> params){
		logger.info("#=================开始根据不同条件查询一条【系统升级日志】数据，返回实体对象========================#");
		return sysUpdateLogMapper.queryOne(params);
	}
	
	/**
	 * 根据不同条件查询一条数据
	 * @param record
	 * @return
	 */
	public SysUpdateLog queryOne(SysUpdateLog record){
		return this.selectOne(record);
	}
	
	/**
	 * 保存操作
	 * @param sysUpdateLog
	 * @return
	 */
	public int save(SysUpdateLog sysUpdateLog){
		return this.insertSelective(sysUpdateLog);
	}
	
	/**
	 * 更新操作
	 * @param sysUpdateLog
	 * @return
	 */
	public int update(SysUpdateLog sysUpdateLog){
		return this.updateByPrimaryKeySelective(sysUpdateLog);
	}
	
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public int deleteSysUpdateLog(String id){
		return this.deleteByPrimaryKey(id);
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	public Integer deleteSysUpdateLog(String[] ids) {
		int count = 0;
		for (String id : ids) {
			count += deleteSysUpdateLog(id);
		}
		return count;
	}
	
	/**
	 * 根据不同组合条件删除
	 * @param params
	 * @return
	 */
	public int deleteByParams(Map<String, Object> params){
		logger.info("#=================开始根据不同条件删除【系统升级日志】数据========================#");
		return sysUpdateLogMapper.deleteByParams(params);
	}
	
	/**
	 * 获取系统升级日志年份
	 * @param params
	 * @return
	 */
	public List<CommonEntity> updateLogYear(Map<String, Object> params){
		return sysUpdateLogMapper.updateLogYear(params);
	}
	
	public List<CommonEntity> allUpdateLog(Map<String, Object> params){
		return sysUpdateLogMapper.allUpdateLog(params);
	}
}
