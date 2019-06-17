package org.beetl.json.action;

import org.beetl.json.ActionReturn;
import org.beetl.json.JsonWriter;
import org.beetl.json.LocationAction;
import org.beetl.json.OutputNode;
import org.beetl.json.OutputNodeKey;

public interface IValueAction extends LocationAction  {
	public ActionReturn doit(OutputNodeKey field,Object o,OutputNode thisNode,JsonWriter w);
}
