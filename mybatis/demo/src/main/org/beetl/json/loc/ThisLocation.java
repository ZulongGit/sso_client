package org.beetl.json.loc;

import org.beetl.json.JsonTool;
import org.beetl.json.Location;
import org.beetl.json.OutputNode;

/**
 *  like this
 * @author joelli
 *
 */
public class ThisLocation extends Location {

	Class c  = null;
	public ThisLocation(Class c){
		this.c = c ;
	}
	@Override
	public boolean match(OutputNode node,Class type, String field,JsonTool tool) {
		boolean match =   c==Object.class||c==type;
		return super.testNextLoc(match, node, type, field, tool);
	}

}
