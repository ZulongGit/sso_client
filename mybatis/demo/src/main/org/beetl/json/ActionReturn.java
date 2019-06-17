package org.beetl.json;

public class ActionReturn {
	public Object value;
	public int process;
	public static final int  CONTINUE = 0;
	public static final int  BREAK = 1;
	public static final int  RETURN = 2;
	
	public ActionReturn(Object value,int process){
		this.process = process;
		this.value = value;
	}
	
	public ActionReturn(Object value){
		this.process = 0;
		this.value = value;
	}
}
