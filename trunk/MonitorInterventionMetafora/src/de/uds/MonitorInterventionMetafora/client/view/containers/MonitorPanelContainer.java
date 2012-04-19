package de.uds.MonitorInterventionMetafora.client.view.containers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;

import de.uds.MonitorInterventionMetafora.client.actionresponse.RequestHistoryCallBack;
import de.uds.MonitorInterventionMetafora.client.communication.ServerCommunication;
import de.uds.MonitorInterventionMetafora.client.communication.servercommunication.ActionMaintenance;
import de.uds.MonitorInterventionMetafora.client.datamodels.EntityViewModel;
import de.uds.MonitorInterventionMetafora.client.datamodels.TableViewModel;
import de.uds.MonitorInterventionMetafora.client.manager.ClientInterfaceManager;
import de.uds.MonitorInterventionMetafora.client.resources.Resources;
import de.uds.MonitorInterventionMetafora.client.view.charts.ExtendedPieChart;
import de.uds.MonitorInterventionMetafora.client.view.grids.ExtendedFilterGrid;
import de.uds.MonitorInterventionMetafora.client.view.grids.ExtendedGroupedGrid;
import de.uds.MonitorInterventionMetafora.client.view.widgets.ColumnChartFilterPanel;
import de.uds.MonitorInterventionMetafora.client.view.widgets.FilterListPanel;
import de.uds.MonitorInterventionMetafora.client.view.widgets.MultiModelTabPanel;
import de.uds.MonitorInterventionMetafora.client.view.widgets.PieChartFilterPanel;
import de.uds.MonitorInterventionMetafora.client.view.widgets.PieChartGroupTypeComboBox;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfActionType;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfInteractionData;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.IndicatorEntity;
import de.uds.MonitorInterventionMetafora.shared.utils.GWTUtils;


public class MonitorPanelContainer extends VerticalPanel implements RequestHistoryCallBack{

	Image loadingImage;
	FilterListPanel flp;
	
	ActionMaintenance maintenance;
	ClientInterfaceManager controller;
	
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
		MultiModelTabPanel tabs=new MultiModelTabPanel();
		tabs.setId("_tabMainPanel");

		  maintenance.startMaintenance();
		 
		 
		 ExtendedGroupedGrid indicatorTable=new ExtendedGroupedGrid(maintenance, controller);
		  tabs.addTab("Table",indicatorTable,false);
		  
		  
		  //this.add(tabs);
		
		  VerticalPanel panel=new VerticalPanel();
			panel.setId("allContainer");
		  panel.add(flp);
		  PieChartFilterPanel _filterPieChart=new PieChartFilterPanel(maintenance, controller);
		  ColumnChartFilterPanel _filterBarChart=new ColumnChartFilterPanel(maintenance, controller);
		  tabs.addTab("pieChartViewTab","Pie Chart View", _filterPieChart,false);
		  tabs.addTab("barChartViewTab","Bar Chart View", _filterBarChart,false);
		  panel.add(tabs);
		  panel.setHeight(600);
		  
		  
		  this.add(panel);
		  this.layout();
		  this.setHeight(600);
		  
		  
		// RootPanel.get().add(panel);
		 
		  //RootPanel.get().add(this);
		  
		
	}

}
