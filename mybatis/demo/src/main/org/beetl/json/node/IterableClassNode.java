package org.beetl.json.node;

import java.io.IOException;
import java.util.List;

import org.beetl.json.JsonTool;
import org.beetl.json.JsonWriter;
import org.beetl.json.Location;
import org.beetl.json.LocationAction;
import org.beetl.json.OutputNode;
import org.beetl.json.OutputNodeKey;
import org.beetl.json.action.IValueAction;
import org.beetl.json.loc.IndexLocation;

public class IterableClassNode extends  OutputNode {
	
	@Override
	public void render(OutputNodeKey field,Object obj, JsonWriter w) throws IOException {
		
		Object attrValue = obj;
		if(this.valueActions!=null)
		{
			NodeUtil.writeIterator (this, field,((Iterable)obj), w);
			
		}else{
			NodeUtil.writeUnkonw(this, field,obj, w, valueActions);
		}
	}

	@Override
	public void addActionIfMatchLocations(List<Location> list,JsonTool tool) {
		for(Location l:list){			
			
			if(l instanceof IndexLocation){				
				this.addIndexLocations((IndexLocation)l);
			}else{
				if(isMatch(l,tool)){	
					 LocationAction action = l.getAction();
					 if(action instanceof IValueAction){
						 this.addValueAction((IValueAction)action);
					 }
					
				 }			
				
			}
				
		}
		
	}
	
	protected boolean isMatch(Location location,JsonTool tool) {
		return location.match(this,Iterable.class,null,tool);
	}

	
	
	
	
	

}
