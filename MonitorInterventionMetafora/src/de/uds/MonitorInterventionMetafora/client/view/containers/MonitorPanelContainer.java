package de.uds.MonitorInterventionMetafora.client.view.containers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;

import de.uds.MonitorInterventionMetafora.client.communication.actionresponses.RequestHistoryCallBack;
import de.uds.MonitorInterventionMetafora.client.communication.servercommunication.ActionMaintenance;
import de.uds.MonitorInterventionMetafora.client.communication.servercommunication.ServerCommunication;
import de.uds.MonitorInterventionMetafora.client.datamodels.PieChartViewModel;
import de.uds.MonitorInterventionMetafora.client.datamodels.TableViewModel;
import de.uds.MonitorInterventionMetafora.client.resources.Resources;
import de.uds.MonitorInterventionMetafora.client.view.charts.ExtendedPieChart;
import de.uds.MonitorInterventionMetafora.client.view.grids.ExtendedFilterGrid;
import de.uds.MonitorInterventionMetafora.client.view.grids.ExtendedGroupedGrid;
import de.uds.MonitorInterventionMetafora.client.view.widgets.FilterListPanel;
import de.uds.MonitorInterventionMetafora.client.view.widgets.MultiModelTabPanel;
import de.uds.MonitorInterventionMetafora.client.view.widgets.PieChartFilterPanel;
import de.uds.MonitorInterventionMetafora.client.view.widgets.PieChartFilterTypeComboBox;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfActionType;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfInteractionData;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.IndicatorEntity;
import de.uds.MonitorInterventionMetafora.shared.utils.GWTDateUtils;


public class MonitorPanelContainer extends VerticalPanel implements RequestHistoryCallBack{

	Image loadingImage;
	FilterListPanel flp;
	
	ActionMaintenance maintenance;
	public MonitorPanelContainer(){
		maintenance=new ActionMaintenance();
		
	   
	   loadingImage = new Image();
 	   loadingImage.setResource(Resources.IMAGES.loaderImage2());
 	   loadingImage.setWidth("200px");
 	   loadingImage.setHeight("200px");
 	   //loadingImage.setVisibleRect(200, 200, 200, 200);
 	 
 	
 	   this.add(loadingImage);
 	   
 	  CfAction _action=new CfAction();
 	  _action.setTime(GWTDateUtils.getTimeStamp());
 	  
 	 CfActionType _cfActionType=new CfActionType();
 	 _cfActionType.setType("START_FILE_INPUT");
 	 _action.setCfActionType(_cfActionType);
 	 
 	 
 	   ServerCommunication.getInstance().processAction("Tool",_action,this);
 	   
 	  
 	

		
	}
	
	
	@Override
	public void onFailure(Throwable caught) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSuccess(List<CfAction> _actionList) {
	
		
		if(_actionList!=null)
			maintenance.setActiveActionList(_actionList);
		
		//System.out.println("Configuration:"+result);

		this.remove(loadingImage);
		//DataModel.initializeInterActionHistory(result.toString());
		  //VerticalPanel vp=new VerticalPanel();
		
		flp=new FilterListPanel(maintenance);
		MultiModelTabPanel tabs=new MultiModelTabPanel();
		tabs.setId("_tabMainPanel");
	
		
		/*
		Map<String, IndicatorFilterItem> _filterItems=new HashMap<String, IndicatorFilterItem>();
		
		IndicatorFilterItem item;
		item=new IndicatorFilterItem();
		item.setType(CommonFormatStrings.CONTENT_STRING);
		item.setProperty("INDICATOR_TYPE");
		item.setValue("activity");
		_filterItems.put("INDICATOR_TYPE", item);
		
		
		
		item=new IndicatorFilterItem();
		item.setType(CommonFormatStrings.OBJECT_STRING);
		item.setProperty("USERNAME");
		item.setValue("Bob");
		_filterItems.put("USERNAME", item);
		
		item=new IndicatorFilterItem();
		item.setType(CommonFormatStrings.ACTION_STRING);
		item.setProperty("classification");
		item.setValue("CREATE");
		_filterItems.put("classification", item);
		
		
		*/
		
		// this.add(flp);
		  maintenance.startMaintenance();
		 
		 
		 ExtendedGroupedGrid indicatorTable=new ExtendedGroupedGrid(maintenance);
		  tabs.addTab("Table",indicatorTable,false);
		  
		  
		  //this.add(tabs);
		
		  VerticalPanel panel=new VerticalPanel();
			panel.setId("allContainer");
		  panel.add(flp);
		  
		  
		  PieChartFilterPanel _filterPieChart=new PieChartFilterPanel(maintenance);
		 // ExtendedPieChart iaf=new ExtendedPieChart(maintenance);
		 // iaf.setId("_pieChartPanel");
		  tabs.addTab("Pie View", _filterPieChart,false);
		  //tabs.getWidget(0).a
		  
		  panel.add(tabs);
		 // this.add(panel);
		  
		  
		 RootPanel.get().add(panel);
		 
		  //RootPanel.get().add(this);
		  
		
	}

}
