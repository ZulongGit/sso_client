package org.beetl.json.action;

import java.io.IOException;

import org.beetl.json.JsonWriter;
import org.beetl.json.node.PojoNode;

public interface ICycleAction {
	public void cycleRender(PojoNode node,Object obj, JsonWriter w)  throws IOException;
}
