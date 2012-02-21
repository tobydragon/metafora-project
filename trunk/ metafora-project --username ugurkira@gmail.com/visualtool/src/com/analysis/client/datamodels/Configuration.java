package com.analysis.client.datamodels;

import java.util.ArrayList;
import java.util.List;

public class Configuration {

	String name="";
	
	List<ExtendedActionFilter> filters=new ArrayList<ExtendedActionFilter>();
	
	public void setName(String _name){
		
		name=_name;
	}
	
	public String getName(){
				
		return name;
	}
	
	public void addFilter(ExtendedActionFilter af){
		
		filters.add(af);
	}
	
	
	public List<ExtendedActionFilter> getFilters(){
		
		return filters;
	}
	
	
	
}
