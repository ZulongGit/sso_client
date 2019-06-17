package org.beetl.json.action;

import org.beetl.json.ActionReturn;
import org.beetl.json.JsonWriter;
import org.beetl.json.LocationAction;
import org.beetl.json.OutputNode;

public interface IKeyAction extends LocationAction {
	public ActionReturn doit(Object o,OutputNode thisNode,JsonWriter w);
}
