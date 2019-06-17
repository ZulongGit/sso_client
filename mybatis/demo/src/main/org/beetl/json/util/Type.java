package org.beetl.json.util;

import java.util.Map;

public class Type {
	public static final int  JAVA_TYPE = 0;
	public static final int  Collection_TYPE = 1;
	public static final int  MAP_TYPE = 2;
	public static final int  ARRAY_TYPE = 3;
	public static final int  POJO_TYPE = 4;
	public static final int  UNKONW_TYPE = 5;
	public static int getType(Class c){
		if(c==Object.class) return UNKONW_TYPE;
		else if(Iterable.class.isAssignableFrom(c)) return Collection_TYPE;
		else if(Map.class.isAssignableFrom(c)) return MAP_TYPE;
		else if(c.isArray()){
			return ARRAY_TYPE;
		}else{
			Package pkg = c.getPackage();
			if(pkg==null){
				return JAVA_TYPE;
			}else{
				String pkgName = pkg.getName();
				if(pkgName.startsWith("java")){
					return JAVA_TYPE;
				}else{
					return POJO_TYPE;
				}
			}
			
		}
	}
}
