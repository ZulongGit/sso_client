package org.beetl.json.action;

import org.beetl.json.ActionReturn;
import org.beetl.json.JsonWriter;
import org.beetl.json.OutputNode;
import org.beetl.json.OutputNodeKey;

/**忽略
 * @author xiandafu
 *
 */
public class ValueIgnoreAction implements IKeyAction,IValueAction {



	@Override
	public ActionReturn doit(Object o, OutputNode thisNode, JsonWriter w) {
		return new ActionReturn(null,ActionReturn.RETURN);
	}

	@Override
	public ActionReturn doit(OutputNodeKey field, Object o, OutputNode thisNode, JsonWriter w) {
		return new ActionReturn(null,ActionReturn.RETURN);
	}

}
