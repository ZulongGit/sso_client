package com.krm.common.beetl.sql;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.beetl.sql.core.IDAutoGen;
import org.beetl.sql.core.RowMapper;
import org.beetl.sql.core.SQLManager;
import org.beetl.sql.core.SQLScript;
import org.beetl.sql.core.db.KeyHolder;
import org.springframework.stereotype.Component;

@Component
public class MySQLManager {

	@Resource
	private SQLManager sqlManager;
	
	public void setSqlManager(SQLManager sqlManager) {
		this.sqlManager = sqlManager;
	}

	public SQLManager getSqlManager() {
		sqlManager.addIdAutonGen("uuid32", new IDAutoGen<Object>(){
		    @Override
		    public Object nextID(String params) {
		        return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
		    }
		});
		return sqlManager;
	}
	
	/* ============ 查询部分 ================== */

	/****
	 * 获取为分页语句
	 *
	 * @param selectId
	 * @return
	 */
	public SQLScript getPageSqlScript(String selectId) {
		return sqlManager.getPageSqlScript(selectId);
		
	}

	/**
	 * 根据主键查询 获取唯一记录，如果纪录不存在，将会抛出异常
	 *
	 * @param clazz
	 * @param pk
	 *            主键
	 * @return
	 */
	public <T> T unique(Class<T> clazz, Object pk) {
		return sqlManager.unique(clazz, null, pk);
	}

	/**
	 * 根据主键查询,获取唯一记录，如果纪录不存在，将会抛出异常
	 *
	 * @param clazz
	 * @param mapper
	 *            自定义结果映射方式
	 * @param pk
	 *            主键
	 * @return
	 */
	public <T> T unique(Class<T> clazz, RowMapper<T> mapper, Object pk) {
		return sqlManager.unique(clazz, mapper, pk);
	}

	/* =========模版查询=============== */

	/**
	 * @param clazz
	 * @param pk
	 * @return 如果没有找到，返回null
	 */
	public <T> T single(Class<T> clazz, Object pk) {
		return sqlManager.single(clazz, pk);
	}
	
	/**
	 * 一个行级锁实现，类似select * from xx where id = ? for update
	 * 
	 * @param clazz
	 * @param pk
	 * @return
	 */
	public <T> T lock(Class<T> clazz, Object pk) {
		return sqlManager.single(clazz, pk);
	}

	/**
	 * btsql自动生成查询语句，查询clazz代表的表的所有数据。
	 *
	 * @param clazz
	 * @return
	 */
	public <T> List<T> all(Class<T> clazz) {
		return sqlManager.all(clazz, null);
	}

	/**
	 * btsql自动生成查询语句，查询clazz代表的表的所有数据。
	 *
	 * @param clazz
	 * @param start
	 * @param size
	 * @return
	 */
	public <T> List<T> all(Class<T> clazz, long start, long size) {
		return sqlManager.all(clazz, start, size);
	}

	/**
	 * 查询记录数
	 *
	 * @param clazz
	 * @return
	 */
	public long allCount(Class<?> clazz) {
		return sqlManager.allCount(clazz);
	}

	/**
	 * 查询所有记录
	 *
	 * @param clazz
	 * @param mapper
	 * @param start
	 * @param end
	 * @return
	 */
	public <T> List<T> all(Class<T> clazz, RowMapper<T> mapper, long start, int end) {
		return sqlManager.all(clazz, mapper, start, end);
	}

	/**
	 * 查询所有记录
	 *
	 * @param clazz
	 * @param mapper
	 * @return
	 */
	public <T> List<T> all(Class<T> clazz, RowMapper<T> mapper) {
		return sqlManager.all(clazz, mapper);
	}

	// ========== 取出单个值 ============== //

	/**
	 * 将查询结果返回成Long类型
	 *
	 * @param id
	 * @param paras
	 * @return
	 */
	public Long longValue(String id, Map<String, Object> paras) {
		return this.selectSingle(id, paras, Long.class);
	}

	/**
	 * 将查询结果返回成Long类型
	 *
	 * @param id
	 * @param paras
	 * @return
	 */
	public Long longValue(String id, Object paras) {
		return this.selectSingle(id, paras, Long.class);
	}

	/**
	 * 将查询结果返回成Integer类型
	 *
	 * @param id
	 * @param paras
	 * @return
	 */
	public Integer intValue(String id, Object paras) {
		return this.selectSingle(id, paras, Integer.class);
	}

	/**
	 * 将查询结果返回成Integer类型
	 *
	 * @param id
	 * @param paras
	 * @return
	 */
	public Integer intValue(String id, Map<String, Object> paras) {
		return this.selectSingle(id, paras, Integer.class);
	}

	/**
	 * 将查询结果返回成BigDecimal类型
	 *
	 * @param id
	 * @param paras
	 * @return
	 */
	public BigDecimal bigDecimalValue(String id, Object paras) {
		return this.selectSingle(id, paras, BigDecimal.class);
	}

	/**
	 * 将查询结果返回成BigDecimal类型
	 *
	 * @param id
	 * @param paras
	 * @return
	 */
	public BigDecimal bigDecimalValue(String id, Map<String, Object> paras) {
		return this.selectSingle(id, paras, BigDecimal.class);
	}

	/**
	 * 返回查询的第一行数据，如果有未找到，返回null
	 *
	 * @param sqlId
	 * @param paras
	 * @param target
	 * @return
	 */
	public <T> T selectSingle(String sqlId, Object paras, Class<T> target) {
		return sqlManager.selectSingle(sqlId, paras, target);
	}

