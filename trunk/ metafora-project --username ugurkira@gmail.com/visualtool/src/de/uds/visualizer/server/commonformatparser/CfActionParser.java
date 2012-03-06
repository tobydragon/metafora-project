package de.uds.visualizer.server.commonformatparser;

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

import de.uds.visualizer.server.xml.XmlFragment;
import de.uds.visualizer.server.xml.XmlFragmentInterface;
import de.uds.visualizer.shared.commonformat.CfAction;
import de.uds.visualizer.shared.commonformat.CfActionType;
import de.uds.visualizer.shared.commonformat.CfContent;
import de.uds.visualizer.shared.commonformat.CfObject;
import de.uds.visualizer.shared.commonformat.CfUser;
import de.uds.visualizer.shared.commonformat.CommonFormatStrings;
import de.uds.visualizer.shared.commonformat.CommonFormatUtil;


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
	
	public static CfAction fromXml(XmlFragmentInterface xmlFragment){
		String timeStr = xmlFragment.getAttributeValue(CommonFormatStrings.TIME_STRING);
		long time = CommonFormatUtil.getTime(timeStr);
		
		CfActionType cfActionType = CfActionTypeParser.fromXml(xmlFragment.cloneChild(CommonFormatStrings.ACTION_TYPE_STRING));
		
		List<CfUser> cfUsers = new ArrayList<CfUser>();
		for (XmlFragmentInterface cfUserElement : xmlFragment.getChildren(CommonFormatStrings.USER_STRING)){
			cfUsers.add(CfUserParser.fromXml(cfUserElement));
		}
		
		List<CfObject> cfObjects = new ArrayList<CfObject>();
		for (XmlFragmentInterface cfObjectElement : xmlFragment.getChildren(CommonFormatStrings.OBJECT_STRING)){
			cfObjects.add(CfObjectParser.fromXml(cfObjectElement));
		}
		
		XmlFragmentInterface cfContentElement = xmlFragment.cloneChild(CommonFormatStrings.CONTENT_STRING);
		if (cfContentElement != null){
			CfContent cfContent = CfContentParser.fromXml(cfContentElement);
			return new CfAction (time, cfActionType, cfUsers, cfObjects, cfContent);
		}
		
		return new CfAction (time, cfActionType, cfUsers, cfObjects);
	}
	
	public static CfAction getTestableInstance(){
		return CfActionParser.fromXml(XmlFragment.getFragmentFromFile("resources/xml/CfCreateUser.xml"));
	}
	
}
