package org.beetl.json.util;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class JsonUtil {
	public static String getGetMethod(String attrName)
	{
		StringBuilder mbuffer = new StringBuilder("get");
		mbuffer.append(attrName.substring(0, 1).toUpperCase()).append(attrName.substring(1));
		return mbuffer.toString();
	}
	
	public static String getAttribute(Method getter)
	{
		String methodName = getter.getName();
		StringBuilder mbuffer = new StringBuilder("");
		if(methodName.startsWith("is")){
			mbuffer.append(Character.toLowerCase(methodName.charAt(2)));
			mbuffer.append(methodName.substring(3));	
		}else{
			mbuffer.append(Character.toLowerCase(methodName.charAt(3)));
			mbuffer.append(methodName.substring(4));	
		}
		
		
		return mbuffer.toString();
	}
	
	public static boolean isGetterMethod(Method m){
		if(m.getName().startsWith("get")){
			if(m.getParameterTypes().length==0&&!Modifier.isStatic(m.getModifiers())){
				return true;
			}
		}else if(m.getName().startsWith("is")){
			if(m.getParameterTypes().length==0&&!Modifier.isStatic(m.getModifiers())){
				return true;
			}
		}
		return false;
	}
}
