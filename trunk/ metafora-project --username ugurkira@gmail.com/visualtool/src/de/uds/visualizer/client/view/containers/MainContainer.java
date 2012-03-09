package de.uds.visualizer.client.view.containers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;

import de.uds.visualizer.client.communication.actionresponses.RequestHistoryCallBack;
import de.uds.visualizer.client.communication.servercommunication.ActionMaintenance;
import de.uds.visualizer.client.communication.servercommunication.Server;
import de.uds.visualizer.client.datamodels.TableViewModel;
import de.uds.visualizer.client.resources.Resources;
import de.uds.visualizer.client.view.charts.ExtendedPieChart;
import de.uds.visualizer.client.view.grids.ExtendedFilterGrid;
import de.uds.visualizer.client.view.grids.ExtendedGroupedGrid;
import de.uds.visualizer.client.view.widgets.FilterListPanel;
import de.uds.visualizer.client.view.widgets.MultiModelTabPanel;
import de.uds.visualizer.shared.commonformat.CfAction;
import de.uds.visualizer.shared.commonformat.CfActionType;
import de.uds.visualizer.shared.commonformat.CfInteractionData;
import de.uds.visualizer.shared.communication.objects_old.CommonFormatStrings;
import de.uds.visualizer.shared.interactionmodels.IndicatorEntity;
import de.uds.visualizer.shared.utils.GWTDateUtils;


public class MainContainer extends VerticalPanel implements RequestHistoryCallBack{

	Image loadingImage;
	FilterListPanel flp;
	ActionMaintenance maintenance;
	public MainContainer(){
	   
	   flp=new FilterListPanel();
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
 	 
 	 
 	   Server.getInstance().processAction("Tool",_action,this);
 	   
 	  maintenance=new ActionMaintenance();
 	

		
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
		MultiModelTabPanel tabs=new MultiModelTabPanel();
		
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
		 ExtendedPieChart iaf=new ExtendedPieChart(maintenance);
		 
		 ExtendedGroupedGrid indicatorTable=new ExtendedGroupedGrid(maintenance);
		  tabs.addTab("Table",indicatorTable);
		  tabs.addTab("PieView", iaf);
		  
		  //this.add(tabs);
		  VerticalPanel panel=new VerticalPanel();
		  panel.add(flp);
		  panel.add(tabs);
		 
		  RootPanel.get().add(panel);
		 
		  //RootPanel.get().add(this);
		  
		
	}

}
