package org.beetl.json.ext;


import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;

import org.beetl.json.JsonTool;
import org.beetl.json.LocationAction;
import org.beetl.json.SerObject;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

public class BeetlJsonHttpMessageConverter extends AbstractHttpMessageConverter<Object> {

    public final static Charset UTF8     = Charset.forName("UTF-8");

    private Charset             charset  = UTF8;
    
    JsonTool tool = new JsonTool();
    
    private Map<String, String> policys = Collections.emptyMap();
    boolean isPretty = false ;
    private Map<String, LocationAction> actions = Collections.emptyMap();
    Map<String,String> alias = new HashMap<String,String>();
	
    public boolean isPretty() {
		return isPretty;
	}

	public void setPretty(boolean isPretty) {
		this.isPretty = isPretty;
	}

	public Map<String, LocationAction> getActions() {
		return actions;
	}

	public void setActions(Map<String, LocationAction> actions) {
		this.actions = actions;
	}

	public Map<String, String> getAlias() {
		return alias;
	}

	public void setAlias(Map<String, String> alias) {
		this.alias = alias;
	}

	public BeetlJsonHttpMessageConverter(){
        super(new MediaType("application", "json", UTF8), new MediaType("application", "*+json", UTF8));
    
    }
    
    @PostConstruct
    public void init(){
    	tool = new JsonTool();
    	tool.pretty = this.isPretty;
    	tool.alias = this.alias;
    	 tool.userActions.putAll(this.actions);
    	for(Entry<String,String> entry:policys.entrySet()){
    		tool.addLocationAction(entry.getKey(), entry.getValue());
    	}
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return true;
    }

    public Charset getCharset() {
        return this.charset;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }
    
    


	public Map<String, String> getPolicys() {
		return policys;
	}

	public void setPolicys(Map<String, String> policys) {
		this.policys = policys;
	}

	@Override
    protected Object readInternal(Class<? extends Object> clazz, HttpInputMessage inputMessage) throws IOException,
                                                                                               HttpMessageNotReadableException {

       throw new UnsupportedOperationException("BeetlJson不支持解析Json");
    }

    @Override
    protected void writeInternal(Object obj, HttpOutputMessage outputMessage) throws IOException,
                                                                             HttpMessageNotWritableException {

        OutputStream out = outputMessage.getBody();
        Object target = null;
        String policy = JsonTool.EMPTY_POLICY;
       	if(obj instanceof SerObject){
       		SerObject ser = (SerObject)obj;
       		target = ser.getTarget();
       		policy = ser.getPolicy();
       	}else{
       		target = obj;
       	}
       	String text = tool.serialize(target,policy);
        byte[] bytes = text.getBytes(charset);
        out.write(bytes);
    }

}