package de.uds.MonitorInterventionMetafora.shared.monitor.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ComponentManager;
import com.extjs.gxt.ui.client.widget.grid.EditorGrid;

import de.uds.MonitorInterventionMetafora.client.monitor.filter.FilterGridRow;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.ActionElementType;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.OperationType;

public class ActionFilter {

	//List<ActionPropertyRule> filterRules;
	private ListStore<FilterGridRow> filterRules;
	
	public ActionFilter(){
		filterRules = new ListStore<FilterGridRow>();
	}
	

	
	public void addFilterRule(ActionPropertyRule filterRule){
		filterRules.add(new FilterGridRow(filterRule));
		}
	
	public ListStore<FilterGridRow>  getFilterStore(){
		
		return filterRules;
	
	}
	public List<CfAction> getFilteredList(List<CfAction> listToFilter){
		List<CfAction> filteredList = new Vector<CfAction>();
		for (CfAction action : listToFilter){
			if (filterIncludesAction(action)){
				filteredList.add(action);
			}
		}
		return filteredList;
	}
	

	public boolean filterIncludesAction(CfAction action){
		for (FilterGridRow rule : filterRules.getRange(0, filterRules.getCount()-1)){
			if (! rule.getActionPropertyRule().ruleIncludesAction(action)){
				return false;
			}
		}
		return true;
	}
	
	
	



}
