package com.krm.common.utils;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.krm.common.base.XmlNode;

public class StringUtil {
	/**
	 * 转换为Double类型
	 */
	public static Double toDouble(Object val){
		if (val == null){
			return 0D;
		}
		try {
			return Double.valueOf((val.toString().trim()));
		} catch (Exception e) {
			return 0D;
		}
	}
	
	/**
	 * 转换为Float类型
	 */
	public static Float toFloat(Object val){
		return toDouble(val).floatValue();
	}

	/**
	 * 转换为Long类型
	 */
	public static Long toLong(Object val){
		return toDouble(val).longValue();
	}

	/**
	 * 转换为Integer类型
	 */
	public static Integer toInteger(Object val){
		return toLong(val).intValue();
	}

	/**
	 * 将驼峰风格替换为下划线风格
	 */
	public static String camelhumpToUnderline(String str) {
		final int size;
		final char[] chars;
		final StringBuilder sb = new StringBuilder(
				(size = (chars = str.toCharArray()).length) * 3 / 2 + 1);
		char c;
		for (int i = 0; i < size; i++) {
			c = chars[i];
			if (isLowercaseAlpha(c)) {
				sb.append(toUpperAscii(c));
			} else {
				sb.append('_').append(c);
			}
		}
		return sb.charAt(0) == '_' ? sb.substring(1) : sb.toString();
	}

	/**
	 * 将下划线风格替换为驼峰风格
	 */
	public static String underlineToCamelhump(String name) {
		if(!name.contains("_")) return name;
		char[] buffer = name.toCharArray();
		int count = 0;
		boolean lastUnderscore = false;
		for (int i = 0; i < buffer.length; i++) {
			char c = buffer[i];
			if (c == '_') {
				lastUnderscore = true;
			} else {
				c = (lastUnderscore && count != 0) ? toUpperAscii(c)
						: toLowerAscii(c);
				buffer[count++] = c;
				lastUnderscore = false;
			}
		}
		if (count != buffer.length) {
			buffer = subarray(buffer, 0, count);
		}
		return new String(buffer);
	}

	public static char[] subarray(char[] src, int offset, int len) {
		char[] dest = new char[len];
		System.arraycopy(src, offset, dest, 0, len);
		return dest;
	}

	public static boolean isLowercaseAlpha(char c) {
		return (c >= 'a') && (c <= 'z');
	}

	public static char toUpperAscii(char c) {
		if (isLowercaseAlpha(c)) {
			c -= (char) 0x20;
		}
		return c;
	}

	public static char toLowerAscii(char c) {
		if ((c >= 'A') && (c <= 'Z')) {
			c += (char) 0x20;
		}
		return c;
	}
	
	public static boolean isEmpty(Object str){
		if(str == null || str.toString().trim().length() == 0)
			return true;
		return false;			
	}
	
	public static boolean isNotEmpty(Object str){
		return !isEmpty(str);
	}
	
	public static String firstCharToUpper(String str){
		StringBuffer sb = new StringBuffer(str);
		sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
		return sb.toString(); 
	}
	
	public static String firstCharToLower(String str){
		StringBuffer sb = new StringBuffer(str);
		sb.setCharAt(0, Character.toLowerCase(sb.charAt(0)));
		return sb.toString(); 
	}
	
	public static boolean equals(String str1, String str2){
		if(isEmpty(str1) || isEmpty(str2)){
			return false;
		}
		return str1.equals(str2);
	}
	
	/**
	 * 非固定字典自定义sql逻辑判断转换
	 * 目前仅支持isNotBlank，NotBlank方法
	 * @param str SQL语句
	 * @param params 动态语句参数
	 * @return
	 * @throws Exception
	 */
	public static String transfer(String str, Map<String,Object> params) throws Exception{
		//添加父节点，否则xml解析报错
		str = "<start>" + str + "</start>";
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		ByteArrayInputStream in = new ByteArrayInputStream(str.getBytes());
		Document node = db.parse(in);
		List<XmlNode> list = Lists.newArrayList();
		List<XmlNode> nodes = parse(node, list);
		for (XmlNode xmlNode : nodes) {
			//正则匹配xml节点，只匹配只有一行内容的节点，且无子节点
			String regex = "<"+xmlNode.getNodeName()+".*(\\r\\n)?(.*\\r\\n)?.*</"+xmlNode.getNodeName()+">";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(str);
			while(matcher.find()){
				String ognl = matcher.group();
				//只处理当前mxlnode的内容
//				System.out.println(xmlNode.getTextContent().replaceAll("\\r|\\n|\\t", "").trim());
				if(ognl.indexOf(xmlNode.getTextContent().replaceAll("\\r|\\n|\\t", "").trim()) != -1){
					String [] arr = xmlNode.getAttributeValue().split("@");
					String temp = arr[arr.length-1];
					String method = temp.substring(0, temp.indexOf("("));
					String param = temp.substring(temp.indexOf("(")+1, temp.length()-1);
					//执行ognl方法
					boolean val = (boolean) Class.forName(StringUtils.class.getName()).getMethod(method, CharSequence.class).invoke(null, params.containsKey(param)?params.get(param):null);
					if(val){
						str = str.replace(ognl, xmlNode.getTextContent());
					}else{
						str = str.replace(ognl, "");
					}
				}
			}
		}
		return str.replace("<start>", "").replace("</start>", "");
	}
	
	private static List<XmlNode> parse(Node node, List<XmlNode> list) {
		//类型1：元素节点，例如：<if test="@Ognl@isNotBlank(dynamicSQL)">and m.member_id = #{userId}</if>
		if (node.getNodeType() == 1) {
			XmlNode xmlNode = new XmlNode();
			if(!node.getNodeName().equals("start")){
				xmlNode.setNodeName(node.getNodeName());
				//节点属性，例如：test="@Ognl@isNotBlank(dynamicSQL)
				if(node.getAttributes().getLength() > 0){
					Node attribute = node.getAttributes().item(0);
					xmlNode.setAttributeName(attribute.getNodeName());
					xmlNode.setAttributeValue(attribute.getNodeValue());
				}
				//节点内容，例如：and m.member_id = #{userId}
				xmlNode.setTextContent(node.getTextContent());
				list.add(xmlNode);
			}
		}
		//递归查找
		NodeList children = node.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);
			parse(child, list);
		}
		return list;
	}
	
	public static void main(String[] args) {
		Map<String,Object> params = Maps.newHashMap();
		try {
			String str = transfer("1=1<if test=\"@Ognl@isNotBlank(dynamicSQL)\">\r\nand m.member_id = #{userId}\r\n</if>", params);
			System.out.println(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
