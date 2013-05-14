package de.uds.MonitorInterventionMetafora.server.analysis.notification;

import java.util.List;

import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.BehaviorType;

public class BehaviorInstance {
	
	BehaviorType behaviorType;
	
	List<String> usernames;

	public BehaviorInstance(BehaviorType behaviorType, List<String> usernames) {
		this.behaviorType = behaviorType;
		this.usernames = usernames;
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
	
}
