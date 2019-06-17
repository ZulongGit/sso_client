package org.beetl.json;

public class AttribiuteErrorHandler {
	public void process(Object obj,String key,JsonWriter w,Throwable ex){
		throw new RuntimeException(ex);
	}
}
