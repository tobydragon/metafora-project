package de.uds.MonitorInterventionMetafora.client.view.charts;








import java.util.List;

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

import de.uds.MonitorInterventionMetafora.client.communication.servercommunication.ActionMaintenance;
import de.uds.MonitorInterventionMetafora.client.datamodels.IndicatorFilterItemGridRowModel;
import de.uds.MonitorInterventionMetafora.client.datamodels.PieChartComboBoxModel;
import de.uds.MonitorInterventionMetafora.client.datamodels.PieChartViewModel;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CommonFormatStrings;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.FilterItemType;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.IndicatorEntity;

public class ExtendedPieChart extends VerticalPanel {
	
	
	//Map<Integer, String>subsection = new HashMap<Integer, String>();
	private IndicatorEntity _selectedEntity=null;
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
	  
	  
	    
	    comboType.setDisplayField("displaytext");
	    comboType.setValueField("entityname");
	    comboType.setWidth(150);
	    comboType.setEditable(false);
	 
	    comboType.setAutoHeight(true);
	    comboType.setId("comboType");
	    comboType.setStore(toComboBoxEntities(model.getIndicatorEntities()));
	  //  comboType.setTypeAhead(true);
	    comboType.setTriggerAction(TriggerAction.ALL);
	  
	   
	    
	    hp.setWidth(600);
	    hp.add(new Label("Type:"));
	    hp.add(comboType);
	    /*/
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
	    
	    
	    */
	    
	    SelectionChangedListener<PieChartComboBoxModel> comboListener =new SelectionChangedListener<PieChartComboBoxModel>(){
	        @Override
	        public void selectionChanged(SelectionChangedEvent<PieChartComboBoxModel> se) { 

	        	PieChartComboBoxModel vg = se.getSelectedItem();   
	        	
	         //   Record record = GroupingOptions.getObjectProperties().getRecord(vg);  
	            
	        	_selectedEntity=new IndicatorEntity();
	        	_selectedEntity.setEntityName(vg.getEntityName());
	        	_selectedEntity.setDisplayText(vg.getDisplayText());
	        	_selectedEntity.setType(vg.getItemType());
	              String _entityName =  vg.getEntityName();
	              String _displayText=vg.getDisplayText();
	              FilterItemType _itemType=vg.getItemType();
	              
	              Info.display("Display","name:"+_entityName+" text:"+_displayText+" ItemType:"+_itemType);
	              //Type=filter;
	            
	              
	              /*
	              comboItem.removeAllListeners();
	              comboItem.clear();            
	              comboItem.setStore(getFilterItems(filter));
	              comboItem.addSelectionChangedListener(comboListenerItem);*/
	        }

	    };
	    comboType.addSelectionChangedListener(comboListener);
	    //comboItem.addSelectionChangedListener(comboListenerItem);
	    
	
	    
	    
	    Button retriveBtn=new Button("Retrieve");
	    retriveBtn.setWidth("55px");
	    retriveBtn.setHeight("29px");
	    
	    retriveBtn.addClickHandler(new ClickHandler() {
	        public void onClick(ClickEvent event) {
	   
	        	if(_selectedEntity!=null){
	       	RootPanel.get().add(createPieChart(model.getPieChartData(_selectedEntity),"pieChart"));
	        	}
	       	//_mainContainer.repaint();
	        	
	        	
	          }
	        });
	    

	    hp.add(new Label(""));
	    //hp.add(comboItem);
	    hp.add(retriveBtn);

	   
	    
	   // this.add(hp);
	    HorizontalPanel space=new HorizontalPanel();
	    space.setWidth(600);
	    space.setHeight(30);
	    
	    mainContainer.add(hp);
	    mainContainer.add(space);
	    
	    
	    this.add(mainContainer);
	    
	    
		
	}
	


	ListStore<PieChartComboBoxModel> toComboBoxEntities(List<IndicatorEntity>  _entityList) {
		ListStore<PieChartComboBoxModel>  _comboBoxModelList = new ListStore<PieChartComboBoxModel>();
	    for(IndicatorEntity _ent: _entityList){
	    	PieChartComboBoxModel _comboBoxItem=new PieChartComboBoxModel(_ent);
	    	_comboBoxModelList.add(_comboBoxItem);
	    	
	    	
	    }
	    return  _comboBoxModelList;
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
		    	  
		    	    
		    	    EditorGrid<IndicatorFilterItemGridRowModel> editorGrid = (EditorGrid<IndicatorFilterItemGridRowModel>) ComponentManager.get().get("_filterItemGrid");
					EditorGrid<IndicatorFilterItemGridRowModel> _grid = editorGrid;
		    	    
					SimpleComboBox<String> _filterCombo=(SimpleComboBox<String>) ComponentManager.get().get("_filterGroupCombo");
					
		    	    
		    	  
		    	  IndicatorEntity _entity=new IndicatorEntity();
		    	  _entity=model.getIndicatorEntity(selection);
		    	    

		    	   
			          String _key= _entity.getType().toString()+"-"+ _entity.getEntityName()+"-"+ _entity.getValue();
			          
			       
					if(!isInFilterList(_key,_grid) && _entity!=null && !_entity.getValue().equalsIgnoreCase("")){
			        
			        
			       // IndicatorEntity _filter = new IndicatorEntity();  
			        //_filter.setProperty(_property);
			        //_filter.setValue(_value);
			        //_filter.setType(_propertyType);
			         
			    	//Info.display("Info","Filter for "+ value+" is added!");
			        
			        if(!reseted){
			        	
			        	reseted=true;
			        	//_grid.getStore().removeAll();
			        	_filterCombo.clearSelections();
			        }
			        

			        IndicatorFilterItemGridRowModel  _newRow=new IndicatorFilterItemGridRowModel(_entity.getEntityName(),_entity.getValue(),_entity.getType().toString()); 
			    	
			        
			        _grid.stopEditing();  
			        _grid.getStore().insert(_newRow, 0);  
			        _grid.startEditing(_grid.getStore().indexOf(_newRow), 0); 
			        _filterCombo.clearSelections();
				       
			        }
			        else {
			        	
			        	
			        	Info.display("Info","Selected Filter is<ul><li> already in  the filter list</li></ul>");
			        }
			        
			
		      }
		    };
		    
		    
		  
		  }

	  
	
	 boolean isInFilterList(String _key,EditorGrid<IndicatorFilterItemGridRowModel> _grid){
		 boolean result=false;
		 
		 for (int i = 0; i < _grid.getStore().getCount(); i++) {
			 
			 IndicatorFilterItemGridRowModel _item=	 _grid.getStore().getAt(i);
			 
			String row= _item.getType()+"-"+_item.getProperty()+"-"+_item.getValue();
			
			if(row.equalsIgnoreCase(_key)){
				
				result =true;
			}
		 }
		 
		 return result;
	 }
	 
	 
		

		
}
