package org.beetl.json;

import java.util.ArrayList;
import java.util.List;

import org.beetl.json.annotation.Json;
import org.beetl.json.annotation.JsonPolicy;
import org.beetl.json.loc.AttributeLocation;
import org.beetl.json.loc.ClassTypeLocation;
import org.beetl.json.loc.IndexLocation;
import org.beetl.json.loc.MatchAllFieldLocation;
import org.beetl.json.loc.ThisLocation;
import org.beetl.json.loc.TypeLocation;

/** 序列化规则解析
 * @author xiandafu
 *
 */
public class PolicyParser {
	
	
	public static List<Location> parseAnnotation(Class cls,JsonTool tool){
	
		Json json = (Json)cls.getAnnotation(Json.class);
		
		if(json!=null){
			JsonPolicy[] policys = json.policys();
			List<Location> list = new ArrayList<Location>(policys.length);
			for(JsonPolicy po:policys){	
				list.add(parse(cls,po.location(),po.action(),tool));
			}	
		    Class parent = cls.getSuperclass();
		    if(!tool.isStopParse(parent.getName())){
		    	List  supers = parseAnnotation(parent,tool);
		    	// 父类加在前面，在Node节点，同样的规则，前面的总会被后面的覆盖
				list.addAll(0,supers);
				return list;		    
		    }
			return list;
			
		}else{
			return new ArrayList();
		}
		
	}
	
	
	public static Location  parse(Class c,String loc,String act,JsonTool tool){
		boolean parseAction = true ;
		LocationAction action = null;
		Location location = null;
		//先处理位置=====
		if(loc.startsWith("~")){
			//this type of 
			String cmd = loc.substring(1);
			if(cmd.equals("n")){
				//浮点type
				location = new TypeLocation(cmd);
			}else if(cmd.equals("d")){
				// date type
				location = new TypeLocation(cmd);
			}else if(cmd.equals("*")){
				if(c==null){
					c = Object.class;
				}
				location = new ThisLocation(c);
				
			}else if(cmd.equals("c")){
				// collection type
				location = new TypeLocation(cmd);
			}else if(cmd.startsWith("L/")){
				//某个类
				String clsName = cmd.substring(2,cmd.length()-1);
				if(clsName.endsWith("*")){
					location = new ClassTypeLocation(getClassByName(clsName.substring(0,clsName.length()-1),tool),false,loc+":"+act);
					
				}else{
					location = new ClassTypeLocation(getClassByName(clsName,tool),true,loc+":"+act);
				}
				
			}
			else{
				throw new UnsupportedOperationException();
			}
		}else if(loc.startsWith("[")){
			int index = loc.indexOf("]");
			String strRef = loc.substring(1,index);
			Object ref = null;
			try{
				ref = Integer.parseInt(strRef);
			}catch(Exception ex){
				ref = strRef;
			}
			
			if(index==loc.length()-1){		
//				[*]:action
				location = new IndexLocation(ref);	
			}else{
				if(loc.charAt(index+1)=='.'){
					// [1].aa:action
					String newLocation = loc.substring(index+2);
					location = new IndexLocation(ref);
					Location nextLoc  = parse(c, newLocation, act, tool);
					parseAction = false ;
					location.setNextLoc(nextLoc);
				}else if( loc.charAt(index+1)=='[') {
					//[1][2]:action
					String nextLocation = loc.substring(index+1);	
					location = new IndexLocation(ref);		
					Location nextLoc  = parse(c, nextLocation, act, tool);
					parseAction = false ;
					location.setNextLoc(nextLoc);
				}else{
					throw new JsonException(JsonException.ERROR, "解析错:"+loc);
				}
			}
			
			
		}
		else if(loc.startsWith("*./")){
				String reg = loc.substring(2,loc.length());
				location = new MatchAllFieldLocation(reg);
		}

		else if(loc.equals("*")){
			location = new MatchAllFieldLocation();
			
		}else if(loc.startsWith("/")){
			// 属性正则表达式
			Object[] result = parseRegAttribute(c, loc, act, tool);
			location = (Location)result[0];
			parseAction = (Boolean)result[1];
			
		}
		else{
			// 以属性名开头的，aa,aa[0].  aa[0].cc ,
			Object[] result = parseAttribute(c, loc, act, tool);
			location = (Location)result[0];
			parseAction = (Boolean)result[1];
			
			
		}
		location.setTool(tool);
		if(parseAction){
			action = location.parseAction(act);
			location.setAction(action);	
		}
		
		return location;
	}
	
