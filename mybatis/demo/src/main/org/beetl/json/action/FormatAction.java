package org.beetl.json.action;

import org.beetl.json.ActionReturn;
import org.beetl.json.Format;
import org.beetl.json.JsonWriter;
import org.beetl.json.OutputNode;
import org.beetl.json.OutputNodeKey;


/** 格式化输出
 * @author xiandafu
 *
 */
public class FormatAction implements IValueAction {

	String pattern ;
	public FormatAction(String pattern){
		this.pattern = pattern;
	}
	@Override
	public ActionReturn doit(OutputNodeKey field,Object o,OutputNode thisNode,JsonWriter w) {
		// TODO Auto-generated method stub
		if(o==null) return new ActionReturn(o,ActionReturn.CONTINUE);
		Format format = w.getTool().getFormat(o.getClass());
		if(format!=null){
			return new ActionReturn(format.format(o, pattern),ActionReturn.BREAK);
		}else{
			throw new RuntimeException("not support:"+field.getKey()+" class type="+o.getClass());

		}
		
	}
	
	public boolean equals(Object o){
		if( o instanceof FormatAction){
			FormatAction other = (FormatAction)o;
			return other.pattern.equals(this.pattern);
		}else{
			return false ;
		}
	}
	

}
