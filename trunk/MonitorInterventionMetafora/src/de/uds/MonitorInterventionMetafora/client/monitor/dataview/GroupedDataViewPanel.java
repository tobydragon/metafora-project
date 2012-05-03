package de.uds.MonitorInterventionMetafora.client.monitor.dataview;

import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.google.gwt.visualization.client.visualizations.Table;

import de.uds.MonitorInterventionMetafora.client.monitor.ClientMonitorController;
import de.uds.MonitorInterventionMetafora.client.monitor.datamodel.ClientMonitorDataModel;
import de.uds.MonitorInterventionMetafora.client.monitor.dataview.chart.BarChartPanel;
import de.uds.MonitorInterventionMetafora.client.monitor.dataview.chart.PieChartPanel;
import de.uds.MonitorInterventionMetafora.client.monitor.dataview.table.TablePanel;
import de.uds.MonitorInterventionMetafora.client.monitor.grouping.GroupingChooserPanel;
import de.uds.MonitorInterventionMetafora.client.monitor.grouping.GroupingChooserToolbar;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionPropertyRule;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionPropertyRuleSelectorModel;

public class GroupedDataViewPanel extends ContentPanel {

	GroupingChooserToolbar groupingChooserToolbar;
//	GroupingChooserPanel groupingChooserPanel;
	DataViewPanel dataViewPanel;
	
	//TODO: Get rid of all ID Strings (last 2 params)
	public GroupedDataViewPanel(DataViewPanelType dataViewPanelType, ClientMonitorDataModel model, 
			ClientMonitorController controller, ActionPropertyRule  groupingProperty, String panelId, String groupingChooserId){
		this.dataViewPanel = DataViewPanel.createDataViewPanel(dataViewPanelType, model, controller, this, groupingProperty);
		
//		groupingChooserPanel = new GroupingChooserPanel(this, model.getGroupingRulesComboBoxModel(), groupingProperty, groupingChooserId);
		groupingChooserToolbar = new GroupingChooserToolbar(this, groupingProperty, groupingChooserId);
		
//		ContentPanel panel = new ContentPanel();
//	    panel.setHeading("Indicator List");
//	    panel.setIcon(Resources.ICONS.table());
//	    panel.setId("_groupedGridPanel");
	   
		this.setCollapsible(false);
	    this.setFrame(true);
	    this.setWidth(960);
	    
	    this.setTopComponent(groupingChooserToolbar);
	    this.add(dataViewPanel);
	    
//		this.setWidth(600);
//		this.setId(panelId);
//		this.add(groupingChooserPanel);
//		this.add(dataViewPanel);
	}
	
	public void changeGroupingProperty(ActionPropertyRule newPropToGroupBy){
		dataViewPanel.setGroupingProperty(newPropToGroupBy);
	}

	public void refresh() {
//		groupingChooserPanel.refresh();
		dataViewPanel.refresh();
		layout();
	}

	public ActionPropertyRule getSelectedGroupingProperty() {
		return groupingChooserToolbar.getSelectedProperty();
	}
	
}
