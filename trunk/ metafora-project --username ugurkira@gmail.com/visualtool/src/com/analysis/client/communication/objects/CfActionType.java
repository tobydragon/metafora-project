package com.analysis.client.communication.objects;

import com.analysis.client.xml.GWTXmlFragment;



public class CfActionType {
	private String type, classification, succeed;

	public CfActionType(String type, String classification, String succeed) {
		this.type = type;
		this.classification = classification;
		this.succeed = succeed;
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
	
	public String getSucceeded() {
		return succeed;
	}

	public void setSucceed(String succeeded) {
		this.succeed = succeeded;
	}
/*
	public XmlFragment toXml(){
		XmlFragment xmlFragment = new XmlFragment(CommonFormatStrings.ACTION_TYPE_STRING);
		xmlFragment.setAttribute(CommonFormatStrings.TYPE_STRING, getType());
		xmlFragment.setAttribute(CommonFormatStrings.CLASSIFICATION_STRING, getClassification());
		if (succeed != null){
			xmlFragment.setAttribute(CommonFormatStrings.SUCCEED_STRING, succeed);
		}

		return xmlFragment;
	}
	
	public static CfActionType fromXml(XmlFragment xmlFragment){
		String type = xmlFragment.getAttributeValue(CommonFormatStrings.TYPE_STRING);
		String classification = xmlFragment.getAttributeValue(CommonFormatStrings.CLASSIFICATION_STRING);
		String succeeded = xmlFragment.getAttributeValue(CommonFormatStrings.SUCCEED_STRING);
		
		return new CfActionType (type, classification, succeeded);
	}*/

}
