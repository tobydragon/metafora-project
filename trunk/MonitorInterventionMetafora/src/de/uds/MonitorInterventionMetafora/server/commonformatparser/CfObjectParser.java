package de.uds.MonitorInterventionMetafora.server.commonformatparser;

import java.util.HashMap;
import java.util.Map;

import de.uds.MonitorInterventionMetafora.server.xml.XmlFragment;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfObject;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfProperty;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CommonFormatStrings;

/*
import de.uds.commonformat.CfObject;
import de.uds.commonformat.CfProperty;
import de.uds.commonformat.CommonFormatStrings;
import de.uds.xml.XmlFragment;
import de.uds.xml.XmlFragmentInterface;*/

public class CfObjectParser {
	
	public static XmlFragment toXml(CfObject cfObject){
		XmlFragment xmlFragment= new XmlFragment(CommonFormatStrings.OBJECT_STRING);
		xmlFragment.setAttribute(CommonFormatStrings.ID_STRING, cfObject.getId());
		xmlFragment.setAttribute(CommonFormatStrings.TYPE_STRING, cfObject.getType());
		XmlFragment propertyFragment = new XmlFragment(CommonFormatStrings.PROPERTIES_STRING);
		for (CfProperty objectProperty : cfObject.getProperties().values()){
			propertyFragment.addContent(CfPropertyParser.toXml(objectProperty));
		}
		xmlFragment.addContent(propertyFragment);
		return xmlFragment;	
	}
	
	public static CfObject fromXml(XmlFragment xmlFragment){
		String id = xmlFragment.getAttributeValue(CommonFormatStrings.ID_STRING);
		String type = xmlFragment.getAttributeValue(CommonFormatStrings.TYPE_STRING);
		
		XmlFragment propertyFragment = xmlFragment.cloneChild(CommonFormatStrings.PROPERTIES_STRING);
		Map<String, CfProperty> cfProperties = new HashMap<String, CfProperty>();
		for (XmlFragment cfPropertyElement : propertyFragment.getChildren(CommonFormatStrings.PROPERTY_STRING)){
			CfProperty cfProperty = CfPropertyParser.fromXml(cfPropertyElement);
			cfProperties.put(cfProperty.getName(), cfProperty);
		}
		return new CfObject(id, type, cfProperties);	
	}

}
