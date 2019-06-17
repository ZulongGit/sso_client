package org.beetl.json.node;

import java.io.IOException;
import java.util.Map;

import org.beetl.json.JsonTool;
import org.beetl.json.JsonWriter;
import org.beetl.json.Location;
import org.beetl.json.OutputNodeKey;

public class MapClassNode extends IterableClassNode {
	
	
	@Override
	public void render(OutputNodeKey field,Object obj, JsonWriter w) throws IOException {
		
		
		Object attrValue = obj;
		if(valueActions!=null){
			NodeUtil.writeMap (this,field, ((Map)obj), w);
			
		}else{
			NodeUtil.writeUnkonw(this,field, obj, w, valueActions);
		}
	}
	protected boolean isMatch(Location location,JsonTool tool) {
		return location.match(this,Map.class,null,tool);
	}

}
