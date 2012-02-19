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



import com.analysis.client.communication.resources.DataProcess;
import com.analysis.client.communication.server.IOManager;
import com.analysis.client.communication.server.Server;

import com.analysis.client.components.charts.Showcase;
import com.analysis.client.resources.Resources;
import com.analysis.client.view.charts.ExtendedPieChart;
import com.analysis.client.view.grids.ExtendedGroupedGrid;
import com.analysis.client.view.widgets.ExtendedTab;


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


/**
 * Google Visualization API in GWT demo.
 */
class VisualAnalyzer implements EntryPoint {
  //private final TabPanel tabPanel = new TabPanel();

  public void onModuleLoad() {
    VisualizationUtils.loadVisualizationApi(new Runnable() {
      public void run() {
           	 
    	
    	  
    	   final Image loadingImage = new Image();
    	   loadingImage.setResource(Resources.IMAGES.loaderImage2());
    	   loadingImage.setWidth("200px");
    	   loadingImage.setHeight("200px");
    	  
    	  
    	// loadingImage.setUrlAndVisibleRect(Resources.IMAGES.loaderImage().getSafeUri(), 10, 10, 400, 400);
    	
    	
    	   RootPanel.get().add(loadingImage,500,200);
    	
    
    	//Server.getInstance()
   
    	// IOManager io=new IOManager();
    	 
    	 Server.getInstance().sendActionPackage("RequestHistory",new AsyncCallback<String>() {
				public void onFailure(Throwable caught) {
					
					
					
				}

				public void onSuccess(String result) {
					
					
					
					
					
					RootPanel.get().remove(loadingImage);
					DataProcess.initializeInterActionHistory(result.toString());
					  //VerticalPanel vp=new VerticalPanel();
					ExtendedTab tabs=new ExtendedTab("ddd");
			    	  ExtendedPieChart iaf=new ExtendedPieChart();
			    	  ExtendedGroupedGrid indicatorTable=new ExtendedGroupedGrid("");
			    	  tabs.addTab("Table View",indicatorTable);
			    	  tabs.addTab("Views", iaf);
			    	  
			    	  RootPanel.get().add(tabs.renderExtendedTabPanel());
										
				}
			});
    	 
    	 
    	 //io.sendToServer("RequestHistory",);
    	  //DialogBox db=new DialogBox();
    	  //XmlFragment xf=new XmlFragment();
    	  
    	  //DataProcess dp=new DataProcess();
    	 // dp.groupObjectByProperty("MAP_ID");
    	  
    	  //xf.processActions(xf.getInterAction(""));
			 
    	 // db.setText(xf.getInterAction(""));
    	  //db.show();
    	  
    	 
    	// RootPanel.get().add(ColumnChartWidget());
    	 
    	 //RootPanel.get().add(BarChartWidget());
    	 
        //vp.add(new Label("Visual Analyzer"));
      //  vp.add(tabPanel);
        //tabPanel.setWidth("1100");
       // tabPanel.setHeight("600");
        
        //Annotater aa =new Annotater();
    //    tabPanel.add(aa.createPieChart(),"Ugur");
        
        
        
        //tabPanel.selectTab(0);
      }}, PieChart.PACKAGE, Table.PACKAGE, MotionChart.PACKAGE, OrgChart.PACKAGE,Gauge.PACKAGE);
  }

 


	
  
  
  public Widget ColumnChartWidget() {
	    VerticalPanel result = new VerticalPanel();
	    com.google.gwt.visualization.client.visualizations.corechart.Options options = CoreChart.createOptions();
	    options.setHeight(300);
	    options.setTitle("Annotations");
	    options.setWidth(500);

	    AxisOptions vAxisOptions = AxisOptions.create();
	    vAxisOptions.setMinValue(0);
	    vAxisOptions.setMaxValue(20);
	    options.setVAxisOptions(vAxisOptions);
	    DataTable data = Showcase.getCompanyPerformanceWithNulls();
	    ColumnChart viz = new ColumnChart(data, options);
	    Label status = new Label();
	    Label onMouseOverAndOutStatus = new Label();
	    viz.addSelectHandler(createSelectHandlerForColumn(viz));
	    
	    
	    
	  //  viz.addReadyHandler(new ReadyDemo(status));
	   // viz.addOnMouseOverHandler(new OnMouseOverDemo(onMouseOverAndOutStatus));
	    //viz.addOnMouseOutHandler(new OnMouseOutDemo(onMouseOverAndOutStatus));
	 
	    result.add(status);
	    result.add(viz);
	    result.add(onMouseOverAndOutStatus);
	    return result;
	  }
  
