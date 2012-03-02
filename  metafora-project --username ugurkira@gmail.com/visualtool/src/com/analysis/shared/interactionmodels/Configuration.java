package com.analysis.shared.interactionmodels;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Configuration implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4344917692123021453L;
	String name="";
	String _datasourceType="";
	
	
	Map<String, IndicatorFilter> filters=new HashMap<String,IndicatorFilter>();
	
	
	public Configuration(){
		
		filters=new HashMap<String,IndicatorFilter>();
		
	}
	public void setName(String _name){

		name=_name;
	}
	
	public String getName(){
				
		return name;
	}
	
	public void addFilter(IndicatorFilter af){
		
		filters.put(af.name, af);
		//filters.add(af);
	}
	
	
	public Map<String, IndicatorFilter>  getFilters(){
		
		return filters;
	}
	
	
	public String getDataSource(){
		
		return _datasourceType;
	}
	
	public void setDataSource(String _source){
		
		_datasourceType=_source;
	}
	
	
	
}
