package org.beetl.json.loc;


import org.beetl.json.JsonTool;
import org.beetl.json.Location;
import org.beetl.json.LocationAction;
import org.beetl.json.OutputNode;
import org.beetl.json.action.ValueIgnoreAction;
import org.beetl.json.action.ThisExcludeAction;
import org.beetl.json.node.PojoAttributeNode;

/**指定类型
 * @author xiandafu
 *
 */
public class ClassTypeLocation extends Location {

	Class target = null;
	boolean exactMatch = false;
	String policy ;
	public ClassTypeLocation(Class target,boolean exactMatch,String policy){
		this.target = target;
		this.exactMatch = exactMatch;
		this.policy = policy ;
	}
	@Override
	public boolean match(OutputNode node,Class cls, String field,JsonTool tool) {
		boolean match = false ;
		if(exactMatch){
			match = target==cls;
		}else{
			match = target.isAssignableFrom(cls);
		}
		
		node.addInHeritedPolicy(this);
		return match ;	
	
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

}
