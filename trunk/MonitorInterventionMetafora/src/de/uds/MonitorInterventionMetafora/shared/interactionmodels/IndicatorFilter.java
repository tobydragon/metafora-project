package de.uds.MonitorInterventionMetafora.shared.interactionmodels;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class IndicatorFilter implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7810691925967577846L;
	String name="";
	String editable="true";
	
	HashMap<String, IndicatorProperty> filterIndicatorEntities;
	
	public IndicatorFilter(){
		
		filterIndicatorEntities=new HashMap<String, IndicatorProperty>();
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
	public  void addIndicatorEntity(String _key,IndicatorProperty  _value){
		
		filterIndicatorEntities.put(_key, _value);	
		
	}
	
	
	public IndicatorProperty getIndicatorEntity(String _key){
		
		if(filterIndicatorEntities.containsKey(_key))
			return filterIndicatorEntities.get(_key);
		return new IndicatorProperty();
		
	}

	
	public List<IndicatorProperty> toIndicatorEntityList(){
		
		List<IndicatorProperty> indicatorEntityList=new ArrayList<IndicatorProperty>();
		for(String _key:filterIndicatorEntities.keySet())
		{
			
			indicatorEntityList.add(filterIndicatorEntities.get(_key));
			
		}
		
		return indicatorEntityList;
	}
	
	
	public Map<String, IndicatorProperty> getIndicatorEntities(){
		
		return filterIndicatorEntities;
	}
	
	
	
	
}
