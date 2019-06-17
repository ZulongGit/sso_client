package org.beetl.json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.beetl.json.action.IValueAction;
import org.beetl.json.annotation.Json;
import org.beetl.json.annotation.JsonPolicy;

public class Test {

	public static void main(String[] args)throws IOException {
		{
			JsonTool tool = new JsonTool();
			
			{
//				String policy= "~c:?null->[],~f:f/#.##/";
//				List<Location> list = JsonTool.parseStringPolicy(policy);
//				for(Location loc:list){
//					System.out.println(loc.getClass());
//				}
			}
			
//			tool.addLocationAction("~d","f/yyyy.MM.dd/");	
		
//			tool.addAction("list", new IValueAction() {
//				
//				@Override
//				public ActionReturn doit(OutputNodeKey field, Object o,
//						OutputNode thisNode, JsonWriter w) {
//					if(o==null){
//						return new ActionReturn(o);
//					}else if(o instanceof List){
//						return new ActionReturn("hello",ActionReturn.RETURN);
//					}else{
//						return new ActionReturn(o);
//					}
//				}
//			});
	

//			tool.addLocationAction("~L/java.util.Calendar*/","$.getTime->f/yyyy-MM-dd/");
			
			tool.pretty = false;
			
			{
					
				User user = new User();	
				user.setCustomer(new Customer());
				String jsonString = tool.serialize(user);			
//				System.out.println("ojbString="+ojbString);
				System.out.println("jsonString="+jsonString);
			}
			
	
			{
//				Map map = new HashMap();
////				map.put("date",Calendar.getInstance());
//				map.put("age",1);
//				map.put("array", new Object[]{1,2,"abc"});
//				map.put("name","joelli");
////				
//				String json = tool.serialize(map,"[array][0]:i");
//				System.out.println("jsonString="+json);
			}
			
			{
//				List list = new ArrayList();
//				list.add(new Foo());
//				list.add(new Foo());
//				
////				list.add(1);
////				list.add(new Foo());
//				String json = tool.serialize(list,"[0].~*:u/id/");
//				System.out.println("jsonString="+json);
			}
//			
			{
//				String jsonString = tool.serialize(new Foo());
//				System.out.println("jsonString="+jsonString);
			}
			{
//				Map<String, Object> m = new HashMap<String, Object>();
//				String argsContent = "{\"carBrands\":[\"15123\"],\"companyId\":[\"5\"]}";
//				System.out.println(argsContent);
//				m.put("argsContent", argsContent);
////				System.out.println(Json.toJson(m));
//				System.out.println(tool.serialize(m));

			}
			
			{
//				Map<String, Object> m = new HashMap<String, Object>();
////				m.put("name", "姓名");
////				m.put("age", 32);				
//				m.put("list", null);
//				System.out.println(tool.serialize(m));

			}
			{
//				List list = new ArrayList();
//				list.add("\"hello");
////				list.add(new ArrayList());		
//				list.add(null);
//				System.out.println(tool.serialize(list));
			}
			
		
			
		
		}
	}	

}

class Foo{
	int id =1;
	String age = "12";
//	List list = null;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
//	public List getList() {
//		return list;
//	}
//	public void setList(List list) {
//		this.list = list;
//	}
	
	
}

class User{	
	int id = 1;
	Customer  customer  = null;	
	public User(){
		
	}
	
	
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
}

class Customer{
	String name="lijz1";	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	
	
}

class Product{
	String name = "p";
	User user = null;
	Customer customer;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
}
