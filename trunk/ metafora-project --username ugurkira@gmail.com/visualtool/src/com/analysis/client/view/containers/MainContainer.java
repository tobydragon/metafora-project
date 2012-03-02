package com.analysis.client.view.containers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.analysis.client.communication.actionresponses.RequestHistoryCallBack;
import com.analysis.client.communication.models.DataModel;
import com.analysis.client.communication.server.Server;
import com.analysis.client.resources.Resources;
import com.analysis.client.utils.GWTDateUtils;
import com.analysis.client.view.charts.ExtendedPieChart;
import com.analysis.client.view.grids.ExtendedGroupedGrid;
import com.analysis.client.view.widgets.TabDataViewPanel;
import com.analysis.shared.commonformat.CfAction;
import com.analysis.shared.commonformat.CfActionType;
import com.analysis.shared.commonformat.CfInteractionData;
import com.analysis.shared.communication.objects_old.CommonFormatStrings;
import com.analysis.shared.interactionmodels.IndicatorFilterItem;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;


public class MainContainer extends VerticalPanel implements RequestHistoryCallBack{

	Image loadingImage;
	public MainContainer(){
		
	   loadingImage = new Image();
 	   loadingImage.setResource(Resources.IMAGES.loaderImage2());
 	   loadingImage.setWidth("200px");
 	   loadingImage.setHeight("200px");
 	
 	   this.add(loadingImage);
 	   
 	  CfAction _action=new CfAction();
 	  _action.setTime(GWTDateUtils.getTimeStamp());
 	  
 	 CfActionType _cfActionType=new CfActionType();
 	 _cfActionType.setType("REQUEST_HISTORY");
 	 _action.setCfActionType(_cfActionType);
 	 
 	 
 	   Server.getInstance().processAction(_action,this);

		
	}
	
	
	@Override
	public void onFailure(Throwable caught) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSuccess(CfInteractionData result) {
	
		
		System.out.println("Configuration:"+result);
		
		
		
		RootPanel.get().remove(loadingImage);
		DataModel.initializeInterActionHistory(result.toString());
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
		
		DataModel.getIndicatorList(_filterItems);
		
		  ExtendedPieChart iaf=new ExtendedPieChart();
		  ExtendedGroupedGrid indicatorTable=new ExtendedGroupedGrid(DataModel.getIndicatorList());
		  tabs.addTab("Table View",indicatorTable);
		  tabs.addTab("Views", iaf);
		  RootPanel.get().add(tabs);
		
	}

}
