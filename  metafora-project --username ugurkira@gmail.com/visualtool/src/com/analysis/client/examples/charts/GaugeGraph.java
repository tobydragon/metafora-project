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
package com.analysis.client.examples.charts;

import com.analysis.client.examples.charts.LeftTabPanel.WidgetProvider;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.visualizations.Gauge;

/**
 * Demo for Gauge visualization.
 */
public class GaugeGraph implements LeftTabPanel.WidgetProvider {
  private Widget widget;

  public GaugeGraph() {
    Gauge.Options options = Gauge.Options.create();
    options.setWidth(400);
    options.setHeight(240);
    options.setGaugeRange(0, 24);
    options.setGreenRange(0, 6);
    options.setYellowRange(6, 12);
    options.setRedRange(12, 24);

    DataTable data = getDailyActivities();
    
    widget = new Gauge(data, options);
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

  public Widget getWidget() {
    return widget;
  }
}
