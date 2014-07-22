package de.uds.MonitorInterventionMetafora.client.monitor;

import com.allen_sauer.gwt.log.client.Log;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.button.ButtonGroup;
import com.extjs.gxt.ui.client.widget.form.Radio;
import com.extjs.gxt.ui.client.widget.form.RadioGroup;
import com.google.gwt.user.client.Timer;

public class DataUpdateButtonGroup extends ButtonGroup{
	private ClientMonitorDataModelUpdater controller;
	
	private Radio localUpdateRadioButton;
	private Radio serverUpdateRadioButton;
	private RadioGroup radioGroup;
	private Button updateButton;
	
	private Window uploadWindow;
	
//	private CheckBox autoRefresh;
//	private Button refreshButton;
	
	public DataUpdateButtonGroup(ClientMonitorDataModelUpdater controller) {
		//3 columns (for 3 components)
		super(3);
		this.controller = controller;
		
		updateButton = new Button("Update Data From: ");
		updateButton.setBorders(true);
		this.add(updateButton);
		
		localUpdateRadioButton= new Radio();
		localUpdateRadioButton.setBoxLabel("Local File");
		localUpdateRadioButton.setValue(true);
		serverUpdateRadioButton = new Radio();
		serverUpdateRadioButton.setBoxLabel("Server");
		radioGroup = new RadioGroup();
		radioGroup.add(localUpdateRadioButton);
		radioGroup.add(serverUpdateRadioButton);
		this.add(radioGroup);
		
		
		uploadWindow = buildUploadWindow(controller);
		
		updateButton.addSelectionListener(new SelectionListener<ButtonEvent>(){
			public void componentSelected(ButtonEvent ce){
				boolean flag = DataUpdateButtonGroup.this.localUpdateRadioButton.getValue();
				//if getting data from file
				if (flag == true){
					uploadWindow.show();
				}
				else{
					Log.debug("Update button clicked with server radio button selected");
					Timer t = new Timer() {
		        	      public void run() {
		        	    	  updateButton.setEnabled(true);
		        	      }
		        	};
		        	t.schedule(5000);
		        	
		        	DataUpdateButtonGroup.this.controller.getUpdate();
		        	updateButton.setEnabled(false);
				}	
			}	
		});
		
		
		
	}
	
	private static Window buildUploadWindow(ClientMonitorDataModelUpdater controller){
		UploadFormPanel uploadPanel = new UploadFormPanel(controller);
		
		Window window = new Window();
		window.add(uploadPanel);
		window.setWidth(150);
		window.setHeight(100);
		window.setPagePosition(12,15);
		window.setModal(true);
		
		//So uploadPanel can close its window when submitted
		uploadPanel.setContainingWindow(window);
		return window;
	}
	
	//commented out until auto refresh is needed
//	public void initAutoRefresh(){
//		autoRefresh = new CheckBox();
//	    autoRefresh.setBoxLabel("Auto Refresh");
//		autoRefresh.setValue(false);
//		
//		autoRefresh.addListener(Events.Change, 
//			new Listener<BaseEvent>() {
//	        	public void handleEvent(BaseEvent be) {
//	        		if (autoRefresh.getValue()) {
//	        			
//	        			UserLog userActionLog=new UserLog();
//	                	userActionLog.setComponentType(ComponentType.UPDATER_TOOLBAR);
//	                	userActionLog.setDescription("Auto Refresh is enabled!");
//	                	userActionLog.setTriggeredBy(ComponentType.UPDATER_TOOLBAR);
//	                	userActionLog.setUserActionType(UserActionType.AUTO_REFRESH_ENABLED);
//	                	Logger.getLoggerInstance().log(userActionLog);
//	        			
//	                	DataUpdateButtonGroup.this.controller.startUpdates();
//	        		}
//	        		else {
//	        			
//	        			UserLog userActionLog=new UserLog();
//	                	userActionLog.setComponentType(ComponentType.UPDATER_TOOLBAR);
//	                	userActionLog.setDescription("Auto Refresh is disabled!");
//	                	userActionLog.setTriggeredBy(ComponentType.UPDATER_TOOLBAR);
//	                	userActionLog.setUserActionType(UserActionType.AUTO_REFRESH_DISABLED);
//	                	Logger.getLoggerInstance().log(userActionLog);
//	                	DataUpdateButtonGroup.this.controller.stopUpdates();
//	        		}
//	        	}
//			});
//		
//		refreshButton = new Button(); 
//		refreshButton.setToolTip("Refresh");
//		refreshButton.setIcon(Resources.ICONS.refresh());
//		refreshButton.addSelectionListener(new SelectionListener<ButtonEvent>() {  
//	        @Override  
//	        public void componentSelected(ButtonEvent ce) {  
//	        	System.out.println("refreshClicked");
//	        	
//	        	//This prevents users from clicking the button multiple times while it loads
//	        	//only enable the button every few seconds
//	        	Timer t = new Timer() {
//	        	      public void run() {
//	        	    	  refreshButton.setEnabled(true);
//	        	      }
//	        	};
//	        	t.schedule(5000);
//	        	
//	        	
//	        	DataUpdateButtonGroup.this.controller.getUpdate();
//	        	refreshButton.setEnabled(false);
//	        	
//	        }  
//	      });
//	}

}
