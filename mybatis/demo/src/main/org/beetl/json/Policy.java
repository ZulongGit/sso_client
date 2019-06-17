package org.beetl.json;

import java.io.IOException;
import java.io.Writer;

import javax.activation.FileDataSource;

import org.beetl.json.util.NoLockStringWriter;

public class Policy {
	static Policy directPolicy = new DirectOutputValuePolicy(null);
	public OutputNode  ndoe ;
	public Policy(OutputNode node){
		this.ndoe = node;
	}
	public String toJson(Object obj,JsonTool tool)throws IOException {
		NoLockStringWriter writer = new NoLockStringWriter();
		toJson(writer,obj,tool);		;
		String json =  writer.toString();
		
		return json;
	}
	
	public void toJson(Writer writer,Object obj,JsonTool tool) throws IOException{		
		toJson(new JsonWriter(writer,tool),null,obj);
	}
	
	public void toJson(JsonWriter writer,OutputNodeKey field,Object obj) throws IOException{
		
		ndoe.render(field,obj, (writer));
	}
	
	static class DirectOutputValuePolicy extends Policy{

		public DirectOutputValuePolicy(OutputNode node) {
			super(node);
			// TODO Auto-generated constructor stub
		}
		public void toJson(JsonWriter writer,OutputNodeKey field,Object obj) throws IOException{
			
			writer.writeKeyValue(field.getKey().toString(), (DirectOutputValue)obj);
		}

	}
	
}
