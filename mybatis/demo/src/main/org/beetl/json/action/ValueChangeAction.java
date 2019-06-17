package org.beetl.json.action;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.beetl.json.ActionReturn;
import org.beetl.json.JsonException;
import org.beetl.json.JsonWriter;
import org.beetl.json.OutputNode;
import org.beetl.json.OutputNodeKey;

public class ValueChangeAction implements IValueAction {

	String methodName = "" ;
	IValueAction action = null;
	public ValueChangeAction(String methodName){
		this.methodName = methodName;
	}
	
	public ValueChangeAction(String methodName,IValueAction action){
		this.methodName = methodName;
		this.action = action ;
	}
	

	@Override
	public ActionReturn doit(OutputNodeKey field,Object o, OutputNode thisNode,JsonWriter w) {
		if(o==null){
			return new ActionReturn(o,ActionReturn.BREAK);
		}else{
			try {
				Method m = o.getClass().getMethod(methodName, new Class[0]);
				Object value = m.invoke(o, new Object[0]);
				if(action!=null){
					ActionReturn newValue = (ActionReturn)action.doit(field,value, thisNode,w);
					return newValue;
				}else{
					return  new ActionReturn(value,ActionReturn.BREAK);
				}
				
			} catch (NoSuchMethodException e) {
				throw new JsonException(JsonException.ERROR, "No method:"+methodName);
			} catch (IllegalAccessException e) {
				throw new JsonException(JsonException.ERROR, "can not access method :"+methodName);
			} catch (IllegalArgumentException e) {
				throw new JsonException(JsonException.ERROR, "can not access:"+methodName);
			} catch (InvocationTargetException e) {
				throw new JsonException(JsonException.ERROR, e.getTargetException().toString());
			} 
		}
		
	}

}
