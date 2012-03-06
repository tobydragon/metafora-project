package de.uds.visualizer.shared.communication.objects_old;

import java.util.HashMap;
import java.util.Map;

import de.uds.visualizer.client.xml.GWTXmlFragment;



public class CfObject {
	
	private String id;
	private String type;
	
	private Map<String, CfProperty> properties;
	
	public CfObject(String id, String type) {
		super();
		this.id = id;
		this.type = type;
		this.properties = new HashMap<String, CfProperty>();
	}
	

	
	public CfObject(String id, String type, Map<String, CfProperty> properties) {
		super();
		this.id = id;
		this.type = type;
		this.properties = properties;
	}

	
	
	public String getId() {
		return id;
	}



	public String getType() {
		return type;
	}



	public void setId(String id) {
		this.id = id;
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

	public Map<String, CfProperty> getObjectProperties(){
				
		return properties;
	}
	/*

	public XmlFragment toXml(){
		XmlFragment xmlFragment= new XmlFragment(CommonFormatStrings.OBJECT_STRING);
		xmlFragment.setAttribute(CommonFormatStrings.ID_STRING, id);
		xmlFragment.setAttribute(CommonFormatStrings.TYPE_STRING, type);
		XmlFragment propertyFragment = new XmlFragment(CommonFormatStrings.PROPERTIES_STRING);
		for (CfProperty objectProperty : properties.values()){
			propertyFragment.addContent(objectProperty.toXml());
		}
		xmlFragment.addContent(propertyFragment);
		return xmlFragment;	
	}
	
	public static CfObject fromXml(XmlFragment xmlFragment){
		String id = xmlFragment.getAttributeValue(CommonFormatStrings.ID_STRING);
		String type = xmlFragment.getAttributeValue(CommonFormatStrings.TYPE_STRING);
		
		XmlFragment propertyFragment = xmlFragment.getChild(CommonFormatStrings.PROPERTIES_STRING);
		Map<String, CfProperty> cfProperties = new HashMap<String, CfProperty>();
		for (XmlFragment cfPropertyElement : propertyFragment.getChildren(CommonFormatStrings.PROPERTY_STRING)){
			CfProperty cfProperty = CfProperty.fromXml(cfPropertyElement);
			cfProperties.put(cfProperty.getName(), cfProperty);
		}
		return new CfObject(id, type, cfProperties);	
	}
	*/

}
