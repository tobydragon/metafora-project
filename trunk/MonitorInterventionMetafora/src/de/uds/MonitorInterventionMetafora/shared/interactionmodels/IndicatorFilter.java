package de.uds.MonitorInterventionMetafora.shared.interactionmodels;

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
	
	HashMap<String, IndicatorEntity> _properties;
	
	public IndicatorFilter(){
		
		_properties=new HashMap<String, IndicatorEntity>();
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
	public  void addIndicatorEntity(String _key,IndicatorEntity  _value){
		
		_properties.put(_key, _value);	
		
	}
	
	
	public IndicatorEntity getIndicatorEntity(String _key){
		
		if(_properties.containsKey(_key))
			return _properties.get(_key);
		return new IndicatorEntity();
		
	}

	
	public Map<String, IndicatorEntity> getEntities(){
		
		return _properties;
	}
	
	
	
	
}
