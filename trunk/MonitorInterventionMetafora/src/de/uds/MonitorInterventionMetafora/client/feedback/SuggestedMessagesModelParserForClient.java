package de.uds.MonitorInterventionMetafora.client.feedback;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;

import de.uds.MonitorInterventionMetafora.shared.suggestedmessages.SuggestedMessagesCategory;
import de.uds.MonitorInterventionMetafora.shared.suggestedmessages.SuggestedMessagesModel;

public class SuggestedMessagesModelParserForClient {
	
	public static String toXML(SuggestedMessagesModel model) {
		StringBuffer b = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");		
		b.append("<messages>\n");
		for (SuggestedMessagesCategory category : model.getSuggestionCategories()) {
			b.append( SuggestedMessagesCategoryParserForClient.toXml(category));
		}
		b.append("</messages>");
		
		return b.toString();
	}
	
	public static SuggestedMessagesModel fromXML(String XML) {
		try {
			SuggestedMessagesModel model = new SuggestedMessagesModel();
			
			Document xmlDocument = XMLParser.parse(XML);
			Element docElement = xmlDocument.getDocumentElement();
			XMLParser.removeWhitespace(docElement);
			
			NodeList sets = docElement.getElementsByTagName("set");
			for (int iset = 0; iset < sets.getLength(); iset++) {
				Element setOfMessages = (Element) sets.item(iset);
				model.addCategory(SuggestedMessagesCategoryParserForClient.fromXml(setOfMessages));
			}
			
			return model;
		}
		catch (Exception e){
			Log.warn("[SuggestedMessagesModel.fromXML] problem parsing XML from string:\n" + XML);
			return null;
		}
	}


}