	/**
	 * 返回查询的第一行数据，如果有未找到，返回null
	 *
	 * @param sqlId
	 * @param paras
	 * @param target
	 * @return
	 */
	public <T> T selectSingle(String sqlId, Map<String, Object> paras, Class<T> target) {
		return sqlManager.selectSingle(sqlId, paras, target);
	}

	/**
	 * 返回一行数据，如果有多行或者未找到，抛错
	 *
	 * @param id
	 * @param paras
	 * @param target
	 * @return
	 */
	public <T> T selectUnique(String id, Object paras, Class<T> target) {
		return sqlManager.selectUnique(id, paras, target);
	}

	/**
	 * 返回一行数据，如果有多行或者未找到，抛错
	 *
	 * @param id
	 * @param paras
	 * @param target
	 * @return
	 */
	public <T> T selectUnique(String id, Map<String, Object> paras, Class<T> target) {
		return sqlManager.selectUnique(id, paras, target);
	}

	/**
	 * delete from user where 1=1 and id= #id#
	 * <p>
	 * 根据Id删除数据：支持联合主键
	 *
	 * @param clazz
	 * @param pkValue
	 * @return
	 */
	public int deleteById(Class<?> clazz, Object pkValue) {
		return sqlManager.deleteById(clazz, pkValue);
	}

	/**
	 * 删除对象, 通过对象的主键
	 *
	 * @param obj
	 *            对象,必须包含了主键，实际上根据主键来删除
	 * @return
	 */
	public int deleteObject(Object obj) {
		return sqlManager.deleteObject(obj);
	}

	// ============= 插入 =================== //

	/**
	 * 通用插入操作
	 *
	 * @param paras
	 * @return
	 */
	public int insert(Object paras) {
		return this.insert(paras.getClass(), paras, false);
	}

	/**
	 * 插入实体，且该实体对应的表有自增主键
	 *
	 * @param paras
	 * @param autoDbAssignKey
	 *            是否自动从数据库获取主键值
	 * @return
	 */
	public int insert(Object paras, boolean autoDbAssignKey) {
		return this.insert(paras.getClass(), paras, autoDbAssignKey);
	}

	/**
	 * 对于有自增主键的表，插入一行记录
	 *
	 * @param clazz
	 * @param paras
	 * @param autoDbAssignKey，是否获取自增主键
	 * @return
	 */
	public int insert(Class<?> clazz, Object paras, boolean autoDbAssignKey) {
		return getSqlManager().insert(clazz, paras, autoDbAssignKey);
	}

	/**
	 * 模板插入，非空值插入到数据库，并且获取到自增主键的值
	 *
	 * @param clazz
	 * @param paras
	 * @param autoDbAssignKey
	 * @return
	 */
	public int insertTemplate(Class<?> clazz, Object paras, boolean autoDbAssignKey) {
		return getSqlManager().insert(clazz, paras, autoDbAssignKey);

	}

	/**
	 * 插入对象通用的方法，如果数据表有自增主键，需要获取到自增主键，参考使用 insert(Object paras,boolean
	 * autoAssignKey)，或者使用 带有KeyHolder的方法
	 *
	 * @param clazz
	 * @param paras
	 * @return
	 */
	public int insert(Class<?> clazz, Object paras) {
		return this.insert(clazz, paras, false);
	}


	/**
	 * 批量插入
	 *
	 * @param clazz
	 * @param list
	 */
	public int[] insertBatch(Class<?> clazz, List<?> list) {
		return getSqlManager().insertBatch(clazz, list);
	}

	/**
	 * 插入，并获取自增主键的值
	 *
	 * @param clazz
	 * @param paras
	 * @param holder
	 */
	public int insert(Class<?> clazz, Object paras, KeyHolder holder) {
		return getSqlManager().insert(clazz, paras, holder);
	}


	/**
	 * 更新一个对象
	 *
	 * @param obj
	 * @return
	 */
	public int updateById(Object obj) {
		return sqlManager.updateById(obj);
	}

	/**
	 * 为null的值不参与更新，如果想更新null值，请使用updateById
	 *
	 * @param obj
	 * @return 返回更新的条数
	 */
	public int updateTemplateById(Object obj) {
		return sqlManager.updateTemplateById(obj);
	}

	/**
	 * @param c
	 *            c对应的表名
	 * @param paras
	 *            参数，仅仅更新paras里包含的值，paras里必须带有主键的值作为更新条件
	 * @return 返回更新的条数
	 */
	public int updateTemplateById(Class<?> c, Map<?, ?> paras) {
		return sqlManager.updateTemplateById(paras);
	}

	/****
	 * 批量更新
	 *
	 * @param list
	 *            ,包含pojo（不支持map）
	 * @return
	 */
	public int[] updateByIdBatch(List<?> list) {
		return sqlManager.updateByIdBatch(list);
	}


	/**
	 * 批量模板更新方式，list包含的对象是作为参数，非空属性参与更新
	 *
	 * @param clz
	 * @param list
	 * @return
	 */
	public int[] updateBatchTemplateById(Class<?> clz, List<?> list) {
		return sqlManager.updateBatchTemplateById(clz, list);
	}


	/**
	 * 更新指定表
	 *
	 * @param clazz
	 * @param param
	 *            参数
	 * @return
	 */
	public int updateAll(Class<?> clazz, Object param) {
		return sqlManager.updateAll(clazz, param);
	}

	public static void main(String[] args) {
		for (int i = 0; i < 100; i++) {
			System.out.println(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase());
		}
	}
}
