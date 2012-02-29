package com.analysis.shared.commonformat;

import java.util.ArrayList;
import java.util.List;

import com.analysis.server.xml.XmlFragment;
import com.analysis.server.xml.XmlFragmentInterface;


public class CfInteractionData {
	
	private List<CfAction> cfActions;
	
	public CfInteractionData(List<CfAction> cfActions){
		this.cfActions = cfActions;
	}

	public List<CfAction> getCfActions() {
		return cfActions;
	}
	
	
	
//	public XmlFragmentInterface toXml(){
//		XmlFragmentInterface xmlFragment= new XmlFragment(CommonFormatStrings.INTERACTION_DATA_STRING);
//		XmlFragment actionsFragment = new XmlFragment(CommonFormatStrings.ACTIONS_STRING);
//		for (CfAction cfAction : cfActions){
//			actionsFragment.addContent(cfAction.toXml());
//		}
//		xmlFragment.addContent(actionsFragment);
//		return xmlFragment;
//	}
//	
//	public static CfInteractionData fromXml(XmlFragmentInterface xmlFragment){
//		List<CfAction> cfActions = new ArrayList<CfAction>();
//		
//		XmlFragmentInterface actionsFragment = xmlFragment.cloneChild(CommonFormatStrings.ACTIONS_STRING);
//		for (XmlFragmentInterface cfActionElement : actionsFragment.getChildren(CommonFormatStrings.ACTION_STRING)){
//			cfActions.add(CfAction.fromXml(cfActionElement));
//		}
//		
//		return new CfInteractionData(cfActions);
//	}

}
