package org.beetl.json.action;

import java.util.Collection;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.beetl.json.ActionReturn;
import org.beetl.json.DirectOutputValue;
import org.beetl.json.JsonWriter;
import org.beetl.json.OutputNode;
import org.beetl.json.OutputNodeKey;

public class IfAction implements IValueAction {

	String condtion;
	String output;
	public IfAction(String condtion,String output){
		this.condtion = condtion;
		this.output = output;
	}
	

	@Override
	public ActionReturn doit(OutputNodeKey field,Object o, OutputNode thisNode,JsonWriter w){
		
		DirectOutputValue value = new DirectOutputValue(output);
		ActionReturn ret =  new ActionReturn(value,ActionReturn.BREAK);
		if(condtion.equals("empty")){
			if(o==null){
				return ret;
			}else if(o instanceof String){
				if(((String)o).length()==0){
					return ret;
				}
			}else if( o instanceof Collection){
				if(((Collection)o).size()==0){
					return ret;
				}
			}else if(o instanceof Map){
				if(((Map)o).size()==0){
					return ret;
				}
			}else if(o.getClass().isArray()){
				Object[] array = (Object[])o;
				if(array.length==0){
					return ret;
				}
			}else{
				return ret;
			}
		}else	if(condtion.equals("null")){
			if(o==null){
				return ret;
			}
		}else if(o instanceof Number&&isNumeric(condtion)){
			Number  nu = (Number)o;
			double cond = Double.parseDouble(condtion);
			if(nu.doubleValue() == cond){
				return ret;
			}
		}
		// TODO Auto-generated method stub
		return new ActionReturn(o,ActionReturn.CONTINUE);
	}

	private boolean isNumeric(String str){ 
		   Pattern pattern = Pattern.compile("[0-9]*"); 
		   Matcher isNum = pattern.matcher(str);
		   if( !isNum.matches() ){
		       return false; 
		   } 
		   return true; 
		}
	
}
