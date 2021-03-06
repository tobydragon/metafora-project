package de.uds.MonitorInterventionMetafora.server.commonformatparser;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
/*
import de.uds.commonformat.CfAction;
import de.uds.commonformat.CfActionType;
import de.uds.commonformat.CfContent;
import de.uds.commonformat.CfObject;
import de.uds.commonformat.CfUser;
import de.uds.commonformat.CommonFormatStrings;
import de.uds.commonformat.CommonFormatUtil;
import de.uds.xml.XmlFragment;
import de.uds.xml.XmlFragmentInterface;*/




import de.uds.MonitorInterventionMetafora.server.xml.XmlFragment;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfActionType;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfContent;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfObject;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfUser;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CommonFormatStrings;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CommonFormatUtil;
import de.uds.MonitorInterventionMetafora.shared.commonformat.RunestoneStrings;


public class CfActionParser {
	
	public static XmlFragment toXml(CfAction cfAction){
		XmlFragment xmlFragment= new XmlFragment(CommonFormatStrings.ACTION_STRING);
		xmlFragment.setAttribute(CommonFormatStrings.TIME_STRING, Long.toString(cfAction.getTime()));
		
		xmlFragment.addContent(CfActionTypeParser.toXml(cfAction.getCfActionType()));
		for (CfUser cfUser : cfAction.getCfUsers()){
			xmlFragment.addContent(CfUserParser.toXml(cfUser));
		}
		for (CfObject cfObject : cfAction.getCfObjects()){
			xmlFragment.addContent(CfObjectParser.toXml(cfObject));
		}
		
		if (cfAction.getCfContent() != null){
			xmlFragment.addContent(CfContentParser.toXml(cfAction.getCfContent()));
		}
		return xmlFragment;	
	}
	
	public static CfAction fromXml(XmlFragment xmlFragment){
		String timeStr = xmlFragment.getAttributeValue(CommonFormatStrings.TIME_STRING);
		long time = CommonFormatUtil.getTime(timeStr);
		
		CfActionType cfActionType = CfActionTypeParser.fromXml(xmlFragment.cloneChild(CommonFormatStrings.ACTION_TYPE_STRING));
		
		List<CfUser> cfUsers = new ArrayList<CfUser>();
		for (XmlFragment cfUserElement : xmlFragment.getChildren(CommonFormatStrings.USER_STRING)){
			cfUsers.add(CfUserParser.fromXml(cfUserElement));
		}
		
		List<CfObject> cfObjects = new ArrayList<CfObject>();
		for (XmlFragment cfObjectElement : xmlFragment.getChildren(CommonFormatStrings.OBJECT_STRING)){
			cfObjects.add(CfObjectParser.fromXml(cfObjectElement));
		}
		
		XmlFragment cfContentElement = xmlFragment.cloneChild(CommonFormatStrings.CONTENT_STRING);
		if (cfContentElement != null){
			CfContent cfContent = CfContentParser.fromXml(cfContentElement);
			return new CfAction (time, cfActionType, cfUsers, cfObjects, cfContent);
		}
		
		return new CfAction (time, cfActionType, cfUsers, cfObjects);
	}

	public static CfAction fromRunsetoneXml(XmlFragment xmlFragment) {
		//TODO: get each element from rusnestone xml and create a CfAction object		
		
		String timestampStr = xmlFragment.getChildValue(RunestoneStrings.TIMESTAMP_STRING);
		DateFormat dateForm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long time = 0;
		
		try{
		time = dateForm.parse(timestampStr).getTime();
		}
		catch (ParseException e){
			System.out.println("Parse exception, could not get time" + e);
		
		}
	
		CfActionType cfActionType = CfActionTypeParser.fromRunestoneXml(xmlFragment);
				
		List<CfUser> cfUsers = new ArrayList<CfUser>();		
		cfUsers.add(CfUserParser.fromRunestoneXml(xmlFragment));
		
		List <CfObject> cfObjects = new ArrayList<CfObject>();
		cfObjects.add(CfObjectParser.fromRunestoneXml(xmlFragment));
		
		CfContent cfContent = CfContentParser.fromRunestoneXml(xmlFragment);
		if (cfContent != null){
			return new CfAction (time, cfActionType, cfUsers, cfObjects, cfContent);
		}
		
		return new CfAction (time, cfActionType, cfUsers, cfObjects);
	}
	
	
	
}
