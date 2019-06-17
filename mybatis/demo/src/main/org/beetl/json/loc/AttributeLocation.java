package org.beetl.json.loc;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.beetl.json.JsonTool;
import org.beetl.json.Location;
import org.beetl.json.OutputNode;

/**
 * a.b.c:action
 * @author xiandafu
 *
 */
public class AttributeLocation extends Location {

	String attrName ;
	boolean isReg = false ;
	Pattern p = null;
	public AttributeLocation(String attrName){
		this.attrName = attrName;
		if(attrName.startsWith("/")){
			isReg = true ;
			attrName = attrName.substring(1,attrName.length()-1);
			p = Pattern.compile(attrName);
		}
	}
	@Override
	public boolean match(OutputNode node,Class type, String field,JsonTool tool) {
		
		boolean match =  false ;
		if(isReg){
			Matcher m = p.matcher(field);
			match = m.find();
				
		}else{
			match = field.equals(attrName);
		}
		
		return super.testNextLoc(match, node, type, field, tool);
	}
	
	
}
