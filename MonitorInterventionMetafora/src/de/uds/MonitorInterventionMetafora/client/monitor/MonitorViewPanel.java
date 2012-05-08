package de.uds.MonitorInterventionMetafora.client.monitor;

import java.util.List;

import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.google.gwt.user.client.ui.Image;
import de.uds.MonitorInterventionMetafora.client.communication.ServerCommunication;
import de.uds.MonitorInterventionMetafora.client.communication.actionresponses.RequestHistoryCallBack;
import de.uds.MonitorInterventionMetafora.client.monitor.datamodel.ClientMonitorDataModel;
import de.uds.MonitorInterventionMetafora.client.monitor.dataview.DataViewPanelType;
import de.uds.MonitorInterventionMetafora.client.monitor.dataview.GroupedDataViewPanel;
import de.uds.MonitorInterventionMetafora.client.monitor.dataview.TabbedDataViewPanel;
import de.uds.MonitorInterventionMetafora.client.monitor.filter.FilterListPanel;
import de.uds.MonitorInterventionMetafora.client.resources.Resources;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfActionType;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionPropertyRule;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionPropertyRuleSelectorModel;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionPropertyRuleSelectorModelType;
import de.uds.MonitorInterventionMetafora.shared.utils.GWTUtils;


public class MonitorViewPanel extends ContentPanel implements RequestHistoryCallBack{

	Image loadingImage;
	
	
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
		
	public MonitorViewPanel(){

		actionPropertyRuleCreator = ActionPropertyRuleSelectorModel.getActionPropertyRuleSelectorModel(ActionPropertyRuleSelectorModelType.GROUPING);
		monitorModel = new ClientMonitorDataModel(actionPropertyRuleCreator);
		controller = new ClientMonitorController(monitorModel);

		updater = new ClientMonitorDataModelUpdater(monitorModel, controller);

		updaterToolbar = new UpdaterToolbar(updater);
		this.setTopComponent(updaterToolbar);
		this.setHeaderVisible(false);
		
		setLoadingImage();
		
		sendStartupMessage();	
	}
	
	private void sendStartupMessage() {
		CfAction startupMessage=new CfAction();
	 	 startupMessage.setTime(GWTUtils.getTimeStamp());
	 	  
	 	 CfActionType _cfActionType=new CfActionType();
	 	 _cfActionType.setType("START_FILE_INPUT");
	 	 startupMessage.setCfActionType(_cfActionType);
	 	 System.out.println("INFO\t\t[MonitorPanelContainer.sendStartupMessage] Sending monitoring start from Client");
	 	 ServerCommunication.getInstance().processAction("MonitoringClient",startupMessage,this);	
	}

	@Override
	public void onFailure(Throwable caught) {
		System.out.println("Error"+caught.toString());
	}

	@Override
	public void onSuccess(List<CfAction> actionList) {
	
		if(actionList!=null){
		 	 System.out.println("INFO\t\t[MonitorPanelContainer.onSuccess] Adding actions count=" + actionList.size());
			monitorModel.addData(actionList);
		}
		
		updaterToolbar.setAutoRefresh(true);
	
		VerticalPanel panel=new VerticalPanel();
		flp=new FilterListPanel(monitorModel, controller);
		panel.add(flp);
		
		createTabbedDataViewsPanel(actionPropertyRuleCreator);
		panel.add(tabbedDataViewPanel);
		
		panel.setHeight(600);
		 
		this.remove(loadingImage);
		this.add(panel);
		this.layout();
		this.setHeight(600);

	}
	
	private void createTabbedDataViewsPanel(ActionPropertyRuleSelectorModel actionPropertyRuleCreator){
		tabbedDataViewPanel=new TabbedDataViewPanel();
		tabbedDataViewPanel.setId("_tabMainPanel");
		
		ActionPropertyRule defaultGrouping = ActionPropertyRuleSelectorModel.getDefaultGrouping();
		
		GroupedDataViewPanel tableWithChooser = new GroupedDataViewPanel(DataViewPanelType.TABLE, monitorModel, controller, defaultGrouping, "tablePanel", "comboTableType");
		addDataView("tableViewTab", "Table View", tableWithChooser);
		
		GroupedDataViewPanel pieChartWithChooser = new GroupedDataViewPanel(DataViewPanelType.PIE_CHART, monitorModel, controller, defaultGrouping, "pieChartFilterPanel", "comboPieChartType");
		addDataView("pieChartViewTab", "Pie Chart View", pieChartWithChooser);

		GroupedDataViewPanel barChartWithChooser = new GroupedDataViewPanel(DataViewPanelType.BAR_CHART, monitorModel, controller, defaultGrouping, "barChartFilterPanel", "comboColumnChartType");
		addDataView("barChartViewTab", "Bar Chart View", barChartWithChooser);
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
