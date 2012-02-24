package com.analysis.client.datamodels;

import java.util.HashMap;
import java.util.Map;

public class IndicatorFilter {

	
	String name="";
	String editable="true";
	
	HashMap<String, ExtendedIndicatorFilterItem> properties=new HashMap<String, ExtendedIndicatorFilterItem>();
	
	
	public void setName(String _name){
		name=_name;
		
	}
	
	public String getName(){
		
		return name;
	}
	
	

	
	
	public void setEditable(String _editable){
		
		editable=_editable;
	}
	
	public String getEditable(){		
		return editable;
	}
	public  void addFilterItem(String _key,ExtendedIndicatorFilterItem  _value){
		
		properties.put(_key, _value);	
		
	}
	
	
	public ExtendedIndicatorFilterItem getFilterItem(String _key){
		
		if(properties.containsKey(_key))
			return properties.get(_key);
		return new ExtendedIndicatorFilterItem();
		
	}

	
	public Map<String, ExtendedIndicatorFilterItem> getProperties(){
		
		return properties;
	}
	
	
	
	
}
