package org.beetl.json;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.beetl.json.util.ContextLocal;
import org.beetl.json.util.IntIOWriter;

public class JsonWriter {
	Writer w = null ;
	char quotes = 0;
	static char[] NULL = "null".toCharArray();
	static char[] trueChar= "true".toCharArray();
	static char[] falseChar = "false".toCharArray();
	static char[] emptySquare = "[]".toCharArray();
	static char[] emptyBrace = "{}".toCharArray();
	
	boolean pretty = false ;
	int indent = 0;
	
	ContextLocal localBuffer = ContextLocal.get();
	
	boolean[] scope = localBuffer.getScope();
	int scopeIndex = 0;
	
	//对象路径
	Map<Object,Object> objectMapPath = new LinkedHashMap<Object,Object>();
	
	JsonTool tool = null;
	public JsonWriter(Writer w,JsonTool tool){
		this.w = w;
		this.tool = tool;
		if(tool.singleQuotes){
			quotes = '\'';
		}else{
			quotes= '\"';
		}
		pretty = tool.pretty;
		
	}
	
	
	
	protected void writeComma() throws IOException{
		if(scope[scopeIndex]){
			scope[scopeIndex] = false ;
			return ;
		}else{
			w.write(',');	
		}
		
	}
	
	public void writeKeyValue(OutputNodeKey key,Object value) throws IOException{		
		writeKey(key);
		writeValue(value);
	}
	
	public void writeKeyValue(String key,Object value) throws IOException{		
		this.writeComma();		
		writeKey(key);
		write(':');
		writeValue(value);
	}
	
	public void writeScopeNull() throws IOException{
		this.writeComma();
		this.writeNull();
	}
	
	public boolean isFirstInScope(){
		return scope[scopeIndex];
	}
	
	public void writeScopeChar(char c) throws IOException{
		
			switch(c){
			case '{':
			case '[':
				if(this.pretty){
					w.write('\n');
					this.writeIndent();				
					this.indent++;
				}
				scopeIndex++;
				scope[scopeIndex] = true;
				
				break;
			case '}':
			case ']':
				if(this.pretty){
					w.write('\n');
					this.indent--;
					writeIndent();			
				}
				scopeIndex--;
				break;			
			}
			
			
			w.write(c);
		
		
		
	}
	public void  write(char c) throws IOException{
		
		
		w.write(c);
	}
	

	protected void writeKey(String key)throws IOException{
		if(this.pretty){
			w.write('\n');
			writeIndent();
		}
		
		w.write(quotes);
		w.write(key);
		w.write(quotes);
		
	}
	
	protected void writeKey(char[] key)throws IOException{
		if(this.pretty){
			w.write('\n');
			writeIndent();
		}
		
		w.write(quotes);
		w.write(key);
		w.write(quotes);
		
	}

	
	private void writeBoolean(Boolean b) throws IOException{
		if(b){
			w.write(trueChar);
		}else{
			w.write(falseChar);
		}
	}
	public void writeNull() throws IOException{
		w.write(NULL);;
	}

	public void writeEmptySqure() throws IOException{
		w.write(emptySquare);
	}
	
	public void writeEmptyBrace() throws IOException{
		w.write(emptyBrace);
	}
	
	public void writeValue(Object  o) throws IOException{
		if(o==null){
			w.write(NULL);			
		}else{
			if( o instanceof String){
				w.write(quotes);
				w.write(o.toString());
				w.write(quotes);
			}else	if(o instanceof Boolean){
				writeBoolean((Boolean)o);
			}else if(o instanceof Number){				
				if(o instanceof Integer){
					IntIOWriter.writeInteger(this, (Integer)o);
				}else{
					w.write(o.toString());
				}
				
			}else if(o instanceof DirectOutputValue){
				w.write(o.toString());
			}else{
				w.write(quotes);
				w.write(o.toString());
				w.write(quotes);
			}
		
		}
		
	}
	
	public void writeNumberChars(char[] chars, int len) throws IOException

	{
		this.w.write(chars, 0, len);

	}
	
	protected void writeIndent() throws IOException{
		for(int i=0;i<indent;i++){
			w.write('\t');
		}
	}
	

	public ContextLocal getLocalBuffer() {
		return localBuffer;
	}
	
	
	
	public void addObjectPath(Object field,Object o){
		objectMapPath.put(o, field);
	}
	
	public void removeObjectPath(Object o){
		objectMapPath.remove(o);
	}
	
	public boolean containObjectInPath(Object o){
		return objectMapPath.containsKey(o);
	}
	
	public String getObjectPath(Object o){
		StringBuilder sb = null;
		for(Entry<Object,Object> entry:objectMapPath.entrySet()){
			Object key = entry.getKey();
			OutputNodeKey value = (OutputNodeKey)entry.getValue();
			if(value==null){
				sb=new StringBuilder("$");
			}else{
				if(value.isIndex()){
					sb.append('[').append(value).append(']');
				}else{
					sb.append('.').append(value.toString());
				}
			}
			if(o.equals(key)){
				return sb.toString();
			}
		}
		throw new RuntimeException("不可能发生");
	}
	
	public void writeKey(OutputNodeKey nodeKey) throws IOException{
		if(nodeKey==null) return ;
		Object key = nodeKey.getKey();
		if(key==null) return ;
		writeComma();
		if(!nodeKey.isIndex()){
			if(nodeKey.isFix()){
				this.writeKey((char[])key);
			}else{
				this.writeKey((String)key);
			}
			
			this.write(':');
		}
		return ;
		
	}

	public JsonTool getTool() {
		return tool;
	}



	public void setTool(JsonTool tool) {
		this.tool = tool;
	}

	
	public char getQuotes() {
		return quotes;
	}



}