  public Widget LineChartWidget() {
	    VerticalPanel result = new VerticalPanel();

	    com.google.gwt.visualization.client.visualizations.corechart.Options options = CoreChart.createOptions();
	    options.setHeight(240);
	    options.setTitle("Student Contributions");
	    options.setWidth(400);
	    options.setInterpolateNulls(true);
	    AxisOptions vAxisOptions = AxisOptions.create();
	    vAxisOptions.setMinValue(0);
	    vAxisOptions.setMaxValue(2000);
	    options.setVAxisOptions(vAxisOptions);

	    DataTable data = Showcase.getCompanyPerformanceWithNulls();
	    LineChart viz = new LineChart(data, options);

	    Label status = new Label();
	    Label onMouseOverAndOutStatus = new Label();
	    viz.addSelectHandler(createSelectHandlerForLineChart(viz));
	    //viz.addReadyHandler(new ReadyDemo(status));
	    //viz.addOnMouseOverHandler(new OnMouseOverDemo(onMouseOverAndOutStatus));
	    //viz.addOnMouseOutHandler(new OnMouseOutDemo(onMouseOverAndOutStatus));
	    result.add(status);
	    result.add(viz);
	    result.add(onMouseOverAndOutStatus);
	    return result;
	  }
  
  public Widget BarChartWidget() {
	    VerticalPanel result = new VerticalPanel();
	    com.google.gwt.visualization.client.visualizations.corechart.Options options = com.google.gwt.visualization.client.visualizations.corechart.Options.create();
	    options.setHeight(240);
	    options.setTitle("Student Performance");
	    options.setWidth(400);
	    AxisOptions vAxisOptions = AxisOptions.create();
	    vAxisOptions.setMinValue(0);
	    vAxisOptions.setMaxValue(2000);
	    options.setVAxisOptions(vAxisOptions);

	    DataTable data = Showcase.getCompanyPerformance();
	    BarChart viz = new BarChart(data, options);

	    Label status = new Label();
	    Label onMouseOverAndOutStatus = new Label();
	    viz.addSelectHandler(createSelectHandlerForBarChart(viz));
	    //viz.addReadyHandler(new ReadyDemo(status));
	    //viz.addOnMouseOverHandler(new OnMouseOverDemo(onMouseOverAndOutStatus));
	    //viz.addOnMouseOutHandler(new OnMouseOutDemo(onMouseOverAndOutStatus));
	    result.add(status);
	    result.add(viz);
	    result.add(onMouseOverAndOutStatus);
	    return result;
	  }
  
  private SelectHandler createSelectHandlerForColumn(final   ColumnChart chart) {
	    return new SelectHandler() {

			@Override
			public void onSelect(SelectEvent event) {
			
				StringBuffer b = new StringBuffer();
			    JsArray<Selection> s = chart.getSelections();
			    for (int i = 0; i < s.length(); ++i) {
			      if (s.get(i).isCell()) {
			        b.append(" cell ");
			        b.append(s.get(i).getRow());
			        b.append(":");
			        b.append(s.get(i).getColumn());
			      } else if (s.get(i).isRow()) {
			        b.append(" row ");
			        b.append(s.get(i).getRow());
			      } else {
			        b.append(" column ");
			        b.append(s.get(i).getColumn());
			      }
			    }
			}
	    	
	    };
  }
  
  
  private SelectHandler createSelectHandlerForLineChart(final  LineChart chart) {
	    return new SelectHandler() {

			@Override
			public void onSelect(SelectEvent event) {
			
				StringBuffer b = new StringBuffer();
			    JsArray<Selection> s = chart.getSelections();
			    for (int i = 0; i < s.length(); ++i) {
			      if (s.get(i).isCell()) {
			        b.append(" cell ");
			        b.append(s.get(i).getRow());
			        b.append(":");
			        b.append(s.get(i).getColumn());
			      } else if (s.get(i).isRow()) {
			        b.append(" row ");
			        b.append(s.get(i).getRow());
			      } else {
			        b.append(" column ");
			        b.append(s.get(i).getColumn());
			      }
			    }
			}
	    	
	    };
}
  
  
  private SelectHandler createSelectHandlerForBarChart(final   BarChart chart) {
	    return new SelectHandler() {

			@Override
			public void onSelect(SelectEvent event) {
			
				StringBuffer b = new StringBuffer();
			    JsArray<Selection> s = chart.getSelections();
			    for (int i = 0; i < s.length(); ++i) {
			      if (s.get(i).isCell()) {
			        b.append(" cell ");
			        b.append(s.get(i).getRow());
			        b.append(":");
			        b.append(s.get(i).getColumn());
			      } else if (s.get(i).isRow()) {
			        b.append(" row ");
			        b.append(s.get(i).getRow());
			      } else {
			        b.append(" column ");
			        b.append(s.get(i).getColumn());
			      }
			    }
			}
	    	
	    };
  }
}
