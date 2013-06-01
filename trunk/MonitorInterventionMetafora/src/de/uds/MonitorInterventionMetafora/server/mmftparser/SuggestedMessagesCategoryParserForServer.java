package de.uds.MonitorInterventionMetafora.server.mmftparser;

import com.allen_sauer.gwt.log.client.Log;
import com.sun.net.ssl.internal.ssl.SSLContextImpl;

import de.uds.MonitorInterventionMetafora.server.xml.XmlFragment;
import de.uds.MonitorInterventionMetafora.shared.commonformat.MetaforaStrings;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.BehaviorType;
import de.uds.MonitorInterventionMetafora.shared.suggestedmessages.L2L2category;
import de.uds.MonitorInterventionMetafora.shared.suggestedmessages.SuggestedMessage;
import de.uds.MonitorInterventionMetafora.shared.suggestedmessages.SuggestedMessagesCategory;

public class SuggestedMessagesCategoryParserForServer {
	
	public static XmlFragment toXml(SuggestedMessagesCategory messageCategory){
		XmlFragment xmlFragment= new XmlFragment(MetaforaStrings.SUGGESTED_MESSAGE_CATEGORY);
		
		xmlFragment.setAttribute(MetaforaStrings.CATEGORY_LABEL, messageCategory.getName());
		
		if (messageCategory.isHighlight()){
			xmlFragment.setAttribute(MetaforaStrings.HIGHLIGHT, "true");
		}
		if (messageCategory.getL2l2category() != null){
			xmlFragment.setAttribute(MetaforaStrings.L2L2_TAG, messageCategory.getL2l2category().toString());
		}
		for (SuggestedMessage suggestedMessage : messageCategory.getSuggestedMessages()){
			xmlFragment.addContent(SuggestedMessageParserForServer.toXml(suggestedMessage));
		}
		return xmlFragment;
	}
	
	public static SuggestedMessagesCategory fromXml(XmlFragment xmlFragment){
		boolean highlight = false;
		String highlighttag =  xmlFragment.getAttributeValue(MetaforaStrings.HIGHLIGHT);
		if (highlighttag != null && "true".equalsIgnoreCase(highlighttag)){
			highlight = true;
		}
		
		String l2l2str = xmlFragment.getAttributeValue(MetaforaStrings.L2L2_TAG);
		L2L2category l2l2category = L2L2category.getFromString(l2l2str);
		
		String label = xmlFragment.getAttributeValue(MetaforaStrings.CATEGORY_LABEL);
				
		if (label != null){
			SuggestedMessagesCategory category = new SuggestedMessagesCategory(label, highlight, l2l2category);
			for (XmlFragment messageFragment : xmlFragment.getChildren(MetaforaStrings.SUGGESTED_MESSAGE)){
				SuggestedMessage suggestedMessage = SuggestedMessageParserForServer.fromXml(messageFragment, category);
				if (suggestedMessage != null){
					category.addMessage(suggestedMessage);
				}
			}
			return category;
		}
		else {
			Log.warn("Ignoring SuggestedMessageCategory, no label found for: " + xmlFragment.toString());
			return null;
		}
	}
	
	
}