	protected static Object[] parseAttribute(Class c,String loc,String act,JsonTool tool){
		Location location = null;
		String attrName = loc;
		char[] chs = attrName.toCharArray();
		boolean findSub = false ;
		for(int i=0;i<chs.length;i++){
			if(chs[i]=='.'||chs[i]=='['){
				findSub = true ;
				String thisAttr = attrName.substring(0,i);
				String nextLocStr = null;
				if(chs[i]=='.'){
					nextLocStr = attrName.substring(i+1);
				}else{
					nextLocStr = attrName.substring(i);
				}
				location = new AttributeLocation(thisAttr);
				location.setNextLoc(parse(c, nextLocStr, act, tool));
				break;
				
			}
		}
		if(!findSub){
			location = new AttributeLocation(attrName);
			return new Object[]{location,true};
		
		}else{
			return new Object[]{location,false};
			
		}
	}
	
	protected static Object[] parseRegAttribute(Class c,String loc,String act,JsonTool tool){
		Location location = null;
		String attrName = loc;
		// the /xxxx/
		int index = attrName.indexOf('/', 1);
		if(index==0) throw new JsonException(JsonException.ERROR, "must /xxx/");
		attrName = attrName.substring(0,index+1);
		if(loc.length()>index+1){
			String nextLocStr = null;
			char ch = loc.charAt(index+1);
			if(ch=='.'){
				nextLocStr = attrName.substring(index+1);
			}else{
				nextLocStr = attrName.substring(index);
			}
			location = new AttributeLocation(attrName);
			location.setNextLoc(parse(c, nextLocStr, act, tool));
			return new Object[]{location,false};
		}else{
			location = new AttributeLocation(attrName);
			return new Object[]{location,true};
			
		}
	
	}
	
	public  static List<Location> parseStringPolicy(Class c, String policy,JsonTool jsonTool) {
		List<Location> list = new ArrayList<Location>();
		char[] chars = policy.toCharArray();
		int start = 0;
		boolean findKey = true;
		String key = null;
		String value = null;
		boolean paraStat = false;
		;
		for (int i = 0; i < chars.length; i++) {
			char ch = chars[i];
			if (findKey) {
				if (ch == ':') {
					findKey = false;
					key = new String(chars, start, i - start);
					start = i + 1;
				} else {
					continue;
				}
			} else {
				if (!paraStat) {
					if (ch == ',') {
						findKey = true;
						value = new String(chars, start, i - start);
						start = i + 1;
						Location location = PolicyParser.parse(c, key, value, jsonTool);

						list.add(location);
					} else if (ch == '/') {
						paraStat = true;
					}
				} else if (ch == '/') {
					paraStat = false;
				}
			}
		}
		if (!findKey && start != chars.length) {
			value = new String(chars, start, chars.length - start);
			Location location = PolicyParser.parse(c, key, value, jsonTool);
			list.add(location);
		}
		return list;

	}
	
	
	protected static Class getClassByName(String clsName,JsonTool tool){
			String name  = tool.getNameByAlias(clsName);		
			try{
				return Class.forName(name);
			}catch (ClassNotFoundException e) {
				throw new JsonException(JsonException.ERROR, "找不到类"+name);
			}
		
	}
	
	
	
}
