package de.uds.MonitorInterventionMetafora.client.view.widgets;

import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedListener;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.Label;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import de.uds.MonitorInterventionMetafora.client.datamodels.EntitiesComboBoxModel;
import de.uds.MonitorInterventionMetafora.client.datamodels.GroupedByPropertyModel;
import de.uds.MonitorInterventionMetafora.client.manager.FilteredDataViewManager;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.IndicatorProperty;

public class GroupingChooserPanel extends HorizontalPanel{
	
	private IndicatorProperty selectedEntity=null;
	private ComboBox<EntitiesComboBoxModel> comboType;
	
	GroupedByPropertyModel model;
//	private final ClientInterfaceManager controller;
	GroupedDataViewPanel parentViewPanel;

	public GroupingChooserPanel(final GroupedDataViewPanel parentViewPanel, GroupedByPropertyModel modelIn, String panelId){
		
		this.parentViewPanel = parentViewPanel;
		model= modelIn;
		comboType = new ComboBox<EntitiesComboBoxModel>();
		
		comboType.setId(panelId);
		
		comboType.setEmptyText("Select a proptery to group by:");
	    comboType.setDisplayField("displaytext");
	    comboType.setValueField("entityname");
	    comboType.setWidth(150);
	    comboType.setEditable(false);
	    comboType.setAutoHeight(true);
	    comboType.setStore(model.getComboBoxEntities());
	  //  comboType.setTypeAhead(true);
	    comboType.setTriggerAction(TriggerAction.ALL);
	    comboType.addSelectionChangedListener(comboListener);
	    
	    
	    Button retriveBtn=new Button("Re-Group");
	    retriveBtn.setWidth("65px");
	    retriveBtn.setHeight("29px");
	   
	    retriveBtn.addClickHandler(new ClickHandler() {
	        public void onClick(ClickEvent event) {
	   
	        	if(selectedEntity!=null){
        			model.splitActions(true);
        			parentViewPanel.changeGroupingProperty(selectedEntity);
	        	}
	         }
	     });

	    this.setWidth(600);
	    this.add(new Label("Type:"));
	    this.add(comboType);
	    this.add(retriveBtn);
	    
	}

	SelectionChangedListener<EntitiesComboBoxModel> comboListener =new SelectionChangedListener<EntitiesComboBoxModel>(){
        @Override
        public void selectionChanged(SelectionChangedEvent<EntitiesComboBoxModel> se) { 

        	EntitiesComboBoxModel vg = se.getSelectedItem();   
  
        	selectedEntity=new IndicatorProperty();
        	selectedEntity.setEntityName(vg.getEntityName());
        	selectedEntity.setDisplayText(vg.getDisplayText());
        	selectedEntity.setType(vg.getItemType());

    	}
     };

	public void refresh() {
		comboType.clearSelections();
		layout();
	}
	
	public IndicatorProperty getSelectedProperty(){
		return selectedEntity;
	}
	

}
