package de.uds.MonitorInterventionMetafora.client.monitor;

import java.util.List;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.toolbar.FillToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.SeparatorToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;

import de.uds.MonitorInterventionMetafora.client.communication.CommunicationServiceAsync;
import de.uds.MonitorInterventionMetafora.client.monitor.datamodel.ClientMonitorDataModel;
import de.uds.MonitorInterventionMetafora.client.monitor.filter.FilterListPanel;
import de.uds.MonitorInterventionMetafora.client.resources.Resources;

public class UpdaterToolbar extends ToolBar{
	
	DataUpdateButtonGroup updateButtons;
	AnalysisControlsButtonGroup analysisButtons;
	
	private Button configurationButton;
	private PopupWindow configurationWindow;
	
	final ClientMonitorDataModelUpdater updater;

	public UpdaterToolbar(ClientMonitorDataModelUpdater updater,final ClientMonitorDataModel _maintenance, final ClientMonitorController controller, final CommunicationServiceAsync serverlet){
		this.updater = updater;
		
		this.setWidth(600);
		updateButtons = new DataUpdateButtonGroup(updater);
		add(updateButtons);
		
		add(new SeparatorToolItem());
		
		analysisButtons = new AnalysisControlsButtonGroup(updater);
	    add(analysisButtons);

	    add(new FillToolItem());
	 
	    FilterListPanel configPanel=new FilterListPanel(_maintenance, controller, serverlet, true);
		configurationWindow= new PopupWindow(configPanel);
		
		configurationButton = new Button(); 
		configurationButton.setToolTip("Configuration");
		configurationButton.setIcon(Resources.ICONS.configuration());
		configurationButton.setWidth(25);
		configurationButton.addSelectionListener(new SelectionListener<ButtonEvent>() {  
	        @Override  
	        public void componentSelected(ButtonEvent ce) {  
	        	configurationWindow.show();
	        }  
	    }); 
		add(configurationButton);
	}

	public void updateView(List<String> groups) {
		analysisButtons.updateView(groups);
		
	}
}
