package org.beetl.json.node;

import java.io.IOException;
import java.util.List;

import org.beetl.json.ActionReturn;
import org.beetl.json.JsonTool;
import org.beetl.json.JsonWriter;
import org.beetl.json.Location;
import org.beetl.json.LocationAction;
import org.beetl.json.OutputNode;
import org.beetl.json.OutputNodeKey;
import org.beetl.json.action.IValueAction;
import org.beetl.json.action.ValueIgnoreAction;

public class JavaObjectNode extends OutputNode {


	Class c;
	


	public JavaObjectNode(Class c) {
		this.c = c;
	}

	@Override
	public void render(OutputNodeKey field,Object obj, JsonWriter w) throws IOException {
		
		
		if (this.valueActions != null) {
			for (IValueAction valueAction : this.valueActions) {
				ActionReturn ar = valueAction.doit(field,obj, this,w);
				obj = ar.value;
				if (ar.process == ActionReturn.BREAK) {
					break;
				} else if (ar.process == ActionReturn.RETURN) {
					return;
				}
			}

		}
		w.writeKey(field);
		w.writeValue(obj);

	}

	@Override
	public void addActionIfMatchLocations(List<Location> list,JsonTool tool) {
		for (Location l : list) {
			LocationAction action = l.getAction();
			if (action instanceof IValueAction) {
				if(action instanceof ValueIgnoreAction){
					this.ignore = true;
				}else if (isMatch(l,tool)) {
					this.addValueAction((IValueAction) action);
				}
			}
			
			
		}

	}

	protected boolean isMatch(Location location,JsonTool tool) {
		return location.match(this,this.c, null,tool);
	}
	
	

}
