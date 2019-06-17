package org.beetl.json.loc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.beetl.json.Location;

public class IndexLocationList {
	Set<IndexLocation> set = new HashSet<IndexLocation>();
	
	public void addIndexLocation(IndexLocation l){
			set.add(l);
		
	}
	
	public List<IndexLocation> match(Object key){
		Iterator<IndexLocation> it = set.iterator();
		List list = new ArrayList<IndexLocation>();
		while(it.hasNext()){
			IndexLocation l = it.next();
			if(l.getRef().equals("*")){
				if(l.getAction()!=null){
					list.add(l);
				}
				
			}
			if(l.getRef().equals(key)){
				if(l.getAction()!=null){
					list.add(l);
				}
				
			}
		}
		return list;
	}
	
	public List<Location>  getMatchPolicy(Object key){
		List<Location> list = new LinkedList<Location>();
		Iterator<IndexLocation> it = set.iterator();
		while(it.hasNext()){
			IndexLocation l = it.next();
			if(l.getRef().equals("*")){
				if(list==null){
					list = new LinkedList<Location>();
				}
				list.add(l.getNextLoc());
					
			}
			if(l.getRef().equals(key)){
				if(list==null){
					list = new LinkedList<Location>();
				}
				list.add(l.getNextLoc());
				
			}
		}
		return list;
		
	}
}
