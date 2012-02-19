package com.analysis.client.view.widgets;

import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.Label;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Button;

public class ExtendedFilterItem  extends HorizontalPanel{
	
	public ExtendedFilterItem(String _property,String _value){
		
		Label filteritemlbl=new Label(_property+": "+_value);
		
		    DOM.setStyleAttribute(filteritemlbl.getElement(), "textAlign", "center");
	        DOM.setStyleAttribute(filteritemlbl.getElement(), "backgroundColor", "#ddd");
	        DOM.setStyleAttribute(filteritemlbl.getElement(), "border", "1px solid #000");
	        //this.add(filteritemlbl);
		//this.add(new Button("x"));
		
	}

}
