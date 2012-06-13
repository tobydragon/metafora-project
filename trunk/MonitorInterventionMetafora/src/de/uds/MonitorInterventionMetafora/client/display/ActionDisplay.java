package de.uds.MonitorInterventionMetafora.client.display;


import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.Label;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.sencha.gxt.widget.core.client.form.FieldLabel;

import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.utils.GWTUtils;

public class ActionDisplay extends ContentPanel{

	CfAction action;
	TextField<String>  actionTime;
	TextField<String>  actionType;  
	TextField<String> actionClassification;
	
	public ActionDisplay(CfAction action) {
		actionTime=new TextField<String>();
		actionTime.setValue(GWTUtils.getFullDate(action.getTime()));
		actionTime.setReadOnly(true);
		actionType=new TextField<String>();
		actionType.setValue(action.getCfActionType().getType());
		actionType.setReadOnly(true);
		actionClassification=new TextField<String>();
		actionClassification.setValue(action.getCfActionType().getClassification());
		actionClassification.setReadOnly(true);
		
		
		int w = 380;
		actionTime.setWidth(w);
		actionType.setWidth(w);
		actionClassification.setWidth(w);
		
		
		 this.add(new FieldLabel(actionTime, "Indicator Time"));
		 this.add(new FieldLabel(actionType, "Indicator Type"));
		 this.add(new FieldLabel(actionClassification, "Classification"));
		 this.setWidth(561);
		
	
		
		
	}
	
	void setHeader(String header){
		
		this.setHeading(header);
	}
	
	
}
