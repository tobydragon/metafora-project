package de.uds.MonitorInterventionMetafora.client.view.widgets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ScrollPanel;

import de.uds.MonitorInterventionMetafora.client.view.grids.ExtendedFilterGrid;


public class FilterListPanel extends ContentPanel {
	
	
	Map<String, String> _filterList;
	

	public FilterListPanel(){
		
		
		this.setCollapsible(true);
		this.setHeading("Filter Options");
		
		 DOM.setStyleAttribute(this.getElement(), "border", "1px solid #000");
	     DOM.setStyleAttribute(this.getElement(), "borderBottom", "0");
	     this.setHeight("200px");	
		_filterList=new HashMap<String,String>();
		
		//ExtendedFilterItem efi=new ExtendedFilterItem(_property,_value);
		//this.add(efi);
		ExtendedFilterGrid ef=new ExtendedFilterGrid();
		ExtendedSaveFilterSet saveFilterSet=new ExtendedSaveFilterSet();
		
		
		this.add(ef);
		
	}
	
	
	
	public  void addFilter(String _property,String _value){
		
		ExtendedFilterItem efi=new ExtendedFilterItem(_property,_value);
		this.add(efi);
		_filterList.put(_property, _value);		
	}
	
	

}
