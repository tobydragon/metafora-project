package com.analysis.client.datamodels;

import java.util.HashMap;
import java.util.Map;

public class ExtendedActionFilter {

	
	String name="";
	String editable="true";
	Map<String, String> properties=new HashMap<String, String>();
	
	
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
	public  void addProperty(String _key,String _value){
		
		properties.put(_key, _value);	
		
	}
	
	
	public String getProperty(String _key){
		
		if(properties.containsKey(_key))
			return properties.get(_key);
		return "";
		
	}

	
	public Map<String, String> getProperties(){
		
		return properties;
	}
	
	
	
	
}
