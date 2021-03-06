package de.uds.visualizer.server.commonformatparser;

import de.uds.visualizer.server.xml.XmlFragment;
import de.uds.visualizer.server.xml.XmlFragmentInterface;
import de.uds.visualizer.server.xml.XmlUtil;
import de.uds.visualizer.shared.commonformat.CfProperty;
import de.uds.visualizer.shared.commonformat.CommonFormatStrings;



public class CfPropertyParser {
	
	public static XmlFragmentInterface toXml(CfProperty cfProperty){
		XmlFragmentInterface xmlFragment = new XmlFragment(CommonFormatStrings.PROPERTY_STRING);
		xmlFragment.setAttribute(CommonFormatStrings.NAME_STRING, cfProperty.getName());
		xmlFragment.setAttribute(CommonFormatStrings.VALUE_STRING, cfProperty.getValue());
		if (cfProperty.getId() != null){
			xmlFragment.setAttribute(CommonFormatStrings.ID_STRING, cfProperty.getId());
		}
		return xmlFragment;
	}
	
	public static CfProperty fromXml(XmlFragmentInterface xmlFragment){
		String name = xmlFragment.getAttributeValue(CommonFormatStrings.NAME_STRING);
		String value = xmlFragment.getAttributeValue(CommonFormatStrings.VALUE_STRING);
		value = XmlUtil.convertSpecialCharacterDescriptionsBack(value);
		String id = xmlFragment.getAttributeValue(CommonFormatStrings.ID_STRING);
		if (id != null){
			return new CfProperty(name, value, id);
		}
		else {
			return new CfProperty(name, value);
		}
	}

}
