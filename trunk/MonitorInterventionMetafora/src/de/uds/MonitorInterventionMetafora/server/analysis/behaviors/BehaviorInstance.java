package de.uds.MonitorInterventionMetafora.server.analysis.behaviors;

import java.util.List;
import java.util.Vector;

import org.apache.commons.lang.Validate;

import de.uds.MonitorInterventionMetafora.shared.commonformat.CfProperty;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.BehaviorType;
import de.uds.MonitorInterventionMetafora.shared.suggestedmessages.SuggestedMessage;

public class BehaviorInstance {
	
	BehaviorType behaviorType;
	
	List<String> usernames;
	List<SuggestedMessage> suggestedMessages;
	SuggestedMessage bestSuggestedMessage;
	List <CfProperty> behaviorInstanceProperties;
	
	public BehaviorInstance(BehaviorType behaviorType, List<String> usernames, List<CfProperty> behaviorInstanceProperties) {
		this.behaviorType = behaviorType;
		this.usernames = usernames;
		suggestedMessages = new Vector<SuggestedMessage>();
		this.behaviorInstanceProperties = behaviorInstanceProperties;
	}

	@Override
	public String toString() {
		return "BehaviorInstance [behaviorType=" + behaviorType+ ", usernames=" + usernames + "]";
	}
	
	public BehaviorType getBehaviorType() {
		return behaviorType;
	}

	public void setBehaviorType(BehaviorType behaviorType) {
		this.behaviorType = behaviorType;
	}
	
	public List<String> getUsernames() {
		return usernames;
	}

	public void setUsernames(List<String> usernames) {
		this.usernames = usernames;
	}
	
	public void addSuggestedMessages(List<SuggestedMessage> suggestedMessage){
		if (suggestedMessage != null){
			suggestedMessages.addAll(suggestedMessage);
			//TODO: Currently best is just last one 
			bestSuggestedMessage = suggestedMessages.get(suggestedMessages.size()-1);
		}
	}
	
	public List<SuggestedMessage> getSuggestedMessages (){
		return suggestedMessages;
	}
	
	public SuggestedMessage getBestSuggestedMessage(){
		return bestSuggestedMessage;
	}

	public List<CfProperty> getProperties(){
		return behaviorInstanceProperties;
	}
	
}
