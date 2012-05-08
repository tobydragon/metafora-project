package de.uds.MonitorInterventionMetafora.client.monitor.datamodel;

import java.util.List;
import java.util.Vector;

import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;

public class ActionPropertyValueModel {
		
	private String propertyValue;
	private int rowIndex;
	private List<CfAction> actions;
	
	public ActionPropertyValueModel(String propertyValue, int rowIndex, CfAction firstAction){
		this.propertyValue = propertyValue;
		this.rowIndex = rowIndex;
		actions = new Vector<CfAction>();
		actions.add(firstAction);
	}

	public String getPropertyValue() {
		return propertyValue;
	}

	public int getRowIndex() {
		return rowIndex;
	}

	public int getOccurenceCount() {
		return actions.size();
	}
	
	public void increment(CfAction action){
		actions.add(action);
	}
	
	public List<CfAction> getValueActions(){
		return actions;
	}
}
