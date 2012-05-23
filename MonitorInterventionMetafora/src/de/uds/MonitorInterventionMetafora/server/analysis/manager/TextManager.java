package de.uds.MonitorInterventionMetafora.server.analysis.manager;

import java.util.List;
import java.util.Vector;

import org.hamcrest.core.IsAnything;

import de.uds.MonitorInterventionMetafora.server.analysis.tagging.TextAgent;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfObject;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfProperty;

public class TextManager {

	TextAgent agent;
	boolean addWordCount=false;
	
	public TextManager(){
		
		agent=new TextAgent();
	}
	
public TextManager(boolean addWordCount){
		this.addWordCount=addWordCount;
		agent=new TextAgent(addWordCount);
	}


	public CfAction tagAction(CfAction action){
	
		return processActionCfAction(action);
	}
	
	CfAction processActionCfAction(CfAction action){
		
		String contenttextToTag=getTextfromPropertyList(getContentPropertiesToTag(action));
		if(contenttextToTag!=null && contenttextToTag!=""){
			
			action.getCfContent().addProperty(agent.tag(contenttextToTag).toCfProperty());
			if(addWordCount)
			{
				action.getCfContent().addProperty(agent.getWordCount(contenttextToTag).toCfProperty());
			}
		}
		
		
		String objecttextToTag=getTextfromPropertyList(getObjectPropertiesToTag(action));
		if(objecttextToTag!=null && objecttextToTag!=""){
			
			action.getCfObjects().get(0).addProperty(agent.tag(objecttextToTag).toCfProperty());
			if(addWordCount)
			{
				action.getCfObjects().get(0).addProperty(agent.getWordCount(objecttextToTag).toCfProperty());
			}
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
