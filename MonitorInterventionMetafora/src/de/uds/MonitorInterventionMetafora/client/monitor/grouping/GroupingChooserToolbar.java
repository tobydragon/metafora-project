package de.uds.MonitorInterventionMetafora.client.monitor.grouping;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedListener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.Label;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import de.uds.MonitorInterventionMetafora.client.monitor.datamodel.PropertyComboBoxItemModel;
import de.uds.MonitorInterventionMetafora.client.monitor.dataview.DataViewPanelType;
import de.uds.MonitorInterventionMetafora.client.monitor.dataview.GroupedDataViewPanel;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionPropertyRule;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionPropertyRuleSelectorModel;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionPropertyRuleSelectorModelType;

public class GroupingChooserToolbar extends ToolBar{
	 
	private ActionPropertyRule selectedProperty=null;
	private ComboBox<PropertyComboBoxItemModel> comboType;
	
	GroupedDataViewPanel parentViewPanel;
	

	public GroupingChooserToolbar(final GroupedDataViewPanel parentViewPanel, ActionPropertyRule groupingProperty, String panelId){
		
		this.selectedProperty = groupingProperty;
		this.parentViewPanel = parentViewPanel;
		
		comboType = new ActionPropertyComboBox(ActionPropertyRuleSelectorModel.getActionPropertyRuleSelectorModel(ActionPropertyRuleSelectorModelType.GROUPING), panelId);
		comboType.addSelectionChangedListener(comboListener);
	    
	    Button retriveBtn=new Button("Re-Group");
	    retriveBtn.setShadow(true);
//	    retriveBtn.setWidth("65px");
//	    retriveBtn.setHeight("29px");
	    
	    retriveBtn.addSelectionListener(new SelectionListener<ButtonEvent>() {  
	        @Override  
	        public void componentSelected(ButtonEvent ce) {  
	        	if(selectedProperty!=null){
        			parentViewPanel.changeGroupingProperty(selectedProperty);
	        	}
	        }  
	      });  

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

//	public void refresh() {
//		comboType.clearSelections();
//		layout();
//	}
	
	public ActionPropertyRule getSelectedProperty(){
		return selectedProperty;
	}
	

}
