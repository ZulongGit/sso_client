package org.beetl.json.loc;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.beetl.json.JsonTool;
import org.beetl.json.Location;
import org.beetl.json.OutputNode;
import org.beetl.json.node.JavaObjectNode;

/**
 * 匹配所有属性 ，＊：!hibernate
 * @author xiandafu
 *
 */
public class MatchAllFieldLocation extends Location {
	Pattern p = null;
	public MatchAllFieldLocation(){
	}
	public MatchAllFieldLocation(String attrName){
		attrName = attrName.substring(1,attrName.length()-1);
		p = Pattern.compile(attrName);
	}
	public boolean match(OutputNode node, Class type, String field,JsonTool tool) {
		node.addInHeritedPolicy(this);
		if(p==null){
			return true;
		}else{
			Matcher m = p.matcher(field);
			return m.find();
		}
		
	}
	
	
	

}
