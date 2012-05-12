package de.uds.MonitorInterventionMetafora.client.monitor.dataview;

import com.extjs.gxt.ui.client.widget.ContentPanel;

import de.uds.MonitorInterventionMetafora.client.logger.ComponentType;
import de.uds.MonitorInterventionMetafora.client.logger.Log;
import de.uds.MonitorInterventionMetafora.client.logger.Logger;
import de.uds.MonitorInterventionMetafora.client.logger.UserActionType;
import de.uds.MonitorInterventionMetafora.client.monitor.ClientMonitorController;
import de.uds.MonitorInterventionMetafora.client.monitor.datamodel.ClientMonitorDataModel;
import de.uds.MonitorInterventionMetafora.client.monitor.grouping.GroupingChooserToolbar;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionPropertyRule;

public class GroupedDataViewPanel extends ContentPanel {

	GroupingChooserToolbar groupingChooserToolbar;
	DataViewPanel dataViewPanel;
	DataViewPanelType dataViewPanelType;
	
	//TODO: Total and filtered indicator count, maybe for all dataViewPanels, not just table?
//Nuu
	//TODO: Get rid of all ID Strings (last 2 params)
	public GroupedDataViewPanel(DataViewPanelType dataViewPanelType, ClientMonitorDataModel model, 
			ClientMonitorController controller, ActionPropertyRule  groupingProperty, String panelId, String groupingChooserId){
		this.dataViewPanel = DataViewPanel.createDataViewPanel(dataViewPanelType, model, controller, this, groupingProperty);
		this.dataViewPanelType=dataViewPanelType;
		groupingChooserToolbar = new GroupingChooserToolbar(this, groupingProperty, groupingChooserId);
		
		this.setCollapsible(false);
	    this.setFrame(true);
	    this.setWidth(960);
	    this.setHeight(560);
	 
	  
	    
	    this.setTopComponent(groupingChooserToolbar);
	    this.add(dataViewPanel);
	    this.setHeaderVisible(false);
	    
	}
	
	public void changeGroupingProperty(ActionPropertyRule newPropToGroupBy){
		dataViewPanel.setGroupingProperty(newPropToGroupBy);
		
		//Logging Action
		Log userActionLog=new Log();
    	userActionLog.setComponentType(dataViewPanelType);
    	userActionLog.setDescription("Grouping actions  for "+userActionLog.getComponentType()+" Grouping Rule:",newPropToGroupBy);
    	userActionLog.setTriggeredBy(userActionLog.getComponentType());
    	userActionLog.setUserActionType(UserActionType.ACTION_RE_GROUP);
    	Logger.getLoggerInstance().log(userActionLog);
		
	}

	public void refresh() {
		dataViewPanel.refresh();
		layout();
	}

	public ActionPropertyRule getSelectedGroupingProperty() {
		return groupingChooserToolbar.getSelectedProperty();
	}
	
}
