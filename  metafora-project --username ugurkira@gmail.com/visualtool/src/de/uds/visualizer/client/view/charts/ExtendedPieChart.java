package de.uds.visualizer.client.view.charts;








import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.Record;

import com.extjs.gxt.ui.client.widget.ComponentManager;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.Label;

import com.extjs.gxt.ui.client.widget.VerticalPanel;

import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.grid.EditorGrid;

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

import de.uds.visualizer.client.communication.servercommunication.ActionMaintenance;
import de.uds.visualizer.client.datamodels.PieChartComboBoxModel;
import de.uds.visualizer.client.datamodels.PieChartViewModel;
import de.uds.visualizer.shared.commonformat.CommonFormatStrings;
import de.uds.visualizer.shared.interactionmodels.IndicatorFilterItem;

public class ExtendedPieChart extends VerticalPanel {
	
	
	//Map<Integer, String>subsection = new HashMap<Integer, String>();
	private String Type="";
	private String Item="";
	private PieChart pie=null;
	private VerticalPanel mainContainer;
	
	private PieChartViewModel model;
	private ActionMaintenance maintenance;
	
	public ExtendedPieChart(ActionMaintenance _maintenance){
		
		maintenance =_maintenance;
		model=new PieChartViewModel(maintenance);
		mainContainer=new VerticalPanel();
		
		createFilterHeader();
		
	}

public ExtendedPieChart(String title){
	this.setId("interActionForm");
	this.add(new Label(title));
	mainContainer=new VerticalPanel();
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
	    
	
	    
	    
	    Button retriveBtn=new Button("Retrieve");
	    retriveBtn.setWidth("50px");
	    retriveBtn.setHeight("29px");
	    
	    retriveBtn.addClickHandler(new ClickHandler() {
	        public void onClick(ClickEvent event) {
	   
	       	RootPanel.get().add(createPieChart(model.getPieChartData(Type,Item),"pieChart"));
	        
	       	//_mainContainer.repaint();
	        	
	        	
	          }
	        });
	    

	    hp.add(new Label("Property:"));
	    hp.add(comboItem);
	    hp.add(retriveBtn);

	   
	    
	   // this.add(hp);
	    HorizontalPanel space=new HorizontalPanel();
	    space.setWidth(600);
	    space.setHeight(30);
	    
	    mainContainer.add(hp);
	    mainContainer.add(space);
	    
	    
	    this.add(mainContainer);
	    
	    
		
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
	
	
	
	
	

	
	
	

	 public PieChart createPieChart(DataTable data,String ID) {
		    
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
			
		   
		    
		    return pie;
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
		    	  
		    	    
		    	    EditorGrid<IndicatorFilterItem> editorGrid = (EditorGrid<IndicatorFilterItem>) ComponentManager.get().get("_filterItemGrid");
					EditorGrid<IndicatorFilterItem> _grid = editorGrid;
		    	    
					SimpleComboBox<String> _filterCombo=(SimpleComboBox<String>) ComponentManager.get().get("_filterGroupCombo");
					
		    	    
		    	  
		    	    String _propertyType=Type;
		    	    String _property=model.getSubSectionProperty(selection);
		    	    String _value=model.getSubSectionValue(selection);

		    	   
			          String _key=_propertyType+"-"+_property+"-"+_value;
			          
			       
					if(!isInFilterList(_key,_grid) && _value!=null && !_value.equalsIgnoreCase("")){
			        
			        
			        IndicatorFilterItem _filter = new IndicatorFilterItem();  
			        _filter.setProperty(_property);
			        _filter.setValue(_value);
			        _filter.setType(_propertyType);
			         
			    	//Info.display("Info","Filter for "+ value+" is added!");
			        
			        if(!reseted){
			        	
			        	reseted=true;
			        	//_grid.getStore().removeAll();
			        	_filterCombo.clearSelections();
			        }
			        
			        
			    	
			    	
			        _grid.stopEditing();  
			        _grid.getStore().insert(_filter, 0);  
			        _grid.startEditing(_grid.getStore().indexOf(_filter), 0); 
			        _filterCombo.clearSelections();
				       
			        }
			        else {
			        	
			        	
			        	Info.display("Info","Selected Filter is<ul><li> already in  the filter list</li></ul>");
			        }
			        
			
		      }
		    };
		    
		    
		  
		  }

	  
	
	 boolean isInFilterList(String _key,EditorGrid<IndicatorFilterItem> _grid){
		 boolean result=false;
		 
		 for (int i = 0; i < _grid.getStore().getCount(); i++) {
			 
			 IndicatorFilterItem _item=	 _grid.getStore().getAt(i);
			 
			String row= _item.getType()+"-"+_item.getProperty()+"-"+_item.getValue();
			 
			if(row.equalsIgnoreCase(_key)){
				
				result =true;
			}
		 }
		 
		 return result;
	 }
		

		
}
