package de.uds.MonitorInterventionMetafora.client.monitor.dataview;

import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;

import de.uds.MonitorInterventionMetafora.client.logger.ComponentType;
import de.uds.MonitorInterventionMetafora.client.logger.Log;
import de.uds.MonitorInterventionMetafora.client.logger.Logger;
import de.uds.MonitorInterventionMetafora.client.logger.UserActionType;
import de.uds.MonitorInterventionMetafora.client.monitor.ClientMonitorController;
import de.uds.MonitorInterventionMetafora.client.monitor.datamodel.ClientMonitorDataModel;
import de.uds.MonitorInterventionMetafora.client.monitor.dataview.table.TablePanel;
import de.uds.MonitorInterventionMetafora.client.monitor.filter.FilterListPanel;
import de.uds.MonitorInterventionMetafora.client.monitor.grouping.GroupingChooserToolbar;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionPropertyRule;

public class GroupedDataViewPanel extends ContentPanel {

	GroupingChooserToolbar groupingChooserToolbar;
	DataViewPanel dataViewPanel;
	DataViewPanelType dataViewPanelType;
	private StatusPanel statusPanel;
	
	
	//TODO: Total and filtered indicator count, maybe for all dataViewPanels, not just table?


	public GroupedDataViewPanel(DataViewPanelType dataViewPanelType, ClientMonitorDataModel model, 
			ClientMonitorController controller, ActionPropertyRule  groupingProperty, SimpleComboBox<String> filterGroupCombo,FilterListPanel filterPanel,
			TabbedDataViewPanel tabbedDataViewPanel){
		this.dataViewPanel = DataViewPanel.createDataViewPanel(dataViewPanelType, model, controller, this, groupingProperty,filterGroupCombo,filterPanel,tabbedDataViewPanel);
		this.dataViewPanelType=dataViewPanelType;
		groupingChooserToolbar = new GroupingChooserToolbar(this, groupingProperty);
		statusPanel=new StatusPanel(model);
		enableReSize(filterPanel);
		this.setCollapsible(false);
	    this.setFrame(true);
	    this.setWidth(960);
	    this.setHeight(560);
	 
	  
	    this.dataViewPanel.add(statusPanel);
	    this.setTopComponent(groupingChooserToolbar);
	    this.add(dataViewPanel);
	    this.setHeaderVisible(false);
	    
	}
	
	
	void enableReSize(ContentPanel filterPanel){
		
		
		filterPanel.addListener(Events.Collapse, new Listener<BaseEvent>()
		        {

		            public void handleEvent(BaseEvent be)
		            {
		            	
		            	dataViewPanel.setHeight(557);
		            	GroupedDataViewPanel.this.setHeight(560);
		            	GroupedDataViewPanel.this.refresh();
		            	dataViewPanel.refresh();
		            };
		        });
				 
		filterPanel.addListener(Events.Expand, new Listener<BaseEvent>()
		        {

		            public void handleEvent(BaseEvent be)
		            {
		            	
		            	dataViewPanel.setHeight(357);
		            	GroupedDataViewPanel.this.setHeight(360);
		            	GroupedDataViewPanel.this.refresh();
		            	dataViewPanel.refresh();
		            	
		            };
		        });
	
		
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
		this.setLayoutNeeded(true);
		this.layout();
		statusPanel.refresh();
	}

	public DataViewPanelType getDataViewType(){
	
		return dataViewPanelType;
	}
	public ActionPropertyRule getSelectedGroupingProperty() {
		return groupingChooserToolbar.getSelectedProperty();
	}
	
}
