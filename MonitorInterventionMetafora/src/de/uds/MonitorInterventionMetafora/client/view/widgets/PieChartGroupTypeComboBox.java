package de.uds.MonitorInterventionMetafora.client.view.widgets;

import java.util.List;

import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ComponentManager;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.Label;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import de.uds.MonitorInterventionMetafora.client.datamodels.EntitiesComboBoxModel;
import de.uds.MonitorInterventionMetafora.client.datamodels.GroupedByPropertyModel;
import de.uds.MonitorInterventionMetafora.client.manager.ClientInterfaceManager;
import de.uds.MonitorInterventionMetafora.client.view.charts.PieChartPanel;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.IndicatorProperty;

public class PieChartGroupTypeComboBox extends HorizontalPanel{
	
	public int i=0;
	private IndicatorProperty selectedEntity=null;
	private ComboBox<EntitiesComboBoxModel> comboType;
	
	GroupedByPropertyModel model;
	private final ClientInterfaceManager controller;
	//ExtendedPieChart _newPieChart;
	
	//private VerticalPanel pieChartPanel;
	public PieChartGroupTypeComboBox(GroupedByPropertyModel _model, ClientInterfaceManager controllerIn){
		
		this.controller = controllerIn;
		model=_model;
		comboType = new ComboBox<EntitiesComboBoxModel>();
		
		comboType.setEmptyText("Select a type");
	  
	  
	    
	    comboType.setDisplayField("displaytext");
	    comboType.setValueField("entityname");
	    comboType.setWidth(150);
	    comboType.setEditable(false);
	 
	    comboType.setAutoHeight(true);
	    comboType.setId("comboPieChartType");
	    comboType.setStore(model.getComboBoxEntities());
	  //  comboType.setTypeAhead(true);
	    comboType.setTriggerAction(TriggerAction.ALL);
	    comboType.addSelectionChangedListener(comboListener);
	    
	    
	    Button retriveBtn=new Button("Retrieve");
	    retriveBtn.setWidth("55px");
	    retriveBtn.setHeight("29px");
	   
	    retriveBtn.addClickHandler(new ClickHandler() {
	        public void onClick(ClickEvent event) {
	   
	        	if(selectedEntity!=null){
	        		
	        		
	        		
	        		//LayoutContainer _pieChartPanel = (LayoutContainer) ComponentManager.get().get("pieChartVerticalPanel");
	        		
	        		
	        		PieChartPanel _pieChartPanel = (PieChartPanel) ComponentManager.get().get("pieChartVerticalPanel");
	        		
	        		if( _pieChartPanel!=null){
	        			model.splitActions(true);
	        			
	        			controller.refreshPieChart(selectedEntity);
	        		
	        			
//	        			_pieChartPanel.getPieChart().draw(model.getEntityDataTable(selectedEntity),_pieChartPanel.getPieChartOptions());
//	        			_pieChartPanel.layout();
//	        			i++;
//	        			VerticalPanel _comboPieChartpanel = (VerticalPanel) ComponentManager.get().get("pieChartFilterPanel");
//	        			_comboPieChartpanel.layout();	        		
//	        			TabItem _pieChartTable = (TabItem) ComponentManager.get().get("pieChartViewTab");
//	        			_pieChartTable.layout();
	        		}
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
	        	
	         //   Record record = GroupingOptions.getObjectProperties().getRecord(vg);  
	            
	        	selectedEntity=new IndicatorProperty();
	        	selectedEntity.setEntityName(vg.getEntityName());
	        	selectedEntity.setDisplayText(vg.getDisplayText());
	        	selectedEntity.setType(vg.getItemType());
	                 
	            //  Info.display("Display","name:"+_entityName+" text:"+_displayText+" ItemType:"+_itemType);
	              //Type=filter;
	            
	              
	              /*
	              comboItem.removeAllListeners();
	              comboItem.clear();            
	              comboItem.setStore(getFilterItems(filter));
	              comboItem.addSelectionChangedListener(comboListenerItem);*/
	        }

	    };
	

}
