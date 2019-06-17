package org.beetl.json.util;

import java.lang.reflect.Method;

public class MethodInvoker {
	Class c;
	String name;
	public Method m = null;
	public int  returnType = Type.UNKONW_TYPE;
	String attrName = null;
	public MethodInvoker(Class c,Method m,String attrName){
		this.m = m;
		this.attrName = attrName;
		this.returnType = Type.getType(m.getReturnType());
		m.setAccessible(true);	
		
		
	}
	
	public Object invoke(Object ins){
		try{
			Object value = m.invoke(ins, new Object[0]);
			return value;
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}
		
	}
}
