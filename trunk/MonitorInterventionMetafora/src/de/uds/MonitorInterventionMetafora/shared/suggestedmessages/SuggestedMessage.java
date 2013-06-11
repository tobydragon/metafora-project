package de.uds.MonitorInterventionMetafora.shared.suggestedmessages;

import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.BehaviorType;

public class SuggestedMessage {
	private String text;
	private boolean highlight;
	private BehaviorType behaviorType;
	private SuggestedMessagesCategory parentCategory;
	
	public SuggestedMessage(String text) {
		this(text, false, null, null);
	}
	
	public SuggestedMessage(String text, boolean highlight, BehaviorType behaviorType, SuggestedMessagesCategory parentCategory) {
		this.text = text;
		this.highlight = highlight;
		this.behaviorType = behaviorType;
		this.parentCategory = parentCategory;
	}
	
	public SuggestedMessage(SuggestedMessage message, SuggestedMessagesCategory newParentCategory) {
		this(message.getText(), message.isHighlight(), message.getBehaviorType(), newParentCategory);
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

	public L2L2category getL2L2Category() {
		if (parentCategory != null){
			return parentCategory.getL2l2category();
		}
		return null;
	}
	
	public void setParentCategoryHighlight(boolean highlight){
		if (parentCategory != null){
			parentCategory.setHighlight(highlight);
		}
	}
	
	
	
}
