package de.uds.MonitorInterventionMetafora.client.display;

import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.google.gwt.user.client.ui.PopupPanel;

import de.uds.MonitorInterventionMetafora.client.monitor.datamodel.PropertyGridModel;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;

public class PlainTextDisplay extends VerticalPanel{

	CfAction action;
	Window popupPanel;
	public PlainTextDisplay(CfAction action){
		popupPanel=new Window();
		popupPanel.setWidth("560");
		popupPanel.setHeight("670");
		popupPanel.setModal(true);
		//popupPanel.setResizable(false);
		popupPanel.setPagePosition(180, 90);
		
		
		
	
		this.action=action;
		this.setWidth("600");
		this.setHeight("550");
		ActionDisplay actionDisplay=new ActionDisplay(action);
		actionDisplay.setHeader("Indicator Info");
		
		TextArea textArea = new TextArea();
		textArea.setValue(action.toString());
		textArea.setWidth("600");
		textArea.setHeight("550");
		
		this.add(textArea);

		popupPanel.add(this);
		
		popupPanel.show();

	
	
	}
	
}
