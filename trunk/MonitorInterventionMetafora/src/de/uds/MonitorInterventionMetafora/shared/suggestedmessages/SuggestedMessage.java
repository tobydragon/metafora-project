package de.uds.MonitorInterventionMetafora.shared.suggestedmessages;

public class SuggestedMessage {
	private String text;
	private boolean highlight;
	
	public SuggestedMessage(String text) {
		this(text, false);
	}
	
	public SuggestedMessage(String text, boolean highlight) {
		this.text = text;
		this.highlight = highlight;
	}
	
	//
	// GETTERS & SETTERS
	//
	
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
	
	
}
