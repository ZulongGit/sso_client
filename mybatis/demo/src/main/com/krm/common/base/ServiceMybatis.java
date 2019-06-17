package com.krm.common.base;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.krm.common.constant.Constant;
import com.krm.common.mybatis.EntityHelper;
import com.krm.common.mybatis.SqlMapper;
import com.krm.common.mybatis.mapper.BaseMapper;
import com.krm.common.spring.utils.SpringContextHolder;
import com.krm.common.utils.BeanUtils;
import com.krm.common.utils.StringConvert;
import com.krm.common.utils.StringUtil;
import com.krm.web.sys.model.SysUser;
import com.krm.web.util.SysUserUtils;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

public abstract class ServiceMybatis<T> implements BaseService<T>{

	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	protected Mapper<T> mapper;
	@Resource
	protected BaseMapper baseMapper;
	@Resource
	protected SqlMapper sqlMapper;
	
	
	/**
	 * 根据实体类不为null的字段进行查询,条件全部使用=号and条件
	 * @param <T extend T>
	 */
	public List<T> select(T record) {
		logger.info("根据非空字段进行查询【{}】列表(通用方法)", BeanUtils.getProperty(record, "moduleName"));
		BeanUtils.setProperty(record, "delFlag", Constant.DEL_FLAG_NORMAL, false);
		return mapper.select(record);
	}
	
