package de.uds.MonitorInterventionMetafora.client.view.widgets;

import java.util.List;

import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ComponentManager;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.Label;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;

import de.uds.MonitorInterventionMetafora.client.datamodels.PieChartComboBoxModel;
import de.uds.MonitorInterventionMetafora.client.datamodels.PieChartViewModel;
import de.uds.MonitorInterventionMetafora.client.view.charts.ExtendedPieChart;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.FilterItemType;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.IndicatorEntity;

public class PieChartFilterTypeComboBox extends HorizontalPanel{
	
	
	private IndicatorEntity selectedEntity=null;
	private ComboBox<PieChartComboBoxModel> comboType;
	
	PieChartViewModel model;
	ExtendedPieChart _pieChart;
	
	private VerticalPanel pieChartPanel;
	public PieChartFilterTypeComboBox(PieChartViewModel _model){
		
	
		model=_model;
		comboType = new ComboBox<PieChartComboBoxModel>();
		
		comboType.setEmptyText("Select a type");
	  
	  
	    
	    comboType.setDisplayField("displaytext");
	    comboType.setValueField("entityname");
	    comboType.setWidth(150);
	    comboType.setEditable(false);
	 
	    comboType.setAutoHeight(true);
	    comboType.setId("comboType");
	    comboType.setStore(toComboBoxEntities(model.getIndicatorEntities()));
	  //  comboType.setTypeAhead(true);
	    comboType.setTriggerAction(TriggerAction.ALL);
	    comboType.addSelectionChangedListener(comboListener);
	    
	    
	    Button retriveBtn=new Button("Retrieve");
	    retriveBtn.setWidth("55px");
	    retriveBtn.setHeight("29px");
	    
	    retriveBtn.addClickHandler(new ClickHandler() {
	        public void onClick(ClickEvent event) {
	   
	        	if(selectedEntity!=null){
	        		
	        		
	        		
	        		MultiModelTabPanel _tabPanel = (MultiModelTabPanel) ComponentManager.get().get("_tabMainPanel");
	        		
	        		if(_tabPanel!=null){
	        			model.sliptActions(true);
	        			_pieChart=new ExtendedPieChart(selectedEntity,model);
	        			
	        			Dialog dp=new Dialog();
	        			
	        			//dp.removeAll();
	        			
	        			dp.setHeight(400);
	        			dp.setWidth(400);
	        			
	        			dp.add(_pieChart);
	        			
	        			dp.show();
	        			dp.center();
	        			
	        			
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
	        		if(pieChartPanel!=null)
	        		{
	        			
	        			//RootPanel.get().add(_pieChart);
	        			
	        			//pieChartPanel.render(_pieChart.getElement(),0);
	        		//	pieChartPanel.repaint();
	        			
	        			//RootPanel.get().remove(_chart);
	        		}
	        		
	        		
	        		
	        		
	        		
	        		
	        		
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
	
	
	
	ListStore<PieChartComboBoxModel> toComboBoxEntities(List<IndicatorEntity>  _entityList) {
		ListStore<PieChartComboBoxModel>  _comboBoxModelList = new ListStore<PieChartComboBoxModel>();
	    for(IndicatorEntity _ent: _entityList){
	    	PieChartComboBoxModel _comboBoxItem=new PieChartComboBoxModel(_ent);
	    	_comboBoxModelList.add(_comboBoxItem);
	    	
	    	
	    }
	    return  _comboBoxModelList;
	  }
	
	
	
	   SelectionChangedListener<PieChartComboBoxModel> comboListener =new SelectionChangedListener<PieChartComboBoxModel>(){
	        @Override
	        public void selectionChanged(SelectionChangedEvent<PieChartComboBoxModel> se) { 

	        	PieChartComboBoxModel vg = se.getSelectedItem();   
	        	
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
