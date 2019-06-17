package org.beetl.json;

import java.util.Comparator;
import java.util.Map;

import org.beetl.json.node.PojoAttributeNode;

/**
 * 属性排序，java原始类型排在前面，集合类型排在后面，业务对象排在中间。同类型的按照名字排序
 * @author xiandafu
 *
 */
public class AttributeComparator implements Comparator {

	
	public int compare(Object o1, Object o2) {
		PojoAttributeNode n1 = (PojoAttributeNode)o1;
		PojoAttributeNode n2 = (PojoAttributeNode)o2;
		int s1 = score(n1.getAttrType());
		int s2 = score(n2.getAttrType());
		if(s1==s2){
			return n1.getAttrName().compareTo(n2.getAttrName());
		}else{
			return s1-s2;
		}
		
	}
	
	private int score(Class type){
		if(type.isArray()){
			return  3;
		}
		Package pkg = type.getPackage();
		if(pkg==null){
			//原始类型
			return 1;
		}
		String pkgName = type.getPackage().getName();
		if(pkgName.startsWith("java.lang")){
			return  1;
		}else if(pkgName.startsWith("java.util")){
			if(Iterable.class.isAssignableFrom(type)||Map.class.isAssignableFrom(type)){
				return 4;
			}else{
				return 3;
			}
			
		}else{
			return  2;
		}
		
	}
}
