/*
 * Ext GWT 2.2.5 - Ext for GWT
 * Copyright(c) 2007-2010, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package de.uds.MonitorInterventionMetafora.client.monitor.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;

import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.Store;
import com.extjs.gxt.ui.client.store.StoreEvent;
import com.extjs.gxt.ui.client.widget.BoxComponent;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.MessageBox;

import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;

import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.EditorGrid;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;

import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.extjs.gxt.ui.client.widget.toolbar.LabelToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;

import com.google.gwt.user.client.Element;


import com.extjs.gxt.ui.client.widget.grid.ColumnData;

import com.extjs.gxt.ui.client.event.ButtonEvent;

import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.GridEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.button.Button;

import de.uds.MonitorInterventionMetafora.client.communication.CommunicationServiceAsync;

import de.uds.MonitorInterventionMetafora.client.monitor.ClientMonitorController;
import de.uds.MonitorInterventionMetafora.client.monitor.datamodel.ClientMonitorDataModel;
import de.uds.MonitorInterventionMetafora.client.resources.Resources;


public class FilterGrid  extends LayoutContainer {

	 EditorGrid<FilterGridRow> grid;
     ClientMonitorController controller;
	 ClientMonitorDataModel model;
	 FilterSelectorToolBar filterSelectorToolBar;
	 
	
	public FilterGrid(ClientMonitorDataModel model, ClientMonitorController controller){
		
		this.model = model;
		this.controller = controller;
		
	}
	

  @Override
  protected void onRender(Element parent, int index) {
    super.onRender(parent, index);
    setLayout(new FlowLayout(1));
 
    ContentPanel panel = new ContentPanel();
    grid = new EditorGrid<FilterGridRow>(model.getFilterGridViewModel(), getColumnModel());
    grid.setBorders(true);
    FilterManagementToolBar _filterManagementToolBar=new FilterManagementToolBar(grid,model.getFilterSelectorModel());
    panel.setBottomComponent(_filterManagementToolBar);
    panel.setHeaderVisible(false);
    panel.setIcon(Resources.ICONS.table());
    panel.setButtonAlign(HorizontalAlignment.CENTER);
    panel.setCollapsible(true);
    panel.setFrame(true);
    panel.setSize(960, 150);
    grid.setWidth(950);
    grid.setHeight(180);
    panel.setLayout(new FitLayout());
    panel.add(grid);
    filterSelectorToolBar=new FilterSelectorToolBar(grid,model,controller);
    panel.setTopComponent(filterSelectorToolBar);
    
    add(panel);
   
    
  }
  
  

  ColumnModel getColumnModel(){
	
			  ColumnConfig _type = new ColumnConfig("filtertype", "filtertype", 50);
			  _type.setHeader("Type");
			  _type.setWidth(240);
		    
		    ColumnConfig _property = new ColumnConfig("property", "property", 50);
		    _property.setHeader("Property");  
		    _property.setWidth(230);
		    
		    ColumnConfig _operation = new ColumnConfig("operation", "operation", 50);
		    _operation.setHeader("Operation");
		    _operation.setWidth(200);
		    
		    
		    ColumnConfig _value = new ColumnConfig("value", "value", 50);
		    _value.setHeader("Value");
		    _value.setWidth(230);
		    

    
    GridCellRenderer<FilterGridRow> buttonRenderer = new GridCellRenderer<FilterGridRow>() {

        private boolean init;

        public Object render(final FilterGridRow model, String property, ColumnData config, final int rowIndex,
            final int colIndex, final ListStore<FilterGridRow> store, Grid<FilterGridRow> grid) {
          if (!init) {
            init = true;
            grid.addListener(Events.ColumnResize, new Listener<GridEvent<FilterGridRow>>() {

              public void handleEvent(GridEvent<FilterGridRow> be) {
                for (int i = 0; i < be.getGrid().getStore().getCount(); i++) {
                  if (be.getGrid().getView().getWidget(i, be.getColIndex()) != null
                      && be.getGrid().getView().getWidget(i, be.getColIndex()) instanceof BoxComponent) {
                    ((BoxComponent) be.getGrid().getView().getWidget(i, be.getColIndex())).setWidth(be.getWidth() - 10);
                  }
                }
              }
            });
          }

          Button b = new Button((String) model.get(property), new SelectionListener<ButtonEvent>() {
            @Override
            public void componentSelected(ButtonEvent ce) {

            	
            	store.remove(model);
            	filterSelectorToolBar.getFilterSelectorComboBox().clearSelections();
            	
            
            }
          });
          b.setWidth(grid.getColumnModel().getColumnWidth(colIndex) - 10);
          b.setToolTip("Remove Filter");
          b.setIcon(Resources.ICONS.delete());

          return b;
        }
      };
      
      
    
      ColumnConfig  buttoncolumn = new ColumnConfig();
      buttoncolumn.setId("action");
      buttoncolumn.setHeader("Act.");
      buttoncolumn.setWidth(35);
      buttoncolumn.setFixed(true);
      buttoncolumn.setRenderer(buttonRenderer);
      
      List<ColumnConfig> config = new ArrayList<ColumnConfig>();
      config.add(_type);
      config.add(_property);
      config.add(_operation);
      config.add(_value);
      config.add( buttoncolumn);
      
       ColumnModel cm = new ColumnModel(config);
       
       return cm;
   }
  
  public EditorGrid<FilterGridRow>  getGrid(){
	  
	  return grid;
  }
  
		
}
