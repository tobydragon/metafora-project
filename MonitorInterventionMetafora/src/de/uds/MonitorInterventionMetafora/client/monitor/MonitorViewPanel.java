package de.uds.MonitorInterventionMetafora.client.monitor;

import java.util.List;

import com.allen_sauer.gwt.log.client.Log;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Image;

import de.uds.MonitorInterventionMetafora.client.communication.CommunicationServiceAsync;
import de.uds.MonitorInterventionMetafora.client.monitor.datamodel.ClientMonitorDataModel;
import de.uds.MonitorInterventionMetafora.client.monitor.dataview.DataViewPanelType;
import de.uds.MonitorInterventionMetafora.client.monitor.dataview.GroupedDataViewPanel;
import de.uds.MonitorInterventionMetafora.client.monitor.dataview.TabbedDataViewPanel;
import de.uds.MonitorInterventionMetafora.client.monitor.filter.FilterListPanel;
import de.uds.MonitorInterventionMetafora.client.resources.Resources;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfActionType;
import de.uds.MonitorInterventionMetafora.shared.monitor.UpdateResponse;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionPropertyRule;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionPropertyRuleSelectorModel;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionPropertyRuleSelectorModelType;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.StandardRuleBuilder;
import de.uds.MonitorInterventionMetafora.shared.utils.GWTUtils;


public class MonitorViewPanel extends ContentPanel implements AsyncCallback<UpdateResponse>{

	Image loadingImage;
	
	private CommunicationServiceAsync commServiceServlet;
	//Views
	UpdaterToolbar updaterToolbar;
	FilterListPanel flp;
	TabbedDataViewPanel tabbedDataViewPanel;
	
	//models
	ActionPropertyRuleSelectorModel actionPropertyRuleCreator;
	ClientMonitorDataModel monitorModel;
	
	//controllers
	ClientMonitorDataModelUpdater updater;
	ClientMonitorController controller;
	
	private boolean complexDataViews;
		
	public MonitorViewPanel(boolean complexDataViews, CommunicationServiceAsync monitoringViewServiceServlet){
		this.complexDataViews = complexDataViews;
		this.commServiceServlet=monitoringViewServiceServlet;
		actionPropertyRuleCreator = ActionPropertyRuleSelectorModel.getActionPropertyRuleSelectorModel(ActionPropertyRuleSelectorModelType.GROUPING);
		ActionPropertyRuleSelectorModel filterPropertyRuleSelector = ActionPropertyRuleSelectorModel.getActionPropertyRuleSelectorModel(ActionPropertyRuleSelectorModelType.FILTER);
		
		monitorModel = new ClientMonitorDataModel(complexDataViews, actionPropertyRuleCreator, filterPropertyRuleSelector,monitoringViewServiceServlet);
		controller = new ClientMonitorController(monitorModel);

		updater = new ClientMonitorDataModelUpdater(monitorModel, controller);

		updaterToolbar = new UpdaterToolbar(updater,monitorModel,controller,monitoringViewServiceServlet);
		controller.setUpdaterToolbar(updaterToolbar);
		updaterToolbar.setAutoRefresh(false);
		this.setTopComponent(updaterToolbar);
		this.setHeaderVisible(false);
		
		setLoadingImage();
		
		sendStartupMessage();	
	}
	
	private void sendStartupMessage() {
		//create an initial message 
		CfAction startupMessage=new CfAction();
	 	 startupMessage.setTime(GWTUtils.getTimeStamp());
	 	 CfActionType _cfActionType=new CfActionType();
	 	 _cfActionType.setType("START_FILE_INPUT");
	 	 startupMessage.setCfActionType(_cfActionType);
	 	 
	 	 Log.info("[sendStartupMessage] initial update request from client");
	 	 updater.getActionsAfterThisAction(startupMessage, this);
	}

	@Override
	public void onFailure(Throwable caught) {
		System.out.println("Error"+caught.toString());
	}

	@Override
	public void onSuccess(UpdateResponse updateResponse) {
		List <CfAction> actionList = updateResponse.getActions();
		Log.debug("Update Response recieved, Action Size: "+actionList.size());
		if(actionList!=null){
		 	 Log.info("[MonitorPanelContainer.onSuccess] First actions count = " + actionList.size());
			monitorModel.addData(updateResponse);
		}
		
		Log.info("[MonitorPanelContainer.onSuccess] Starting group IDs: " + updateResponse.getAssociatedGroups());
			
		VerticalPanel panel=new VerticalPanel();
		flp=new FilterListPanel(monitorModel, controller,commServiceServlet);
		//enableResizeListener(flp.getFilterGridPanel());
		panel.add(flp);
		
		createDataViewsPanel( actionPropertyRuleCreator,flp.getFilterGrid().getfilterSelectorToolBar().getFilterSelectorComboBox(),flp);
		panel.add(tabbedDataViewPanel);
		
		panel.setHeight(600);
		 
		this.remove(loadingImage);
		this.add(panel);
		this.layout();
		this.setHeight(800);

	}
	

	private void createDataViewsPanel(ActionPropertyRuleSelectorModel actionPropertyRuleCreator,SimpleComboBox<String> filterGroupCombo,
			FilterListPanel filterPanel){
		
		ActionPropertyRule defaultGrouping = StandardRuleBuilder.getDefaultGroupingRule();
		
		tabbedDataViewPanel=new TabbedDataViewPanel();
		
		GroupedDataViewPanel tableWithChooser = new GroupedDataViewPanel(DataViewPanelType.TABLE, monitorModel, controller, defaultGrouping, filterGroupCombo,filterPanel,tabbedDataViewPanel);
		addDataView("tableViewTab", "Table View", tableWithChooser);
		
		if (complexDataViews){			
			GroupedDataViewPanel pieChartWithChooser = new GroupedDataViewPanel(DataViewPanelType.PIE_CHART, monitorModel, controller, defaultGrouping,filterGroupCombo,filterPanel,tabbedDataViewPanel);
			addDataView("pieChartViewTab", "Pie Chart View", pieChartWithChooser);
	
			GroupedDataViewPanel barChartWithChooser = new GroupedDataViewPanel(DataViewPanelType.BAR_CHART, monitorModel, controller, defaultGrouping, filterGroupCombo,filterPanel,tabbedDataViewPanel);
			addDataView("barChartViewTab", "Bar Chart View", barChartWithChooser);
		}

	}
	
	private void addDataView(String id, String name, GroupedDataViewPanel panel){
		tabbedDataViewPanel.addTab(id, name, panel,false);
		controller.addDataView(panel); 

	}
	
	private void setLoadingImage(){
		loadingImage = new Image();
 	   	loadingImage.setResource(Resources.IMAGES.loaderImage2());
 	   	loadingImage.setWidth("200px");
 	   	loadingImage.setHeight("200px");
 	   	this.add(loadingImage);
	}

}
