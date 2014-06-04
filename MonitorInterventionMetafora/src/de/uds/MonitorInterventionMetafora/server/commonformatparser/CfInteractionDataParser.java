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
		
		XmlFragment actionsFragment = xmlFragment.cloneChild(RunestoneStrings.ROWS_STRING);
		for (XmlFragment cfActionElement : actionsFragment.getChildren(RunestoneStrings.ROW_STRING)){
			cfActions.add(CfActionParser.fromRunsetoneXml(cfActionElement));
		}
		
		return new CfInteractionData(cfActions);
	}
	
	public static CfInteractionData getTestableInstance(){
		return CfInteractionDataParser.fromXml(XmlFragment.getFragmentFromLocalFile("war/conffiles/xml/test/runestoneXml/commonFormatExample.xml"));
	}
	
	public static void main(String []args){
		CfInteractionData testActions = getTestableInstance();
		
		logger.info(testActions.toString());
		logger.info(CfInteractionDataParser.toXml(testActions));
		
		XmlFragment runestoneFrag = XmlFragment.getFragmentFromLocalFile("war/conffiles/xml/test/runestoneXml/runestoneExample.xml");
		logger.info(runestoneFrag.toString());
		
		String ts = (runestoneFrag.getChildren("row").get(0).getChildValue("timestamp"));
	
		
		//converts a time stamp to epoch
		DateFormat dateForm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //not sure if to use HH (0-23) or kk (1-24)
		try{
		long date1 = dateForm.parse(ts).getTime();
		long date2 = dateForm.parse("2014-01-29 19:22:05").getTime();
		long date3 = dateForm.parse("2014-06-13 3:42:32").getTime();
		
		
		System.out.println("Epoch time of 2014-01-29 19:22:12: " + (date1/1000)); //divide by 1000 to convert to seconds
		System.out.println("Epoch time of 2014-01-29 19:22:05: " + (date2/1000));
		System.out.println("Epoch time of 2014-06-13 3:42:32: " + (date3/1000));
		}
		catch (ParseException e){
			;
		
		}
	
	}

}
