package org.beetl.json.action;

import java.util.ArrayList;
import java.util.List;

import org.beetl.json.ActionReturn;
import org.beetl.json.OutputNode;
import org.beetl.json.node.PojoAttributeNode;
import org.beetl.json.node.PojoNode;

public class ThisOrderAction implements IInstanceAction {

	String[] names = null;
	public ThisOrderAction(String list){
		names = list.split(",");
	}
	@Override
	public ActionReturn doit(OutputNode thisNode) {
		PojoNode node = (PojoNode)thisNode;
		List<PojoAttributeNode> list = node.getAttrs();		
		List<PojoAttributeNode> newList = new ArrayList<PojoAttributeNode>(names.length);
		
		for(int i=0;i<names.length;i++){
			newList.add(null);
		}
		
		for(PojoAttributeNode n:list){
			String name = n.getAttrName();
			int index = contain(name,names);
			if(index<names.length){
				newList.set(index, n);
			}else{
				newList.add(index,n);
			}
			
			
		}
		return new ActionReturn(newList);
	}
	
	private int  contain(String name,String[] names){
		int index = 0;
		for(String n:names){
			if(n.equals(name)){
				break;
			}
			index++;
		}
		return index;
	}
	

}
