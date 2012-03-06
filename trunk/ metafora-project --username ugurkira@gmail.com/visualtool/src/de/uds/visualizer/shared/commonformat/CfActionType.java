package de.uds.visualizer.shared.commonformat;

import java.io.Serializable;

public class CfActionType implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2581705673900090200L;
	private String type, classification, succeed, logged;

	public CfActionType(){}
	
	public CfActionType(String type, String classification, String succeed, String logged) {
		this.type = type;
		this.classification = classification;
		this.succeed = succeed;
		this.logged = logged;
	}
	
	
	public CfActionType(String type, String classification, String succeed) {
		this(type, classification, succeed, null);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getClassification() {
		return classification;
	}

	public void setClassification(String classification) {
		this.classification = classification;
	}
	
	public String getSucceed() {
		return succeed;
	}

	public void setSucceed(String succeeded) {
		this.succeed = succeeded;
	}

	public String getLogged() {
		return logged;
	}

	public void setLogged(String logged) {
		this.logged = logged;
	}
	
	

//	public XmlFragment toXml(){
//		XmlFragment xmlFragment = new XmlFragment(CommonFormatStrings.ACTION_TYPE_STRING);
//		xmlFragment.setAttribute(CommonFormatStrings.TYPE_STRING, getType());
//		xmlFragment.setAttribute(CommonFormatStrings.CLASSIFICATION_STRING, getClassification());
//		if (succeed != null){
//			xmlFragment.setAttribute(CommonFormatStrings.SUCCEED_STRING, succeed);
//		}
//
//		return xmlFragment;
//	}
//	
//	public static CfActionType fromXml(XmlFragmentInterface xmlFragment){
//		String type = xmlFragment.getAttributeValue(CommonFormatStrings.TYPE_STRING);
//		String classification = xmlFragment.getAttributeValue(CommonFormatStrings.CLASSIFICATION_STRING);
//		String succeeded = xmlFragment.getAttributeValue(CommonFormatStrings.SUCCEED_STRING);
//		
//		return new CfActionType (type, classification, succeeded);
//	}

}
