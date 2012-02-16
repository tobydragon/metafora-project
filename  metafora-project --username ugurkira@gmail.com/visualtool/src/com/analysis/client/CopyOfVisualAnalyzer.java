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


import java.util.Date;

import com.analysis.client.components.Annotater;
import com.analysis.client.components.charts.Showcase;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptException;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.DataView;
import com.google.gwt.visualization.client.Query;
import com.google.gwt.visualization.client.QueryResponse;
import com.google.gwt.visualization.client.Selection;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.Query.Callback;
import com.google.gwt.visualization.client.Query.SendMethod;
import com.google.gwt.visualization.client.events.SelectHandler;
import com.google.gwt.visualization.client.events.StateChangeHandler;
import com.google.gwt.visualization.client.events.StateChangeHandler.StateChangeEvent;
import com.google.gwt.visualization.client.formatters.ArrowFormat;
import com.google.gwt.visualization.client.visualizations.Gauge;
import com.google.gwt.visualization.client.visualizations.MotionChart;
import com.google.gwt.visualization.client.visualizations.OrgChart;
import com.google.gwt.visualization.client.visualizations.Table;
import com.google.gwt.visualization.client.visualizations.OrgChart.Size;
import com.google.gwt.visualization.client.visualizations.Table.Options;
import com.google.gwt.visualization.client.visualizations.corechart.AxisOptions;
import com.google.gwt.visualization.client.visualizations.corechart.BarChart;
import com.google.gwt.visualization.client.visualizations.corechart.ColumnChart;
import com.google.gwt.visualization.client.visualizations.corechart.CoreChart;
import com.google.gwt.visualization.client.visualizations.corechart.LineChart;
import com.google.gwt.visualization.client.visualizations.corechart.PieChart;
import com.google.gwt.visualization.client.visualizations.corechart.ScatterChart;
import com.google.gwt.visualization.client.visualizations.corechart.PieChart.PieOptions;




/**
 * Google Visualization API in GWT demo.
 */
class CopyOfVisualAnalyzer implements EntryPoint {
  private final TabPanel tabPanel = new TabPanel();

  public void onModuleLoad() {
    VisualizationUtils.loadVisualizationApi(new Runnable() {
      public void run() {
        final VerticalPanel vp = new VerticalPanel();
        vp.getElement().getStyle().setPropertyPx("margin", 15);
        RootPanel.get().add(vp);
        vp.add(new Label("Visual Analyzer"));
        vp.add(tabPanel);
        tabPanel.setWidth("1100");
        tabPanel.setHeight("600");
        
        Annotater aa =new Annotater();
        tabPanel.add(aa.createPieChart(),"Ugur");
        /*tabPanel.add(createPieChart(), "Pie Chart");
        tabPanel.add(ColumnChartWidget(), "Column Chart");
        tabPanel.add(BarChartWidget(), "Bar Chart");
        tabPanel.add(LineChartWidget(), "Line Chart");
        
        tabPanel.add(ScatterChartWidget(), "Scatter Chart");
        tabPanel.add(new MotionGraph().getWidget(), "MotionChart");
        tabPanel.add(new OrgGraph().getWidget(), "Org Chart");
        tabPanel.add(new GaugeGraph().getWidget(), "Gauge Chart");*/
        
        
        
        
        tabPanel.selectTab(0);
      }}, PieChart.PACKAGE, Table.PACKAGE, MotionChart.PACKAGE, OrgChart.PACKAGE,Gauge.PACKAGE);
  }

