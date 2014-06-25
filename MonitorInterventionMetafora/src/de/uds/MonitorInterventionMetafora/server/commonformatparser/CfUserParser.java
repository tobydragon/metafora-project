package de.uds.MonitorInterventionMetafora.server.commonformatparser;

import de.uds.MonitorInterventionMetafora.server.xml.XmlFragment;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfUser;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CommonFormatStrings;
import de.uds.MonitorInterventionMetafora.shared.commonformat.RunestoneStrings;

//import de.uds.commonformat.CfUser;
//import de.uds.commonformat.CommonFormatStrings;
//import de.uds.xml.XmlFragment;
//import de.uds.xml.XmlFragmentInterface;

public class CfUserParser {
	
	public static XmlFragment toXml(CfUser cfUser){
		XmlFragment xmlFragment = new XmlFragment(CommonFormatStrings.USER_STRING);
		xmlFragment.setAttribute(CommonFormatStrings.ID_STRING, cfUser.getid());
		if (cfUser.getrole() != null){
			xmlFragment.setAttribute(CommonFormatStrings.ROLE_STRING, cfUser.getrole());
		}
		return xmlFragment;
	}
	
	public static CfUser fromXml(XmlFragment xmlFragment){
		String id = xmlFragment.getAttributeValue(CommonFormatStrings.ID_STRING);
		String role = xmlFragment.getAttributeValue(CommonFormatStrings.ROLE_STRING);
		return new CfUser(id, role);
	}

	public static CfUser fromRunestoneXml(XmlFragment xmlFragment) {
		String id = xmlFragment.getChildValue(RunestoneStrings.SID_STRING);
		String role = "ORIGINATOR";
		return new CfUser(id, role);
	}
	
	
	
	

}
