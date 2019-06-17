package org.beetl.json.action;

import org.beetl.json.ActionReturn;
import org.beetl.json.DirectOutputValue;
import org.beetl.json.JsonWriter;
import org.beetl.json.OutputNode;
import org.beetl.json.OutputNodeKey;

public class DirectOutpuAction implements IValueAction,IInstanceAction {

	String output = null;
	public DirectOutpuAction(String output){
		this.output = output;
	}
	
	

	@Override
	public ActionReturn doit(OutputNodeKey field,Object o, OutputNode thisNode,JsonWriter w) {
		return new ActionReturn(new DirectOutputValue(output),ActionReturn.BREAK);
	}

	@Override
	public ActionReturn doit(OutputNode thisNode) {
		return new ActionReturn(new DirectOutputValue(output),ActionReturn.BREAK);

	}

}
