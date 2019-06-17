package org.beetl.json;

/**提示jsonwirter 直接输出，不要加引号
 * @author joelli
 *
 */
public class DirectOutputValue {
	Object value = null;
	public DirectOutputValue(Object value){
		this.value = value;
	}
	public String toString(){
		if(value==null) return null;
		else return value.toString();
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	
	
}
