package de.uds.MonitorInterventionMetafora.server.commonformatparser;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import de.uds.MonitorInterventionMetafora.server.xml.XmlFragment;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfInteractionData;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CommonFormatStrings;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import de.uds.MonitorInterventionMetafora.shared.commonformat.RunestoneStrings;




public class CfInteractionDataParser {
	static Logger logger = Logger.getLogger(CfInteractionDataParser.class);
	
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
	
	public static CfInteractionData fromRunestoneXml(XmlFragment xmlFragment){
		List<CfAction> cfActions = new ArrayList<CfAction>();
		
		
		for (XmlFragment cfActionElement : xmlFragment.getChildren(RunestoneStrings.ROW_STRING)){
			cfActions.add(CfActionParser.fromRunsetoneXml(cfActionElement));
		}
		
		return new CfInteractionData(cfActions);
	}
	
	public static CfInteractionData getTestableInstance(){
		return CfInteractionDataParser.fromXml(XmlFragment.getFragmentFromLocalFile("war/conffiles/xml/test/runestoneXml/commonFormatExample.xml"));
	}
	
	public static void main(String []args){
		//CfInteractionData testActions = getTestableInstance();
		
		//logger.info(testActions.toString());
		//logger.info(CfInteractionDataParser.toXml(testActions));
		
//		XmlFragment runestoneFrag = XmlFragment.getFragmentFromLocalFile("war/conffiles/xml/test/runestoneXml/oneWeek.xml");
//		//logger.info(runestoneFrag.toString());
//		CfInteractionData testCf = CfInteractionDataParser.fromRunestoneXml(runestoneFrag);
//		//testCf.replaceAllIds();
//		XmlFragment cfFrag = CfInteractionDataParser.toXml(testCf);
//		logger.info(cfFrag);
//		cfFrag.overwriteFile("war/conffiles/xml/test/runestoneXml/oneWeekCF.xml");
	
	}

}
