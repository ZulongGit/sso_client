package com.krm.common.mybatis.provider;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.reflection.MetaObject;

import com.google.common.collect.Maps;
import com.krm.common.base.BaseEntity;
import com.krm.common.constant.Constant;
import com.krm.common.mybatis.EntityHelper;
import com.krm.common.utils.BeanUtils;


public class CommonSqlProvider extends BaseProvider{
	
	public String beforeDeleteTreeStructureSql(Map<String, Object> params){
		final String tableNameOne = params.get("t0").toString();
		final String tableNameTwo = params.get("t1").toString();
		final String checkField = params.get("checkField").toString();
		final String relateField = params.get("relateField").toString();
		return new SQL(){{
			SELECT("count(0)");
			FROM(tableNameOne+" t0");
			FROM(tableNameTwo+" t1");
			WHERE("t1.parent_ids like '%' || #{id} ||',%' or t1."+relateField+"=#{id}");
//			WHERE("t1.id in(select id from "+tableNameTwo+" start with id = #{id} connect by PRIOR id = parent_id)");
			AND();
			WHERE("t0."+checkField+"=t1."+relateField);
			
		}}.toString();
	}
	
	public <T extends BaseEntity<T>> String findEntityListByDataScope(final T record){
		Map<String,Object> params = Maps.newHashMap();
		params.put(Constant.FIELD_DEL_FLAG, Constant.DEL_FLAG_NORMAL);
		final String dataScope = BeanUtils.getProperty(record, "userDataScope").toString();
		return new SQL(){{
//			Object entity = getEntity(record);
            Class<?> entityClass = record.getClass();
            com.krm.common.mybatis.EntityHelper.EntityTable entityTable = EntityHelper.getEntityTable(entityClass);
            SELECT(com.krm.common.mybatis.EntityHelper.getAllColumns(entityClass));
            FROM(entityTable.getName());
            if (record != null) {
                final MetaObject metaObject = forObject(record);
                for (EntityHelper.EntityColumn column : entityTable.getEntityClassColumns()) {
                    Object value = metaObject.getValue(column.getProperty());
                    if (column.getJavaType().equals(String.class)) {
                        if (isNotEmpty((String) value)) {
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
	}
	
	public String exeuteDynamicSql(Map<String, Object> params){
		String sql = params.get("sql").toString();
		return sql;
	}
	
	
}
