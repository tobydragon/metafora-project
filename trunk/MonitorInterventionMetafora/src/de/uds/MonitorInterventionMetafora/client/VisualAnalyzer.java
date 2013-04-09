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
package de.uds.MonitorInterventionMetafora.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.corechart.PieChart;

import de.uds.MonitorInterventionMetafora.client.communication.CommunicationService;
import de.uds.MonitorInterventionMetafora.client.communication.CommunicationServiceAsync;
import de.uds.MonitorInterventionMetafora.client.feedback.FeedbackPanelContainer;
import de.uds.MonitorInterventionMetafora.client.monitor.MonitorViewPanel;
import de.uds.MonitorInterventionMetafora.client.urlparameter.UrlParameterConfig;
//migen specific tools commented out for now
//import de.uds.MonitorInterventionMetafora.client.migen.TeacherTools;
import de.uds.MonitorInterventionMetafora.client.urlparameter.UrlParameterConfig.UserType;


class VisualAnalyzer implements EntryPoint {

	  public void onModuleLoad() {
		  //TODO: Check this, it seems like it only waits for pieChart, what about BarChart, etc.?
		  VisualizationUtils.loadVisualizationApi(new Runnable() {
	      public void run() {
	    	  
	    	 CommunicationServiceAsync commServiceServlet = GWT.create(CommunicationService.class);

	    	 MainContainerTabPanel _mainPanel=new MainContainerTabPanel();

	    	 //If userType is not wizardy then monitoring is not needed
	    	 if (UrlParameterConfig.getInstance().getUserType() != UserType.METAFORA_USER) {
	    	  MonitorViewPanel _monitoringContainer=new MonitorViewPanel(commServiceServlet);
	    	  //migen specific tools commented out for now
	    	  //TeacherTools _migenContainer=new TeacherTools(monitoringViewServiceServlet);
	    	  //_mainPanel.addTab("Migen", _migenContainer,false);
	    	  _mainPanel.addTab("Monitoring", _monitoringContainer,false);
	    	 } 
	    	 
	    	 FeedbackPanelContainer _feedbackPanelContainer=new  FeedbackPanelContainer(commServiceServlet);
	    	 _mainPanel.addTab("Messaging", _feedbackPanelContainer,false);
	    	  
	    	 RootPanel.get().add(_mainPanel);
	
	    	
	      }}, PieChart.PACKAGE);
	  }
}
