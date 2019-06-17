package org.beetl.json.action;

import org.beetl.json.ActionReturn;
import org.beetl.json.JsonWriter;
import org.beetl.json.OutputNode;

public class KeyChangeAction implements IKeyAction {

	String targetName;
	public KeyChangeAction(String targetName){
		this.targetName = targetName;
	}
	@Override
	public ActionReturn doit(Object o, OutputNode thisNode,JsonWriter w) {
		// TODO Auto-generated method stub
		return new ActionReturn(targetName);
	}

	
	// allow only one key change action for same key
	public int hashCode(){
		return 0;
	}
	public boolean equals(Object o){
		
		if(o instanceof KeyChangeAction ){
			return true ;
		}else{
			return false ;
		}
	}

}
