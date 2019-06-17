package org.beetl.json;

public class OutputNodeKey {
	private Object  key;
	private boolean isIndex = false;;
	private boolean isFix = false ;
	public OutputNodeKey(Object key,boolean isIndex){
		this.key = key;
		this.isIndex = isIndex;
	}
	

	public OutputNodeKey(String key){
		this(key,false);
	}
	
	public OutputNodeKey(char[] array){
		this(array,false);
		isFix = true;
	}
	
	
	public Object getKey() {
		return key;
	}
	public void setKey(Object key) {
		this.key = key;
	}
	public boolean isIndex() {
		return isIndex;
	}
	public void setIndex(boolean isIndex) {
		this.isIndex = isIndex;
	}
	
	public int hashCode(){
		return  key.hashCode();
	}
	
	public String toString(){
		if(this.isFix){
			return new String((char[])key);
		}else{
			return key.toString();
		}
	}


	public boolean isFix() {
		return isFix;
	}


	public void setFix(boolean isFix) {
		this.isFix = isFix;
	}
	
}
