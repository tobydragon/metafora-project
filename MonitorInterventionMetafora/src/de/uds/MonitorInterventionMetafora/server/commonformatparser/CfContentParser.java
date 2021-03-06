package de.uds.MonitorInterventionMetafora.server.commonformatparser;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.uds.MonitorInterventionMetafora.server.xml.XmlFragment;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfContent;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfObject;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfProperty;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CommonFormatStrings;
import de.uds.MonitorInterventionMetafora.shared.commonformat.RunestoneStrings;

/*
import de.uds.commonformat.CfContent;
import de.uds.commonformat.CfProperty;
import de.uds.commonformat.CommonFormatStrings;
import de.uds.xml.XmlFragment;
import de.uds.xml.XmlFragmentInterface;*/

public class CfContentParser {
	static Log logger = LogFactory.getLog(CfContentParser.class);

	
	public static XmlFragment toXml(CfContent cfContent){
		XmlFragment xmlFragment= new XmlFragment(CommonFormatStrings.CONTENT_STRING);
		
		XmlFragment descripitonFragment = new XmlFragment(CommonFormatStrings.DESCRIPTION_STRING);
		descripitonFragment.addCdataContent(cfContent.getDescription());
		xmlFragment.addContent(descripitonFragment);
		
		XmlFragment propertyFragment = new XmlFragment(CommonFormatStrings.PROPERTIES_STRING);
		for (CfProperty objectProperty : cfContent.getProperties().values()){
			propertyFragment.addContent(CfPropertyParser.toXml(objectProperty));
		}
		xmlFragment.addContent(propertyFragment);
		return xmlFragment;	
	}
	
	public static CfContent fromXml(XmlFragment xmlFragment){
		String description = xmlFragment.getChildValue(CommonFormatStrings.DESCRIPTION_STRING);
		
		XmlFragment propertyFragment = xmlFragment.cloneChild(CommonFormatStrings.PROPERTIES_STRING);
		Map<String, CfProperty> cfProperties = new HashMap<String, CfProperty>();
		
		if (propertyFragment != null){
			for (XmlFragment cfPropertyElement : propertyFragment.getChildren(CommonFormatStrings.PROPERTY_STRING)){
				CfProperty cfProperty = CfPropertyParser.fromXml(cfPropertyElement);
				if (cfProperty != null){
					cfProperties.put(cfProperty.getName(), cfProperty);
				}
			}
		}
		else {
			logger.warn("[fromXml] no properties fragment found for content with description - " + description);
		}
		return new CfContent(description, cfProperties);	
	}

	public static CfContent fromRunestoneXml(XmlFragment xmlFragment) {
		String sid = xmlFragment.getChildValue(RunestoneStrings.SID_STRING);
		String event = xmlFragment.getChildValue(RunestoneStrings.EVENT_STRING);
		String div_id = xmlFragment.getChildValue(RunestoneStrings.DIV_ID_STRING);
		String act = xmlFragment.getChildValue(RunestoneStrings.ACT_STRING);
		
		String description = sid + " used " + event +" " + div_id + ", their action was " + act;
		
		Map<String, CfProperty> cfProperties = new HashMap<String, CfProperty>();
		CfProperty cfProperty = CfPropertyParser.fromRunestoneXmlContent(xmlFragment);
		if (cfProperty != null){
			cfProperties.put(cfProperty.getName(), cfProperty);
			
		}	
		return new CfContent(description, cfProperties);
	}
	

}
