package de.uds.MonitorInterventionMetafora.shared.commonformat;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.i18n.client.LocalizableResource.Description;



public class CfContent implements Serializable  {
	//static Log logger = LogFactory.getLog(CfContent.class);

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 798674379182318122L;

	private String description;
	
//	private long  _actionTime;
	private Map<String, CfProperty> properties;
	
	public CfContent(){
	  super();	
	  this.properties = new HashMap<String, CfProperty>();
	}
	
	public CfContent(String description) {
		super();
		this.description = description;
		this.properties = new HashMap<String, CfProperty>();
	}
	
	public CfContent(String description, Map<String, CfProperty> properties) {
		super();
		this.description = description;
		this.properties = properties;
	}

	
	
//	public void setActionTime(long _time){
//		_actionTime=_time;
//	}
//	
//	public long getActionTime(){
//		
//		return _actionTime;
//	}
	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}

	public void addProperty(CfProperty property){
		properties.put(property.getName(), property);
	}
	
	public String getPropertyValue(String propertyName) {
		CfProperty property = getProperty(propertyName);
		if (property != null){
			return property.getValue();
		}
		return null;
	}
	
	public CfProperty removeProperty(String propertyName){
		return properties.remove(propertyName);
	}

	public CfProperty getProperty(String propertyName) {
		return properties.get(propertyName);
	}

	public Map<String, CfProperty> getProperties() {
		return properties;
	}
	
	public String toString(){
		String str= CommonFormatStrings.CONTENT_STRING + "\n" ;
		str += "\t" + CommonFormatStrings.DESCRIPTION_STRING + " - " + description +"\n";
		str += "\t" + CommonFormatStrings.PROPERTIES_STRING +  " - " + properties.toString();
		return str;	
	}

	public void replaceStringInDescription(String oldName, String newName) {
		
		description = description.replaceAll(oldName, newName);
		System.out.println("Old: " + oldName + "\tNew: " + newName + "\tDesc: " + description);
	}
	
}
