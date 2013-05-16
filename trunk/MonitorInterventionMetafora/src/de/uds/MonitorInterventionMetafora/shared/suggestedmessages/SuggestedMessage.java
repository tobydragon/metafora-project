package de.uds.MonitorInterventionMetafora.shared.suggestedmessages;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.xml.client.NamedNodeMap;
import com.google.gwt.xml.client.Node;

import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.BehaviorType;

public class SuggestedMessage {
	private String text;
	private boolean highlight;
	private BehaviorType behaviorType;
	
	public SuggestedMessage(String text) {
		this(text, false, null);
	}
	
	public SuggestedMessage(String text, boolean highlight, BehaviorType behaviorType) {
		this.text = text;
		this.highlight = highlight;
		this.behaviorType = behaviorType;
	}
	

	
	
	// GETTERS & SETTERS
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isHighlight() {
		return highlight;
	}

	public void setHighlight(boolean highlight) {
		this.highlight = highlight;
	}

	public BehaviorType getBehaviorType() {
		return behaviorType;
	}
	
	
	
}
