package org.beetl.json;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.beetl.json.node.ArrayNode;
import org.beetl.json.node.IterableClassNode;
import org.beetl.json.node.JavaObjectNode;
import org.beetl.json.node.MapClassNode;
import org.beetl.json.node.PojoNode;

public class PolicyCache {
	JsonTool jsonTool = null;
	Map<Class,Map<String,Map<List<Location>,Policy>>> cache = 
			new HashMap<Class,Map<String,Map<List<Location>,Policy>>>();
	Map<Class,List<Location>> clsAnnotation = new HashMap<Class,List<Location>>();
	
	Map<Class,Policy> fastCache = new HashMap<Class,Policy>();
	
	public PolicyCache(JsonTool jsonTool){
		this.jsonTool = jsonTool;
	}
	
	public synchronized Policy getPolicy(Class cls,String policyStr,List<Location> inheritList ){
		
		if (cls == DirectOutputValue.class) {
			return Policy.directPolicy;
		}
		
		if( (policyStr==null||policyStr.length()==0)&&(inheritList==null||inheritList.size()==0)){
			Policy policy = this.fastCache.get(cls);
			if(policy!=null){
				return policy;
			}
		}
		
		String clsName = cls.getName();
		Map<String,Map<List<Location>,Policy>> cache2 = cache.get(cls);
		if(cache2==null){
			cache2 = new HashMap<String,Map<List<Location>,Policy>>(); 
			cache.put(cls, cache2);
		}
		if(policyStr==null)policyStr = jsonTool.EMPTY_POLICY;
		Map<List<Location>,Policy> cache3 = cache2.get(policyStr);
		if(cache3==null){
			cache3 = new HashMap<List<Location>,Policy>();
			cache2.put(policyStr, cache3);
		}
		
		Policy policy = cache3.get(inheritList);
		if(policy==null){
			
			policy = createPolicy(cls, policyStr, inheritList);
			
			if( (policyStr==null||policyStr.length()==0)&&(inheritList==null||inheritList.size()==0)){
				this.fastCache.put(cls, policy);
			}
			cache3.put(inheritList, policy);
		}
		
		return policy ;
		
		
	}
	
	public Policy createPolicy(Class cls,String policyStr,List<Location> inheritList ){
		

	
		List<Location> annotation =  clsAnnotation.get(cls);
		if(annotation==null){
			annotation = PolicyParser.parseAnnotation(cls, this.jsonTool);
			clsAnnotation.put(cls, annotation);
		}
		
		List<Location>  policy = PolicyParser.parseStringPolicy(cls, policyStr, jsonTool);
		List<Location>  all = new LinkedList<Location>(jsonTool.defaultLocs);
		all.addAll(annotation);
		all.addAll(policy);
		if(inheritList!=null){
			all.addAll(inheritList);
		}
		
		
		OutputNode node = null;
		Policy jsonPolicy = null;
		
		
		if (Iterable.class.isAssignableFrom(cls)) {
			node = new IterableClassNode();
		} else if (Iterator.class.isAssignableFrom(cls)) {
			node = new IterableClassNode();
		} else if (Map.class.isAssignableFrom(cls)) {
			node = new MapClassNode();
		} else if (cls.isArray()) {
			node = new ArrayNode();
		} else if (cls.getPackage().getName().startsWith("java")) {
			node = new JavaObjectNode(cls);

		} else {
			node = new PojoNode(cls);
		}

		node.addActionIfMatchLocations(all, jsonTool);
		jsonPolicy = new Policy(node);
		return jsonPolicy;
		
		
	}
}
