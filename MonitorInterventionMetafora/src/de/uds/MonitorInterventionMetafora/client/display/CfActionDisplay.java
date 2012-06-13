package de.uds.MonitorInterventionMetafora.client.display;

import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.Window;
import com.google.gwt.user.client.ui.PopupPanel;

import de.uds.MonitorInterventionMetafora.client.monitor.datamodel.PropertyGridModel;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;

public class CfActionDisplay extends VerticalPanel{

	CfAction action;
	Window popupPanel;
	public CfActionDisplay(CfAction action){
		popupPanel=new Window();
		popupPanel.setWidth("560");
		popupPanel.setHeight("670");
		popupPanel.setModal(true);
		//popupPanel.setResizable(false);
		popupPanel.setPagePosition(150,60);
		
		
		
	
	this.action=action;
	this.setWidth("600");
	this.setHeight("550");
	ActionDisplay actionDisplay=new ActionDisplay(action);
	actionDisplay.setHeader("Indicator Info");
	this.add(actionDisplay);
	if(action.getCfContent()!=null){
	PropertyDisplay contentProperties=new PropertyDisplay(action.getCfContent().getProperties(),"Indicator Content",new PropertyGridModel("Description",action.getCfContent().getDescription()));
	this.add(contentProperties);
	}
	if(action.getCfObjects()!=null && action.getCfObjects().size()>0){
	CfObjectDisplay objectDisplay=new CfObjectDisplay(action.getCfObjects());
	this.add(objectDisplay);
	}
	

	
	popupPanel.add(this);
	
	popupPanel.show();

	
	
	}
	
}
