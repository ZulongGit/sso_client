package org.beetl.json;

public class AttribiuteErrorIgnoreHandler extends AttribiuteErrorHandler {
	public void process(Object obj,String key,JsonWriter w,Throwable ex){
		return ;
	}
}
