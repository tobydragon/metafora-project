package de.uds.MonitorInterventionMetafora.shared.monitor.filter;

import java.io.Serializable;

import java.util.Arrays;
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
	private String name;
	
	List<ActionPropertyRule> actionPropertyRules;
	private FilterViewModel filterRules;
	
	private boolean isServerFilter;
	
	private RuleRelation ruleRelation;
	
	//optional attributes
	private String type;
	private String color;
	
	
	public ActionFilter(List<ActionPropertyRule> actionPropertyRules){
		this(null, true, actionPropertyRules);
	}
	
	public ActionFilter(String name, boolean serverFilter, List<ActionPropertyRule> actionPropertyRules ){
		this (name, serverFilter, null, null, actionPropertyRules, RuleRelation.AND);	
	}
	
	public ActionFilter(String name, boolean serverFilter, String type, String color, List<ActionPropertyRule> actionPropertyRules, RuleRelation ruleRelation ){
		setName(name);
		setServerFilter(serverFilter);
		setType(type);
		setColor(color);
		
		this.actionPropertyRules=new Vector<ActionPropertyRule>();
		filterRules = new FilterViewModel();
		
		for (ActionPropertyRule rule : actionPropertyRules){
			addFilterRule(rule);
		}
		this.ruleRelation = ruleRelation;
		
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public ActionFilter(){
		this ("No Name", false, new Vector<ActionPropertyRule>());
	}

	public void addFilterRule(ActionPropertyRule filterRule){
		actionPropertyRules.add(filterRule);
		if(!isServerFilter){
			filterRules.add(new FilterGridRow(filterRule));
		}
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
		if(isServerFilter){
			if (actionPropertyRules.size() > 0){
				if (ruleRelation == RuleRelation.AND){	
					for (ActionPropertyRule rule : actionPropertyRules){
						if (! rule.ruleIncludesAction(action)){
							return false;
						}
					}
					return true;
				}
				
				else {
					for (ActionPropertyRule rule : actionPropertyRules){
						if (rule.ruleIncludesAction(action)){
							return true;
						}
					}
					return false;
				}
			}
			else {
				return true;
			}
		}
		
		//TODO: Get rid of this tragedy of coding...
		else {
			for (FilterGridRow rule : filterRules.getRange(0, filterRules.getCount()-1)){
				if (!rule.getActionPropertyRule().ruleIncludesAction(action)){
					return false;
				}
			}
			return true;
		}		
	}
	
	public List<ActionPropertyRule>  getActionPropertyRules(){	
		return actionPropertyRules;
	}
	
	public FilterViewModel  getFilterStore(){
		return filterRules;
	}

	public void setName(String name){
		this.name=name;
	}
	
	public String getName(){
		return name;
	}

	public boolean isServerFilter() {
		return isServerFilter;
	}

	public void setServerFilter(boolean isServerFilter) {
		this.isServerFilter = isServerFilter;
	}
	
	

}
