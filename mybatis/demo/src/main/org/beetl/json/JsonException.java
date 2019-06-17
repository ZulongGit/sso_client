package org.beetl.json;

public class JsonException extends RuntimeException {
	public int error = 0;
	public static final int ERROR = 0;
	public JsonException(int error,String msg){
		super(msg);
		this.error = error ;
		
	}
	
	public JsonException(int error,String msg,Exception ex){
		super(msg,ex);
		this.error = error ;
		
	}
}
