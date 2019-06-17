package org.beetl.json.node;

import java.io.IOException;
import java.util.Arrays;

import org.beetl.json.JsonWriter;
import org.beetl.json.OutputNodeKey;

public class ArrayNode extends  IterableClassNode {
	
	@Override
	public void render(OutputNodeKey field,Object obj, JsonWriter w) throws IOException {	
	
		if(valueActions!=null){
			NodeUtil.writeIterator(this, field,Arrays.asList((Object[])obj), w);
		}else{
			NodeUtil.writeUnkonw(this, field,obj, w, valueActions);
		}
		
	}

	
	

}
