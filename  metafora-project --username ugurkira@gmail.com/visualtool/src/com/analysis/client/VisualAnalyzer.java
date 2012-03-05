/*
 * Copyright 2008 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.analysis.client;



import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.analysis.client.communication.actionresponses.RequestHistoryCallBack;
import com.analysis.client.communication.models.DataModel;

import com.analysis.client.communication.server.Server;

import com.analysis.client.examples.charts.Showcase;
import com.analysis.client.resources.Resources;
import com.analysis.client.view.charts.ExtendedPieChart;
import com.analysis.client.view.containers.MainContainer;
import com.analysis.client.view.grids.ExtendedGroupedGrid;
import com.analysis.client.view.widgets.MultiModelTabPanel;
import com.analysis.shared.commonformat.CfAction;
import com.analysis.shared.commonformat.CfActionType;
import com.analysis.shared.communication.objects_old.CommonFormatStrings;
import com.analysis.shared.interactionmodels.IndicatorFilterItem;
import com.analysis.shared.utils.GWTDateUtils;


import com.google.gwt.core.client.EntryPoint;


import com.google.gwt.core.client.JsArray;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;


import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;

import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.Selection;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.events.SelectHandler;
import com.google.gwt.visualization.client.visualizations.Gauge;
import com.google.gwt.visualization.client.visualizations.MotionChart;
import com.google.gwt.visualization.client.visualizations.OrgChart;
import com.google.gwt.visualization.client.visualizations.Table;
import com.google.gwt.visualization.client.visualizations.corechart.AxisOptions;
import com.google.gwt.visualization.client.visualizations.corechart.BarChart;
import com.google.gwt.visualization.client.visualizations.corechart.ColumnChart;
import com.google.gwt.visualization.client.visualizations.corechart.CoreChart;
import com.google.gwt.visualization.client.visualizations.corechart.LineChart;
import com.google.gwt.visualization.client.visualizations.corechart.PieChart;



class VisualAnalyzer implements EntryPoint {
  //private final TabPanel tabPanel = new TabPanel();

  public void onModuleLoad() {
    VisualizationUtils.loadVisualizationApi(new Runnable() {
      public void run() {
           	 
    	  MainContainer _container=new MainContainer();
    	  RootPanel.get().add(_container);

    	
      }}, PieChart.PACKAGE);
  }

 

  //, PieChart.PACKAGE, Table.PACKAGE, MotionChart.PACKAGE, OrgChart.PACKAGE,Gauge.PACKAGE
	
  
  
  void sendMultipleRequest(){
	  
	  /*
	  Server.getInstance().sendRequest("RequestsHistory1",new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				
				
				
			}

			public void onSuccess(String result) {
				
				System.out.println("MyResult:"+result);
			}});
	   
	   
	   Server.getInstance().sendRequest("RequestsHistory2",new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				
				
				
			}

			public void onSuccess(String result) {
				
				System.out.println("MyResult:"+result);
			}});
	   
	  Server.getInstance().sendRequest("RequestsHistory3",new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				
				
				
			}

			public void onSuccess(String result) {
				
				System.out.println("MyResult:"+result);
			}});
	  */
  }


  
 
}
