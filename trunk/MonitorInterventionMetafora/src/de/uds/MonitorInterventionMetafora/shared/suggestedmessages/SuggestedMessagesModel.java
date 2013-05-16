package de.uds.MonitorInterventionMetafora.shared.suggestedmessages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;

import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.BehaviorType;



public class SuggestedMessagesModel {
	private List<SuggestedMessagesCategory> suggestedMessagesCategories;
	private Map<L2L2category, SuggestedMessagesCategory> categoryMap;
	private Map<BehaviorType, List<SuggestedMessage>> behavior2messageMap;
	
	public SuggestedMessagesModel() {
		this.suggestedMessagesCategories = new Vector<SuggestedMessagesCategory>();
		this.categoryMap = new HashMap<L2L2category, SuggestedMessagesCategory>();
		this.behavior2messageMap = new HashMap<BehaviorType, List<SuggestedMessage>>();
	}
	
	public void addCategory(SuggestedMessagesCategory category) {
		suggestedMessagesCategories.add(category);
		
		//track in maps for lookup efficiency
		categoryMap.put(category.getL2l2category(), category);
		for (SuggestedMessage suggestedMessage : category.getSuggestedMessages()){
			BehaviorType behaviorType = suggestedMessage.getBehaviorType();
			if (behaviorType != null){
				List<SuggestedMessage> suggestedMessages = behavior2messageMap.get(behaviorType);
				if (suggestedMessages == null){
					suggestedMessages = new Vector<SuggestedMessage>();
					behavior2messageMap.put(behaviorType, suggestedMessages);
				}
				suggestedMessages.add(suggestedMessage);
			}
		}
	}
		
	
	// GETTERS & SETTERS
	public SuggestedMessagesCategory getSuggestionCategory(int index) {
		return suggestedMessagesCategories.get(index);
	}
	
	public List<SuggestedMessagesCategory> getSuggestionCategories() {
		return suggestedMessagesCategories;
	}

	public SuggestedMessagesCategory getSuggestionCategory(String categoryName) {
		for (SuggestedMessagesCategory category : suggestedMessagesCategories) {
			if (category.getName().equals(categoryName))
				return category;
		}
		return null;
	}

	public void highlightMessagesForBehaviorType(BehaviorType behaviorType) {
		List<SuggestedMessage> messagesToHighlight = behavior2messageMap.get(behaviorType);
		if (messagesToHighlight != null){
			for (SuggestedMessage message : messagesToHighlight){
				message.setHighlight(true);
			}
		}
		//TODO: make link from message to category, highlight category too
	}
}
