package de.uds.MonitorInterventionMetafora.client.datamodels;

public class ActionPropertyValueModel {
	
	//TODO: Here we could hold hook to all actions that are part of the row, then, when applying filter, we could just return the set...
	
	private String propertyValue;
	private int rowIndex;
	private int occurenceCount;
	
	public ActionPropertyValueModel(String propertyValue, int rowIndex){
		this.propertyValue = propertyValue;
		this.rowIndex = rowIndex;
		this.occurenceCount = 1;
	}

	public String getPropertyValue() {
		return propertyValue;
	}

	public int getRowIndex() {
		return rowIndex;
	}

	public int getOccurenceCount() {
		return occurenceCount;
	}
	
	public void increment(){
		occurenceCount++;
	}
}
