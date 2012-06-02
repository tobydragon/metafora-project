package de.uds.MonitorInterventionMetafora.client.monitor;

import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedListener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.Label;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.CheckBox;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;

import de.uds.MonitorInterventionMetafora.client.communication.CommunicationServiceAsync;
import de.uds.MonitorInterventionMetafora.client.monitor.datamodel.ClientMonitorDataModel;
import de.uds.MonitorInterventionMetafora.client.monitor.datamodel.PropertyComboBoxItemModel;
import de.uds.MonitorInterventionMetafora.client.monitor.dataview.GroupedDataViewPanel;
import de.uds.MonitorInterventionMetafora.client.resources.Resources;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionPropertyRule;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionPropertyRuleSelectorModel;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionPropertyRuleSelectorModelType;

public class UpdaterToolbar extends ToolBar{
	 
	private CheckBox autoRefresh;
	private Button refreshButton;
	private Button configurationButton;
	
	final ClientMonitorDataModelUpdater updater;

	public UpdaterToolbar(ClientMonitorDataModelUpdater updater,final ClientMonitorDataModel _maintenance, final ClientMonitorController controller, final CommunicationServiceAsync serverlet){
		this.updater = updater;
		
		autoRefresh = new CheckBox();
	    autoRefresh.setBoxLabel("Auto Refresh");
		autoRefresh.setValue(false);
		
		autoRefresh.addListener(Events.Change, 
			new Listener<BaseEvent>() {
	        	public void handleEvent(BaseEvent be) {
	        		if (autoRefresh.getValue()) {
	        			System.out.println("autoRefresh unChecked");
	        			UpdaterToolbar.this.updater.startUpdates();
	        		}
	        		else {
	        			System.out.println("autoRefresh unChecked");
	        			UpdaterToolbar.this.updater.stopUpdates();
	        		}
	        	}
			});
		
		refreshButton = new Button(); 
		refreshButton.setToolTip("Refresh");
		refreshButton.setIcon(Resources.ICONS.refresh());
		refreshButton.addSelectionListener(new SelectionListener<ButtonEvent>() {  
	        @Override  
	        public void componentSelected(ButtonEvent ce) {  
	        	System.out.println("refreshClicked");
	        	UpdaterToolbar.this.updater.getUpdate();
	        }  
	      });
		
		
		
		configurationButton = new Button(); 
		configurationButton.setToolTip("Configuration");
		configurationButton.setIcon(Resources.ICONS.configuration());
		configurationButton.setWidth(25);
		configurationButton.addSelectionListener(new SelectionListener<ButtonEvent>() {  
	        @Override  
	        public void componentSelected(ButtonEvent ce) {  
	        	ConfigurationPanel panel=new ConfigurationPanel(_maintenance,controller,serverlet);
	        }  
	      });  


	    this.setWidth(600);
	    this.add(autoRefresh);
	    this.add(refreshButton);
	    this.add(configurationButton);
	    
	}
	
	public void setAutoRefresh(boolean setting){
		autoRefresh.setValue(setting);
	}

}
