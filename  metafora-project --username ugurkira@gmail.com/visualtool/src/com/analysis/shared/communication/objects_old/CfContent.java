package com.analysis.shared.communication.objects_old;

import java.util.HashMap;
import java.util.Map;

//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;

import de.uds.visualizer.client.xml.GWTXmlFragment;



public class CfContent {
	//static Log logger = LogFactory.getLog(CfContent.class);

	
	private String descripiton;
	
	private Map<String, CfProperty> properties;
	
	public CfContent(String description) {
		super();
		this.descripiton = description;
		this.properties = new HashMap<String, CfProperty>();
	}
	
	public CfContent(String description, Map<String, CfProperty> properties) {
		super();
		this.descripiton = description;
		this.properties = properties;
	}

	
	
	public String getDescription() {
		return descripiton;
	}


	public void setDescription(String description) {
		this.descripiton = description;
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
	

	public Map<String, CfProperty> getContentProperties(){
				
		return properties;
	}

/*
	public XmlFragment toXml(){
		XmlFragment xmlFragment= new XmlFragment(CommonFormatStrings.CONTENT_STRING);
		
		XmlFragment descripitonFragment = new XmlFragment(CommonFormatStrings.DESCRIPTION_STRING);
		descripitonFragment.addCdataContent(descripiton);
		xmlFragment.addContent(descripitonFragment);
		
		XmlFragment propertyFragment = new XmlFragment(CommonFormatStrings.PROPERTIES_STRING);
		for (CfProperty objectProperty : properties.values()){
			propertyFragment.addContent(objectProperty.toXml());
		}
		xmlFragment.addContent(propertyFragment);
		return xmlFragment;	
	}
	
	public static CfContent fromXml(XmlFragment xmlFragment){
		String description = xmlFragment.getChildValue(CommonFormatStrings.DESCRIPTION_STRING);
		
		XmlFragment propertyFragment = xmlFragment.getChild(CommonFormatStrings.PROPERTIES_STRING);
		Map<String, CfProperty> cfProperties = new HashMap<String, CfProperty>();
		
		if (propertyFragment != null){
			for (XmlFragment cfPropertyElement : propertyFragment.getChildren(CommonFormatStrings.PROPERTY_STRING)){
				CfProperty cfProperty = CfProperty.fromXml(cfPropertyElement);
				cfProperties.put(cfProperty.getName(), cfProperty);
			}
		}
		else {
			logger.warn("[fromXml] no properties fragment found for content with description - " + description);
		}
		return new CfContent(description, cfProperties);	
	}*/
	

}
