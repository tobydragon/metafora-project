package de.uds.MonitorInterventionMetafora.client.feedback;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NamedNodeMap;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;

import de.uds.MonitorInterventionMetafora.shared.suggestedmessages.L2L2category;
import de.uds.MonitorInterventionMetafora.shared.suggestedmessages.SuggestedMessage;
import de.uds.MonitorInterventionMetafora.shared.suggestedmessages.SuggestedMessagesCategory;

public class SuggestedMessagesCategoryParserForClient {
	
	public static SuggestedMessagesCategory fromXml(Element setOfMessages){
		NamedNodeMap categoryAttributes = setOfMessages.getAttributes();
		String categoryName = categoryAttributes.getNamedItem("id").getNodeValue();
		SuggestedMessagesCategory suggestionCategory = new SuggestedMessagesCategory(categoryName);

		Node highlightItem = categoryAttributes.getNamedItem("highlight");
		if (highlightItem != null && highlightItem.getNodeValue().equalsIgnoreCase("true")){ 
			suggestionCategory.setHighlight(true);
		}
		Node l2l2Item = categoryAttributes.getNamedItem("l2l2tag");
		if (l2l2Item != null ){ 
			try {
				L2L2category l2l2category = L2L2category.valueOf(l2l2Item.getNodeValue());
				suggestionCategory.setL2l2category(l2l2category);
			}
			catch (Exception e){
				Log.warn("[SuggestedMessagesCategory.fromXml] unrecognized l2l2tag: " + l2l2Item.getNodeValue());
			}
		}
		
		NodeList messages = setOfMessages.getElementsByTagName("message");
		for (int imessage = 0; imessage < messages.getLength(); imessage++) {
			Node messageItem = messages.item(imessage); 
			suggestionCategory.addMessage(SuggestedMessageParserForClient.fromXmlNode(messageItem, suggestionCategory));
		}
		return suggestionCategory;
	}
	
	public static String toXml(SuggestedMessagesCategory category){
		StringBuffer b = new StringBuffer();
		String categoryHighlight = category.isHighlight() ? " highlight=\"true\" " : "";
		b.append("<set id=\" " +category.getName());
		String attribute2 = category.getL2l2category()!=null ? " l2l2tag=\""+  category.getL2l2category() + "\" " : "";
		b.append(" \""+ categoryHighlight + attribute2 +" >\n");
		for (SuggestedMessage message : category.getSuggestedMessages()) {
			b.append( SuggestedMessageParserForClient.toXml(message));
		}
		b.append("</set>\n");
		return b.toString();
	}

}
