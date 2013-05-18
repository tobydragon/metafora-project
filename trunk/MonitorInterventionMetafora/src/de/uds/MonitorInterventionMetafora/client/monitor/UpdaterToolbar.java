package de.uds.MonitorInterventionMetafora.client.monitor;

import java.util.List;

import com.allen_sauer.gwt.log.client.Log;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.CheckBox;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.extjs.gxt.ui.client.widget.form.SimpleComboValue;
import com.extjs.gxt.ui.client.widget.toolbar.SeparatorToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.user.client.Timer;

import de.uds.MonitorInterventionMetafora.client.communication.CommunicationServiceAsync;
import de.uds.MonitorInterventionMetafora.client.display.DisplayUtil;
import de.uds.MonitorInterventionMetafora.client.logger.ComponentType;
import de.uds.MonitorInterventionMetafora.client.logger.Logger;
import de.uds.MonitorInterventionMetafora.client.logger.UserActionType;
import de.uds.MonitorInterventionMetafora.client.logger.UserLog;
import de.uds.MonitorInterventionMetafora.client.monitor.datamodel.ClientMonitorDataModel;
import de.uds.MonitorInterventionMetafora.client.resources.Resources;
import de.uds.MonitorInterventionMetafora.client.urlparameter.UrlParameterConfig;

public class UpdaterToolbar extends ToolBar{
	 
	private CheckBox autoRefresh;
	private Button refreshButton;
	private Button analyzeButton;
	private Button configurationButton;
	private ConfigurationPanel configurationPanel;
	
	SimpleComboBox<String> groupIdChooser;
	
	final ClientMonitorDataModelUpdater updater;

	public UpdaterToolbar(ClientMonitorDataModelUpdater updater,final ClientMonitorDataModel _maintenance, final ClientMonitorController controller, final CommunicationServiceAsync serverlet){
		this.updater = updater;
		
		autoRefresh = new CheckBox();
	    autoRefresh.setBoxLabel("Auto Refresh");
		autoRefresh.setValue(false);
		configurationPanel= new ConfigurationPanel(_maintenance,controller,serverlet);
		
		autoRefresh.addListener(Events.Change, 
			new Listener<BaseEvent>() {
	        	public void handleEvent(BaseEvent be) {
	        		if (autoRefresh.getValue()) {
	        			
	        			UserLog userActionLog=new UserLog();
	                	userActionLog.setComponentType(ComponentType.UPDATER_TOOLBAR);
	                	userActionLog.setDescription("Auto Refresh is enabled!");
	                	userActionLog.setTriggeredBy(ComponentType.UPDATER_TOOLBAR);
	                	userActionLog.setUserActionType(UserActionType.AUTO_REFRESH_ENABLED);
	                	Logger.getLoggerInstance().log(userActionLog);
	        			
	        			UpdaterToolbar.this.updater.startUpdates();
	        		}
	        		else {
	        			
	        			UserLog userActionLog=new UserLog();
	                	userActionLog.setComponentType(ComponentType.UPDATER_TOOLBAR);
	                	userActionLog.setDescription("Auto Refresh is disabled!");
	                	userActionLog.setTriggeredBy(ComponentType.UPDATER_TOOLBAR);
	                	userActionLog.setUserActionType(UserActionType.AUTO_REFRESH_DISABLED);
	                	Logger.getLoggerInstance().log(userActionLog);
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
	        	
	        	//only enable the button every few seconds
	        	Timer t = new Timer() {
	        	      public void run() {
	        	    	  refreshButton.setEnabled(true);
	        	      }
	        	};
	        	t.schedule(5000);
	        	
	        	UpdaterToolbar.this.updater.getUpdate();
	        	refreshButton.setEnabled(false);
	        	
	        }  
	      });
		
		groupIdChooser = new SimpleComboBox<String>();
		groupIdChooser.setEditable(false);
		groupIdChooser.setTriggerAction(TriggerAction.ALL);
		
		
		
		
		analyzeButton = new Button(); 
		analyzeButton.setToolTip("Analyze");
		analyzeButton.setText("Analyze");
		analyzeButton.addSelectionListener(new SelectionListener<ButtonEvent>() {  
	        @Override  
	        public void componentSelected(ButtonEvent ce) {  
	        	Log.info("UpdaterToolbar: Analyze button clicked");
	        	SimpleComboValue<String> value = UpdaterToolbar.this.groupIdChooser.getValue();
	        	if (value != null && value.getValue() != null &&  value.getValue() != ""){
		        	Log.debug("[UpdaterToolbar.analyzeButton] Updating with chosen groupId: " +  value.getValue());
	        		UpdaterToolbar.this.updater.analyzeGroup( value.getValue());
	        	}
	        	else {
	        		String defaultGroupId = UrlParameterConfig.getInstance().getGroupId();
		        	if (defaultGroupId != null && defaultGroupId != ""){
		        		Log.debug("[UpdaterToolbar.analyzeButton] Updating with default groupId: " + defaultGroupId);
		        		UpdaterToolbar.this.updater.analyzeGroup(defaultGroupId);
		        	}
		        	else {
		        		Log.warn("[UpdaterToolbar.analyzeButton] No action taken, no group selected to analyze");
		        		DisplayUtil.postNotificationMessage("No analysis done, you must select a group to analyze");
		        	}
	        	}
	        }  
	      });
		
		configurationButton = new Button(); 
		configurationButton.setToolTip("Configuration");
		configurationButton.setIcon(Resources.ICONS.configuration());
		configurationButton.setWidth(25);
		configurationButton.addSelectionListener(new SelectionListener<ButtonEvent>() {  
	        @Override  
	        public void componentSelected(ButtonEvent ce) {  
	        	configurationPanel.show();
	        }  
	      });  


	    this.setWidth(600);
	    this.add(autoRefresh);
	    this.add(refreshButton);
	    
	    this.add(new SeparatorToolItem());
	    this.add(groupIdChooser);
	    this.add(analyzeButton);
	    
	    this.add(new SeparatorToolItem());
	    this.add(configurationButton);
	    
	}
	
	public void setAutoRefresh(boolean setting){
		autoRefresh.setValue(setting);
	}

	public void updateView(List<String> groups) {
		if (groups != null){
			groupIdChooser.add(groups);
		}
		
	}

}
