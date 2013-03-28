package de.uds.MonitorInterventionMetafora.client.feedback;

import java.util.List;
import java.util.Vector;

public class SuggestionCategory {
	private String name;
	private boolean highlight;
	private List<SuggestedMessage> messages;
	
	public SuggestionCategory(String name) {
		this.name = name;
		this.messages= new Vector<SuggestedMessage>();
	}

	public void addMessage(SuggestedMessage message) {
		messages.add(message);
	}
	
	public void removeMessage(int index) {
		messages.remove(index);
	}
	
	//
	// GETTERS & SETTERS
	//
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SuggestedMessage getSuggestedMessage(int index) {
		return messages.get(index);
	}
	
	public List<SuggestedMessage> getSuggestedMessages() {
		return messages;
	}

	public void setCategories(List<SuggestedMessage> messages) {
		this.messages = messages;
	}
	
	public boolean isHighlight() {
		return highlight;
	}
	
	public void setHighlight(boolean highlight) {
		this.highlight = highlight;
	}
}
