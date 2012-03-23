package de.uds.MonitorInterventionMetafora.client.view.charts;





import com.extjs.gxt.ui.client.widget.ComponentManager;
import com.extjs.gxt.ui.client.widget.Label;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.TabPanel;

import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;

import com.extjs.gxt.ui.client.widget.grid.EditorGrid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;

import com.google.gwt.core.client.JsArray;

import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.Selection;
import com.google.gwt.visualization.client.events.SelectHandler;

import com.google.gwt.visualization.client.visualizations.corechart.PieChart;
import com.google.gwt.visualization.client.visualizations.corechart.PieChart.PieOptions;

import de.uds.MonitorInterventionMetafora.client.communication.servercommunication.ActionMaintenance;
import de.uds.MonitorInterventionMetafora.client.datamodels.IndicatorFilterItemGridRowModel;
import de.uds.MonitorInterventionMetafora.client.datamodels.EntityViewModel;
import de.uds.MonitorInterventionMetafora.client.view.widgets.MultiModelTabPanel;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.IndicatorEntity;

public class ExtendedPieChart extends  VerticalPanel {
	
	
	//Map<Integer, String>subsection = new HashMap<Integer, String>();

	
	private  PieChart pieChart;
	private EntityViewModel model;
	//private ActionMaintenance maintenance;
	private IndicatorEntity  entity;
	
	
	public ExtendedPieChart(IndicatorEntity  _entity,EntityViewModel _model){
		entity=_entity;
		
		this.setId("pieChartVerticalPanel");
		//maintenance =_maintenance;
		model=_model;
		//model=new PieChartViewModel(maintenance);
		this.removeAll();
		//model.sliptActions(true);
		this.add(createPieChart(model.getPieChartData(entity),"pieChart"));
		pieChart.setLayoutData(new FitLayout());
	
		//pieChart.draw(data, options)
		this.layout(true);
		this.doLayout();
		
		
	}

public ExtendedPieChart(String title){
	this.setId("_pieChartVerticalPanel");
	this.add(new Label(title));
	this.layout(true);
	this.doLayout();
	
}





public EntityViewModel getPieChartModel(){
	
	return model;
	
}

	



	



	
	
	
	
	

public PieOptions  getPieChartOptions(){
		
		  PieOptions options = PieChart.createPieOptions();
		    options.setWidth(500);
		    options.setHeight(400);
		    options.set3D(true);
		    options.setTitle("Indicator Overview");	
		    
		    return options;
	}
	
	

	 public PieChart createPieChart(DataTable data,String ID) {
		 
	
		  	    
		    pieChart= new PieChart(data, getPieChartOptions());  
		   
		    pieChart.addSelectHandler(createSelectHandler(pieChart));
		    
		    
		    return pieChart;
			  }
	 
	 
	 
	 public PieChart getPieChart(){
		 
		 //pieChart.set
		 return pieChart;
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
		    	    

		    	  if(_entity==null){
		    		  
		    		  
		    		  MessageBox.info("Message","Selected Filter is<ul><li> already in  the filter list</li></ul>", null);
		    		  return;
		    		  
		    	  }
		    	  
		    	  
			          String _key= _entity.getType().toString()+"-"+ _entity.getEntityName()+"-"+ _entity.getValue();
			          
			       
					if(!isInFilterList(_key,_grid) && !_entity.getValue().equalsIgnoreCase("")){
			        

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
			        

			        IndicatorFilterItemGridRowModel  _newRow=new IndicatorFilterItemGridRowModel(_entity.getEntityName(),_entity.getValue(),_entity.getType().toString(),_entity.getDisplayText(),_entity.getOperationType().toString().toUpperCase()); 
			    	
			        
			        _grid.stopEditing();  
			        _grid.getStore().insert(_newRow, 0);  
			        _grid.startEditing(_grid.getStore().indexOf(_newRow), 0); 
			        _filterCombo.clearSelections();
			       
			        
			        model.getActionMaintenance().refreshTableView();
			         
			        TabPanel tabPanel = (TabPanel) ComponentManager.get().get("_multiModelTabPanel");
			        TabItem tabItem=(TabItem) ComponentManager.get().get("Table");
	
			        VerticalPanel verticalPanel = (VerticalPanel) ComponentManager.get().get("_tabMainPanel");
			        MessageBox.info("Message","Filter is added to the list!", null);
			        tabPanel.setTabIndex(0);
			        tabPanel.repaint();
			        tabPanel.setLayoutData(new FitLayout());
			        tabPanel.setSelection(tabItem);
			       
			        refresh();
			        verticalPanel.layout();
			        verticalPanel.repaint();
			        
		        	
			   	        }
			        else {
			        	
			        	MessageBox.info("Message","Selected Filter is<ul><li> already in  the filter list</li></ul>", null);
			        	
			        
			        }
			        
			
		      }
		    };
		    
		    
		  
		  }

	  
	
	 public void refresh(){
		 
		 this.layout();
		
		 
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
