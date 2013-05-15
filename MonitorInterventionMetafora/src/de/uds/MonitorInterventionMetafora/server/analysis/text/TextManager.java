package de.uds.MonitorInterventionMetafora.server.analysis.text;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Vector;

import org.hamcrest.core.IsAnything;

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
		
		String contenttextToTag=getTextfromPropertyList(getContentPropertiesToTag(action));
		boolean taggedAlready = false;
		
		if(contenttextToTag!=null && contenttextToTag!=""){
			taggedAlready = true;
			action.getCfContent().addProperty(agent.tag(contenttextToTag).toCfProperty());
			if(addWordCount){
				action.getCfContent().addProperty(agent.getWordCount(contenttextToTag).toCfProperty());
			}
		}
		String objecttextToTag=getTextfromPropertyList(getObjectPropertiesToTag(action));
		if(objecttextToTag!=null && objecttextToTag!=""){
			taggedAlready = true;
			action.getCfObjects().get(0).addProperty(agent.tag(objecttextToTag).toCfProperty());
			if(addWordCount){
				action.getCfObjects().get(0).addProperty(agent.getWordCount(objecttextToTag).toCfProperty());
			}
		}

		if(action.getCfObjects().size()>0 && !taggedAlready){
			action.getCfObjects().get(0).addProperty(agent.tag("").toEmptyCfProperty());
			if(addWordCount){
				action.getCfObjects().get(0).addProperty(agent.getWordCount("").toEmptyCfProperty());
			}
		}

		return action;
	}
	
	private List<CfProperty> getObjectPropertiesToTag(CfAction action){
		List<CfProperty> propertiesToTag=new Vector<CfProperty>();
		for(CfObject object:action.getCfObjects()){	
			propertiesToTag.addAll(getPropertiesToTag(object.getProperties().values()));
		}
		return propertiesToTag;
	}
	
	private List<CfProperty> getContentPropertiesToTag(CfAction action){
		List<CfProperty> propertiesToTag=new Vector<CfProperty>();
		if (action.getCfContent() != null) 
			propertiesToTag.addAll(getPropertiesToTag(action.getCfContent().getProperties().values()));
		return propertiesToTag;
	}
	
	private Collection<CfProperty> getPropertiesToTag(Collection<CfProperty> properties){
		Collection<CfProperty> propertiesToTag=new Vector<CfProperty>();

		if (properties != null){
			for(CfProperty property: properties ){	
		    	String propertyKey = property.getName(); 
				if(propertyKey.toUpperCase().endsWith("TEXT") || propertyKey.toUpperCase().endsWith("TXT")){
					propertiesToTag.add(property);
				}
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
