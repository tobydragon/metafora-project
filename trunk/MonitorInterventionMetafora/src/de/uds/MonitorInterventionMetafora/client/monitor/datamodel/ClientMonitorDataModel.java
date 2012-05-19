package de.uds.MonitorInterventionMetafora.client.monitor.datamodel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.extjs.gxt.ui.client.store.GroupingStore;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.Store;
import com.google.gwt.visualization.client.DataTable;

import de.uds.MonitorInterventionMetafora.client.communication.CommunicationServiceAsync;
import de.uds.MonitorInterventionMetafora.client.communication.actionresponses.RequestConfigurationCallBack;
import de.uds.MonitorInterventionMetafora.client.logger.ComponentType;
import de.uds.MonitorInterventionMetafora.client.logger.Log;
import de.uds.MonitorInterventionMetafora.client.logger.Logger;
import de.uds.MonitorInterventionMetafora.client.logger.UserActionType;
import de.uds.MonitorInterventionMetafora.client.monitor.dataview.table.CfActionGridRow;
import de.uds.MonitorInterventionMetafora.client.monitor.filter.FilterGridRow;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.ActionElementType;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.Configuration;
import de.uds.MonitorInterventionMetafora.shared.monitor.MonitorConstants;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionFilter;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionPropertyRule;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionPropertyRuleSelectorModel;

public class ClientMonitorDataModel{
	
	private List<CfAction> allActions;
	private Map<String, ActionFilter> allActionFilters;
	private ActionFilter currentActionFilter;
	
	private  List<CfAction> filteredActions;
	private CommunicationServiceAsync monitoringViewServiceServlet;
	
	private GroupingStore<CfActionGridRow> tableViewModel;
	//private ListStore<FilterGridRow> filterGridViewModel;
	ActionPropertyRuleSelectorModel groupingSelectorModel;
	ActionPropertyRuleSelectorModel filterSelectorModel;
	//for aggregated data views (charts so far)
	//a mapping of IndicatorPropertyTables, which each tracking the different values 
	//and occurrence counts for an AcionPropertyRule
	private Map<String, ActionPropertyValueGroupingTableModel> rule2ValueGroupingTableMap;
	
	public ClientMonitorDataModel(ActionPropertyRuleSelectorModel groupingSelectorModel,ActionPropertyRuleSelectorModel filterSelectorModel,CommunicationServiceAsync monitoringViewServiceServlet){
		
		this.groupingSelectorModel = groupingSelectorModel;
		this.filterSelectorModel=filterSelectorModel;
		this.monitoringViewServiceServlet=monitoringViewServiceServlet;
		currentActionFilter = new ActionFilter();
		allActions = new Vector<CfAction>();
		allActionFilters=new HashMap<String, ActionFilter>();
		
		tableViewModel = new GroupingStore<CfActionGridRow>();
	
		clearFilteredData();
		
		
	}
	
	
	
	public CommunicationServiceAsync getServiceServlet(){
		
		return monitoringViewServiceServlet;
	}
	
	public ActionPropertyRuleSelectorModel getFilterSelectorModel(){
		
		return filterSelectorModel;
	}
	
	
	public void clearFilteredData(){
		filteredActions = new Vector<CfAction>();
		tableViewModel.removeAll();
		rule2ValueGroupingTableMap = createIndicatorPropertyTableMap();	
	}
	
	
	public ListStore<FilterGridRow> getFilterGridViewModel(){
		
		return currentActionFilter.getFilterStore();
	}
	
	
	
	public void updateFilteredList(){
		clearFilteredData();
		addFilteredData(allActions);
	}
	
	public void addFilteredData(List<CfAction> actionsToFilter){
		for (CfAction action : actionsToFilter){
			if (currentActionFilter.filterIncludesAction(action)){
				tableViewModel.add(new CfActionGridRow(action));
				
				for (ActionPropertyValueGroupingTableModel indicatorPropertyTable : rule2ValueGroupingTableMap.values()){
					indicatorPropertyTable.addAction(action);
				}
				filteredActions.add(action);
			}
		}
		
		
		
		
		Log userActionLog=new Log();
    	userActionLog.setComponentType(ComponentType.ACTION_FILTERER);
    	userActionLog.setDescription("Actions are filtered by the rules:",currentActionFilter.getFilterStore());
    	userActionLog.setTriggeredBy(ComponentType.ACTION_FILTERER);
    	userActionLog.setUserActionType(UserActionType.ACTION_FILTERING);
    	userActionLog.addProperty(MonitorConstants.ACTIONS_COUNT,Integer.toString(actionsToFilter.size()));
    	userActionLog.addProperty(MonitorConstants.FILTERED_ACTIONS_COUNT,Integer.toString(filteredActions.size()));
    	Logger.getLoggerInstance().log(userActionLog);
		
		
	}
	
	public void addData(List<CfAction> actions){
		allActions.addAll(actions);
		addFilteredData(actions);
	}
	
	public CfAction getLastAction(){
		if(allActions.size()<=0){
			return null;
		}
		int index=allActions.size()-1;
		return allActions.get(index);
	}
	
	//Creates table for each rule, where each row will represent one value for each rule
	private Map<String, ActionPropertyValueGroupingTableModel> createIndicatorPropertyTableMap() {
		Map<String, ActionPropertyValueGroupingTableModel> inMap = new HashMap<String, ActionPropertyValueGroupingTableModel>();
		//Make a table for each possible rule that can be grouped by
		for (ActionPropertyRule actionPropertyRule : groupingSelectorModel.getAssociatedRules()){
			ActionPropertyValueGroupingTableModel actionPropertyValueGroupingTable = new ActionPropertyValueGroupingTableModel(actionPropertyRule);
			inMap.put(actionPropertyRule.getKey(), actionPropertyValueGroupingTable);
		}
		return inMap;
	}
	
	public DataTable getDataTable(ActionPropertyRule propertyToGroupBy){
		ActionPropertyValueGroupingTableModel table = rule2ValueGroupingTableMap.get(propertyToGroupBy.getKey());
		if (table != null){
			return table.getDataTable();
		}
		else {
			return null;
		}
	}

	public int getMaxValue(ActionPropertyRule propertyToGroupBy){
		ActionPropertyValueGroupingTableModel table = rule2ValueGroupingTableMap.get(propertyToGroupBy.getKey());
		if (table != null){
			return table.getMaxValue();
		}
		else {
			return 0;
		}
	}
	
	public List<CfAction> getFilteredActions(){
		return filteredActions;
	}
	
	public GroupingStore<CfActionGridRow> getTableViewModel(){
		return tableViewModel;
	}


	
}
