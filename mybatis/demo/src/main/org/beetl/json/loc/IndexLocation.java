package org.beetl.json.loc;

import org.beetl.json.JsonTool;
import org.beetl.json.Location;
import org.beetl.json.OutputNode;

public class IndexLocation extends Location {
	Object ref ;
	
	public IndexLocation(Object ref) {	
		this.ref = ref;
	}
	

	@Override
	public boolean match(OutputNode node,Class type, String field,JsonTool tool) {		
		node.addIndexLocations(this);
		return false;
	}
	

	public Object getRef() {
		return ref;
	}


	public void setRef(Object ref) {
		this.ref = ref;
	}


	
	
	

}
