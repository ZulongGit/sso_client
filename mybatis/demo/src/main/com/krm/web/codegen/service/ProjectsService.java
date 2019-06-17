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
import com.krm.common.utils.StringUtil;
import com.krm.web.codegen.model.Projects;
import com.krm.web.util.SysUserUtils;
import com.krm.web.codegen.mapper.ProjectsMapper;



/**
 * 
 * @author Parker
 * 项目业务层
 * 2018-04-11
 */
@Service("projectsService")
public class ProjectsService extends ServiceMybatis<Projects>{

	@Resource
	private ProjectsMapper projectsMapper;
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
			logger.info("#=================开始分页查询【项目】数据，带动态权限========================#");
			PageHelper.startPage(params);
			if(SysUserUtils.getCacheLoginUser().isAdmin()){
				list = projectsMapper.queryPageInfo(params);
			}else{
				list = projectsMapper.queryPageList(params);
			}
			logger.info("#=================动态权限查询成功！=================================#");
		} catch (Exception e) {
			logger.info("#=================动态权限查询出错，原因如下：========================#");
			logger.info("#                 1、此表没有和机构或者用户相关联的字段                               #");
			logger.info("#                 2、角色配置不正确                                                                     #");
			logger.info("#                 3、SQL本身语法错误                                                                   #");
			logger.info("#=================系统默认处理机制：查询所有数据======================#");
			params.remove("dynamicSQL");
			PageHelper.startPage(params);
			if(SysUserUtils.getCacheLoginUser().isAdmin()){
				list = projectsMapper.queryPageInfo(params);
			}else{
				list = projectsMapper.queryPageList(params);
			}
		}
		for (CommonEntity entity : list) {
			if((StringUtil.equals(entity.getString("proType"), "02") && StringUtil.equals(entity.getString("myBuilt"), "Y"))
					|| (StringUtil.equals(entity.getString("proType"), "02") && StringUtil.equals(entity.getString("post"), "01"))
					|| (StringUtil.equals(entity.getString("proType"), "02") && SysUserUtils.getCacheLoginUser().isAdmin())){
				entity.put("disabled", "N");
			}else{
				entity.put("disabled", "Y");
			}
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
		logger.info("#=================开始根据不同条件查询【项目】列表数据，返回通用对象========================#");
		List<CommonEntity> list = projectsMapper.queryPageInfo(params);
		return list;
	}
	
	/**
	 * 列表(可带条件查询)
	 * 返回的是实体list
	 * @param params
	 * @return List<Projects>
	 */
	public List<Projects> entityList(Map<String, Object> params) {
		logger.info("#=================开始根据不同条件查询【项目】列表数据，返回实体对象========================#");
		List<Projects> list = projectsMapper.entityList(params);
		return list;
	}
	
	/**
	 * 列表(查询所有，不带条件)
	 * @return
	 */
	public List<Projects> list() {
		List<Projects> list = this.selectAll();
		return list;
	}
	
	/**
	 * 根据主键查询
	 * @param id
	 * @return
	 */
	public Projects queryById(String id){
		return this.selectByPrimaryKey(id);
	}
	
	/**
	 * 根据不同条件查询一条数据
	 * @param params
	 * @return
	 */
	public Projects queryOne(Map<String, Object> params){
		logger.info("#=================开始根据不同条件查询一条【项目】数据，返回实体对象========================#");
		return projectsMapper.queryOne(params);
	}
	
	/**
	 * 根据不同条件查询一条数据
	 * @param record
	 * @return
	 */
	public Projects queryOne(Projects record){
		return this.selectOne(record);
	}
	
	 /**
	 * 查询单条数据,返回的是通用实体，不受实体属性限制，相当于map
	 * @param params
	 * @return
	 */
	public CommonEntity queryOneCommon(Map<String, Object> params){
	logger.info("#=================开始根据不同条件查询一条【项目】数据，返回通用对象========================#");
		return projectsMapper.queryOneCommon(params);
	}
	
	public String[] queryMyProject(Map<String, Object> params){
		logger.info("开始获取我拥有的项目列表");
		if(!SysUserUtils.getCacheLoginUser().isAdmin()){
			String[] proIds = projectsMapper.queryMyProject(params);
			if(proIds.length == 0){
				proIds = "".split(",");	//传一个长度为1的空字串
			}
			return proIds;
		}else{
			return null;
		}
	}
	
	/**
	 * 保存操作
	 * @param projects
	 * @return
	 */
	public int save(Projects projects){
		return this.insertSelective(projects);
	}
	
	/**
	 * 批量保存操作
	 * @param list
	 * @return
	 */
	public int insertBatch(List<Projects> list){
		return projectsMapper.insertBatch(list);
	}
	
	/**
	 * 更新操作
	 * @param projects
	 * @return
	 */
	public int update(Projects projects){
		return this.updateByPrimaryKeySelective(projects);
	}
	
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public int deleteProjects(String id){
		return this.updateDelFlagToDelStatusById(Projects.class, id);
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	public Integer deleteProjects(String[] ids) {
		int count = 0;
		for (String id : ids) {
			count += this.updateDelFlagToDelStatusById(Projects.class, id);
		}
		return count;
	}
	
	/**
	 * 根据不同组合条件删除
	 * @param params
	 * @return
	 */
	public int deleteByParams(Map<String, Object> params){
		logger.info("#=================开始根据不同条件删除【项目】数据========================#");
		return projectsMapper.deleteByParams(params);
	}
	
	
}