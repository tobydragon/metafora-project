package com.analysis.client.view.charts;





import com.analysis.client.datamodels.PieChartComboBoxModel;
import com.analysis.client.datamodels.PieChartViewModel;



import com.analysis.shared.commonformat.CommonFormatStrings;
import com.analysis.shared.interactionmodels.IndicatorFilterItem;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.Record;

import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.Label;

import com.extjs.gxt.ui.client.widget.VerticalPanel;

import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.RootPanel;

import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.Selection;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.events.SelectHandler;

import com.google.gwt.visualization.client.visualizations.corechart.PieChart;
import com.google.gwt.visualization.client.visualizations.corechart.PieChart.PieOptions;

public class ExtendedPieChart extends VerticalPanel {
	
	
	//Map<Integer, String>subsection = new HashMap<Integer, String>();
	public String Type="";
	public String Item="";
	public  PieChart pie=null;
	
	public ExtendedPieChart(){
		
		createFilterHeader();
	}

public ExtendedPieChart(String title){
	this.setId("interActionForm");
	this.add(new Label(title));
	createFilterHeader();
}
	


	void createFilterHeader(){
	this.setWidth(650);
	HorizontalPanel hp=new HorizontalPanel();
	
	
	 final ComboBox<PieChartComboBoxModel> comboType = new ComboBox<PieChartComboBoxModel>();
	
	 comboType.setEmptyText("Select a type");
	  
	  
	    
	    comboType.setDisplayField("name");
	    comboType.setValueField("text");
	    comboType.setWidth(150);
	    comboType.setEditable(false);
	 
	    comboType.setAutoHeight(true);
	    comboType.setId("comboType");
	    comboType.setStore(GroupingOptions.getFilterTypes());
	    comboType.setTypeAhead(true);
	    comboType.setTriggerAction(TriggerAction.ALL);
	  
	   
	    
	    hp.setWidth(600);
	    hp.add(new Label("Type:"));
	    hp.add(comboType);
	    
	    final ComboBox<PieChartComboBoxModel> comboItem = new ComboBox<PieChartComboBoxModel>();
	    comboItem.setEmptyText("Select filter type...");
	    comboItem.setDisplayField("name");
	    comboItem.setValueField("text");
	    	    
	    comboItem.setWidth(150);
	    comboItem.setEditable(false);
	 
	    comboItem.setAutoHeight(true);
	    comboItem.setId("comboType2");
	    
	    comboItem.setStore(new ListStore<PieChartComboBoxModel>());
	    comboItem.setTypeAhead(true);
	    comboItem.setTriggerAction(TriggerAction.ALL);
	    
	    final SelectionChangedListener<PieChartComboBoxModel> comboListenerItem =new SelectionChangedListener<PieChartComboBoxModel>(){
	        @Override
	        public void selectionChanged(SelectionChangedEvent<PieChartComboBoxModel> se) { 

	        
	        	  Item=comboItem.getValue().getText();
	        	
	        	
	     
	        }

	    };
	    
	    
	    
	    
	    SelectionChangedListener<PieChartComboBoxModel> comboListener =new SelectionChangedListener<PieChartComboBoxModel>(){
	        @Override
	        public void selectionChanged(SelectionChangedEvent<PieChartComboBoxModel> se) { 

	        	PieChartComboBoxModel vg = se.getSelectedItem();   
	        	
	            Record record = GroupingOptions.getObjectProperties().getRecord(vg);  
	            
	              String filter = record.getModel().get("name");
	              Type=filter;
	            
	              
	              
	              comboItem.removeAllListeners();
	              comboItem.clear();            
	              comboItem.setStore(getFilterItems(filter));
	              comboItem.addSelectionChangedListener(comboListenerItem);
	        }

	    };
	    comboType.addSelectionChangedListener(comboListener);
	    comboItem.addSelectionChangedListener(comboListenerItem);
	    
	
	    
	    
	    Button retriveBtn=new Button("Retrive");
	    retriveBtn.setWidth("50px");
	    retriveBtn.setHeight("29px");
	    
	    retriveBtn.addClickHandler(new ClickHandler() {
	        public void onClick(ClickEvent event) {
	   
	        	PieChartViewModel _model=new PieChartViewModel();
	        	
	        	createPieChart(_model.getPieChartData(Type,Item),"pieChart");
	        
	        	
	        	
	          }
	        });
	    

	    hp.add(new Label("Property:"));
	    hp.add(comboItem);
	    hp.add(retriveBtn);

	    this.add(hp);
	    HorizontalPanel space=new HorizontalPanel();
	    space.setWidth(600);
	    space.setHeight(30);
	    this.add(space);
	    
	    
		
	}
	

