package de.uds.visualizer.shared.interactionmodels;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;




public class Configuration implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4344917692123021453L;
	String name="";
	String _dataSourceType="";
	
	
	Map<String, IndicatorFilter> filters;
	
	
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
	
	
	public String getActionSource(){
		
		return _dataSourceType;
	}
	
	public void setDataSource(String _source){
		
		_dataSourceType=_source;
	}
	
	
	
}
