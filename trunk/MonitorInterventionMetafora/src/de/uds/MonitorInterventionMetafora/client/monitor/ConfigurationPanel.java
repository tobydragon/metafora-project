package de.uds.MonitorInterventionMetafora.client.monitor;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.Popup;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.google.gwt.user.client.ui.ClickListener;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

import de.uds.MonitorInterventionMetafora.client.communication.CommunicationServiceAsync;
import de.uds.MonitorInterventionMetafora.client.monitor.datamodel.ClientMonitorDataModel;
import de.uds.MonitorInterventionMetafora.client.monitor.filter.FilterListPanel;

public class ConfigurationPanel  extends Window  {

	
	
	
	public ConfigurationPanel(ClientMonitorDataModel _maintenance, ClientMonitorController controller, CommunicationServiceAsync serverlet){
		
		// Set the dialog box's caption.
	     // setText("My First Dialog");

	      // DialogBox is a SimplePanel, so you have to set its widget property to
	      // whatever you want its contents to 
		
		
	     
	     // this.
	    
	      FilterListPanel configurationPanel=new FilterListPanel(_maintenance, controller, serverlet,true);
	      //configurationPanel.setHeight(470);
	      this.add(configurationPanel);
	      this.setWidth("1000");
	      this.setHeight("405");
	      this.setPagePosition(12,15);
	      
	      configurationPanel.setExpanded(true);
	      configurationPanel.setCollapsible(false);
	   
	      
	      
	     // this.center();
	      
	      
	   
	      //setWidget(ok);
		
		
	}
}
