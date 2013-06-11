package de.uds.MonitorInterventionMetafora.shared.suggestedmessages;

import java.util.List;
import java.util.Vector;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NamedNodeMap;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;


public class SuggestedMessagesCategory {
	private String label;
	private boolean highlight;
	private L2L2category l2l2category;
	private List<SuggestedMessage> messages;
	
	public SuggestedMessagesCategory(String label) {
		this(label, false, null);
	}

	public SuggestedMessagesCategory(String label, boolean highlight, L2L2category l2l2category) {
		this.label = label;
		this.highlight = highlight;
		this.l2l2category = l2l2category;
		this.messages= new Vector<SuggestedMessage>();
	}

	public SuggestedMessagesCategory(SuggestedMessagesCategory category) {
		this(category.getName(), category.isHighlight(), category.getL2l2category());
		for (SuggestedMessage message : category.getSuggestedMessages()){
			addMessage(new SuggestedMessage(message, this));
		}
	}

	public void addMessage(SuggestedMessage message) {
		messages.add(message);
	}
	
	public void removeMessage(int index) {
		messages.remove(index);
	}
	
	
	
	// GETTERS & SETTERS
	public String getName() {
		return label;
	}

	public void setName(String name) {
		this.label = name;
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
	
	public L2L2category getL2l2category(){
		return l2l2category;
	}
	
	public void setL2l2category(L2L2category l2l2category){
		this.l2l2category = l2l2category;
	}
}
