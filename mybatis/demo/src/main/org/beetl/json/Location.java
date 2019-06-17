package org.beetl.json;

import java.util.ArrayList;
import java.util.List;

import org.beetl.json.action.AsJsonAction;
import org.beetl.json.action.ClassCycleIgnoreAction;
import org.beetl.json.action.ClassCycleIncludeAction;
import org.beetl.json.action.DirectOutpuAction;
import org.beetl.json.action.FormatAction;
import org.beetl.json.action.IValueAction;
import org.beetl.json.action.IfAction;
import org.beetl.json.action.KeyChangeAction;
import org.beetl.json.action.ValueIgnoreAction;
import org.beetl.json.loc.IndexLocation;
import org.beetl.json.action.ThisExcludeAction;
import org.beetl.json.action.ThisIncludeAction;
import org.beetl.json.action.ThisOrderAction;
import org.beetl.json.action.ValueChangeAction;

/**
 * 描述了序列化规则一个loc:action
 * @author xiandafu
 *
 */
public abstract class Location {

	protected LocationAction action = null;	
	protected JsonTool tool = null;
	protected Location nextLoc = null;
	
	public LocationAction getAction() {
		return action;
	}
	public void setAction(LocationAction action) {
		this.action = action;
	}
	
	
	/** 判断是否是此位置
	 * @param type
	 * @param field
	 * @return
	 */
	public abstract boolean match(OutputNode node,Class type, String field,JsonTool tool);
	
	
	protected boolean testNextLoc(boolean selfMatch,OutputNode node,Class type, String field,JsonTool tool){
		if(selfMatch){
			if(nextLoc!=null){
				if(nextLoc instanceof IndexLocation){
					node.addIndexLocations((IndexLocation)nextLoc);
				}else{
					node.addInHeritedPolicy(nextLoc);
					
				}
				return false ;
			}else{
				return true ;
			}
		}else{
			return false;
		}
	}
	public LocationAction parseAction(String act){
		
		LocationAction action = null;
		if(act.startsWith("f")){
			action = parserFormat(act);
			
		}else if(act.startsWith("nn")){
			action = parserNewName(act);
			
		}
		else if(act.startsWith("?")){
			action = parserIfAction(act);
		}else if(act.startsWith("$.")){
			action = parserValueChange(act);
		}else if(act.startsWith("ci")){
			action = parserCycleIngore(act);
		}else if(act.startsWith("cu")){
			action = parserCycleUsing(act);
		}else if(act.startsWith("!")) {
			action = parserUserDefine(act);
			
		}else if(act.startsWith("i")){			
			action =  parserIgnoreAction(act);			
		}else if(act.startsWith("u")){
			 action = parserAttributeUsing(act);
		}else if(act.startsWith("o")){
			 action = parserOrder(act);
		}else if(act.startsWith("->")){
			action = parserDirectOutput(act);
		}else if(act.equals("asJson")){
			action = new AsJsonAction();
		}
		
		return action;
		
	}
	
	protected LocationAction  parserIgnoreAction(String act){
		
		LocationAction action = null;
		if(act.length()==1){
			action = new ValueIgnoreAction();
		}else{
			String names = act.substring(2,act.length()-1);
			action = new ThisExcludeAction(names);
		}
		return action;
	}
	
	protected LocationAction  parserAttributeUsing(String act){
				
		LocationAction action = null;
		String names = act.substring(2,act.length()-1);
		action = new ThisIncludeAction(names);
		return action;
		
	}
	
	protected LocationAction  parserOrder(String act){
		LocationAction action = null;
		//排序
		String names = act.substring(2,act.length()-1);
		action = new ThisOrderAction(names);
		return action;
		
	}
	
	protected LocationAction  parserDirectOutput(String act){
		String output = act.substring(2);
		return new DirectOutpuAction(output);
		
	}
	
	protected LocationAction  parserFormat(String act){
		LocationAction action = null;
		//格式化操作 f'yyyy-MM-dd'
		if(act.length()>1&&act.charAt(1)=='/'){
			String format = act.substring(2,act.length()-1);
			action = new FormatAction(format);
		}else{			
			action = new FormatAction("");
		
		}
		return action;
		
	}
	
	protected LocationAction  parserNewName(String act){
		//修改keyName
		String targetName = act.substring(3,act.length()-1);
		return new KeyChangeAction(targetName);
		
	}
	
	protected LocationAction  parserIfAction(String act){
		LocationAction action = null;
		int index = act.indexOf("->");
		String condtion = act.substring(1,index);
		String output = act.substring(index+2,act.length());
		action = new IfAction(condtion,output);
		return action;
		
	}
	
	protected LocationAction  parserValueChange(String act){
		int index = act.indexOf("->");
		
		if(index!=-1){
			String methodName = act.substring(2,index);
			String chainActionString = act.substring(index+2);
			IValueAction chainAction = (IValueAction)parseAction(chainActionString);
			ValueChangeAction va = new ValueChangeAction(methodName,chainAction);
			return va;
			
		}else{
			String methodName = act.substring(2);
			ValueChangeAction va = new ValueChangeAction(methodName);
			return va;
			
		}
		
	}
	
	protected LocationAction  parserCycleIngore(String act){
		LocationAction action = null;
		//循环中排除
		String names = act.substring(3,act.length()-1);
		action = new ClassCycleIgnoreAction(names);
		return action;
		
	}
	
	protected LocationAction  parserCycleUsing(String act){
		LocationAction action = null;
		//循环中包括
		String names = act.substring(3,act.length()-1);
		action = new ClassCycleIncludeAction(names);
		return action;
		
	}
	
	protected LocationAction  parserUserDefine(String act){
		LocationAction action = null;
		//用户自定义Action
//		int index = act.indexOf('/');
		String userActionName;
//		String userActionPara;
		userActionName = act.substring(1);
		action = tool.getAction(userActionName);
		if(action==null){
			throw new JsonException(JsonException.ERROR, "找不到"+userActionName);
		}
		
		
		return action;
		
	}
	public JsonTool getTool() {
		return tool;
	}
	public void setTool(JsonTool tool) {
		this.tool = tool;
	}
	public Location getNextLoc() {
		return nextLoc;
	}
	public void setNextLoc(Location nextLoc) {
		this.nextLoc = nextLoc;
	}
	
	
}
