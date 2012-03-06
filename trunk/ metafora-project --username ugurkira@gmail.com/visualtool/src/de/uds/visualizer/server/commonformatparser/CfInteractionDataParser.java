package de.uds.visualizer.server.commonformatparser;

import java.util.ArrayList;
import java.util.List;

import de.uds.visualizer.server.xml.XmlFragment;
import de.uds.visualizer.server.xml.XmlFragmentInterface;
import de.uds.visualizer.shared.commonformat.CfAction;
import de.uds.visualizer.shared.commonformat.CfInteractionData;
import de.uds.visualizer.shared.commonformat.CommonFormatStrings;



public class CfInteractionDataParser {
	
	public static XmlFragmentInterface toXml(CfInteractionData cfInteractionData){
		XmlFragmentInterface xmlFragment= new XmlFragment(CommonFormatStrings.INTERACTION_DATA_STRING);
		XmlFragment actionsFragment = new XmlFragment(CommonFormatStrings.ACTIONS_STRING);
		for (CfAction cfAction : cfInteractionData.getCfActions()){
			actionsFragment.addContent(CfActionParser.toXml(cfAction));
		}
		xmlFragment.addContent(actionsFragment);
		return xmlFragment;
	}
	
	public static CfInteractionData fromXml(XmlFragmentInterface xmlFragment){
		List<CfAction> cfActions = new ArrayList<CfAction>();
		
		XmlFragmentInterface actionsFragment = xmlFragment.cloneChild(CommonFormatStrings.ACTIONS_STRING);
		for (XmlFragmentInterface cfActionElement : actionsFragment.getChildren(CommonFormatStrings.ACTION_STRING)){
			cfActions.add(CfActionParser.fromXml(cfActionElement));
		}
		
		return new CfInteractionData(cfActions);
	}

}
