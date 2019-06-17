package com.krm.common.beetl.function;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.krm.common.mybatis.SqlMapper;

@Component
public class AutoNoGenFunction{

	
	@Resource
	private SqlMapper sqlMapper;
	
	public Integer getDynamicNum(String field, String tableName){
		try {
			String countSql = "select count(1) as count from "+ tableName + " where "+ field +" is not null and "+ field +">=1000";
			Map<String,Object> count = sqlMapper.selectOne(countSql);
			if(!count.get("count").toString().equals("0")){
//				String sql = "select max(Integer("+field+"))+1 as num from "+ tableName;
				String sequence = tableName.toUpperCase() + "_" + field.toUpperCase() + "_SEQ";
				String sql = "select "+sequence+".nextval as num from sysibm.sysdummy1";
				Map<String,Object> result = sqlMapper.selectOne(sql);
				String num = result.get("num").toString();
				if(num.contains(".")){
					num = num.substring(0, num.indexOf("."));
				}
				if(result != null){
					return Integer.parseInt(num) + 1000;
				}
			}
			return 1000;
		} catch (Exception e) {
			return -1;
		}
	}

}
