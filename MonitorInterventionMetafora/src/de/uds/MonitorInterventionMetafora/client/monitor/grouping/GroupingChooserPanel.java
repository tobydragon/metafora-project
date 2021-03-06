package de.uds.MonitorInterventionMetafora.client.monitor.grouping;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedListener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.Label;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.extjs.gxt.ui.client.widget.button.Button;

import de.uds.MonitorInterventionMetafora.client.monitor.datamodel.PropertyComboBoxItemModel;
import de.uds.MonitorInterventionMetafora.client.monitor.dataview.GroupedDataViewPanel;
import de.uds.MonitorInterventionMetafora.client.resources.Resources;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionPropertyRule;

public class GroupingChooserPanel extends HorizontalPanel{
	 
	private ActionPropertyRule selectedProperty=null;
	private ComboBox<PropertyComboBoxItemModel> comboType;
	
	GroupedDataViewPanel parentViewPanel;

	public GroupingChooserPanel(final GroupedDataViewPanel parentViewPanel, ListStore<PropertyComboBoxItemModel> items, ActionPropertyRule groupingProperty, String panelId){
		
		this.selectedProperty = groupingProperty;
		this.parentViewPanel = parentViewPanel;
		
//		comboType = new ActionPropertyComboBox(items, panelId);
		comboType.addSelectionChangedListener(comboListener);
	    
	    Button retriveBtn=new Button("",new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				if(selectedProperty!=null){
        			parentViewPanel.changeGroupingProperty(selectedProperty);

	        	}
			} });
	    
	    
	    retriveBtn.setIcon(Resources.ICONS.refresh());
	    retriveBtn.setWidth("45px");
	    retriveBtn.setHeight("29px");
	  

	    this.setWidth(600);
	    this.add(new Label("Type:"));
	    this.add(comboType);
	    this.add(retriveBtn);
	    
	}

	SelectionChangedListener<PropertyComboBoxItemModel> comboListener =new SelectionChangedListener<PropertyComboBoxItemModel>(){
        @Override
        public void selectionChanged(SelectionChangedEvent<PropertyComboBoxItemModel> se) { 
        	selectedProperty = se.getSelectedItem().getActionPropertyRule();   
    	}
     };

	public void refresh() {
		comboType.clearSelections();
		layout();
	}
	
	public ActionPropertyRule getSelectedProperty(){
		return selectedProperty;
	}
	

}
