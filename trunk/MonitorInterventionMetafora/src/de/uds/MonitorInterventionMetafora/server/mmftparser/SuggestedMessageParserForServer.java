package de.uds.MonitorInterventionMetafora.server.mmftparser;

import com.allen_sauer.gwt.log.client.Log;

import de.uds.MonitorInterventionMetafora.server.xml.XmlFragment;
import de.uds.MonitorInterventionMetafora.shared.commonformat.MetaforaStrings;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.BehaviorType;
import de.uds.MonitorInterventionMetafora.shared.suggestedmessages.SuggestedMessage;

public class SuggestedMessageParserForServer {
	
	public static XmlFragment toXml(SuggestedMessage message){
		XmlFragment xmlFragment= new XmlFragment(MetaforaStrings.SUGGESTED_MESSAGE);
		
		if (message.isHighlight()){
			xmlFragment.setAttribute(MetaforaStrings.HIGHLIGHT, "true");
		}
		if (message.getBehaviorType() != null){
			xmlFragment.setAttribute(MetaforaStrings.L2L2_TAG, message.getBehaviorType().toString());
		};
		xmlFragment.addContent(message.getText());
		return xmlFragment;
	}
	
	public static SuggestedMessage fromXml(XmlFragment xmlFragment){
		boolean highlight = false;
		String highlighttag =  xmlFragment.getAttributeValue(MetaforaStrings.HIGHLIGHT);
		if (highlighttag != null && "true".equalsIgnoreCase(highlighttag)){
			highlight = true;
		}
		
		String behaviortag = xmlFragment.getAttributeValue(MetaforaStrings.BEHAVIOR_TAG);
		BehaviorType behaviorType = BehaviorType.getFromString(behaviortag);
		
		String text = xmlFragment.getText();
		
		if (text != null){
			return new SuggestedMessage(text, highlight, behaviorType);
		}
		else {
			Log.warn("Ignoring SuggestedMessage, no text found for: " + xmlFragment.toString());
			return null;
		}
	}

}
