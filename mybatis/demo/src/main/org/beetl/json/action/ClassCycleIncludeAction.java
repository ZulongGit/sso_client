package org.beetl.json.action;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.beetl.json.ActionReturn;
import org.beetl.json.JsonWriter;
import org.beetl.json.OutputNode;
import org.beetl.json.OutputNodeKey;
import org.beetl.json.node.PojoAttributeNode;
import org.beetl.json.node.PojoNode;

/**
 * 循环的时候包含某些属性（导致循环的元素)
 * @author joelli
 *
 */
public class ClassCycleIncludeAction implements IValueAction {

	String[] names = null;
	public ClassCycleIncludeAction(String names){
		this.names = names.split(",");
	}
	
	

	@Override
	public ActionReturn doit(OutputNodeKey field,Object obj,OutputNode thisNode,JsonWriter w) {
		if(thisNode instanceof PojoAttributeNode){
			return new ActionReturn(obj,ActionReturn.CONTINUE);
		}
		//  pojo node,direct render
		try{
			if(w.containObjectInPath(obj)){
				w.writeKey(field);
				String path = w.getObjectPath(obj);
				w.writeScopeChar('{');	
				w.writeKeyValue("$ref", path);
				cycleRender((PojoNode)thisNode,field,obj,w);
				w.writeScopeChar('}');
				return new ActionReturn(obj,ActionReturn.RETURN);
			}else{
				return new ActionReturn(obj,ActionReturn.CONTINUE);
			}

			
			
		}catch(IOException  ioe){
			throw new RuntimeException(ioe);
		}
		
		
		
	}

	
	public void cycleRender(PojoNode pojo,OutputNodeKey field,Object obj, JsonWriter w) throws IOException {
		
//		w.writeScopeChar('{');
		List<PojoAttributeNode> attrs =  pojo.getAttrs();
		Iterator<PojoAttributeNode> it = attrs.iterator();
		if(!it.hasNext()){
//			w.writeScopeChar('}');
			return ;
		}
		boolean firstWrite = true ;
		while(true){			
			PojoAttributeNode node = it.next();
			if(containAttr(node.getAttrName())){
			
				node.render(field,obj, w);				
				firstWrite = true ;
			}		
			if(!it.hasNext()){
				return ;
			}
		
		}
		
	}
	
	private boolean containAttr(String attr){
		for(String s:names){
			if(s.equals(attr)){
				return true;
			}
		}
		return false;
	}

}
