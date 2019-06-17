package org.beetl.json.node;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.beetl.json.ActionReturn;
import org.beetl.json.JsonTool;
import org.beetl.json.JsonWriter;
import org.beetl.json.Location;
import org.beetl.json.LocationAction;
import org.beetl.json.OutputNode;
import org.beetl.json.OutputNodeKey;
import org.beetl.json.action.IKeyAction;
import org.beetl.json.action.IValueAction;
import org.beetl.json.action.ValueIgnoreAction;
import org.beetl.json.util.JsonUtil;
import org.beetl.json.util.MethodInvoker;
import org.beetl.json.util.Type;

public class PojoAttributeNode extends OutputNode{

	protected Class parent;
	//key
	protected String attrName;
	
	protected Class attrType;
	
	protected OutputNodeKey atrrKey = null;
	protected MethodInvoker methodProxy = null;
	protected  List<IKeyAction>  keyActions =  null ;
	public PojoAttributeNode(Class parent,Method m){
		
		this.parent = parent ;
		this.attrType = m.getReturnType();
		this.attrName = JsonUtil.getAttribute(m);
		methodProxy = new MethodInvoker(parent,m,attrName);
		
		
	
		
	
	}
	@Override
	public void render(OutputNodeKey field,Object obj, JsonWriter w) throws IOException {

		String strKey = attrName;		
		
		if(keyActions!=null){
			ActionReturn ar = null;
			for(IKeyAction keyAction:keyActions){
				ar = (ActionReturn)keyAction.doit(strKey, this,w);
				if(ar.process==ActionReturn.CONTINUE){
					strKey = (String)ar.value;
				}else if(ar.process==ActionReturn.BREAK){
					break ;
				}else{
					return ;
				}
				
			}			
				
		}
		
		Object attrValue = null;
		try{
			attrValue = methodProxy.invoke(obj);
		}catch(RuntimeException ex){
			w.getTool().attributeErrorHander.process(obj, strKey, w, ex.getCause());
			return;
		}
		
		if(this.valueActions==null){
			if(attrValue==null){
				if(atrrKey==null){
					w.writeKeyValue(strKey,null);
				}else{
					w.writeKeyValue(atrrKey, null);
				}
				
//				w.writeKey(field);
//				w.writeValue(null);
				return ;
			}
			switch(methodProxy.returnType){
			case Type.JAVA_TYPE:{
				if(atrrKey==null){
					w.writeKeyValue(strKey,attrValue);
				}else{
					w.writeKeyValue(atrrKey, attrValue);
				}
			
				return ;
			}
			case Type.Collection_TYPE:{
				NodeUtil.writeIterator(this,  atrrKey==null?new OutputNodeKey(strKey):atrrKey,((Iterable)attrValue), w);return ;
			}
			case Type.MAP_TYPE:NodeUtil.writeMap(this, atrrKey==null?new OutputNodeKey(strKey):atrrKey, (Map)attrValue, w);return ;
			case Type.POJO_TYPE:{
				w.getTool().serializeJW(w,atrrKey==null?new OutputNodeKey(strKey):atrrKey,attrValue,this.inHeritedPolicy); return ;
			}
			default:{
				NodeUtil.writeUnkonw(this, atrrKey==null?new OutputNodeKey(strKey):atrrKey,attrValue, w, valueActions);return ;
			}
			}
			
			
		}else{
			NodeUtil.writeUnkonw(this,atrrKey==null?new OutputNodeKey(strKey):atrrKey, attrValue, w, valueActions);
		}
		
		
		
	}
	
	
	
	@Override
	public void addActionIfMatchLocations(List<Location> list,JsonTool tool) {
		
		for(Location l:list){
			if(isMatch(l,tool)){
				LocationAction action = l.getAction();
				if(action instanceof IKeyAction){
					if(action instanceof  ValueIgnoreAction){
						ignore = true;
					}else{
						this.addKeyAction((IKeyAction)action);
					}
					
				}else if(action instanceof IValueAction){
					this.addValueAction((IValueAction)action);
				}
			}
		
		}

		
	
		if(this.valueActions!=null){
			methodProxy.returnType = Type.getType(methodProxy.m.getReturnType());
		}
		
		if(this.keyActions==null){
			atrrKey = new OutputNodeKey(this.attrName.toCharArray());
		}
		
	}
	
	
	
	

	
	
	protected boolean isMatch(Location location,JsonTool tool) {
		return location.match(this,this.attrType, this.attrName,tool);
	}

	public String getAttrName() {
		return attrName;
	}
	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}
	public Class getAttrType() {
		return attrType;
	}
	public void setAttrType(Class attrType) {
		this.attrType = attrType;
	}
	
	public void addKeyAction(IKeyAction keyAction){
		if(this.keyActions==null){
			this.keyActions = new LinkedList<IKeyAction>();
		}
		this.keyActions.add(keyAction);
	}
	

}
