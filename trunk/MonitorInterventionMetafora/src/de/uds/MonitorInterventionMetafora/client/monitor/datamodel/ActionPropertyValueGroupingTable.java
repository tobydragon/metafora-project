package de.uds.MonitorInterventionMetafora.client.monitor.datamodel;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;

import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.monitor.MonitorConstants;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionPropertyRule;


// A mapping and associated DataTable, containing the various values for a given 
// ActionPropertyRule, and their respective counts
public class ActionPropertyValueGroupingTable {
	
	private DataTable dataTable;
	private ActionPropertyRule actionPropertyRule;
	private Map<String, ActionPropertyValueModel> rowModels;
	
	public ActionPropertyValueGroupingTable(ActionPropertyRule actionPropertyRule) {
		this.actionPropertyRule = actionPropertyRule;
		dataTable = DataTable.create();
		dataTable.addColumn(ColumnType.STRING, "Property Value");
	    dataTable.addColumn(ColumnType.NUMBER, "Count");
		rowModels = new HashMap<String, ActionPropertyValueModel>();
	}
	
	public void addAction(CfAction action){
		String actionValue = actionPropertyRule.getActionValue(action);
		if (actionValue != null){
			addActionValue(actionValue);
		}
		else {
//			System.out.println("INFO\t\t[ActionPropertyRule.addAction] can't get actionValue from action:\n" + action + "\n from rule: " + actionPropertyRule);
			addActionValue(MonitorConstants.BLANK_PROPERTY_LABEL);
		}
	}

	private void addActionValue(String actionValue) {
		ActionPropertyValueModel valueModel = rowModels.get(actionValue.toLowerCase());
		if (valueModel != null){
			valueModel.increment();
			setRow(valueModel);
		}
		else {
			int lastRowIndex = dataTable.addRow();
			valueModel = new ActionPropertyValueModel(actionValue, lastRowIndex);
			rowModels.put(valueModel.getPropertyValue().toLowerCase(), valueModel);
			setRow(valueModel);
		}
	}
	
	public void setRow(ActionPropertyValueModel rowModel){
		dataTable.setValue( rowModel.getRowIndex(), 0, rowModel.getPropertyValue());
		dataTable.setValue( rowModel.getRowIndex(), 1, rowModel.getOccurenceCount());
	}

	public DataTable getDataTable() {
		return dataTable;
	}
	
	public int getMaxValue(){
		int maxValue = 0;
		for (ActionPropertyValueModel rowModel : rowModels.values()){
			if (maxValue < rowModel.getOccurenceCount()){
				maxValue = rowModel.getOccurenceCount();
			}
		}
		return maxValue;
	}
	
	

	
}
