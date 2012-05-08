package de.uds.MonitorInterventionMetafora.client.monitor.dataview.table;

import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.GridEvent;
import com.extjs.gxt.ui.client.widget.grid.GridSelectionModel;
import com.google.gwt.user.client.ui.PopupPanel;

import de.uds.MonitorInterventionMetafora.client.PopupCfActionDisplay;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;

public class TableRowDisplaySelectionListener extends GridSelectionModel<CfActionGridRow>{
	
	public void handleEvent(BaseEvent e){
		System.out.println("Event: ");
		
		if (e instanceof GridEvent<?>){
			GridEvent<CfActionGridRow> ge = (GridEvent<CfActionGridRow>) e; 
			CfAction indicator = ge.getModel().getIndicator();
			

			new PopupCfActionDisplay(indicator);
		}
    	

	}

}
