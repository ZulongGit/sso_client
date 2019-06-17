package com.krm.common.base;

public class XmlNode {

	private String nodeName;
	private String attributeName;
	private String attributeValue;
	private String nodeValue;
	private Integer nodeType ;
	private String textContent;
	
	public String getNodeName() {
		return nodeName;
	}
	public String getNodeValue() {
		return nodeValue;
	}
	public Integer getNodeType() {
		return nodeType;
	}
	public String getTextContent() {
		return textContent;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public void setNodeValue(String nodeValue) {
		this.nodeValue = nodeValue;
	}
	public void setNodeType(Integer nodeType) {
		this.nodeType = nodeType;
	}
	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}
	public String getAttributeName() {
		return attributeName;
	}
	public String getAttributeValue() {
		return attributeValue;
	}
	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}
	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}
	@Override
	public String toString() {
		return "<" + nodeName + " " + attributeName + "=\"" + attributeValue + "\">" + textContent + "</"+nodeName + ">";
	}
	
	public static void main(String[] args) {
		XmlNode node = new XmlNode();
		node.setNodeName("if");
		node.setAttributeName("test");
		node.setAttributeValue("@Ognl@isNotBlank(id)");
		node.setTextContent("and t.ID = #{id}");
		System.out.println(node.toString());
	}
	
}
