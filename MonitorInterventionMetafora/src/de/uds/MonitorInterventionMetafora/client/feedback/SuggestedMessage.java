package de.uds.MonitorInterventionMetafora.client.feedback;

public class SuggestedMessage {
	private String text;
	private boolean isBold;
	
	public SuggestedMessage(String text) {
		this(text, false);
	}
	
	public SuggestedMessage(String text, boolean isBold) {
		this.text = text;
		this.isBold = isBold;
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

	public boolean isBold() {
		return isBold;
	}

	public void setBold(boolean isBold) {
		this.isBold = isBold;
	}
	
	
}
