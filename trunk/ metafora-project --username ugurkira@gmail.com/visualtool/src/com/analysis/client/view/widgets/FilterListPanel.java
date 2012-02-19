package com.analysis.client.view.widgets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.analysis.client.view.grids.ExtendedFilterGrid;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ScrollPanel;


public class FilterListPanel extends ContentPanel {
	
	
	Map<String, String> _filterList;
	

	public FilterListPanel(String _property,String _value){
		
		this.setCollapsible(true);
		this.setHeading("Filter Options");
		
		 DOM.setStyleAttribute(this.getElement(), "border", "1px solid #000");
	     DOM.setStyleAttribute(this.getElement(), "borderBottom", "0");
	     this.setHeight("240px");	
		_filterList=new HashMap<String,String>();
		_filterList.put(_property, _value);
		//ExtendedFilterItem efi=new ExtendedFilterItem(_property,_value);
		//this.add(efi);
		ExtendedFilterGrid ef=new ExtendedFilterGrid("");
		ExtendedSaveFilterSet saveFilterSet=new ExtendedSaveFilterSet();
		
		this.add(ef);
		this.add(saveFilterSet);
	}
	
	
	
	public  void addFilter(String _property,String _value){
		
		ExtendedFilterItem efi=new ExtendedFilterItem(_property,_value);
		this.add(efi);
		_filterList.put(_property, _value);		
	}
	
	

}
