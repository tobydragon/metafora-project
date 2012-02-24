package com.analysis.client.communication.objects;

import java.util.ArrayList;
import java.util.List;

import com.analysis.client.xml.GWTXmlFragment;



public class CfInteractionData {
	
	private static List<CfAction> cfActions=null;
	
	public CfInteractionData(List<CfAction> cfActions){
		this.cfActions = cfActions;
	}
	/*
	public XmlFragment toXml(){
		XmlFragment xmlFragment= new XmlFragment(CommonFormatStrings.INTERACTION_DATA_STRING);
		XmlFragment actionsFragment = new XmlFragment(CommonFormatStrings.ACTIONS_STRING);
		for (CfAction cfAction : cfActions){
			actionsFragment.addContent(cfAction.toXml());
		}
		xmlFragment.addContent(actionsFragment);
		return xmlFragment;
	}
	
	public static CfInteractionData fromXml(XmlFragment xmlFragment){
		List<CfAction> cfActions = new ArrayList<CfAction>();
		
		XmlFragment actionsFragment = xmlFragment.getChild(CommonFormatStrings.ACTIONS_STRING);
		for (XmlFragment cfActionElement : actionsFragment.getChildren(CommonFormatStrings.ACTION_STRING)){
			cfActions.add(CfAction.fromXml(cfActionElement));
		}
		
		return new CfInteractionData(cfActions);
	}

	*/

	public static List<CfAction> getcfActions(){
		
		return cfActions;
	}
}
