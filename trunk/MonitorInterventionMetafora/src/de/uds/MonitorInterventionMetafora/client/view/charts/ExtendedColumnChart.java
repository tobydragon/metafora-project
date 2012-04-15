package de.uds.MonitorInterventionMetafora.client.view.charts;





import com.extjs.gxt.ui.client.widget.ComponentManager;
import com.extjs.gxt.ui.client.widget.Label;
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
import com.google.gwt.visualization.client.events.OnMouseOutHandler;
import com.google.gwt.visualization.client.events.OnMouseOverHandler;
import com.google.gwt.visualization.client.events.SelectHandler;

import com.google.gwt.visualization.client.visualizations.corechart.AxisOptions;
import com.google.gwt.visualization.client.visualizations.corechart.ColumnChart;


import de.uds.MonitorInterventionMetafora.client.datamodels.IndicatorFilterItemGridRowModel;
import de.uds.MonitorInterventionMetafora.client.datamodels.EntityViewModel;
import de.uds.MonitorInterventionMetafora.client.manager.ClientInterfaceManager;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.IndicatorEntity;
import com.google.gwt.visualization.client.visualizations.corechart.Options;

public class ExtendedColumnChart extends  VerticalPanel{
	
	
	//Map<Integer, String>subsection = new HashMap<Integer, String>();

	
	private  ColumnChart columnChart;
	private EntityViewModel model;
	private ClientInterfaceManager interfaceManager;

	Label status;
    Label onMouseOverAndOutStatus;
	private IndicatorEntity  entity;
	
	
	public ExtendedColumnChart(IndicatorEntity  _entity,EntityViewModel _model){
		entity=_entity;
		 status = new Label();
		 onMouseOverAndOutStatus = new Label();
		this.setId("barChartVerticalPanel");
		interfaceManager=new ClientInterfaceManager();
		model=_model;
		
		this.removeAll();
		this.add(renderColumnChart());
		
	
		this.layout(true);
		this.doLayout();
		
		
	}

public ExtendedColumnChart(String title){
	this.setId("_barChartVerticalPanel");
	this.add(new Label(title));
	this.layout(true);
	this.doLayout();
	interfaceManager=new ClientInterfaceManager();
	
}


ColumnChart renderColumnChart(){
	

	DataTable barChartData=model.getEntityDataTable(entity);
	columnChart=new ColumnChart(barChartData, getBarChartOptions(model.getMaxValue()));
	columnChart.addOnMouseOutHandler(createMouseOutHandler());
	columnChart.addOnMouseOverHandler(createOnMouseOverHandler());
	
	columnChart.addSelectHandler(createSelectHandler());
	
	columnChart.setLayoutData(new FitLayout());
	return columnChart;

}




public EntityViewModel getBarChartModel(){
	
	return model;
	
}


public Options  getBarChartOptions(int _maxValue){
		
	 Options options = Options.create();
	    options.setHeight(380);
	    //options.setTitle("Entity Bars");
	    options.setWidth(550);
	    options.setColors("#1876E9");
	    
	    AxisOptions vAxisOptions = AxisOptions.create();
	 
	    vAxisOptions.setMinValue(0);
	    vAxisOptions.setMaxValue(_maxValue);
	    options.setVAxisOptions(vAxisOptions);
		return options;
	
	}
	
	

	 
	 
	 public ColumnChart getBarChart(){
		 
		 //pieChart.set
		 return columnChart;
	 }
	 
	 
	 
	 boolean reseted=false;
	 int selection=-1;
	 private SelectHandler createSelectHandler() {
		    return new SelectHandler() {
		      @Override
		      public void onSelect(SelectEvent event) {
		     
		    	  
		    	  
		    	
		    	  
		    	    
		    	  //  EditorGrid<IndicatorFilterItemGridRowModel> editorGrid = (EditorGrid<IndicatorFilterItemGridRowModel>) ComponentManager.get().get("_filterItemGrid");
					EditorGrid<IndicatorFilterItemGridRowModel> _grid = interfaceManager.getFilterListEditorGrid();		    	    
					SimpleComboBox<String> _filterCombo=interfaceManager.getFilterListComboBox();
					
		    	    
		    	  
		    	  IndicatorEntity _entity=new IndicatorEntity();
		    	  _entity=model.getIndicatorEntity(selection);
		    	    

		    	  if(_entity==null){
		    		  
		    		  
		    		  MessageBox.info("Message","Selected Filter is<ul><li> already in  the filter list</li></ul>", null);
		    		  return;
		    		  
		    	  }
		    	  
		    	  
			          String _key= _entity.getType().toString()+"-"+ _entity.getEntityName()+"-"+ _entity.getValue();
			          
			       
					if(!isInFilterList(_key,_grid) && !_entity.getValue().equalsIgnoreCase("")){
			        
			        
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
			         
			        TabPanel tabPanel = interfaceManager.getMultiModelTabPanel();
			        TabItem tabItem=interfaceManager.getTableViewTabItem();	
			        VerticalPanel verticalPanel = interfaceManager.getTabPanelContainer();
			        //MessageBox.info("Message","Filter is added to the list!", null);
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

	  
	 
	 private  OnMouseOutHandler createMouseOutHandler(){
		 
		 
		 return new OnMouseOutHandler(){

			@Override
			public void onMouseOutEvent(OnMouseOutEvent event) {
				
				
				selection=event.getRow();
				 StringBuffer b = new StringBuffer();
				    b.append(" row: ");
				    b.append(event.getRow());
				    b.append(", column: ");
				    b.append(event.getColumn());
				    onMouseOverAndOutStatus.setText("Mouse out of " + b.toString()); 
				
				
			}};
		
	 }
	 
	 
	 private OnMouseOverHandler createOnMouseOverHandler(){
		return new OnMouseOverHandler(){

			@Override
			public void onMouseOverEvent(OnMouseOverEvent event) {
				
				  int row = event.getRow();
				  selection=row;
				    int column = event.getColumn();
				    StringBuffer b = new StringBuffer();
				    b.append(" row: ");
				    b.append(row);
				    b.append(", column: ");
				    b.append(column);
				    onMouseOverAndOutStatus.setText("Mouse over " + b.toString()); 
			}}; 
		 
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
