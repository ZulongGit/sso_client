package org.beetl.json.action;

import org.beetl.json.ActionReturn;
import org.beetl.json.DirectOutputValue;
import org.beetl.json.JsonWriter;
import org.beetl.json.OutputNode;
import org.beetl.json.OutputNodeKey;

/** 这个字段直接输出，通常用于字段本身是个json
 * @author xiandafu
 *
 */
public class AsJsonAction implements IValueAction {


	@Override
	public ActionReturn doit(OutputNodeKey field, Object o, OutputNode thisNode, JsonWriter w) {
		if(o==null) return new ActionReturn(o);
		return new ActionReturn(new DirectOutputValue(o.toString()));
	}

}