  /**
   * Creates a table and a view and shows both next to each other.
   *
   * @return a panel with two tables.
   */
  

private Widget MotionChartWidget(){
	 Widget widget;
	 String STATE_STRING = "{"
	      + "\"duration\":{\"timeUnit\":\"D\",\"multiplier\":1},\"nonSelectedAlpha\":0.4,"
	      + "\"yZoomedDataMin\":300,\"yZoomedDataMax\":1200,\"iconKeySettings\":[],\"yZoomedIn\":false,"
	      + "\"xZoomedDataMin\":300,\"xLambda\":1,\"time\":\"1988-01-06\",\"orderedByX\":false,\"xZoomedIn\":false,"
	      + "\"uniColorForNonSelected\":false,\"sizeOption\":\"_UNISIZE\",\"iconType\":\"BUBBLE\","
	      + "\"playDuration\":15000,\"dimensions\":{\"iconDimensions\":[\"dim0\"]},\"xZoomedDataMax\":1200,"
	      + "\"yLambda\":1,\"yAxisOption\":\"2\",\"colorOption\":\"4\",\"showTrails\":true,\"xAxisOption\":\"2\","
	      + "\"orderedByY\":false}";
	 
	 String protocol = Window.Location.getProtocol();
	    if (protocol.startsWith("file")) {
	      widget = new HTML(
	          "<font color=\"blue\"><i>Note: Protocol is: "
	              + protocol
	              + ".  Note that this visualization does not work when loading the HTML from "
	              + "a local file. It works only when loading the HTML from a "
	              + "web server. </i></font>");
	      return null;
	    }

	   
	    int year, month, day;

	    com.google.gwt.visualization.client.visualizations.MotionChart.Options options =  com.google.gwt.visualization.client.visualizations.MotionChart.Options.create();
	    options.setHeight(300);
	    options.setWidth(600);
	    options.setState(STATE_STRING);
	    DataTable data = DataTable.create();
	    data.addRows(6);
	    data.addColumn(ColumnType.STRING, "Fruit");
	    data.addColumn(ColumnType.DATE, "Date");
	    data.addColumn(ColumnType.NUMBER, "Sales");
	    data.addColumn(ColumnType.NUMBER, "Expenses");
	    data.addColumn(ColumnType.STRING, "Location");
	    data.setValue(0, 0, "Apples");
	    data.setValue(0, 2, 1000);
	    data.setValue(0, 3, 300);
	    data.setValue(0, 4, "East");
	    data.setValue(1, 0, "Oranges");
	    data.setValue(1, 2, 950);
	    data.setValue(1, 3, 200);
	    data.setValue(1, 4, "West");
	    data.setValue(2, 0, "Bananas");
	    data.setValue(2, 2, 300);
	    data.setValue(2, 3, 250);
	    data.setValue(2, 4, "West");
	    data.setValue(3, 0, "Apples");
	    data.setValue(3, 2, 1200);
	    data.setValue(3, 3, 400);
	    data.setValue(3, 4, "East");
	    data.setValue(4, 0, "Oranges");
	    data.setValue(4, 2, 900);
	    data.setValue(4, 3, 150);
	    data.setValue(4, 4, "West");
	    data.setValue(5, 0, "Bananas");
	    data.setValue(5, 2, 788);
	    data.setValue(5, 3, 617);
	    data.setValue(5, 4, "West");

	    try {
	      data.setValue(0, 1, new Date(year = 1988 - 1900, month = 0, day = 1));
	      data.setValue(1, 1, new Date(year = 1988 - 1900, month = 0, day = 1));
	      data.setValue(2, 1, new Date(year = 1988 - 1900, month = 0, day = 1));
	      data.setValue(3, 1, new Date(year = 1988 - 1900, month = 1, day = 1));
	      data.setValue(4, 1, new Date(year = 1988 - 1900, month = 1, day = 1));
	      data.setValue(5, 1, new Date(year = 1988 - 1900, month = 1, day = 1));
	    } catch (JavaScriptException ex) {
	      GWT.log("Error creating data table - Date bug on mac?", ex);
	    }

	    final MotionChart motionChart = new MotionChart(data, options);
	    motionChart.addStateChangeHandler(new StateChangeHandler() {

	      @Override
	      public void onStateChange(StateChangeEvent event) {
	        String result = motionChart.getState();
	        GWT.log(result);
	      }
	    });

	     widget = motionChart;
	    
	    return widget;
	 
	 
 } 
 private Widget ScatterChartWidget() {
	    VerticalPanel result = new VerticalPanel();

	    com.google.gwt.visualization.client.visualizations.corechart.Options options = CoreChart.createOptions();
		options.setHeight(240);
	    options.setTitle("Discussion,Contribution and Listening ");
	    options.setWidth(400);

	    AxisOptions vAxisOptions = AxisOptions.create();
	    vAxisOptions.setMinValue(0);
	    vAxisOptions.setMaxValue(20);
	    options.setVAxisOptions(vAxisOptions);

	    DataTable data = Showcase.getSugarSaltAndCaloriesComparison();

	    ScatterChart viz = new ScatterChart(data, options);
	    Label status = new Label();
	    Label onMouseOverAndOutStatus = new Label();
	    viz.addSelectHandler(createSelectHandlerForScatterch(viz));
	//    viz.addReadyHandler(new ReadyDemo(status));
	  //  viz.addOnMouseOverHandler(new com.google.gwt.visualization.sample.visualizationshowcase.client.OnMouseOverDemo(onMouseOverAndOutStatus));
	   // viz.addOnMouseOutHandler(new com.google.gwt.visualization.sample.visualizationshowcase.client.OnMouseOutDemo(onMouseOverAndOutStatus));
	    result.add(status);
	    result.add(viz);
	    result.add(onMouseOverAndOutStatus);
	    return result;
	  }
  
  
  private Widget createOrgChart(){
	  VerticalPanel result = new VerticalPanel();
	  com.google.gwt.visualization.client.visualizations.OrgChart.Options options = com.google.gwt.visualization.client.visualizations.OrgChart.Options.create();
	  options.setSize(Size.LARGE);
	    options.setAllowCollapse(true);
	   // options.setNodeClass(css.nodeClass());
	    //options.setSelectedNodeClass(css.selectedNodeClass());

	    DataTable data = DataTable.create();
	    data.addColumn(ColumnType.STRING, "Name");
	    data.addColumn(ColumnType.STRING, "Manager");
	    data.addRows(5);
	    data.setValue(0, 0, "Mike");
	    data.setValue(1, 0, "Jim");
	    data.setValue(1, 1, "Mike");
	    data.setValue(2, 0, "Alice");
	    data.setValue(2, 1, "Mike");
	    data.setValue(3, 0, "Bob");
	    data.setValue(3, 1, "Jim");
	    data.setValue(4, 0, "Carol");
	    data.setValue(4, 1, "Bob");

	    OrgChart viz = new OrgChart(data, options);
	    Label status = new Label();
	   // viz.addSelectHandler(new SelectionDemo(viz, status));
	    result.add(status);
	    result.add(viz);
	   // result.add(onMouseOverAndOutStatus);
	    
	  return result;
  } 
  
