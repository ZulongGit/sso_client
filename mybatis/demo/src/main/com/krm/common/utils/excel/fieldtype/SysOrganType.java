package com.krm.common.utils.excel.fieldtype;

import com.krm.web.sys.model.SysOrgan;
import com.krm.web.util.SysUserUtils;

/**
 * 字段类型转换
 * @author 
 * @version 
 */
public class SysOrganType {

	/**
	 * 获取对象值（导入）
	 */
	public static Object getValue(String val) {
		if(!val.equals("")){
			for (SysOrgan e : SysUserUtils.getUserOrgan()){
				if (val.trim().equals(e.getName())){
					return e.getCode();
				}
			}
			
		}
		return null;
	}

	/**
	 * 设置对象值（导出）
	 */
	public static String setValue(Object val) {
		if(val != null){
			for (SysOrgan e : SysUserUtils.getUserOrgan()){
				if (val.toString().equals(e.getCode())){
					return e.getName();
				}
			}
		}
		return null;
	}
}
