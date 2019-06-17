package org.beetl.json.util;

import java.util.HashMap;
import java.util.Map;

import org.beetl.json.ActionReturn;
import org.beetl.json.DirectOutputValue;
import org.beetl.json.JsonWriter;
import org.beetl.json.OutputNode;
import org.beetl.json.OutputNodeKey;
import org.beetl.json.action.IValueAction;

public class StringQuoteCheck implements IValueAction {
	
	private String cr = null;
	


	@Override
	public ActionReturn doit(OutputNodeKey field, Object o,
			OutputNode thisNode, JsonWriter w) {
		if(o instanceof String){
			return new ActionReturn(getString((String)o,w),ActionReturn.CONTINUE);
		}else if(o instanceof  DirectOutputValue){
			DirectOutputValue dov = (DirectOutputValue)o;
			if(dov.getValue() instanceof String){
				String newValue = (String)dov.getValue();
				newValue = getString(newValue,w);
				return new ActionReturn(newValue,ActionReturn.CONTINUE);
				
			}
		}
	
		return new ActionReturn(o);
	}
	
	public String getString(String str,JsonWriter w){
	
		if(str==null) return str;
		char target = w.getQuotes();
		char[] cs = str.toCharArray();
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<cs.length;i++){
			char c = cs[i];
			if(c==target){
				sb.append('\\');
				sb.append(c);
			}else if(c=='\\'){
				sb.append('\\');
				sb.append(c);
			}else if(cr!=null&&c=='\r'){
				if(isCR(cs,i,'\n')){
					i++;					
				}
				sb.append(cr);
			
			}else if(cr!=null&&c=='\n'){
				if(isCR(cs,i,'\r')){
					i++;
					
				}				
				sb.append(cr);			
			}else{
				sb.append(c);		
			}
		
		}
		return sb.toString();
		
		
	}
	
	private  boolean isCR(char[] cs,int index,char expected){
		int next = index+1;
		if(next<cs.length){
			if(cs[next]==expected){
				return true;
			}
		}
		return false;
	}

	public String getCr() {
		return cr;
	}

	public void setCr(String cr) {
		this.cr = cr;
	}

}
