package org.beetl.json.node;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.beetl.json.ActionReturn;
import org.beetl.json.JsonTool;
import org.beetl.json.JsonWriter;
import org.beetl.json.Location;
import org.beetl.json.LocationAction;
import org.beetl.json.OutputNode;
import org.beetl.json.OutputNodeKey;
import org.beetl.json.action.IInstanceAction;
import org.beetl.json.action.IValueAction;
import org.beetl.json.loc.ClassTypeLocation;
import org.beetl.json.loc.ThisLocation;
import org.beetl.json.util.JsonUtil;

public class PojoNode extends OutputNode {

	Class c;
	List<PojoAttributeNode> attrs = new ArrayList<PojoAttributeNode>();

	public PojoNode(Class c) {
		this.c = c;
	}

	@Override
	public void render(OutputNodeKey field,Object obj, JsonWriter w) throws IOException {
		
	
		if (this.valueActions!=null) {
			for (IValueAction valueAction : valueActions) {
				ActionReturn ar = valueAction.doit(field,obj, this, w);
				obj = ar.value;
				if (ar.process != ActionReturn.RETURN) {
					break;
				} else {
					return;
				}
			}
		}
		
		

		if (obj == null) {
			w.writeKey(field);
			w.writeNull();
		}
		if(w.containObjectInPath(obj)){
			w.writeKey(field);
			String path = w.getObjectPath(obj);
			w.writeScopeChar('{');
			w.writeKeyValue("$ref", path);		
			w.writeScopeChar('}');
			return ;
		}
		w.addObjectPath(field,obj);
		w.writeKey(field);
		w.writeScopeChar('{');
		Iterator<PojoAttributeNode> it = attrs.iterator();
		if (!it.hasNext()) {
			w.writeScopeChar('}');

		} else {

			while (true) {
				PojoAttributeNode node = it.next();
				node.render(null,obj, w);
				if (!it.hasNext()) {
					w.writeScopeChar('}');
					break;
				}
				

			}
		}
		w.removeObjectPath(obj);
		return;

	}

	@Override
	public void addActionIfMatchLocations(List<Location> list,JsonTool tool) {
		// 获得子节点
		Method[] ms = c.getMethods();
		for (Method m : ms) {
			if (JsonUtil.isGetterMethod(m)) {
				// @todo: list map,class,etc node
				if (!tool.isStopParse(m.getDeclaringClass().getName())) {

					PojoAttributeNode node = new PojoAttributeNode(c, m);
					node.addActionIfMatchLocations(list,tool);
					if (!node.isIgnore()) {
						attrs.add(node);
					}
				}

			}
		}
		// 排序
		if (tool.orderAttribute) {
			Collections.sort(attrs, tool.orderAttributeComparator);
		}

		// 处理本类
		List<IInstanceAction> insActions = new ArrayList<IInstanceAction>();

		// 只处理ClassTypeLocation，ThisLocation
		for (Location l : list) {
			if (l instanceof ClassTypeLocation) {
				if (((ClassTypeLocation) l).match(this, c, null,tool)) {
					LocationAction action = l.getAction();
					if (action instanceof IValueAction) {
						this.addValueAction((IValueAction) l.getAction());
					} else if (action instanceof IInstanceAction) {
						insActions.add((IInstanceAction) action);
					}

				}
			} else if (l instanceof ThisLocation) {
				insActions.add((IInstanceAction) l.getAction());
			}
		}

		// 查找是否有作用到instace的Action
		if (insActions.size() != 0) {
			// 预先处理
			for (IInstanceAction ca : insActions) {
				ActionReturn ar = ca.doit(this);
				if (ar.process == ActionReturn.CONTINUE) {
					attrs = (List<PojoAttributeNode>) ar.value;
				} else {
					break;
				}

			}

		}

	
	}

	public List<PojoAttributeNode> getAttrs() {
		return attrs;
	}

}
