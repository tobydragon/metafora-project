package de.uds.MonitorInterventionMetafora.shared.monitor.filter;

import java.io.Serializable;

import java.util.List;
import java.util.Vector;

import de.uds.MonitorInterventionMetafora.client.monitor.datamodel.FilterViewModel;
import de.uds.MonitorInterventionMetafora.client.monitor.filter.FilterGridRow;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;

public class ActionFilter implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6017681413593886575L;
	private String name="";
	private boolean editable=false;
	List<ActionPropertyRule> actionPropertyRules;
	private FilterViewModel filterRules;
	//private ListStore<FilterGridRow> filterRules;
	private boolean isServerFilter;
	
	
	public ActionFilter(){
		isServerFilter=false;
		actionPropertyRules=new Vector<ActionPropertyRule>();
		filterRules = new FilterViewModel();
	}
	public ActionFilter(boolean isServerFilter){
		this.isServerFilter=isServerFilter;
		actionPropertyRules=new Vector<ActionPropertyRule>();
		filterRules = new FilterViewModel();
	}
	

	public void setEditable(boolean editable){
		
		this.editable=editable;
	}
	
	public boolean getEditable(){
		
		return editable;
	}
	public void setName(String name){
		this.name=name;
	}
	
	public String getName(){
		return name;
	}
	public void addFilterRule(ActionPropertyRule filterRule){
		actionPropertyRules.add(filterRule);
		if(!isServerFilter){
		filterRules.add(new FilterGridRow(filterRule));
		}
		}
	
	public FilterViewModel  getFilterStore(){
		
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
	

public List<ActionPropertyRule>  getActionPropertyRules(){
		
		return actionPropertyRules;
	}
	
	public boolean filterIncludesAction(CfAction action){
		if(isServerFilter){
			for (ActionPropertyRule rule : actionPropertyRules){
				if (! rule.ruleIncludesAction(action)){
					return false;
				}
			}
			return true;
		}
		
	else {
		for (FilterGridRow rule : filterRules.getRange(0, filterRules.getCount()-1)){
			if (!rule.getActionPropertyRule().ruleIncludesAction(action)){
				return false;
			}
		}
		return true;
		}		
	}
	
	
	



}
