package com.analysis.client.view.containers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.analysis.client.communication.actionresponses.RequestHistoryCallBack;
import com.analysis.client.communication.models.DataModel;
import com.analysis.client.communication.server.ActionMaintenance;
import com.analysis.client.communication.server.Server;
import com.analysis.client.resources.Resources;
import com.analysis.client.view.charts.ExtendedPieChart;
import com.analysis.client.view.grids.ExtendedGroupedGrid;
import com.analysis.client.view.widgets.TabDataViewPanel;
import com.analysis.shared.commonformat.CfAction;
import com.analysis.shared.commonformat.CfActionType;
import com.analysis.shared.commonformat.CfInteractionData;
import com.analysis.shared.communication.objects_old.CommonFormatStrings;
import com.analysis.shared.interactionmodels.IndicatorFilterItem;
import com.analysis.shared.utils.GWTDateUtils;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;


public class MainContainer extends VerticalPanel implements RequestHistoryCallBack{

	Image loadingImage;
	ActionMaintenance _maintenance;
	public MainContainer(){
		
	   loadingImage = new Image();
 	   loadingImage.setResource(Resources.IMAGES.loaderImage2());
 	   loadingImage.setWidth("200px");
 	   loadingImage.setHeight("200px");
 	 
 	
 	   this.add(loadingImage);
 	   
 	  CfAction _action=new CfAction();
 	  _action.setTime(GWTDateUtils.getTimeStamp());
 	  
 	 CfActionType _cfActionType=new CfActionType();
 	 _cfActionType.setType("START_FILE_INPUT");
 	 _action.setCfActionType(_cfActionType);
 	 
 	 
 	   Server.getInstance().processAction("Tool",_action,this);
 	   
 	  _maintenance=new ActionMaintenance();
 	  _maintenance.startMaintenance();

		
	}
	
	
	@Override
	public void onFailure(Throwable caught) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSuccess(List<CfAction> _actionList) {
	
		
		//System.out.println("Configuration:"+result);

		this.remove(loadingImage);
		//DataModel.initializeInterActionHistory(result.toString());
		  //VerticalPanel vp=new VerticalPanel();
		TabDataViewPanel tabs=new TabDataViewPanel("");
		
		
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
		
		//DataModel.getIndicatorList(_filterItems);
		
		  ExtendedPieChart iaf=new ExtendedPieChart();
		 // ExtendedGroupedGrid indicatorTable=new ExtendedGroupedGrid(DataModel.getIndicatorList());
		  //abs.addTab("Table View",indicatorTable);
		  tabs.addTab("Views", iaf);
		  RootPanel.get().add(tabs);
		
	}

}
