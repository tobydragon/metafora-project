package de.uds.MonitorInterventionMetafora.server.commonformatparser;

import java.util.ArrayList;
import java.util.List;

import de.uds.MonitorInterventionMetafora.server.xml.XmlFragment;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfInteractionData;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CommonFormatStrings;



public class CfInteractionDataParser {
	
	public static XmlFragment toXml(CfInteractionData cfInteractionData){
		XmlFragment xmlFragment= new XmlFragment(CommonFormatStrings.INTERACTION_DATA_STRING);
		XmlFragment actionsFragment = new XmlFragment(CommonFormatStrings.ACTIONS_STRING);
		for (CfAction cfAction : cfInteractionData.getCfActions()){
			actionsFragment.addContent(CfActionParser.toXml(cfAction));
		}
		xmlFragment.addContent(actionsFragment);
		return xmlFragment;
	}
	
	public static CfInteractionData fromXml(XmlFragment xmlFragment){
		List<CfAction> cfActions = new ArrayList<CfAction>();
		
		XmlFragment actionsFragment = xmlFragment.cloneChild(CommonFormatStrings.ACTIONS_STRING);
		for (XmlFragment cfActionElement : actionsFragment.getChildren(CommonFormatStrings.ACTION_STRING)){
			cfActions.add(CfActionParser.fromXml(cfActionElement));
		}
		
		return new CfInteractionData(cfActions);
	}

}
