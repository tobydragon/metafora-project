package de.uds.MonitorInterventionMetafora.shared.commonformat;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;



public class CfContent implements Serializable  {
	//static Log logger = LogFactory.getLog(CfContent.class);

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 798674379182318122L;

	private String descripiton;
	
	private long  _actionTime;
	private Map<String, CfProperty> properties;
	
	public CfContent(){
	  super();	
	  this.properties = new HashMap<String, CfProperty>();
	}
	
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

	
	
	public void setActionTime(long _time){
		_actionTime=_time;
	}
	
	public long getActionTime(){
		
		return _actionTime;
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

	public Map<String, CfProperty> getProperties() {
		return properties;
	}
	
	public String toString(){
		String str= CommonFormatStrings.CONTENT_STRING + "\n" ;
		str += "\t" + CommonFormatStrings.DESCRIPTION_STRING + " - " + descripiton +"\n";
		str += "\t" + CommonFormatStrings.PROPERTIES_STRING +  " - " + properties.toString();
		return str;	
	}
	
//	public XmlFragment toXml(){
//		XmlFragment xmlFragment= new XmlFragment(CommonFormatStrings.CONTENT_STRING);
//		
//		XmlFragment descripitonFragment = new XmlFragment(CommonFormatStrings.DESCRIPTION_STRING);
//		descripitonFragment.addCdataContent(descripiton);
//		xmlFragment.addContent(descripitonFragment);
//		
//		XmlFragment propertyFragment = new XmlFragment(CommonFormatStrings.PROPERTIES_STRING);
//		for (CfProperty objectProperty : properties.values()){
//			propertyFragment.addContent(objectProperty.toXml());
//		}
//		xmlFragment.addContent(propertyFragment);
//		return xmlFragment;	
//	}
//	
//	public static CfContent fromXml(XmlFragmentInterface xmlFragment){
//		String description = xmlFragment.getChildValue(CommonFormatStrings.DESCRIPTION_STRING);
//		
//		XmlFragmentInterface propertyFragment = xmlFragment.cloneChild(CommonFormatStrings.PROPERTIES_STRING);
//		Map<String, CfProperty> cfProperties = new HashMap<String, CfProperty>();
//		
//		if (propertyFragment != null){
//			for (XmlFragmentInterface cfPropertyElement : propertyFragment.getChildren(CommonFormatStrings.PROPERTY_STRING)){
//				CfProperty cfProperty = CfProperty.fromXml(cfPropertyElement);
//				cfProperties.put(cfProperty.getName(), cfProperty);
//			}
//		}
//		else {
//			logger.warn("[fromXml] no properties fragment found for content with description - " + description);
//		}
//		return new CfContent(description, cfProperties);	
//	}
//	

}
