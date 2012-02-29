package com.analysis.server.commonformatparser;

import com.analysis.server.xml.XmlFragment;
import com.analysis.server.xml.XmlFragmentInterface;
import com.analysis.shared.commonformat.CfUser;
import com.analysis.shared.commonformat.CommonFormatStrings;

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
	
	public static CfUser fromXml(XmlFragmentInterface xmlFragment){
		String id = xmlFragment.getAttributeValue(CommonFormatStrings.ID_STRING);
		String role = xmlFragment.getAttributeValue(CommonFormatStrings.ROLE_STRING);
		return new CfUser(id, role);
	}

}
