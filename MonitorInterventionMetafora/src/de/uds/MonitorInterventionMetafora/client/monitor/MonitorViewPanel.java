package de.uds.MonitorInterventionMetafora.client.monitor;

import java.util.List;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.google.gwt.user.client.ui.Image;
import de.uds.MonitorInterventionMetafora.client.communication.ServerCommunication;
import de.uds.MonitorInterventionMetafora.client.communication.actionresponses.RequestHistoryCallBack;
import de.uds.MonitorInterventionMetafora.client.monitor.dataview.DataViewPanelType;
import de.uds.MonitorInterventionMetafora.client.monitor.dataview.GroupedDataViewPanel;
import de.uds.MonitorInterventionMetafora.client.monitor.dataview.TabbedDataViewPanel;
import de.uds.MonitorInterventionMetafora.client.monitor.dataview.table.ExtendedGroupedGrid;
import de.uds.MonitorInterventionMetafora.client.monitor.filter.FilterListPanel;
import de.uds.MonitorInterventionMetafora.client.resources.Resources;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfActionType;
import de.uds.MonitorInterventionMetafora.shared.utils.GWTUtils;


public class MonitorViewPanel extends VerticalPanel implements RequestHistoryCallBack{

	Image loadingImage;
	FilterListPanel flp;
	
	ClientMonitorController controller;
	TabbedDataViewPanel tabs;
	ClientMonitorDataModelUpdater updater;
	ClientMonitorDataModel monitorModel;
	
	public MonitorViewPanel(){

		monitorModel = new ClientMonitorDataModel();
		controller = new ClientMonitorController(monitorModel);

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
	public void onSuccess(List<CfAction> _actionList) {
	
		if(_actionList!=null){
		 	 System.out.println("INFO\t\t[MonitorPanelContainer.onSuccess] Adding actions count=" + _actionList.size());
			monitorModel.addData(_actionList);
		}
		updater = new ClientMonitorDataModelUpdater(monitorModel, controller);
		updater.startUpdates();
	
		VerticalPanel panel=new VerticalPanel();
		panel.setId("allContainer");
		
		flp=new FilterListPanel(monitorModel, controller);
		panel.add(flp);
		createTabbedDataViewsPanel();
		panel.add(tabs);
		
		panel.setHeight(600);
		 
		this.remove(loadingImage);
		this.add(panel);
		this.layout();
		this.setHeight(600);

	}
	
	private void createTabbedDataViewsPanel(){
		tabs=new TabbedDataViewPanel();
		tabs.setId("_tabMainPanel");
		
		//TODO: this indicatorTable should be a GroupedDataViewPanel and added like others
		ExtendedGroupedGrid indicatorTable=new ExtendedGroupedGrid(monitorModel, controller);
		tabs.addTab("Table",indicatorTable,false);
		  		  
		GroupedDataViewPanel pieChartWithChooser = new GroupedDataViewPanel(DataViewPanelType.PIE_CHART, monitorModel, controller, controller.getDefaultGroupingOption(), "pieChartFilterPanel", "comboPieChartType");
		addDataView("pieChartViewTab", "Pie Chart View", pieChartWithChooser);

		GroupedDataViewPanel barChartWithChooser = new GroupedDataViewPanel(DataViewPanelType.BAR_CHART, monitorModel, controller, controller.getDefaultGroupingOption(), "barChartFilterPanel", "comboColumnChartType");
		addDataView("barChartViewTab", "Bar Chart View", barChartWithChooser);
	}
	
	private void addDataView(String id, String name, GroupedDataViewPanel panel){
		 tabs.addTab(id, name, panel,false);
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
