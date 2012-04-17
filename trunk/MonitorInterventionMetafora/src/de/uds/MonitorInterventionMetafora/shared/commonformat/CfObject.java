package de.uds.MonitorInterventionMetafora.shared.commonformat;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class CfObject implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6424779097062164631L;
	private String id;
	private String type;
	private long  _actionTime;
	
	private Map<String, CfProperty> properties;
	
	public CfObject(){
		
		
	}
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

	public void setActionTime(long _time){
		_actionTime=_time;
	}
	
	public long getActionTime(){
		
		return _actionTime;
	}
	
	public String getId() {
		return id;
	}



	public String getType() {
		return type;
	}

	public void setType(String _type) {
	 this.type=_type;
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

	public Map<String, CfProperty> getProperties() {
		return properties;
	}
	
	public String toString(){

		String str= CommonFormatStrings.OBJECT_STRING + "\n" ;
		str += "\t" + CommonFormatStrings.ID_STRING + " - " + id +"\t : " +  CommonFormatStrings.TYPE_STRING + " - " + type;
		str += "\t : " + CommonFormatStrings.PROPERTIES_STRING + " - " + properties.toString();
		return str;	

	}
	
//
//	public XmlFragment toXml(){
//		XmlFragment xmlFragment= new XmlFragment(CommonFormatStrings.OBJECT_STRING);
//		xmlFragment.setAttribute(CommonFormatStrings.ID_STRING, id);
//		xmlFragment.setAttribute(CommonFormatStrings.TYPE_STRING, type);
//		XmlFragment propertyFragment = new XmlFragment(CommonFormatStrings.PROPERTIES_STRING);
//		for (CfProperty objectProperty : properties.values()){
//			propertyFragment.addContent(CfPropertyParser.toXml(objectProperty));
//		}
//		xmlFragment.addContent(propertyFragment);
//		return xmlFragment;	
//	}
//	
//	public static CfObject fromXml(XmlFragmentInterface xmlFragment){
//		String id = xmlFragment.getAttributeValue(CommonFormatStrings.ID_STRING);
//		String type = xmlFragment.getAttributeValue(CommonFormatStrings.TYPE_STRING);
//		
//		XmlFragmentInterface propertyFragment = xmlFragment.cloneChild(CommonFormatStrings.PROPERTIES_STRING);
//		Map<String, CfProperty> cfProperties = new HashMap<String, CfProperty>();
//		for (XmlFragmentInterface cfPropertyElement : propertyFragment.getChildren(CommonFormatStrings.PROPERTY_STRING)){
//			CfProperty cfProperty = CfPropertyParser.fromXml(cfPropertyElement);
//			cfProperties.put(cfProperty.getName(), cfProperty);
//		}
//		return new CfObject(id, type, cfProperties);	
//	}
	

}
