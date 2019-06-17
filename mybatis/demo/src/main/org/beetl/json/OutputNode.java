package org.beetl.json;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.beetl.json.action.IValueAction;
import org.beetl.json.loc.IndexLocation;
import org.beetl.json.loc.IndexLocationList;

public abstract class OutputNode  {

	//待处理的序列化策略
	protected List<Location> inHeritedPolicy = null;;
	//本节点的Action
	protected  List<IValueAction>  valueActions =  null;;
	//是否忽略本节点
	protected boolean ignore = false ;
	//索引位置
	protected IndexLocationList indexLocationList = null;
	public    abstract void render(OutputNodeKey fileld,Object obj, JsonWriter w) throws IOException  ;
	

	/** 增加Action
	 * @param list
	 */
	public abstract void addActionIfMatchLocations(List<Location> list,JsonTool tool) ;
	

	public List<Location> getInHeritedPolicy() {
		return inHeritedPolicy;
	}
	
	public void addInHeritedPolicy(Location loc){
		if(inHeritedPolicy==null){
			inHeritedPolicy = new LinkedList<Location>();
		}
		inHeritedPolicy.add(loc);
	}

	
	public boolean isIgnore() {
		return ignore;
	}

	public void setIgnore(boolean ignore) {
		this.ignore = ignore;
	}

	public IndexLocationList getIndexLocations() {
		return indexLocationList;
	}

	public void addIndexLocations(IndexLocation l){
		if(this.indexLocationList ==null){
			indexLocationList = new IndexLocationList();
			
		}
		indexLocationList.addIndexLocation(l);
	}

	public void addValueAction(IValueAction action){
		if(this.valueActions==null){
			this.valueActions = new LinkedList<IValueAction>();
		}
		valueActions.add(action);
	}
	
	

}
