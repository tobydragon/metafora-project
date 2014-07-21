package de.uds.MonitorInterventionMetafora.client.monitor;

import java.util.List;

import com.allen_sauer.gwt.log.client.Log;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.button.ButtonGroup;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.extjs.gxt.ui.client.widget.form.SimpleComboValue;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;

import de.uds.MonitorInterventionMetafora.client.display.DisplayUtil;
import de.uds.MonitorInterventionMetafora.client.urlparameter.UrlParameterConfig;

public class AnalysisControlsButtonGroup extends ButtonGroup{

	final ClientMonitorDataModelUpdater updater;
	
	SimpleComboBox<String> groupIdChooser;
	private Button analyzeButton;
	private Button clearAnalysisButton;
	
	public AnalysisControlsButtonGroup( final ClientMonitorDataModelUpdater updater) {
		//send in number of columns
		super(3);
		this.updater = updater;
		
		groupIdChooser = new SimpleComboBox<String>();
		groupIdChooser.setEditable(false);
		groupIdChooser.setTriggerAction(TriggerAction.ALL);

		analyzeButton = new Button(); 
		analyzeButton.setToolTip("Analyze");
		analyzeButton.setText("Analyze");
		analyzeButton.setBorders(true);
		analyzeButton.addSelectionListener(new SelectionListener<ButtonEvent>() {  
	        @Override  
	        public void componentSelected(ButtonEvent ce) {  
	        	Log.info("UpdaterToolbar: Analyze button clicked");
	        	SimpleComboValue<String> value = AnalysisControlsButtonGroup.this.groupIdChooser.getValue();
	        	if (value != null && value.getValue() != null &&  value.getValue() != ""){
		        	Log.debug("[UpdaterToolbar.analyzeButton] Updating with chosen groupId: " +  value.getValue());
		        	AnalysisControlsButtonGroup.this.updater.analyzeGroup( value.getValue());
	        	}
	        	else {
	        		String defaultGroupId = UrlParameterConfig.getInstance().getGroupId();
		        	if (defaultGroupId != null && defaultGroupId != ""){
		        		Log.debug("[UpdaterToolbar.analyzeButton] Updating with default groupId: " + defaultGroupId);
		        		AnalysisControlsButtonGroup.this.updater.analyzeGroup(defaultGroupId);
		        	}
		        	else {
		        		Log.warn("[UpdaterToolbar.analyzeButton] No action taken, no group selected to analyze");
		        		DisplayUtil.postNotificationMessage("No analysis done, you must select a group to analyze");
		        	}
	        	}
	        }  
	      });
		
		clearAnalysisButton = new Button(); 
		clearAnalysisButton.setBorders(true);
		clearAnalysisButton.setToolTip("Clear All Tips");
		clearAnalysisButton.setText("Clear All Tips");
		clearAnalysisButton.addSelectionListener(new SelectionListener<ButtonEvent>() {  
	        @Override  
	        public void componentSelected(ButtonEvent ce) {  
	        	Log.info("UpdaterToolbar: Clear Analysis button clicked");
        		AnalysisControlsButtonGroup.this.updater.clearAllAnalysis();
        		DisplayUtil.postNotificationMessage("All Tips for all users cleared");
	        }  
	      });
	
	  this.add(groupIdChooser);
	  this.add(analyzeButton);
	  this.add(clearAnalysisButton);
	}
	
	public void updateView(List<String> groups) {
		if (groups != null){
			groupIdChooser.removeAll();
			groupIdChooser.add(groups);
		}
	}
	
}
