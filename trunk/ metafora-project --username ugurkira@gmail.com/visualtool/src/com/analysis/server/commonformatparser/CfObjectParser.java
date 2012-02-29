package com.analysis.server.commonformatparser;

import java.util.HashMap;
import java.util.Map;

import com.analysis.server.xml.XmlFragment;
import com.analysis.server.xml.XmlFragmentInterface;
import com.analysis.shared.commonformat.CfObject;
import com.analysis.shared.commonformat.CfProperty;
import com.analysis.shared.commonformat.CommonFormatStrings;

/*
import de.uds.commonformat.CfObject;
import de.uds.commonformat.CfProperty;
import de.uds.commonformat.CommonFormatStrings;
import de.uds.xml.XmlFragment;
import de.uds.xml.XmlFragmentInterface;*/

public class CfObjectParser {
	
	public static XmlFragmentInterface toXml(CfObject cfObject){
		XmlFragmentInterface xmlFragment= new XmlFragment(CommonFormatStrings.OBJECT_STRING);
		xmlFragment.setAttribute(CommonFormatStrings.ID_STRING, cfObject.getId());
		xmlFragment.setAttribute(CommonFormatStrings.TYPE_STRING, cfObject.getType());
		XmlFragmentInterface propertyFragment = new XmlFragment(CommonFormatStrings.PROPERTIES_STRING);
		for (CfProperty objectProperty : cfObject.getProperties().values()){
			propertyFragment.addContent(CfPropertyParser.toXml(objectProperty));
		}
		xmlFragment.addContent(propertyFragment);
		return xmlFragment;	
	}
	
	public static CfObject fromXml(XmlFragmentInterface xmlFragment){
		String id = xmlFragment.getAttributeValue(CommonFormatStrings.ID_STRING);
		String type = xmlFragment.getAttributeValue(CommonFormatStrings.TYPE_STRING);
		
		XmlFragmentInterface propertyFragment = xmlFragment.cloneChild(CommonFormatStrings.PROPERTIES_STRING);
		Map<String, CfProperty> cfProperties = new HashMap<String, CfProperty>();
		for (XmlFragmentInterface cfPropertyElement : propertyFragment.getChildren(CommonFormatStrings.PROPERTY_STRING)){
			CfProperty cfProperty = CfPropertyParser.fromXml(cfPropertyElement);
			cfProperties.put(cfProperty.getName(), cfProperty);
		}
		return new CfObject(id, type, cfProperties);	
	}

}
