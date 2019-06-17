package com.krm.web.codegen.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.krm.common.base.CommonEntity;
import com.krm.common.base.ServiceMybatis;
import com.krm.common.beetl.sql.MySQLManager;
import com.krm.web.codegen.model.ProTemplates;
import com.krm.web.util.SysUserUtils;
import com.krm.web.codegen.mapper.ProTeamMemberMapper;
import com.krm.web.codegen.mapper.ProTemplatesMapper;



/**
 * 
 * @author Parker
 * 模板配置业务层
 * 2018-04-09
 */
@Service("proTemplatesService")
public class ProTemplatesService extends ServiceMybatis<ProTemplates>{

	@Resource
	private ProTemplatesMapper proTemplatesMapper;
	@Resource
	private MySQLManager mySQLManager;
	@Resource
	private ProTeamMemberMapper proTeamMemberMapper;
	
	/**
	 * 分页展示(可带条件查询)
	 * 返回的是通用实体，不受实体属性限制，相当于map
	 * @param params
	 * @return
	 */
	public PageInfo<CommonEntity> queryPageInfo(Map<String, Object> params) {
		List<CommonEntity> list = null;
		try {
			logger.info("#=================开始分页查询【模板配置】数据，带动态权限========================#");
			PageHelper.startPage(params);
			list = proTemplatesMapper.queryPageInfo(params);
			logger.info("#=================动态权限查询成功！=================================#");
		} catch (Exception e) {
			logger.info("#=================动态权限查询出错，原因如下：========================#");
			logger.info("#                 1、此表没有和机构或者用户相关联的字段                               #");
			logger.info("#                 2、角色配置不正确                                                                     #");
			logger.info("#                 3、SQL本身语法错误                                                                   #");
			logger.info("#=================系统默认处理机制：查询所有数据======================#");
			params.remove("dynamicSQL");
			PageHelper.startPage(params);
			list = proTemplatesMapper.queryPageInfo(params);
		}
		Map<String, Object> map = Maps.newHashMap();
		map.put("proIds", params.get("proIds"));
		List<CommonEntity> members = proTeamMemberMapper.queryPageInfo(map);
		if(!SysUserUtils.getCacheLoginUser().isAdmin()){
			List<String> proList = Lists.newArrayList();
			for (CommonEntity entity : members) {
				//查找已“我”为项目负责人的项目
				if(entity.getString("post").equals("01") && entity.getString("memberId").equals(SysUserUtils.getCacheLoginUser().getId())){
					proList.add(entity.getString("proId"));
				}
			}
			for (CommonEntity entity : list) {
				if(members.size() > 0){
					entity.put("disabled", "Y");
				}else{
					entity.put("disabled", "N");
				}
			}
			//有以“我”为项目负责人的项目
			for (String proId : proList) {
				for (CommonEntity entity : list) {
					//相同项目
					if(entity.getString("proId").equals(proId)){
						entity.put("disabled", "N");
					}
				}
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
		logger.info("#=================开始根据不同条件查询【模板配置】列表数据，返回通用对象========================#");
		List<CommonEntity> list = proTemplatesMapper.queryPageInfo(params);
		return list;
	}
	
	/**
	 * 列表(可带条件查询)
	 * 返回的是实体list
	 * @param params
	 * @return List<ProTemplates>
	 */
	public List<ProTemplates> entityList(Map<String, Object> params) {
		logger.info("#=================开始根据不同条件查询【模板配置】列表数据，返回实体对象========================#");
		List<ProTemplates> list = proTemplatesMapper.entityList(params);
		return list;
	}
	
	/**
	 * 列表(查询所有，不带条件)
	 * @return
	 */
	public List<ProTemplates> list() {
		List<ProTemplates> list = this.selectAll();
		return list;
	}
	
	/**
	 * 根据主键查询
	 * @param id
	 * @return
	 */
	public ProTemplates queryById(String id){
		return this.selectByPrimaryKey(id);
	}
	
	/**
	 * 根据不同条件查询一条数据
	 * @param params
	 * @return
	 */
	public ProTemplates queryOne(Map<String, Object> params){
		logger.info("#=================开始根据不同条件查询一条【模板配置】数据，返回实体对象========================#");
		return proTemplatesMapper.queryOne(params);
	}
	
	/**
	 * 根据不同条件查询一条数据
	 * @param record
	 * @return
	 */
	public ProTemplates queryOne(ProTemplates record){
		return this.selectOne(record);
	}
	
	 /**
	 * 查询单条数据,返回的是通用实体，不受实体属性限制，相当于map
	 * @param params
	 * @return
	 */
	public CommonEntity queryOneCommon(Map<String, Object> params){
	logger.info("#=================开始根据不同条件查询一条【模板配置】数据，返回通用对象========================#");
		return proTemplatesMapper.queryOneCommon(params);
	}
	
	/**
	 * 保存操作
	 * @param proTemplates
	 * @return
	 */
	public int save(ProTemplates proTemplates){
		return this.insertSelective(proTemplates);
	}
	
	/**
	 * 批量保存操作
	 * @param list
	 * @return
	 */
	public int insertBatch(List<ProTemplates> list){
		return proTemplatesMapper.insertBatch(list);
	}
	
	/**
	 * 更新操作
	 * @param proTemplates
	 * @return
	 */
	public int update(ProTemplates proTemplates){
		return this.updateByPrimaryKeySelective(proTemplates);
	}
	
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public int deleteProTemplates(String id){
		return this.updateDelFlagToDelStatusById(ProTemplates.class, id);
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	public Integer deleteProTemplates(String[] ids) {
		int count = 0;
		for (String id : ids) {
			count += this.updateDelFlagToDelStatusById(ProTemplates.class, id);
		}
		return count;
	}
	
	/**
	 * 根据不同组合条件删除
	 * @param params
	 * @return
	 */
	public int deleteByParams(Map<String, Object> params){
		logger.info("#=================开始根据不同条件删除【模板配置】数据========================#");
		return proTemplatesMapper.deleteByParams(params);
	}

	public List<ProTemplates> getTemplateByIds(String[] templates) {
		return proTemplatesMapper.getTemplateByIds(templates);
	}
	
	public List<String> loadTemplateFile(List<ProTemplates> proTemplates){
		List<String> list = Lists.newArrayList();
		for (ProTemplates entity : proTemplates) {
			list.add(StringEscapeUtils.unescapeHtml3(entity.getContents()));
		}
		return list;
	}
}
