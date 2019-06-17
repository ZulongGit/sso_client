package com.krm.common.beetl.function;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.stereotype.Component;

import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import com.krm.common.constant.Constant;
import com.krm.common.utils.SerializeUtils;
import com.krm.common.utils.StringUtil;
import com.krm.web.sys.model.SysDict;
import com.krm.web.sys.model.SysDynamicDicts;
import com.krm.web.sys.service.SysDictService;
import com.krm.web.sys.service.SysDynamicDictsService;
import com.krm.web.util.SysUserUtils;

/**
 * 字典方法
 * 传入参数 type
 */
@Component
public class DictFunction{
	
	@Resource
	private SysDictService sysDictService;
	
	@Resource
	private SysDynamicDictsService sysDynamicDictsService;
	
	/**
	 * 根据类型和键值得到字典实体
	* @param type 如sys_data_scope等
	* @param value 
	 */
	public SysDict getDictByTypeAndValue(String type,String value){
		Table<String,String, SysDict> tableDicts = sysDictService.findAllDictTable();
		return tableDicts.get(type, value);
	}
	
	/**
	 * 根据类型得到字典列表
	* @param type 如sys_data_scope等
	 */
	public List<SysDict> getDictListByType(String type){
		if(type.indexOf("_dynamic") == -1){
			return (List<SysDict>) sysDictService.findAllMultimap().get(type);
		}
		Map<String, Object> params = Maps.newHashMap();
		params.put("type", type.substring(0, type.indexOf("_dynamic")));
		SysDynamicDicts sysDynamicDicts = sysDynamicDictsService.queryOne(params);
		String sql = SerializeUtils.converString2Object(sysDynamicDicts.getSqlContent()).toString();
		sql = StringEscapeUtils.unescapeHtml4(sql);
		try {
			sql = StringUtil.transfer(sql, params);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		StringBuilder sb = new StringBuilder();
		String[] group = sql.split("where|WHERE");
		if(group.length > 0){
			sb.append(group[0]);
		}
		for (int i = 1; i < group.length; i++) {
			if(StringUtil.isNotEmpty(sysDynamicDicts.getUserIdFieldBind())){
				sb.append(" left join SYS_USER u on t."+sysDynamicDicts.getUserIdFieldBind()+" = u.ID");
			}
			if(StringUtil.isNotEmpty(sysDynamicDicts.getOrganCodeFieldBind())){
				sb.append(" left join SYS_ORGAN o on t."+sysDynamicDicts.getOrganCodeFieldBind()+" = o.CODE");
			}
			sb.append(" WHERE ").append(group[i]);
			if(!SysUserUtils.getSessionLoginUser().isAdmin()){
				if(sysDynamicDicts.getDataScope().equals(Constant.DATA_SCOPE_SELF)){
					sb.append(" and t."+sysDynamicDicts.getUserIdFieldBind()+" = #{"+StringUtil.underlineToCamelhump(sysDynamicDicts.getUserIdFieldBind())+"}");
				}else if(sysDynamicDicts.getDataScope().equals(Constant.DATA_SCOPE_SELF)){
					sb.append(" and t."+sysDynamicDicts.getUserIdFieldBind()+" = #{"+StringUtil.underlineToCamelhump(sysDynamicDicts.getUserIdFieldBind())+"}");
				}
			}
		}
		params.put("sql", sb.toString());
		params.put("key", sysDynamicDicts.getKeyName());
		params.put("value", sysDynamicDicts.getValueName());
		try {
			params.put(StringUtil.underlineToCamelhump(sysDynamicDicts.getUserIdFieldBind()), SysUserUtils.getSessionLoginUser().getId());
			params.put(StringUtil.underlineToCamelhump(sysDynamicDicts.getOrganCodeFieldBind()), SysUserUtils.getSessionLoginUser().getOrganId());
		} catch (Exception e) {
		}
		return sysDynamicDictsService.exeuteSql(params);
	}
	
	/**
	 * 根据类型得到字典列表
	* @param type 如sys_data_scope等
	 */
	public List<SysDict> getDictListByType(String type, Map<String, Object> params){
		if(type.indexOf("_dynamic") == -1){
			return (List<SysDict>) sysDictService.findAllMultimap().get(type);
		}
		params.put("type", type.substring(0, type.indexOf("_dynamic")));
		SysDynamicDicts sysDynamicDicts = sysDynamicDictsService.queryOne(params);
		String sql = SerializeUtils.converString2Object(sysDynamicDicts.getSqlContent()).toString();
		sql = StringEscapeUtils.unescapeHtml4(sql);
		try {
			sql = StringUtil.transfer(sql, params);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		StringBuilder sb = new StringBuilder();
		String[] group = sql.split("where|WHERE");
		if(group.length > 0){
			sb.append(group[0]);
		}
		for (int i = 1; i < group.length; i++) {
			if(StringUtil.isNotEmpty(sysDynamicDicts.getUserIdFieldBind())){
				sb.append(" left join SYS_USER u on t."+sysDynamicDicts.getUserIdFieldBind()+" = u.ID");
			}
			if(StringUtil.isNotEmpty(sysDynamicDicts.getOrganCodeFieldBind())){
				sb.append(" left join SYS_ORGAN o on t."+sysDynamicDicts.getOrganCodeFieldBind()+" = o.CODE");
			}
			sb.append(" WHERE ").append(group[i]);
			if(!SysUserUtils.getSessionLoginUser().isAdmin()){
				if(sysDynamicDicts.getDataScope().equals(Constant.DATA_SCOPE_SELF)){
					sb.append(" and t."+sysDynamicDicts.getUserIdFieldBind()+" = #{"+StringUtil.underlineToCamelhump(sysDynamicDicts.getUserIdFieldBind())+"}");
				}else if(sysDynamicDicts.getDataScope().equals(Constant.DATA_SCOPE_SELF)){
					sb.append(" and t."+sysDynamicDicts.getUserIdFieldBind()+" = #{"+StringUtil.underlineToCamelhump(sysDynamicDicts.getUserIdFieldBind())+"}");
				}
			}
		}
		params.put("sql", sb.toString());
		params.put("key", sysDynamicDicts.getKeyName());
		params.put("value", sysDynamicDicts.getValueName());
		try {
			params.put(StringUtil.underlineToCamelhump(sysDynamicDicts.getUserIdFieldBind()), SysUserUtils.getSessionLoginUser().getId());
			params.put(StringUtil.underlineToCamelhump(sysDynamicDicts.getOrganCodeFieldBind()), SysUserUtils.getSessionLoginUser().getOrganId());
		} catch (Exception e) {
		}
		return sysDynamicDictsService.exeuteSql(params);
	}
	
	/**
	 * 全部字典
	 */
	public Collection<String> getAllDictType(){
		return sysDictService.findAllMultimap().keySet();
	}
	
	
	/**
	 * 全部字典(描述)
	 */
	public Collection<String> getAllDicts(){
		return sysDictService.getAllDicts().keySet();
	}
	
	
	public Map<String, String> findAllType(){
		Map<String, String> map = sysDictService.findAllType();
		Map<String, Object> params = Maps.newHashMap();
		List<SysDynamicDicts> list = sysDynamicDictsService.entityList(params);
		for (SysDynamicDicts sysDynamicDicts : list) {
			map.put(sysDynamicDicts.getType() + "_dynamic", sysDynamicDicts.getRemarks() + "【非固定】");
		}
		return map;
	}

	public Map<String, Object> getEmptyMap(){
		return Maps.newHashMap();
	}
}
