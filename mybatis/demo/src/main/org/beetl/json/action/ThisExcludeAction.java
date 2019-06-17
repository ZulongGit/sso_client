package org.beetl.json.action;

import java.util.ArrayList;
import java.util.List;

import org.beetl.json.ActionReturn;
import org.beetl.json.OutputNode;
import org.beetl.json.node.PojoAttributeNode;
import org.beetl.json.node.PojoNode;

public class ThisExcludeAction implements IInstanceAction {

	String[] names = null;
	public ThisExcludeAction(String list){
		names = list.split(",");
	}
	@Override
	public ActionReturn doit(OutputNode thisNode) {
		PojoNode node = (PojoNode)thisNode;
		List<PojoAttributeNode> list = node.getAttrs();
		List<PojoAttributeNode> newList = new ArrayList<PojoAttributeNode>(); 
		for(PojoAttributeNode n:list){
			PojoAttributeNode attrNode = (PojoAttributeNode)n;
			for(String name:names){
				if(attrNode.getAttrName().equals(name)){
					newList.add(attrNode);
					break;
				}
			}
			
		}
		list.removeAll(newList);
		return new ActionReturn(list);
	}
	

}
