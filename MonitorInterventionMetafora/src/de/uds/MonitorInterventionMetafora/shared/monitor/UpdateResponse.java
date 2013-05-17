package de.uds.MonitorInterventionMetafora.shared.monitor;

import java.io.Serializable;
import java.util.List;

import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;

public class UpdateResponse implements Serializable{
	private static final long serialVersionUID = 6569686557539224417L;
	
	List<CfAction> actions;
	List<String> associatedGroups;
	
	public UpdateResponse() {
		this.actions = null;
		this.associatedGroups = null;
	}
	
	public UpdateResponse(List<CfAction> actions, List<String> associatedGroups) {
		this.actions = actions;
		this.associatedGroups = associatedGroups;
	}

	public List<CfAction> getActions() {
		return actions;
	}

	public List<String> getAssociatedGroups() {
		return associatedGroups;
	}

	public void setActions(List<CfAction> actions) {
		this.actions = actions;
	}

	public void setAssociatedGroups(List<String> associatedGroups) {
		this.associatedGroups = associatedGroups;
	}
	
}
