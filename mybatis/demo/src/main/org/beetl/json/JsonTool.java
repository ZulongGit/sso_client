package org.beetl.json;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.beetl.json.ext.JsonDateFormat;
import org.beetl.json.ext.JsonNumberFormat;
import org.beetl.json.node.ArrayNode;
import org.beetl.json.node.IterableClassNode;
import org.beetl.json.node.JavaObjectNode;
import org.beetl.json.node.MapClassNode;
import org.beetl.json.node.PojoNode;
import org.beetl.json.util.StringQuoteCheck;

/**
 * 序列化工具
 * 
 * @author xiandafu
 *
 */
public class JsonTool {
	PolicyCache cache = new PolicyCache(this);
	List<Location> defaultLocs = new ArrayList<Location>();
	public boolean singleQuotes = false;
	Map<Class, Format> formats = new HashMap<Class, Format>();
	{
		//date
		JsonDateFormat df = new JsonDateFormat();
		formats.put(java.util.Date.class, df);
		formats.put(java.sql.Date.class, df);
		formats.put(java.sql.Time.class, df);
		formats.put(java.sql.Timestamp.class, df);
		// number
		JsonNumberFormat nf = new JsonNumberFormat();
		formats.put(java.lang.Short.class, nf);
		formats.put(java.lang.Long.class, nf);
		formats.put(java.lang.Integer.class, nf);
		formats.put(java.lang.Float.class, nf);
		formats.put(java.lang.Double.class, nf);
		formats.put(java.math.BigInteger.class, nf);
		formats.put(java.math.BigDecimal.class, nf);
		
		
	}
	public Map<String, LocationAction> userActions = new HashMap<String, LocationAction>();

	{
		userActions.put("quote", new StringQuoteCheck());
	}

	List<String> parseStops = new ArrayList<String>();

	public Map<String, String> alias = new HashMap<String, String>();

	{
		alias.put("#ju", "java.util");
		alias.put("#jl", "java.lang");
		alias.put("#String", "java.lang.String");

	}

	Pattern pattern = Pattern.compile("#[_a-zA-Z][_a-zA-Z0-9]*");

	{
		parseStops.add("java.lang.Object");
		// 还可能有些代理类??
	}

	public AttribiuteErrorHandler attributeErrorHander = new AttribiuteErrorHandler();

	public static final String EMPTY_POLICY = "";

	public boolean ignoreClientIOError = false;

	public boolean pretty = false;

	/**
	 * 输出pojo之前对属性作排序，没有实际意义，但单元测试可以保证测试
	 */
	public boolean orderAttribute = true;
	public Comparator orderAttributeComparator = new AttributeComparator();

	public String serialize(Object obj) {
		if (obj == null)
			return "{}";
		Policy po = getPolicy(obj.getClass(), EMPTY_POLICY);
		try {
			return po.toJson(obj, this);
		} catch (IOException e) {
			// ignore
			throw new RuntimeException("不可能发生");
		}
	}

	public String serialize(Object obj, String policy) {
		if (obj == null)
			return "{}";
		Policy po = getPolicy(obj.getClass(), policy);
		try {
			return po.toJson(obj, this);
		} catch (IOException e) {
			// ignore
			throw new RuntimeException("不可能发生");
		}
	}

	public String serialize(Object obj, String... policys) {
		if (obj == null)
			return "{}";
		StringBuilder sb = new StringBuilder();
		for (String p : policys) {
			sb.append(p).append(",");
		}
		sb.setLength(sb.length() - 1);
		Policy po = getPolicy(obj.getClass(), sb.toString());
		try {
			return po.toJson(obj, this);
		} catch (IOException e) {
			// ignore
			throw new RuntimeException("不可能发生");
		}
	}

	public void serialize(Object obj, Writer w, String policy) {

		try {
			if (obj == null) {
				w.write("{}");
			}
			Policy po = getPolicy(obj.getClass(), policy);
			po.toJson(w, obj, this);
		} catch (IOException e) {
			if (!ignoreClientIOError)
				throw new RuntimeException(e);
		}
	}