	public List<T> select(T record,String orderSqlStr){
		logger.info("根据非空字段进行查询【{}】列表，根据【{}】排序(通用方法)", BeanUtils.getProperty(record, "moduleName"), orderSqlStr);
		Example example = new Example(record.getClass(),false);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("delFlag", Constant.DEL_FLAG_NORMAL);
		Method [] methods = record.getClass().getMethods();
		for (Method entry : methods) {
			if (entry.getName().indexOf("get") == 0) {
				String fieldName = StringUtil.firstCharToLower(entry.getName().substring(3));
				try {
					if(entry.invoke(record) == null || "".equals(entry.invoke(record))) continue;
					criteria.andEqualTo(fieldName, entry.invoke(record));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		example.setOrderByClause(orderSqlStr);
		return mapper.selectByExample(example);
	}
	
	/**
	 * 查询全部结果，select(null)方法能达到同样的效果
	 * @return
	 */
	public List<T> selectAll(){
		logger.info("查询全部结果(通用方法)");
		return mapper.selectAll();
	}
	
	
	/**
	 * 根据实体中的属性进行查询，只能有一个返回值，有多个结果是抛出异常，查询条件使用等号
	 * @param record
	 * @return
	 */
	public T selectOne(T record){
		logger.info("根据条件查询单条【{}】数据(通用方法)", BeanUtils.getProperty(record, "moduleName"));
		return mapper.selectOne(record);
	}
	
	
	/**
	 * 根据实体类不为null的字段查询总数,条件全部使用=号and条件
	 * @param <T extend T>
	 */
	public int selectCount(T record) {
		logger.info("根据条件查询【{}】总数(通用方法)", BeanUtils.getProperty(record, "moduleName"));
		BeanUtils.setProperty(record, "delFlag", Constant.DEL_FLAG_NORMAL, false);
		return mapper.selectCount(record);
	}

	/**
	 * 根据主键进行查询,必须保证结果唯一 单个字段做主键时,可以直接写主键的值 联合主键时,key可以是实体类,也可以是Map
	 * 
	 * @param <T extend T>
	 */
	public T selectByPrimaryKey(Object key) {
		logger.info("根据主键查询单条数据(通用方法)");
		return mapper.selectByPrimaryKey(key);
	}

	/**
	 * 插入一条数据 支持Oracle序列,UUID,类似Mysql的INDENTITY自动增长(自动回写)
	 * 优先使用传入的参数值,参数值空时,才会使用序列、UUID,自动增长
	 * 
	 * @param <T extend T>
	 */
	public int insert(T record) {
		logger.info("开始保存一条【{}】数据，空值也会保存(通用方法)", BeanUtils.getProperty(record, "moduleName"));
		BeanUtils.setProperty(record, "createDate", new Date(), false);
		BeanUtils.setProperty(record, "updateDate", new Date(), false);
		BeanUtils.setProperty(record, "delFlag", Constant.DEL_FLAG_NORMAL, false);
		SysUser sysUser = SysUserUtils.getCacheLoginUser();
		if(sysUser != null){
			BeanUtils.setProperty(record, "createBy", SysUserUtils.getCacheLoginUser().getId(), false);
		}
		return mapper.insert(record);
	}

	/**
	 * 插入一条数据,只插入不为null的字段,不会影响有默认值的字段
	 * 支持Oracle序列,UUID,类似Mysql的INDENTITY自动增长(自动回写)
	 * 优先使用传入的参数值,参数值空时,才会使用序列、UUID,自动增长
	 * 
	 * @param <T extend T>
	 */
	public int insertSelective(T record) {
		logger.info("开始保存一条【{}】数据，空值不会保存(通用方法)", BeanUtils.getProperty(record, "moduleName"));
		BeanUtils.setProperty(record, "createDate", new Date(), false);
		BeanUtils.setProperty(record, "updateDate", new Date(), false);
		BeanUtils.setProperty(record, "delFlag", Constant.DEL_FLAG_NORMAL, false);
		SysUser sysUser = SysUserUtils.getCacheLoginUser();
		if(sysUser != null){
			BeanUtils.setProperty(record, "createBy", SysUserUtils.getCacheLoginUser().getId(), false);
		}
		return mapper.insertSelective(record);
	}

	/**
	 * 根据实体类不为null的字段进行查询,条件全部使用=号and条件
	 * 
	 * @param <T extend T>
	 */
	public int delete(T key) {
		logger.info("根据条件进行删除数据(通用方法)");
		return mapper.delete(key);
	}

	/**
	 * 通过主键进行删除,这里最多只会删除一条数据 单个字段做主键时,可以直接写主键的值 联合主键时,key可以是实体类,也可以是Map
	 * 
	 * @param <T extend T>
	 */
	public int deleteByPrimaryKey(Object key) {
		logger.info("根据主键进行删除数据(通用方法)");
		return mapper.deleteByPrimaryKey(key);
	}

	/**
	 * 根据主键进行更新,这里最多只会更新一条数据 参数为实体类
	 * 
	 * @param <T extend T>
	 */
	public int updateByPrimaryKey(T record) {
		logger.info("根据主键更新【{}】数据，null值会被更新(通用方法)", BeanUtils.getProperty(record, "moduleName"));
		SysUser sysUser = SysUserUtils.getCacheLoginUser();
		if(sysUser != null){
			BeanUtils.setProperty(record, "updateBy", SysUserUtils.getCacheLoginUser().getId(), false);
		}
		BeanUtils.setProperty(record, "updateDate", new Date(), false);
		return mapper.updateByPrimaryKey(record);
	}

	/**
	 * 根据主键进行更新 只会更新不是null的数据
	 * 
	 * @param <T extend T>
	 */
	public int updateByPrimaryKeySelective(T record) {
		logger.info("根据主键更新【{}】数据，只会更新不是null的数据(通用方法)", BeanUtils.getProperty(record, "moduleName"));
		BeanUtils.setProperty(record, "updateDate", new Date(), false);
		SysUser sysUser = SysUserUtils.getCacheLoginUser();
		if(sysUser != null){
			BeanUtils.setProperty(record, "updateBy", SysUserUtils.getCacheLoginUser().getId(), false);
		}
		return mapper.updateByPrimaryKeySelective(record);
	}
	
	/**
	 * 单表逻辑删除(需要有delFlag)
	* @param bean 删除的实体类型
	* @return 影响行数
	 */
	public <M> int updateDelFlagToDelStatusById(Class<M> bean,String id){
		try {
			logger.info("单表逻辑删除【{}】数据(通用方法)", BeanUtils.getProperty(bean.newInstance(), "moduleName"));
		} catch (Exception e1) {
			logger.info("单表逻辑删除(通用方法)");
		} 
		String mapperName = StringUtils.uncapitalize(bean.getSimpleName())+"Mapper"; 
		Mapper<M> mapper = SpringContextHolder.getBean(mapperName);
		M m = null;
		try {
			m = bean.newInstance();
			BeanUtils.setProperty(m, "id", id, true);
			BeanUtils.setProperty(m, "delFlag", Constant.DEL_FLAG_DELETE, false);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return mapper.updateByPrimaryKeySelective(m);
	}

	/**
	 * 保存或者更新，根据传入id主键是不是null来确认
	 * 
	 * @param record
	 * @return 影响行数
	 */
	public int saveOrUpdate(T record) {
		logger.info("保存或者更新【{}】数据，自动判断(通用方法)", BeanUtils.getProperty(record, "moduleName"));
		int count = 0;
		if (BeanUtils.getProperty(record, "id") == null 
				|| BeanUtils.getProperty(record, "id").toString().trim().length() == 0) {
			String id = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
			BeanUtils.setProperty(record, "id", id, true);
			count = this.insertSelective(record);
		} else {
			count = this.updateByPrimaryKeySelective(record);
		}
		return count;
	}

	/**
	 * 单表分页
	 * @param pageNum 页码
	 * @param pageSize 条数
	 * @param record 条件实体
	 * @return
	 */
	public PageInfo<T> selectPage(int pageNum, int pageSize, T record) {
		logger.info("单表分页查询第【{}】数据(通用方法)",pageNum, BeanUtils.getProperty(record, "moduleName"));
		BeanUtils.setProperty(record, "delFlag", Constant.DEL_FLAG_NORMAL, false);
		PageHelper.startPage(pageNum, pageSize);
		return new PageInfo<T>(mapper.select(record));
	}
	
	/**
	 * @Description:(单表分页可排序) 
	 * @param:@param pageNum
	 * @param:@param pageSize
	 * @param:@param record
	 * @param:@param orderSqlStr (如:id desc)
	 * @return:PageInfo<T>
	 */
	public PageInfo<T> selectPage(int pageNum, int pageSize, T record,String orderSqlStr) {
		logger.info("单表分页查询第【{}】数据，根据【{}】排序(通用方法)",pageNum, BeanUtils.getProperty(record, "moduleName"), orderSqlStr);
		Example example = new Example(record.getClass(),false);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("delFlag", Constant.DEL_FLAG_NORMAL);
		Method [] methods = record.getClass().getMethods();
		for (Method entry : methods) {
			if (entry.getName().indexOf("get") == 0) {
				String fieldName = StringUtil.firstCharToLower(entry.getName().substring(3));
				try {
					if(entry.invoke(record) == null || "".equals(entry.invoke(record))) continue;
					criteria.andEqualTo(fieldName, entry.invoke(record));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		example.setOrderByClause(orderSqlStr);
		PageHelper.startPage(pageNum, pageSize);
		List<T> list = mapper.selectByExample(example);
		return new PageInfo<T>(list);
	}

	/**
	 * 删除前验证是否有关联(仅限于单表)
	* @param bean 实体class
	* @param fields 检查的实体属性
	* @param values 属性值
	* @return -1有关联
	 */
	public <M> int beforeDelete(Class<M> bean,Map<String, Object> params){
		try {
			logger.info("删除前验证【{}】数据是否有关联(通用方法)", BeanUtils.getProperty(bean.newInstance(), "moduleName"));
		} catch (Exception e1) {
			logger.info("删除前验证是否有关联(通用方法)");
		} 
		String mapperName = StringUtils.uncapitalize(bean.getSimpleName())+"Mapper"; 
		Mapper<M> mapper = SpringContextHolder.getBean(mapperName);
		M m = null;
		try {
			m = bean.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
//		m.setAll(params);
		int count = mapper.selectCount(m);
		return count>0 ? -1:count;
	}
	
	/**
	 * 有树结构的删除前验证(适应于两表)
	* @param id 删除的id
	* @param checkField 验证的属性名称
	* @param relateField 被删除表与其他表有关联的字段名称
	* @param beans class 第一个是要验证的class 第二个为删除的class
	* @return 未通过返回-1
	 */
	public int beforeDeleteTreeStructure(Object id,String checkField,String relateField,Class<?>... beans){
		logger.info("有树结构的删除前验证(通用方法)");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("checkField", StringConvert.camelhumpToUnderline(checkField));
		map.put("relateField", StringConvert.camelhumpToUnderline(relateField));
		for(int i=0;i<beans.length;i++ ){
			Class<?> cl = beans[i];
			Table table = cl.getAnnotation(Table.class);
			if(table == null){
				throw new RuntimeException("请配置table注释");
			}
			String tableName = table.name();
//			String tableName = StringConvert.camelhumpToUnderline(cl.getSimpleName());
			map.put("t"+i, tableName);
		}
		
		int count = baseMapper.beforeDeleteTreeStructure(map);
		return  count>0?-1:count;
	}
	
	/**
	 * 根据数据范围查找(单表操作)    
	* @param record 如果自定义别名等请set key:"userDataScope"
	 */
	public List<T> findEntityListByDataScope(final T record){
		logger.info("根据数据范围查询【{}】数据(通用方法)", BeanUtils.getProperty(record, "moduleName"));
		final String dataScope = BeanUtils.getProperty(record, "userDataScope").toString();
		String sql = new SQL(){{
            Class<?> entityClass = record.getClass();
            com.krm.common.mybatis.EntityHelper.EntityTable entityTable = EntityHelper.getEntityTable(entityClass);
            SELECT(com.krm.common.mybatis.EntityHelper.getAllColumns(entityClass));
            FROM(entityTable.getName());
            if (record != null) {
                final MetaObject metaObject = forObject(record);
                for (EntityHelper.EntityColumn column : entityTable.getEntityClassColumns()) {
                    Object value = metaObject.getValue(column.getProperty());
                    if (column.getJavaType().equals(String.class)) {
                        if (StringUtils.isNotEmpty((String) value)) {
                            WHERE(column.getColumn() + "=#{record." + column.getProperty() + "}");
                        }
                    } else if (value != null) {
                        WHERE(column.getColumn() + "=#{record." + column.getProperty() + "}");
                    }
                }
            }
            if(StringUtils.isNotBlank(dataScope)){
            	WHERE(dataScope);
            }
		}}.toString();
		@SuppressWarnings("unchecked")
		List<T> list = (List<T>) sqlMapper.selectList(sql, record.getClass());
		return list;
	}

	/**
	 * 生成主键
	 * @return
	 */
	public String generateId(){
		logger.info("开始生成主键(通用方法)");
		return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
	}
	
	public static MetaObject forObject(Object object) {
		return MetaObject.forObject(object, new DefaultObjectFactory(), new DefaultObjectWrapperFactory());
	}
	
	/**
	 * 执行动态SQL
	 * @param params
	 * @return
	 */
	public List<CommonEntity> exeuteDynamicSql(Map<String, Object> params){
		logger.info("#=================开始执行动态SQL========================#");
		return baseMapper.exeuteDynamicSql(params);
	}
}
