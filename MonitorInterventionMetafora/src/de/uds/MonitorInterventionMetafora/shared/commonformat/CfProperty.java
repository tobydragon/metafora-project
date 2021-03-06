package de.uds.MonitorInterventionMetafora.shared.commonformat;

import java.io.Serializable;

public class CfProperty   implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7198450909563822060L;
	private String name, value, id;

	public CfProperty(){}
	
	public CfProperty(String name, String value, String id) {
		this(name, value);
		this.id = id;
	}
	
	public CfProperty(String name, String value) {
		this.name = name;
		this.value = value;
		id=null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String toString(){
//		return name + " - " + value;
		return  value;
	}

//	public XmlFragment toXml(){
//		XmlFragment xmlFragment = new XmlFragment(CommonFormatStrings.PROPERTY_STRING);
//		xmlFragment.setAttribute(CommonFormatStrings.NAME_STRING, getName());
//		xmlFragment.setAttribute(CommonFormatStrings.VALUE_STRING, getValue());
//		if (id != null){
//			xmlFragment.setAttribute(CommonFormatStrings.ID_STRING, getId());
//		}
//		return xmlFragment;
//	}
//	
//	public static CfProperty fromXml(XmlFragmentInterface xmlFragment){
//		String name = xmlFragment.getAttributeValue(CommonFormatStrings.NAME_STRING);
//		String value = xmlFragment.getAttributeValue(CommonFormatStrings.VALUE_STRING);
//		value = xmlFragment.convertSpecialCharacterDescriptionsBack(value);
//		String id = xmlFragment.getAttributeValue(CommonFormatStrings.ID_STRING);
//		if (id != null){
//			return new CfProperty(name, value, id);
//		}
//		else {
//			return new CfProperty(name, value);
//		}
//	}
}
