package com.analysis.server.commonformatparser;

import com.analysis.server.xml.XmlFragment;
import com.analysis.server.xml.XmlFragmentInterface;
import com.analysis.shared.commonformat.CfActionType;
import com.analysis.shared.commonformat.CommonFormatStrings;

/*
import de.uds.commonformat.CfActionType;
import de.uds.commonformat.CommonFormatStrings;
import de.uds.xml.XmlFragment;
import de.uds.xml.XmlFragmentInterface;
*/
public class CfActionTypeParser {
	
	public static XmlFragment toXml(CfActionType cfActionType){
		XmlFragment xmlFragment = new XmlFragment(CommonFormatStrings.ACTION_TYPE_STRING);
		xmlFragment.setAttribute(CommonFormatStrings.TYPE_STRING, cfActionType.getType());
		xmlFragment.setAttribute(CommonFormatStrings.CLASSIFICATION_STRING, cfActionType.getClassification());
		if (cfActionType.getSucceed() != null){
			xmlFragment.setAttribute(CommonFormatStrings.SUCCEED_STRING, cfActionType.getSucceed());
		}
		if (cfActionType.getLogged() != null){
			xmlFragment.setAttribute(CommonFormatStrings.LOGGED_STRING, cfActionType.getLogged());
		}
		return xmlFragment;
	}
	
	public static CfActionType fromXml(XmlFragmentInterface xmlFragment){
		String type = xmlFragment.getAttributeValue(CommonFormatStrings.TYPE_STRING);
		String classification = xmlFragment.getAttributeValue(CommonFormatStrings.CLASSIFICATION_STRING);
		String succeeded = xmlFragment.getAttributeValue(CommonFormatStrings.SUCCEED_STRING);
		String logged = xmlFragment.getAttributeValue(CommonFormatStrings.SUCCEED_STRING);
		
		return new CfActionType (type, classification, succeeded, logged);
	}

}
