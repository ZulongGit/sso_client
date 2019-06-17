package com.krm.web.codegen.model;

import java.io.Serializable;

public class CommonParams implements Serializable{

	private static final long serialVersionUID = 4731343301818375343L;
	private String packageName;
	private String entityName;
	private String entityNameU;
	private String date;
	private	String	author;			//作者
	
	public String getPackageName() {
		return packageName;
	}
	public String getEntityName() {
		return entityName;
	}
	public String getEntityNameU() {
		return entityNameU;
	}
	public String getDate() {
		return date;
	}
	public String getAuthor() {
		return author;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	public void setEntityNameU(String entityNameU) {
		this.entityNameU = entityNameU;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
}