  private Widget createDataView() {
    Panel panel = new HorizontalPanel();
    DataTable table = DataTable.create();

    /* create a table with 3 columns */
    table.addColumn(ColumnType.NUMBER, "x");
    table.addColumn(ColumnType.NUMBER, "x * x");
    table.addColumn(ColumnType.NUMBER, "sqrt(x)");
    table.addRows(10);
    for (int i = 0; i < table.getNumberOfRows(); i++) {
      table.setValue(i, 0, i);
      table.setValue(i, 1, i * i);
      table.setValue(i, 2, Math.sqrt(i));
    }
    /* Add original table */
    Panel flowPanel = new FlowPanel();
    panel.add(flowPanel);
    flowPanel.add(new Label("Original DataTable:"));
    Table chart = new Table();
    flowPanel.add(chart);
    chart.draw(table);

    flowPanel = new FlowPanel();
    flowPanel.add(new Label("DataView with columns 2 and 1:"));
    /* create a view on this table, with columns 2 and 1 */
    Table viewChart = new Table();
    DataView view = DataView.create(table);
    view.setColumns(new int[] {2, 1});
    flowPanel.add(viewChart);
    panel.add(flowPanel);
    viewChart.draw(view);

    return panel;
  }

  
  public Widget ColumnChartWidget() {
	    VerticalPanel result = new VerticalPanel();
	    com.google.gwt.visualization.client.visualizations.corechart.Options options = CoreChart.createOptions();
	    options.setHeight(240);
	    options.setTitle("Student Performance");
	    options.setWidth(400);

	    AxisOptions vAxisOptions = AxisOptions.create();
	    vAxisOptions.setMinValue(0);
	    vAxisOptions.setMaxValue(2000);
	    options.setVAxisOptions(vAxisOptions);
	    DataTable data = Showcase.getCompanyPerformance();
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
  
  private ArrowFormat createFormatter() {
    ArrowFormat.Options options = ArrowFormat.Options.create();
    options.setBase(1.5);
    return ArrowFormat.create(options);
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
  
  
  private SelectHandler createSelectHandlerForScatterch(final  ScatterChart chart) {
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
  
  private SelectHandler createSelectHandler(final PieChart chart) {
	    return new SelectHandler() {
	      @Override
	      public void onSelect(SelectEvent event) {
	        String message = "";
	        
	        // May be multiple selections.
	        JsArray<Selection> selections = chart.getSelections();

	        for (int i = 0; i < selections.length(); i++) {
	          // add a new line for each selection
	          message += i == 0 ? "" : "\n";
	          
	          Selection selection = selections.get(i);

	          if (selection.isCell()) {
	            // isCell() returns true if a cell has been selected.
	            
	            // getRow() returns the row number of the selected cell.
	            int row = selection.getRow();
	            // getColumn() returns the column number of the selected cell.
	            int column = selection.getColumn();
	            message += "cell " + row + ":" + column + " selected";
	          } else if (selection.isRow()) {
	            // isRow() returns true if an entire row has been selected.
	            
	            // getRow() returns the row number of the selected row.
	            int row = selection.getRow();
	            
	            message += "row " + row + " selected";
	          } else {
	            // unreachable
	            message += "Pie chart selections should be either row selections or cell selections.";
	            message += "  Other visualizations support column selections as well.";
	          }
	        }
	        
	        Window.alert(message);
	      }
	    };
	  }

  

  /**
   * Creates a pie chart visualization.
   *
   * @return panel with pie chart.
   */
  
  
  
  
  private Widget createPieChart() {
    /* create a datatable */
    DataTable data = DataTable.create();
    data.addColumn(ColumnType.STRING, "Task");
    data.addColumn(ColumnType.NUMBER, "Hours per Day");
    data.addRows(5);
    data.setValue(0, 0, "Discussing");
    data.setValue(0, 1, 11);
    data.setValue(1, 0, "Chatting");
    data.setValue(1, 1, 2);
    data.setValue(2, 0, "Contributing");
    data.setValue(2, 1, 2);
    data.setValue(3, 0, "Referencing");
    data.setValue(3, 1, 2);
    data.setValue(4, 0, "Listening");
    data.setValue(4, 1, 7);

    /* create pie chart */

    PieOptions options = PieChart.createPieOptions();
    options.setWidth(500);
    options.setHeight(400);
    options.set3D(true);
   
    options.setTitle("Students' Activities on Map");
    
    PieChart pie = new PieChart(data, options);
    
    
    pie.addSelectHandler(createSelectHandler(pie));
    
    return  pie;
    //return new PieChart(data, options);
  }
  
  
 private DataTable getDailyActivities() {
	    DataTable data = DataTable.create();
	    data.addColumn(ColumnType.STRING, "Task");
	    data.addColumn(ColumnType.NUMBER, "Hours per Day");
	    data.addRows(5);
	    data.setValue(0, 0, "Discussing");
	    data.setValue(0, 1, 11);
	    data.setValue(1, 0, "Chatting");
	    data.setValue(1, 1, 2);
	    data.setValue(2, 0, "Contributing");
	    data.setValue(2, 1, 2);
	    data.setValue(3, 0, "Referencing");
	    data.setValue(3, 1, 2);
	    data.setValue(4, 0, "Listening");
	    data.setValue(4, 1, 7);
	    return data;
	  }

  
  private Widget createGaugeChart(){
	  
	  
	  Gauge.Options options = Gauge.Options.create();
	    options.setWidth(400);
	    options.setHeight(240);
	    options.setGaugeRange(0, 24);
	    options.setGreenRange(0, 6);
	    options.setYellowRange(6, 12);
	    options.setRedRange(12, 24);

	    DataTable data = getDailyActivities();
	    
	    Gauge  widget = new Gauge(data, options);
	    
	    return widget;
	  
	  
  }

  /**
   * Creates a table visualization from a spreadsheet.
   *
   * @return panel with a table.
   */
  private Widget createTable() {
    final String noSelectionString = "<i>No rows selected.</i>";
    final Panel panel = new FlowPanel();
    final HTML label = new HTML(noSelectionString);
    panel.add(new HTML("<h2>Table visualization with selection support</h2>"));
    panel.add(label);
    // Read data from spreadsheet
    String dataUrl = "http://spreadsheets.google.com/tq?key=prll1aQH05yQqp_DKPP9TNg&pub=1";
    Query.Options queryOptions = Query.Options.create();
    queryOptions.setSendMethod(SendMethod.SCRIPT_INJECTION);
    Query query = Query.create(dataUrl, queryOptions);
    query.send(new Callback() {

      public void onResponse(QueryResponse response) {
        if (response.isError()) {
          Window.alert("Error in query: " + response.getMessage() + ' '
              + response.getDetailedMessage());
          return;
        }

        final Table viz = new Table();
        panel.add(viz);
        Options options = Table.Options.create();
        options.setShowRowNumber(true);
        DataTable dataTable = response.getDataTable();
        ArrowFormat formatter = createFormatter();
        formatter.format(dataTable, 1);
        viz.draw(dataTable, options);

        viz.addSelectHandler(new SelectHandler() {
          @Override
          public void onSelect(SelectEvent event) {
            StringBuffer b = new StringBuffer();
            Table table = viz;
            JsArray<Selection> s = table.getSelections();
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
            if (b.length() == 0) {
              label.setHTML(noSelectionString);
            } else {
              label.setHTML("<i>Selection changed to" + b.toString() + "<i>");
            }
          }
        });
      }
    });
    return panel;
  }
}
