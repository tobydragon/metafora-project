package de.uds.MonitorInterventionMetafora.client.view.containers;

import java.util.List;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.google.gwt.user.client.ui.Image;
import de.uds.MonitorInterventionMetafora.client.actionresponse.RequestHistoryCallBack;
import de.uds.MonitorInterventionMetafora.client.communication.ServerCommunication;
import de.uds.MonitorInterventionMetafora.client.communication.servercommunication.ActionMaintenance;
import de.uds.MonitorInterventionMetafora.client.datamodels.GroupedByPropertyModel;
import de.uds.MonitorInterventionMetafora.client.manager.ClientInterfaceManager;
import de.uds.MonitorInterventionMetafora.client.resources.Resources;
import de.uds.MonitorInterventionMetafora.client.view.charts.BarChartPanel;
import de.uds.MonitorInterventionMetafora.client.view.charts.PieChartPanel;
import de.uds.MonitorInterventionMetafora.client.view.grids.ExtendedGroupedGrid;
import de.uds.MonitorInterventionMetafora.client.view.widgets.DataViewPanelType;
import de.uds.MonitorInterventionMetafora.client.view.widgets.FilterListPanel;
import de.uds.MonitorInterventionMetafora.client.view.widgets.GroupedDataViewPanel;
import de.uds.MonitorInterventionMetafora.client.view.widgets.MultiModelTabPanel;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfActionType;
import de.uds.MonitorInterventionMetafora.shared.utils.GWTUtils;


public class MonitorPanelContainer extends VerticalPanel implements RequestHistoryCallBack{

	Image loadingImage;
	FilterListPanel flp;
	
	ActionMaintenance maintenance;
	ClientInterfaceManager controller;
	MultiModelTabPanel tabs;
	
	public MonitorPanelContainer(){
		maintenance=new ActionMaintenance();
		controller = new ClientInterfaceManager(maintenance);
	   
	   loadingImage = new Image();
 	   loadingImage.setResource(Resources.IMAGES.loaderImage2());
 	   loadingImage.setWidth("200px");
 	   loadingImage.setHeight("200px");
 	   //loadingImage.setVisibleRect(200, 200, 200, 200);
 	 
 	
 	   this.add(loadingImage);
 	   
 	  CfAction _action=new CfAction();
 	  _action.setTime(GWTUtils.getTimeStamp());
 	  
 	 CfActionType _cfActionType=new CfActionType();
 	 _cfActionType.setType("START_FILE_INPUT");
 	 _action.setCfActionType(_cfActionType);
 	 System.out.println("Sending start from file action");
 	 
 	 ServerCommunication.getInstance().processAction("Tool",_action,this);
 	   
 	  
 	

		
	}
	
	
	@Override
	public void onFailure(Throwable caught) {
		
		System.out.println("Error"+caught.toString());
		
		
	}

	@Override
	public void onSuccess(List<CfAction> _actionList) {
	
		
		if(_actionList!=null)
			maintenance.setActiveActionList(_actionList);
		this.remove(loadingImage);
		flp=new FilterListPanel(maintenance, controller);
		
		tabs=new MultiModelTabPanel();
		tabs.setId("_tabMainPanel");

		  maintenance.startMaintenance();
		 
		 
		 ExtendedGroupedGrid indicatorTable=new ExtendedGroupedGrid(maintenance, controller);
		  tabs.addTab("Table",indicatorTable,false);

		  VerticalPanel panel=new VerticalPanel();
			panel.setId("allContainer");
		  panel.add(flp);
		 
		  GroupedByPropertyModel groupedModel = new GroupedByPropertyModel(maintenance);
		  
		  GroupedDataViewPanel pieChartWithChooser = new GroupedDataViewPanel(DataViewPanelType.PIE_CHART, groupedModel, controller, controller.getDefaultGroupingOption(), "pieChartFilterPanel", "comboPieChartType");
		  addDataView("pieChartViewTab", "Pie Chart View", pieChartWithChooser);

		  GroupedDataViewPanel barChartWithChooser = new GroupedDataViewPanel(DataViewPanelType.BAR_CHART, groupedModel, controller, controller.getDefaultGroupingOption(), "barChartFilterPanel", "comboColumnChartType");
		  addDataView("barChartViewTab", "Bar Chart View", barChartWithChooser);
		  
		  panel.add(tabs);
		  panel.setHeight(600);
		  
		  
		  this.add(panel);
		  this.layout();
		  this.setHeight(600);

	}
	
	private void addDataView(String id, String name, GroupedDataViewPanel panel){
		 tabs.addTab(id, name, panel,false);
		 controller.addDataView(panel);
	}

}
