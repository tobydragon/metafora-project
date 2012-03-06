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
package de.uds.visualizer.client;






import com.google.gwt.core.client.EntryPoint;


import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.corechart.PieChart;

import de.uds.visualizer.client.view.containers.MainContainer;



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
