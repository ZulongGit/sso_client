package org.beetl.json.loc;


import java.util.Date;

import org.beetl.json.JsonTool;
import org.beetl.json.Location;
import org.beetl.json.OutputNode;

/**内置类型
 * @author xiandafu
 *
 */
public class TypeLocation extends Location {

	String type = "";
	public TypeLocation(String type){
		this.type = type;
	}
	@Override
	public boolean match(OutputNode node,Class cls, String field,JsonTool tool) {
			if(type.equals("n")){
				if(cls==Double.class||cls==double.class){
					return true;
				}else{
					return false;
				}
			}else if(type.equals("d")){
				if(Date.class.isAssignableFrom(cls)){
					return true;
				}else{
					return false;
				}
			}else if(type.equals("c")){
				if(Iterable.class.isAssignableFrom(cls)){
					return true ;
				}else{
					return false ;
				}
			}else if(type.equals(cls.getName())){
				return true;
			}
			else{
				return false;
			}
	
	
	
	}

}
