package org.beetl.json.node;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.beetl.json.ActionReturn;
import org.beetl.json.DirectOutputValue;
import org.beetl.json.JsonWriter;
import org.beetl.json.Location;
import org.beetl.json.LocationAction;
import org.beetl.json.OutputNode;
import org.beetl.json.OutputNodeKey;
import org.beetl.json.action.IValueAction;
import org.beetl.json.loc.IndexLocation;
import org.beetl.json.loc.IndexLocationList;


class NodeUtil {
	public static void writeUnkonw(OutputNode node,OutputNodeKey field,Object o,JsonWriter w, List<IValueAction>  valueActions) throws IOException{
		if(valueActions!=null){			
			for(IValueAction valueAction:valueActions){
				ActionReturn ar = valueAction.doit(field,o, node,w);	
				o = ar.value;			
				if(ar.process==ActionReturn.CONTINUE){
					continue ;
				}else if(ar.process==ActionReturn.BREAK){
					break;
				}
				else if(ar.process==ActionReturn.RETURN){
					return ;
				}
			}								
		}		
		
		if(o==null){
			w.writeKey(field);
			w.writeValue(null);
			return ;
		}
		if(o instanceof Iterable){
			writeIterator(node,field,((Iterable)o),w);
		}
//		else if(o instanceof Iterator){
//			writeIterator(node,field,(Iterator)o,w);
//		}
		else if( o instanceof Map){
			writeMap(node,field,(Map)o,w);
		}else if (o instanceof Enumeration){
			List list = Collections.list((Enumeration)o);			
			writeIterator(node,field,list,w);
		}else{
			
			Class cls = o.getClass();
			
			if(cls==DirectOutputValue.class){
				w.writeKeyValue(field, (DirectOutputValue)o);
			}else if( cls.isArray()){
				List list = Arrays.asList((Object[])o);
				writeIterator(node,field,list,w);
			}else if(cls.getPackage().getName().startsWith("java")){
				w.writeKeyValue(field, o);				
			} else{
				w.getTool().serializeJW(w,field,o,node.getInHeritedPolicy());
			}
				
				
			
		}
	}
	
	public  static void writeMap(OutputNode node,OutputNodeKey field,Map map,JsonWriter w) throws IOException{
		
	
		w.writeKey(field);
		IndexLocationList il = node.getIndexLocations();
		Iterator<Map.Entry> it = map.entrySet().iterator();		
		if(!it.hasNext()){
			w.writeEmptyBrace();
			return ;
		}
		w.addObjectPath(field, map);
		w.writeScopeChar('{');		
		do {
			
			Map.Entry item = it.next();
			OutputNodeKey key = new OutputNodeKey(item.getKey().toString());		
			renderItem(node, key, item.getValue(), il, w);
			
			
		}while(it.hasNext());
		
		w.writeScopeChar('}');
		w.removeObjectPath(map);
		
		
	
	}
	
	public static void writeIterator(OutputNode node,OutputNodeKey field,Iterable list,JsonWriter w) throws IOException{
	
		Iterator it = null;
		try{
			it = list.iterator();
		}catch(Exception ex){
			w.getTool().attributeErrorHander.process(list, field.getKey().toString(), w, ex);
			return;
		}
		w.writeKey(field);	
		IndexLocationList il = node.getIndexLocations();
		int i = 0;
		if(!it.hasNext()){
			w.writeEmptySqure();
			return ;
		}
		w.writeScopeChar('[');
		w.addObjectPath(field,list );
		
		do {
			
			Object item = it.next();
			OutputNodeKey key = new OutputNodeKey(i,true);		
			renderItem(node, key, item, il, w);
			i++;
			
		}while(it.hasNext());
		
		w.writeScopeChar(']');
		w.removeObjectPath(list);
		
	}
	
	public static void renderItem(OutputNode node,OutputNodeKey key,Object item,IndexLocationList il,JsonWriter w) throws IOException{
		if(il!=null){
			List<IndexLocation> actions = il.match(key.getKey());
			if(actions.size()!=0){
				for(IndexLocation indexLocation:actions){
					LocationAction act = indexLocation.getAction();
					if(act instanceof IValueAction  ){
						IValueAction va = (IValueAction)act;
						ActionReturn ar = va.doit(key, item, node, w);
						item = ar.value;
						if(ar.process==ActionReturn.RETURN){
							return ;
							
						}else if(ar.process==ActionReturn.BREAK){
							break;
						}
					}
				}
			}			
			
			List<Location> policy = il.getMatchPolicy(key.getKey());
			if(policy!=null){
				List<Location> inherit = node.getInHeritedPolicy();
				if(inherit!=null){
					policy.addAll(inherit);
				}
				w.getTool().serializeJW(w,key,item,policy);
				return ;
			}
			
		}
		//正常渲染
		if(item==null){
			if(key.isIndex()){
				w.writeScopeNull();
			}else{
				w.writeKeyValue(key, null);
			}
			
		}else{				
			w.getTool().serializeJW(w,key,item,node.getInHeritedPolicy());
		}
		
	
	
	}
}