	public static ListStore<PieChartComboBoxModel> getFilterItems(String Type) {
		ListStore<PieChartComboBoxModel>  filters = new ListStore<PieChartComboBoxModel>();
	    
	  if(Type.equalsIgnoreCase(CommonFormatStrings.O_OBJECT)){
		  
		  filters=GroupingOptions.getObjectProperties();
	  }
	  else if(Type.equalsIgnoreCase(CommonFormatStrings.C_CONTENT)){
		  
		  filters=GroupingOptions.getContentProperties();  
	  }
	  else if(Type.equalsIgnoreCase(CommonFormatStrings.A_Action)){
		  
		  
		  filters=GroupingOptions.getActionProperties();
	  }
	     
	    return filters;
	  }
	
	
	
	
	

	
	
	
	DataTable getDailyActivities() {
	    DataTable data = DataTable.create();
	    data.addColumn(ColumnType.STRING, "Task");
	    data.addColumn(ColumnType.NUMBER, "Hours per Day");
	    data.addRows(5);
	    data.setValue(0, 0, "Work");
	    data.setValue(0, 1, 11);
	    data.setValue(1, 0, "Eat");
	    data.setValue(1, 1, 2);
	    data.setValue(2, 0, "Commute");
	    data.setValue(2, 1, 2);
	    data.setValue(3, 0, "Watch TV");
	    data.setValue(3, 1, 2);
	    data.setValue(4, 0, "Sleep");
	    data.setValue(4, 1, 7);
	    return data;
	  }
	
	 public void createPieChart(DataTable data,String ID) {
		    
		 if(pie!=null) {
				 if(pie.isAttached()){
			 pie.removeFromParent();
				 }
			 
		 }

		    PieOptions options = PieChart.createPieOptions();
		    options.setWidth(500);
		    options.setHeight(400);
		    options.set3D(true);
		    options.setTitle("Indicator Overview");		    
		    pie= new PieChart(data, options);  
		    pie.addSelectHandler(createSelectHandler(pie));
			RootPanel.get().add(pie);
			  }
	 
	 
	 
	 
	 
	 
	 boolean reseted=false;
	 private SelectHandler createSelectHandler(final PieChart chart) {
		    return new SelectHandler() {
		      @Override
		      public void onSelect(SelectEvent event) {
		     
		    	  
		    	  
		    	   StringBuffer b = new StringBuffer();
		    	   int selection=-1;
		    	    JsArray<Selection> s = chart.getSelections();
		    	    for (int i = 0; i < s.length(); ++i) {
		    	      if (s.get(i).isCell()) {
		    	        b.append(" cell ");
		    	        b.append(s.get(i).getRow());
		    	        b.append(":");
		    	        b.append(s.get(i).getColumn());
		    	      } else if (s.get(i).isRow()) {
		    	       // b.append(" row ");
		    	      selection=s.get(i).getRow();
		    	      } else {
		    	        b.append(" column ");
		    	        b.append(s.get(i).getColumn());
		    	      }
		    	    }
		    	  
		    	    
		    	    
		    	    //String property=subsectionProperty.get(selection);
		    	    //String value=subsectionValue.get(selection);

			          //String _key=property+"-"+value;
			        //if(!DataModel.getActiveFilters().containsKey(_key) && value!=null){
			        //DataModel.getActiveFilters().put(_key,_key);
			       
			        IndicatorFilterItem _filter = new IndicatorFilterItem();  
			       // _filter.setProperty(property);
			        //_filter.setValue(value);
			        //_filter.setType(Type);
			         
			    	//Info.display("Info","Filter for "+ value+" is added!");
			        
			        if(!reseted){
			        	
			        	reseted=true;
			        	// ExtendedFilterGrid.getFilterSetListCombo().clearSelections();
			        	// ExtendedFilterGrid.getExtendedFilterGrid().getStore().removeAll();
			        	 
			        }
			    	
			    	/*
			        ExtendedFilterGrid.getExtendedFilterGrid().stopEditing();  
			        ExtendedFilterGrid.getExtendedFilterGrid().getStore().insert(_filter, 0);  
			        ExtendedFilterGrid.getExtendedFilterGrid().startEditing(ExtendedFilterGrid.getExtendedFilterGrid().getStore().indexOf(_filter), 0); 
			        ExtendedFilterGrid.getFilterSetListCombo().clearSelections();
				      */  
			        //}
			        else {
			        	
			        	
			        	Info.display("Info","Selection is<ul><li> already in Filter List</li></ul>");
			        }
			        
			
		      }
		    };
		  }

	  
	
		

		
}
