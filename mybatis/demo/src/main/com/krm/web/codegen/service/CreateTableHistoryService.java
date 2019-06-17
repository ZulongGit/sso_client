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
import com.krm.common.constant.Constant;
import com.krm.web.codegen.mapper.CreateTableHistoryMapper;
import com.krm.web.codegen.mapper.ProTeamMemberMapper;
import com.krm.web.codegen.model.CreateTableHistory;



/**
 * 
 * @author Parker
 * 建表记录业务层
 * 2018-01-03
 */
@Service("createTableHistoryService")
public class CreateTableHistoryService extends ServiceMybatis<CreateTableHistory>{

	@Resource
	private CreateTableHistoryMapper createTableHistoryMapper;
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
			logger.info("#=================开始分页查询【建表记录】数据，带动态权限========================#");
			PageHelper.startPage(params);
			list = createTableHistoryMapper.list(params);
			logger.info("#=================动态权限查询成功！=================================#");
		} catch (Exception e) {
			logger.info("#=================动态权限查询出错，原因如下：========================#");
			logger.info("#                 1、此表没有和机构或者用户相关联的字段                               #");
			logger.info("#                 2、角色配置不正确                                                                     #");
			logger.info("#                 3、SQL本身语法错误                                                                   #");
			logger.info("#=================系统默认处理机制：查询所有数据======================#");
			params.remove("dynamicSQL");
			PageHelper.startPage(params);
			list = createTableHistoryMapper.queryPageInfo(params);
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
		logger.info("#=================开始根据不同条件查询【建表记录】列表数据，返回通用对象========================#");
		List<CommonEntity> list = createTableHistoryMapper.queryPageInfo(params);
		return list;
	}
	
	/**
	 * 列表(可带条件查询)
	 * 返回的是实体list
	 * @param params
	 * @return List<CreateTableHistory>
	 */
	public List<CreateTableHistory> entityList(Map<String, Object> params) {
		logger.info("#=================开始根据不同条件查询【建表记录】列表数据，返回实体对象========================#");
		List<CreateTableHistory> list = createTableHistoryMapper.entityList(params);
		return list;
	}
	
	/**
	 * 列表(查询所有，不带条件)
	 * @return
	 */
	public List<CreateTableHistory> list() {
		List<CreateTableHistory> list = this.selectAll();
		return list;
	}
	
	/**
	 * 根据主键查询
	 * @param id
	 * @return
	 */
	public CreateTableHistory queryById(String id){
		return this.selectByPrimaryKey(id);
	}
	
	/**
	 * 根据不同条件查询一条数据
	 * @param params
	 * @return
	 */
	public CreateTableHistory queryOne(Map<String, Object> params){
		logger.info("#=================开始根据不同条件查询一条【建表记录】数据，返回实体对象========================#");
		return createTableHistoryMapper.queryOne(params);
	}
	
	/**
	 * 根据不同条件查询一条数据
	 * @param record
	 * @return
	 */
	public CreateTableHistory queryOne(CreateTableHistory record){
		return this.selectOne(record);
	}
	
	/**
	 * 保存操作
	 * @param createTableHistory
	 * @return
	 */
	public int save(CreateTableHistory createTableHistory){
		return this.insertSelective(createTableHistory);
	}
	
	/**
	 * 批量保存操作
	 * @param list
	 * @return
	 */
	public int insertBatch(List<CreateTableHistory> list){
		return createTableHistoryMapper.insertBatch(list);
	}
	
	/**
	 * 更新操作
	 * @param createTableHistory
	 * @return
	 */
	public int update(CreateTableHistory createTableHistory){
		return this.updateByPrimaryKeySelective(createTableHistory);
	}
	
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public int deleteCreateTableHistory(String id){
		return this.deleteByPrimaryKey(id);
	}
	
	/**
	 * 批量删除
	 * @param tableNames
	 * @return
	 */
	public Integer deleteCreateTableHistory(String[] tableNames, Map<String, Object> params) {
		String delFlag = params.containsKey("delFlag") ? params.get("delFlag").toString() : Constant.DEL_FLAG_DELETE;
		int count = 0;
		for (String tableName : tableNames) {
			params.put("tableName", tableName);
			if(delFlag.equals(Constant.DEL_FLAG_DELETE)){
				count += createTableHistoryMapper.updateByParams(params);
			}else{
				count += deleteByParams(params);
			}
		}
		return count;
	}
	
	/**
	 * 根据不同组合条件删除
	 * @param params
	 * @return
	 */
	public int deleteByParams(Map<String, Object> params){
		logger.info("#=================开始根据不同条件删除【建表记录】数据========================#");
		return createTableHistoryMapper.deleteByParams(params);
	}
	
	public int executeCreate(Map<String, Object> params){
		return createTableHistoryMapper.executeCreate(params);
	}
}
