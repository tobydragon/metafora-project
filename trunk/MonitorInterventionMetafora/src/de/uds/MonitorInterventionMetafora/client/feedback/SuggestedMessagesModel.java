package de.uds.MonitorInterventionMetafora.client.feedback;

import java.util.List;
import java.util.Vector;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NamedNodeMap;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;


public class SuggestedMessagesModel {
	private List<SuggestionCategory> suggestionCategories;
	
	public SuggestedMessagesModel() {
		this.suggestionCategories = new Vector<SuggestionCategory>();
	}
	
	public void addCategory(SuggestionCategory category) {
		suggestionCategories.add(category);
	}
	

	//
	// STATIC
	//
	
	public static String toXML(SuggestedMessagesModel model) {
		StringBuffer b = new StringBuffer("<?xml version=\"1.0\" encoding=\"US-ASCII\"?>");
		
		
		b.append("<messages>\n");
		for (SuggestionCategory category : model.getSuggestionCategories()) {
			b.append("<set id=\"" +category.getName()+ "\">\n");
			for (SuggestedMessage message : category.getSuggestedMessages()) {
				String attribute = message.isBold() ? " style=\"bold\"" : "";
				b.append("<message" +attribute+ ">");
				b.append(message.getText());
				b.append("</message>\n");
			}
			b.append("</set>\n");
		}
		b.append("</messages>");
		
		return b.toString();
	}
	
	public static SuggestedMessagesModel fromXML(String XML) {
		SuggestedMessagesModel model = new SuggestedMessagesModel();
		
		Document xmlDocument = XMLParser.parse(XML);
		Element docElement = xmlDocument.getDocumentElement();
		XMLParser.removeWhitespace(docElement);
		
		// get the elements called <sets>
		NodeList sets = docElement.getElementsByTagName("set");
		for (int iset = 0; iset < sets.getLength(); iset++) {
			Element setOfMessages = (Element) sets.item(iset);
			String categoryName = setOfMessages.getAttributes().getNamedItem("id").getNodeValue();
			SuggestionCategory suggestionCategory = new SuggestionCategory(categoryName);

			// check here if there are more sets
			NodeList messages = setOfMessages.getElementsByTagName("message");
			for (int imessage = 0; imessage < messages.getLength(); imessage++) {
				
				Node messageItem = messages.item(imessage);
				String msgText = messageItem.getFirstChild().getNodeValue();
				boolean isBold = false;
				if (messageItem.hasAttributes()) {
					NamedNodeMap msgAttributes = messageItem.getAttributes();
					for (int iattribute = 0; iattribute < msgAttributes.getLength(); iattribute++) {
						Node msgAttribute = msgAttributes.item(iattribute);
						if (msgAttribute.getNodeName().equals("style") && msgAttribute.getNodeValue().equals("bold")) {
							isBold = true;
						}
					}
				}
				SuggestedMessage suggestedMessage = new SuggestedMessage(msgText, isBold); 
				suggestionCategory.addMessage(suggestedMessage);
			}
			model.addCategory(suggestionCategory);
		}
		
		return model;
	}

	//
	// GETTERS & SETTERS
	//
	
	public SuggestionCategory getSuggestionCategory(int index) {
		return suggestionCategories.get(index);
	}
	
	public List<SuggestionCategory> getSuggestionCategories() {
		return suggestionCategories;
	}
	
	public void setSuggestionCategories(List<SuggestionCategory> suggestionCategories) {
		this.suggestionCategories = suggestionCategories;
	}

	public SuggestionCategory getSuggestionCategory(String categoryName) {
		for (SuggestionCategory category : suggestionCategories) {
			if (category.getName().equals(categoryName))
				return category;
		}
		return null;
	}
}
