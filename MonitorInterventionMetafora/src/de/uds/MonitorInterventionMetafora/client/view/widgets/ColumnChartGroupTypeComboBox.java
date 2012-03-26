package de.uds.MonitorInterventionMetafora.client.view.widgets;


import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedListener;

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
import de.uds.MonitorInterventionMetafora.client.datamodels.EntityViewModel;
import de.uds.MonitorInterventionMetafora.client.view.charts.ExtendedColumnChart;
import de.uds.MonitorInterventionMetafora.client.view.charts.ExtendedPieChart;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.IndicatorEntity;

public class ColumnChartGroupTypeComboBox extends HorizontalPanel{
	
	//public int i=0;
	private IndicatorEntity selectedEntity=null;
	private ComboBox<EntitiesComboBoxModel> comboType;
	
	EntityViewModel model;
	
	public ColumnChartGroupTypeComboBox(EntityViewModel _model){
		
	
		model=_model;
		comboType = new ComboBox<EntitiesComboBoxModel>();
		
		comboType.setEmptyText("Select a type");
	  
	  
	    
	    comboType.setDisplayField("displaytext");
	    comboType.setValueField("entityname");
	    comboType.setWidth(150);
	    comboType.setEditable(false);
	 
	    comboType.setAutoHeight(true);
	    comboType.setId("comboColumnChartType");
	    comboType.setStore(model.getComboBoxEntities());
	    comboType.setTriggerAction(TriggerAction.ALL);
	    comboType.addSelectionChangedListener(comboListener);
	    
	    
	    Button retriveBtn=new Button("Render");
	    retriveBtn.setWidth("55px");
	    retriveBtn.setHeight("29px");
	   
	    retriveBtn.addClickHandler(new ClickHandler() {
	        public void onClick(ClickEvent event) {
	   
	        	if(selectedEntity!=null){
	        		
	        		
	        		
	        		//LayoutContainer _pieChartPanel = (LayoutContainer) ComponentManager.get().get("pieChartVerticalPanel");
	        		
	        		
	        		ExtendedColumnChart _barChartPanel = (ExtendedColumnChart) ComponentManager.get().get("barChartVerticalPanel");
	        		
	        		if( _barChartPanel!=null){
	        			model.sliptActions(true);
	        			
	        			
	        			
	        			_barChartPanel.getBarChart().draw(model.getEntityDataTable(selectedEntity),_barChartPanel.getBarChartOptions(model.getMaxValue()));
	        			
	        		
	        			_barChartPanel.layout();
	        		//	i++;
	        			
	        			VerticalPanel _comboPieChartpanel = (VerticalPanel) ComponentManager.get().get("barChartFilterPanel");
	        			_comboPieChartpanel.layout();
	        		
	        		
	        			TabItem _barChartTable = (TabItem) ComponentManager.get().get("barChartViewTab");
	        			_barChartTable.layout();
	        			
	        	
	        			
	        			VerticalPanel _pieChartVerticalPanel = (VerticalPanel) ComponentManager.get().get("_barChartFilterPanel");
		        		if(_pieChartVerticalPanel!=null){
		        			
		        		  	
		        		}
	        					
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
	            
	        	selectedEntity=new IndicatorEntity();
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
