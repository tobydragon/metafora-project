package de.uds.MonitorInterventionMetafora.server.analysis.manager;

import java.util.List;
import java.util.Vector;

import de.uds.MonitorInterventionMetafora.server.analysis.tagging.TaggingAgent;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfObject;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfProperty;

public class TaggingManager {

	TaggingAgent agent;
	
	public TaggingManager(){
		
		agent=new TaggingAgent();
	}
	
	public CfAction tagAction(CfAction action){
	
		return processActionCfAction(action);
	}
	
	CfAction processActionCfAction(CfAction action){
		
		String contenttextToTag=getTextfromPropertyList(getContentPropertiesToTag(action));
		if(contenttextToTag!=null && contenttextToTag!=""){
			
			action.getCfContent().addProperty(agent.tag(contenttextToTag).toCfProperty());
		}
		
		
		String objecttextToTag=getTextfromPropertyList(getObjectPropertiesToTag(action));
		if(objecttextToTag!=null && objecttextToTag!=""){
			
			action.getCfObjects().get(0).addProperty(agent.tag(objecttextToTag).toCfProperty());
		}
		
		return action;
		
	}
	
	private List<CfProperty> getObjectPropertiesToTag(CfAction action){
		List<CfProperty> propertiesToTag=new Vector<CfProperty>();
		for(CfObject object:action.getCfObjects()){	
			
			for(String propertyKey:object.getProperties().keySet()){
						
						if(propertyKey.toUpperCase().endsWith("TEXT") || propertyKey.toUpperCase().endsWith("TXT")){
							CfProperty property=object.getProperty(propertyKey);
							propertiesToTag.add(property);
						}	}	
			}
					return propertiesToTag;
	}
	
	private List<CfProperty> getContentPropertiesToTag(CfAction action){
		
		List<CfProperty> propertiesToTag=new Vector<CfProperty>();
		
		for(String propertyKey:action.getCfContent().getProperties().keySet()){
			
			if(propertyKey.toUpperCase().endsWith("TEXT") || propertyKey.toUpperCase().endsWith("TXT")){
				CfProperty property=action.getCfContent().getProperty(propertyKey);
				propertiesToTag.add(property);
			}
			
		}
		return propertiesToTag;
		
	}

	private String getTextfromPropertyList(List<CfProperty> properties){
		
		String textToTag="";
		for(CfProperty property:properties){
			textToTag=textToTag+property.getValue()+"~";
		}
		return textToTag;
	}
	
	
	
	
	
}
