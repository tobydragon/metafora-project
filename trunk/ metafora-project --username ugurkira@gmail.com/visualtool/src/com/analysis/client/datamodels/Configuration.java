package com.analysis.client.datamodels;

import java.util.ArrayList;
import java.util.List;

public class Configuration {

	String name="";
	
	List<IndicatorFilter> filters=new ArrayList<IndicatorFilter>();
	
	public void setName(String _name){
		
		name=_name;
	}
	
	public String getName(){
				
		return name;
	}
	
	public void addFilter(IndicatorFilter af){
		
		filters.add(af);
	}
	
	
	public List<IndicatorFilter> getFilters(){
		
		return filters;
	}
	
	
	
}