	public void serialize(Object obj, Writer w, String... policys) {

		try {
			if (obj == null) {
				w.write("{}");
			}
			StringBuilder sb = new StringBuilder();
			for (String p : policys) {
				sb.append(p).append(",");
			}
			sb.setLength(sb.length() - 1);
			Policy po = getPolicy(obj.getClass(), sb.toString());
			po.toJson(w, obj, this);
		} catch (IOException e) {
			if (!ignoreClientIOError)
				throw new RuntimeException(e);
		}
	}

	public void serialize(Object obj, Writer w) {

		try {
			if (obj == null) {
				w.write("{}");
			}
			Policy po = getPolicy(obj.getClass(), EMPTY_POLICY);
			po.toJson(w, obj, this);
		} catch (IOException e) {
			if (!ignoreClientIOError)
				throw new RuntimeException(e);
		}
	}

	
	public void serializeJW(JsonWriter w, OutputNodeKey field, Object obj,List<Location> inherit) {

		try {
			if (obj == null) {
				w.writeNull();
				return;
			}
			Policy po = getPolicy(obj.getClass(), this.EMPTY_POLICY,inherit);
			po.toJson(w, field, obj);
		} catch (IOException e) {
			if (!ignoreClientIOError)
				throw new RuntimeException(e);
		}
	}
	public void serializeJW(JsonWriter w, OutputNodeKey field, Object obj, String policy,List<Location> inherit) {

		try {
			if (obj == null) {
				w.writeNull();
				return;
			}
			Policy po = getPolicy(obj.getClass(), policy,inherit);
			po.toJson(w, field, obj);
		} catch (IOException e) {
			if (!ignoreClientIOError)
				throw new RuntimeException(e);
		}
	}

	private Policy getPolicy(Class c, String policy) {

		return cache.getPolicy(c, policy, null);

	}
	
	private Policy getPolicy(Class c, String policy,List<Location> inherit) {

		return cache.getPolicy(c, policy, inherit);

	}
	

	

	public void addFormat(Class type, Format format) {
		formats.put(type, format);
	}

	public Format getFormat(Class type) {
		return formats.get(type);
	}

	public void addAction(String name, LocationAction action) {
		userActions.put(name, action);
	}

	public LocationAction getAction(String name) {
		return userActions.get(name);
	}

	public void addLocationAction(String loc, String action) {

		Location location = PolicyParser.parse(null, loc, action, this);
		defaultLocs.add(location);
	}

	public void addPolicy(String policy) {
		List<Location> list = PolicyParser.parseStringPolicy(null, policy,this);
		defaultLocs.addAll(list);
	}

	public void addParseStopClass(String clsName) {
		parseStops.add(clsName);
	}

	public boolean isStopParse(String clsName) {
		return parseStops.contains(clsName);
	}

	public void addAlias(String shortName, String name) {
		alias.put("#" + shortName, name);
	}

	public String getNameByAlias(String str) {
		Matcher matcher = pattern.matcher(str);
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			int start = matcher.start();
			int end = matcher.end();
			String key = str.substring(start, end);
			String realName = alias.get(key);
			if (realName == null) {
				throw new JsonException(JsonException.ERROR, "别名未找到:" + key + "在" + str);
			}
			matcher.appendReplacement(sb, realName);

		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	// public satic void main(String[] args){
	// Pattern pattern = Pattern.compile("#[_a-zA-Z][_a-zA-Z0-9]*");
	// String str = "/#abddfd.#ac/";
	// Matcher matcher = pattern.matcher(str);
	// StringBuffer sb = new StringBuffer();
	// while(matcher.find()){
	// int start =matcher.start();
	// int end = matcher.end();
	// String key = str.substring(start,end);
	// matcher.appendReplacement(sb, "kkk");
	//
	// }
	// matcher.appendTail(sb);
	// System.out.println(sb);
	// }

}
