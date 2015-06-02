package de.uds.MonitorInterventionMetafora.server.commonformatparser;

import de.uds.MonitorInterventionMetafora.server.xml.XmlFragment;
import de.uds.MonitorInterventionMetafora.server.xml.XmlUtil;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfProperty;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CommonFormatStrings;
import de.uds.MonitorInterventionMetafora.shared.commonformat.RunestoneStrings;



public class CfPropertyParser {
	
	public static XmlFragment toXml(CfProperty cfProperty){
		XmlFragment xmlFragment = new XmlFragment(CommonFormatStrings.PROPERTY_STRING);
		xmlFragment.setAttribute(CommonFormatStrings.NAME_STRING, cfProperty.getName());
		xmlFragment.setAttribute(CommonFormatStrings.VALUE_STRING, cfProperty.getValue());
		if (cfProperty.getId() != null){
			xmlFragment.setAttribute(CommonFormatStrings.ID_STRING, cfProperty.getId());
		}
		return xmlFragment;
	}
	
	public static CfProperty fromXml(XmlFragment xmlFragment){
		String name = xmlFragment.getAttributeValue(CommonFormatStrings.NAME_STRING);
		String value = xmlFragment.getAttributeValue(CommonFormatStrings.VALUE_STRING);
		if (name != null && value != null){
			value = XmlUtil.convertSpecialCharacterDescriptionsBack(value);
			String id = xmlFragment.getAttributeValue(CommonFormatStrings.ID_STRING);
			if (id != null){
				return new CfProperty(name, value, id);
			}
			else {
				return new CfProperty(name, value);
			}
		}
		else {
			
			return null;
		}
	}

	public static CfProperty fromRunestoneXmlObject(XmlFragment xmlFragment) {
		String name = "ACT";
		String value = xmlFragment.getChildValue(RunestoneStrings.ACT_STRING);
		return new CfProperty (name, value);
	}

	
	public static CfProperty fromRunestoneXmlContent(XmlFragment xmlFragment) {
		String event = xmlFragment.getChildValue(RunestoneStrings.EVENT_STRING);
		String act = xmlFragment.getChildValue(RunestoneStrings.ACT_STRING);
		
		
		if (event.equalsIgnoreCase(RunestoneStrings.PARSONS_STRING)){
			if(act.equalsIgnoreCase("yes")){
				String name = "Correct";
				String value = "TRUE";
				return new CfProperty(name, value);
			} 
			else{
				String name = "Correct";
				String value = "FALSE";
				return new CfProperty(name, value);
			}		
		}
		
		
		if(event.equalsIgnoreCase(RunestoneStrings.MCHOICE_STRING)){
			if(act.startsWith("answer")== true){
				if(act.endsWith("no") == true){
					String name = "Correct";
					String value = "FALSE";
					return new CfProperty(name, value);
				}
				else if (act.endsWith("correct") == true){
					String name = "Correct";
					String value = "TRUE";
					return new CfProperty(name, value);
					}
				else{
					return null;
				}
			}
			else{
				return null;
			}
		}
			
		else{
			return null;
		}
	}
}
