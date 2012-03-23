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
import de.uds.MonitorInterventionMetafora.client.datamodels.EntityViewModel;
import de.uds.MonitorInterventionMetafora.client.view.charts.ExtendedPieChart;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.IndicatorEntity;

public class PieChartFilterTypeComboBox extends HorizontalPanel{
	
	public int i=0;
	private IndicatorEntity selectedEntity=null;
	private ComboBox<EntitiesComboBoxModel> comboType;
	
	EntityViewModel model;
	//ExtendedPieChart _newPieChart;
	
	//private VerticalPanel pieChartPanel;
	public PieChartFilterTypeComboBox(EntityViewModel _model){
		
	
		model=_model;
		comboType = new ComboBox<EntitiesComboBoxModel>();
		
		comboType.setEmptyText("Select a type");
	  
	  
	    
	    comboType.setDisplayField("displaytext");
	    comboType.setValueField("entityname");
	    comboType.setWidth(150);
	    comboType.setEditable(false);
	 
	    comboType.setAutoHeight(true);
	    comboType.setId("comboType");
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
	        		
	        		
	        		ExtendedPieChart _pieChartPanel = (ExtendedPieChart) ComponentManager.get().get("pieChartVerticalPanel");
	        		
	        		if( _pieChartPanel!=null){
	        			model.sliptActions(true);
	        			
	        			
	        			
	        			_pieChartPanel.getPieChart().draw(model.getPieChartData(selectedEntity),_pieChartPanel.getPieChartOptions());
	        			
	        			
	        			//_newPieChart=new ExtendedPieChart(selectedEntity,model);
	        			//_pieChartPanel.removeAll();
	        			//VerticalPanel _pieChart = (VerticalPanel) ComponentManager.get().get("pieChartVerticalPanel");
	        			
	        			//_pieChartPanel.add(new Label("weeaseae:"+i));
	        			//_pieChartPanel.add(_newPieChart);
	        			
	        		//	_pieChartPanel.repaint();
	        			//_pieChartPanel.layout(true);
	        			_pieChartPanel.layout();
	        			i++;
	        			
	        			VerticalPanel _comboPieChartpanel = (VerticalPanel) ComponentManager.get().get("pieChartFilterPanel");
	        			_comboPieChartpanel.layout();
	        			//_comboPieChartpanel .repaint();
	        		
	        			TabItem _pieChartTable = (TabItem) ComponentManager.get().get("pieChartViewTab");
	        			_pieChartTable.layout();
	        			
	        			
	        			
	        			/*
	        			Dialog dp=new Dialog();
	        			
	        			//dp.removeAll();
	        			
	        			dp.setHeight(400);
	        			dp.setWidth(400);			
	        			dp.add(_pieChart);
	        			dp.show();
	        			dp.center();*/
	        			
	        			
	        			VerticalPanel _pieChartVerticalPanel = (VerticalPanel) ComponentManager.get().get("_pieChartFilterPanel");
		        		if(_pieChartVerticalPanel!=null){
		        			
		        		//	System.out.println("Panel Bulundu!!");
		        		//_pieChartVerticalPanel.removeAll();
		        		//_pieChartVerticalPanel.add(_pieChart);
		        		//_pieChartVerticalPanel.layout(true);
		        		//_pieChartVerticalPanel.re
		        	
		        		}
	        			//_tabPanel.addTab(selectedEntity.getEntityName(), _pieChart, true);
	        			
	        		}
	        		
	        		
	        		
	        		
	        			
	        			//ComponentManager.get().get("_pieChartVerticalPanel").removeFromParent();
	        		
	        		
	        		//pieChartPanel.remove
	        		/*if(pieChartPanel!=null)
	        		{
	        			
	        			//RootPanel.get().add(_pieChart);
	        			
	        			//pieChartPanel.render(_pieChart.getElement(),0);
	        		//	pieChartPanel.repaint();
	        			
	        			//RootPanel.get().remove(_chart);
	        		}
	        		*/
	        		
	        		
	        		
	        		
	        		
	        		
	        		//((VerticalPanel) ComponentManager.get().get("_pieChartFilterPanel")).add(_pieChart);
	        		
	        		
	        		//_filterPanel.add();
	        		
	        		
	        		
	        		//MultiModelTabPanel _tabPanel = (MultiModelTabPanel) ComponentManager.get().get("_tabMainPanel");
	        		
	        	    //if(_tabPanel!=null){
	        	    	
	        	    	//_tabPanel.getTabPanel().getTabBar().getTab(1).
	        	    	//add(createPieChart(model.getPieChartData(_selectedEntity),"pieChart"),"Test" );
	        	    	//_tabPanel.add(createPieChart(model.getPieChartData(_selectedEntity),"pieChart"));
	        	    	
	        	    	//_panel.getParent().ad
	        	    	
	        	   // }
	        		
	        		
	        	  //  ExtendedPieChart _pieChart=new ExtendedPieChart(model.getActionMaintenance(),selectedEntity);
	        	    
	       	//RootPanel.get().add(_pieChart);
	      // 	RootPanel.get().re
	        	
	        	
	        	}
	       	//_mainContainer.repaint();
	        	
	        	
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
