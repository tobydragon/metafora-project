package de.uds.MonitorInterventionMetafora.client.monitor.datamodel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.allen_sauer.gwt.log.client.Log;
import com.extjs.gxt.ui.client.store.GroupingStore;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.Store;
import com.google.gwt.visualization.client.DataTable;

import de.uds.MonitorInterventionMetafora.client.communication.CommunicationServiceAsync;
import de.uds.MonitorInterventionMetafora.client.communication.actionresponses.RequestConfigurationCallBack;
import de.uds.MonitorInterventionMetafora.client.logger.ComponentType;
import de.uds.MonitorInterventionMetafora.client.logger.UserLog;
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
	//private Map<String, ActionFilter> allActionFilters;
	private ActionFilter currentActionFilter;
	private ActionFilter mainActionFilter;
	private CfAction lastRecievedAction;
	private Configuration currentFilterSets;
	private Configuration mainFilterSets;

	//private FilterViewModel  mainFilters;
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
		
		allActions = new Vector<CfAction>();
		//allActionFilters=new HashMap<String, ActionFilter>();
	
	//	model.getServiceServlet().requestConfiguration(isMainFilterSet, this);
		//mainFilters=new FilterViewModel();
		currentActionFilter = new ActionFilter();
		mainActionFilter=new ActionFilter();
		//mainActionFilter.setFilterStore(mainFilters);
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
	
	public int getAllActionCount(){
		
		return allActions.size();
	}
	
public int getFilteredActionCount(){
		
		return filteredActions.size();
	}
	public ListStore<FilterGridRow> getFilterGridViewModel(){
		
		return currentActionFilter.getFilterStore();
	}
	
public FilterViewModel getMainFilterGridViewModel(){
		
		return mainActionFilter.getFilterStore();
	}
	

List<CfAction> applyMainFilter(List<CfAction> actionsToFilter){
	
	Log.debug("Adding new actions:Applying main filter is started");
	List<CfAction> actions=new Vector<CfAction>();
	for (CfAction action : actionsToFilter){
		if (mainActionFilter.filterIncludesAction(action)){
			
			
			actions.add(action);
		
		}
	}
	Log.debug("Adding new actions:Applying main filter is completed");
	return actions;
}

public void applyMainFilter(){
	List<CfAction> temp=new Vector<CfAction>();
	
	temp.addAll(allActions);
	
	
	allActions.clear();
	allActions.addAll(applyMainFilter(temp));

	List<CfAction> temp2=new Vector<CfAction>();
	temp2.addAll(filteredActions);
	clearFilteredData();
	filteredActions=applyMainFilter(temp2);
	
	
	
	
}
	
	// TODO: optimize!
	public void updateFilteredList(){
		clearFilteredData();
		addFilteredData(allActions);
	}
	
	public void addFilteredData(List<CfAction> actionsToFilter){
		List<CfActionGridRow> tableRows=new Vector<CfActionGridRow>();
		filteredActions.clear();
		Log.debug("Applying user filter is started");
		for (CfAction action : actionsToFilter){
			if (currentActionFilter.filterIncludesAction(action)){
				//TODO: this cause the problem
				/*
				tableViewModel.add(new CfActionGridRow(action));
				//TODO: if checknox of update charts is not check dont do this part
				for (ActionPropertyValueGroupingTableModel indicatorPropertyTable : rule2ValueGroupingTableMap.values()){
					indicatorPropertyTable.addAction(action);
				}*/
				
				
				tableRows.add(new CfActionGridRow(action));
				
				//TODO: if checknox of update charts is not check dont do this part
				for (ActionPropertyValueGroupingTableModel indicatorPropertyTable : rule2ValueGroupingTableMap.values()){
					indicatorPropertyTable.addAction(action);
				}
				
				filteredActions.add(action);
			}
		}
		Log.debug("Filtering is completed and now adding actions to the tableview");
		tableViewModel.add(tableRows);
		Log.debug("Adding actions to the tableview is finished");
		Log.debug("Applying user filter is completed");
		
		
		
		
		UserLog userActionLog=new UserLog();
    	userActionLog.setComponentType(ComponentType.ACTION_FILTERER);
    	userActionLog.setDescription("Actions are filtered by the rules:",currentActionFilter.getFilterStore());
    	userActionLog.setTriggeredBy(ComponentType.ACTION_FILTERER);
    	userActionLog.setUserActionType(UserActionType.ACTION_FILTERING);
    	userActionLog.addProperty(MonitorConstants.ACTIONS_COUNT,Integer.toString(actionsToFilter.size()));
    	userActionLog.addProperty(MonitorConstants.FILTERED_ACTIONS_COUNT,Integer.toString(filteredActions.size()));
    	Logger.getLoggerInstance().log(userActionLog);
		
		
	}
	
	public void addData(List<CfAction> actions){
		
		tableViewModel.removeAll();
		Log.debug("Adding new actions to the List was started");
		if(actions!=null&&actions.size()>0){
		int index=actions.size()-1;
		lastRecievedAction=actions.get(index);
		
		Log.debug("Adding new actions: Last actions is set");
		allActions.addAll(applyMainFilter(actions));
		Log.debug("Main filter is appied and Actions are added to main list");
		
		addFilteredData(allActions);
		}
		Log.debug("Adding new actions to the main List and  filtered List was completed");
	}
	
	
	
	
	public CfAction getLastAction(){
		
		return lastRecievedAction;		
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
