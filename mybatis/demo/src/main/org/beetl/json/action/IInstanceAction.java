package org.beetl.json.action;

import org.beetl.json.ActionReturn;
import org.beetl.json.LocationAction;
import org.beetl.json.OutputNode;

public interface IInstanceAction extends LocationAction  {
	public ActionReturn doit(OutputNode thisNode);
}
