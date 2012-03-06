package de.uds.visualizer.shared.interactionmodels;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class IndicatorFilter implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7810691925967577846L;
	String name="";
	String editable="true";
	
	HashMap<String, IndicatorFilterItem> _properties;
	
	public IndicatorFilter(){
		
		_properties=new HashMap<String, IndicatorFilterItem>();
	}
	
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
	public  void addFilterItem(String _key,IndicatorFilterItem  _value){
		
		_properties.put(_key, _value);	
		
	}
	
	
	public IndicatorFilterItem getFilterItem(String _key){
		
		if(_properties.containsKey(_key))
			return _properties.get(_key);
		return new IndicatorFilterItem();
		
	}

	
	public Map<String, IndicatorFilterItem> getProperties(){
		
		return _properties;
	}
	
	
	
	
}
