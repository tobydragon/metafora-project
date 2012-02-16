package com.analysis.server.xml;

import java.util.List;

public class XmlConfigParser {
	
	XmlFragment configFragment;
	
 
	public XmlConfigParser(XmlFragment newStart){
		configFragment = newStart;
	}
	
	public XmlConfigParser(String filename){
		configFragment = XmlFragment.getFragmentFromFile(filename);
	}
	
	public XmlConfigParser getfragmentById(String fragmentType, String idAttrName,  String id){
		List<XmlFragment> frags = configFragment.getChildren(fragmentType);
		for (XmlFragment frag : frags){
			if( frag.getAttributeValue(idAttrName).equalsIgnoreCase(id)){
				return new XmlConfigParser(frag);
			}
		}
		return null;
	}
	
	public String getConfigValue(String name){
		try {
			return configFragment.cloneChild(name).getRootElement().getText();
		}
		catch (Exception e){
			return null;
		}
	}

}
