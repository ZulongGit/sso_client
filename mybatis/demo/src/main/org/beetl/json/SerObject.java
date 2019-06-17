package org.beetl.json;

/**用于封装序列化对象，以及序列化策略，用于spring mvc等框架应用
 * @author joelli
 *
 */
public class SerObject {
	private Object target;
	String policy;
	public SerObject(Object o,String policy){
		this.target = o;
		this.policy = policy;
	}
	public Object getTarget() {
		return target;
	}
	public void setTarget(Object target) {
		this.target = target;
	}
	public String getPolicy() {
		return policy;
	}
	public void setPolicy(String policy) {
		this.policy = policy;
	}
	
	
}
