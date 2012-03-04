/*
 * Ext GWT 2.2.5 - Ext for GWT
 * Copyright(c) 2007-2010, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package com.analysis.client.view.grids;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




import com.analysis.client.communication.actionresponses.RequestConfigurationCallBack;
import com.analysis.client.communication.models.DataModel;
import com.analysis.client.communication.server.Server;
import com.analysis.client.components.ActionObject;
import com.analysis.client.datamodels.GridIndicatorRow_remove;
import com.analysis.client.resources.Resources;
import com.analysis.client.view.charts.ExtendedPieChart;
import com.analysis.client.view.widgets.TabDataViewPanel;
import com.analysis.client.xml.GWTXmlFragment;
import com.analysis.shared.commonformat.CfAction;
import com.analysis.shared.commonformat.CfActionType;

import com.analysis.shared.interactionmodels.Configuration;
import com.analysis.shared.interactionmodels.IndicatorFilterItem;
import com.analysis.shared.interactionmodels.IndicatorFilter;
import com.analysis.shared.utils.GWTDateUtils;
import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.store.GroupingStore;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.Store;
import com.extjs.gxt.ui.client.store.StoreEvent;
import com.extjs.gxt.ui.client.widget.BoxComponent;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.form.CheckBox;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.grid.CellEditor;
import com.extjs.gxt.ui.client.widget.grid.CheckColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.EditorGrid;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.grid.GridGroupRenderer;
import com.extjs.gxt.ui.client.widget.grid.GroupColumnData;
import com.extjs.gxt.ui.client.widget.grid.GroupingView;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.extjs.gxt.ui.client.widget.toolbar.LabelToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.GridEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.button.Button;

public class ExtendedFilterGrid  extends LayoutContainer implements RequestConfigurationCallBack {

	private String groupingItem="";
	
	private Map<String, IndicatorFilter> configurationFilters;
	
	 static EditorGrid<IndicatorFilterItem> grid;
	 static ListStore<IndicatorFilterItem> store;
	 static SimpleComboBox<String> filterGroupCombo;
	
	public ExtendedFilterGrid(String _groupingItem){
		
		groupingItem=_groupingItem;
	}
	
	


	
  @Override
  protected void onRender(Element parent, int index) {
    super.onRender(parent, index);
    setLayout(new FlowLayout(1));

   store = new ListStore<IndicatorFilterItem>();
 
    
 
    List<IndicatorFilterItem> filters=new ArrayList<IndicatorFilterItem>();
    IndicatorFilterItem ft=new IndicatorFilterItem();
    ft.setProperty("MapID");
    ft.setValue("1");
    ft.setType("Action");
   
    filters.add(ft);
    
    IndicatorFilterItem fts=new IndicatorFilterItem();
    fts.setProperty("User");
    fts.setValue("Ugur");
    fts.setType("Content");
    filters.add(fts);
    
    store.add(filters);
    
    
    ColumnConfig _type = new ColumnConfig("filtertype", "filtertype", 50);
    _type.setHeader("Type");
    _type.setWidth(100);
    
    ColumnConfig _property = new ColumnConfig("property", "property", 50);
    _property.setHeader("Property");  
    _property.setWidth(100);
    
    
    ColumnConfig _value = new ColumnConfig("value", "value", 50);
    _value.setHeader("Value");
    _value.setWidth(100);
    
    
    CheckColumnConfig checkColumn = new CheckColumnConfig("indoor", "Indoor?", 55);  
    CellEditor checkBoxEditor = new CellEditor(new CheckBox());  
    checkColumn.setEditor(checkBoxEditor); 
   
    
    
    
    
    
    
    GridCellRenderer<IndicatorFilterItem> buttonRenderer = new GridCellRenderer<IndicatorFilterItem>() {

        private boolean init;

        public Object render(final IndicatorFilterItem model, String property, ColumnData config, final int rowIndex,
            final int colIndex, final ListStore<IndicatorFilterItem> store, Grid<IndicatorFilterItem> grid) {
          if (!init) {
            init = true;
            grid.addListener(Events.ColumnResize, new Listener<GridEvent<IndicatorFilterItem>>() {

              public void handleEvent(GridEvent<IndicatorFilterItem> be) {
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

            	
            	
            	
            	Info.display(model.getProperty(), "<ul><li>" + model.getValue()+ "is removed!" + "</li></ul>");
            
            	String _key=model.getProperty()+"-"+model.getValue();
            	
            	DataModel.getActiveFilters().remove(_key);
            	
            	store.remove(model);
            	filterGroupCombo.clearSelections();
            
            
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
    config.add(_value);
    //config.add(checkColumn);
    config.add( buttoncolumn);
    
   // config.add(groupingItem);

     ColumnModel cm = new ColumnModel(config);

     
     grid = new EditorGrid<IndicatorFilterItem>(store, cm);

    grid.setBorders(true);
    /*
    grid.addListener(Events.Add, new Listener<BaseEvent>() {
        @Override
        public void handleEvent(BaseEvent be) {
        	Info.display("Info","addedd");
        }
    });
    
    grid.addListener(Events.Remove, new Listener<BaseEvent>() {
        @Override
        public void handleEvent(BaseEvent be) {
        	Info.display("Info","remove");
        }
    });*/
   
    grid.getStore().addListener(Store.Add, new Listener<StoreEvent<IndicatorFilterItem>>() {
          public void handleEvent(StoreEvent<IndicatorFilterItem> be) {
        	  //Info.display("Info","addedd");
        	  
        	//  filterGroup.clearSelections();
          }
        });
    
    grid.getStore().addListener(Store.Remove, new Listener<StoreEvent<IndicatorFilterItem>>() {
        public void handleEvent(StoreEvent<IndicatorFilterItem> be) {
      	 
        	//Info.display("Info","remove");
        	//filterGroup.clearSelections();
        }
      });
    
    ToolBar toolBar = new ToolBar();  
    Button add = new Button("Add Filter");  
    add.addSelectionListener(new SelectionListener<ButtonEvent>() {  
  
      @Override  
      public void componentSelected(ButtonEvent ce) {  
    IndicatorFilterItem filter = new IndicatorFilterItem();  
    filter.setProperty("Tool");
    filter.setValue("Lasad");
     
  
        grid.stopEditing();  
        store.insert(filter, 0);  
        grid.startEditing(store.indexOf(filter), 0);  
      }  
  
    });  
    toolBar.add(add);  
    
    
    
    toolBar.add(new LabelToolItem("Filter Set:"));  
     
  
   toolBar.add(FilterSetComboBox());
    
    
    Button clearbtn = new Button("Clear",new SelectionListener<ButtonEvent>() {

		@Override
		public void componentSelected(ButtonEvent ce) {
		

		    	 Info.display("le","Clear");
		    grid.getStore().removeAll();
		    filterGroupCombo.clearSelections();
		   
			
		}  
   
  
    });  
    toolBar.add(clearbtn);
    

    ContentPanel panel = new ContentPanel();
    panel.setTopComponent(toolBar); 
    panel.setHeaderVisible(false);
    panel.setIcon(Resources.ICONS.table());
    panel.setButtonAlign(HorizontalAlignment.CENTER);
    panel.setCollapsible(false);
    panel.setFrame(true);
    panel.setSize(465, 160);
    panel.setLayout(new FitLayout());
    panel.add(grid);

    /*panel.addButton(new Button("Save", new SelectionListener<ButtonEvent>() {
        @Override
        public void componentSelected(ButtonEvent ce) {
	
        }
      }));*/
     
    
    grid.getAriaSupport().setLabelledBy(panel.getHeader().getId() + "-label");

    
    add(panel);
  }
  
  
  public static EditorGrid<IndicatorFilterItem> getExtendedFilterGrid(){
	  
	 return grid;	  
  }
  
  
  public static SimpleComboBox<String> getFilterSetListCombo(){
		
		return filterGroupCombo;
	}

		
  
  
  SimpleComboBox<String> FilterSetComboBox(){
	  
	  	filterGroupCombo = new SimpleComboBox<String>();  
	    filterGroupCombo.setTriggerAction(TriggerAction.ALL);  
	    filterGroupCombo.setEditable(false);  
	    filterGroupCombo.setFireChangeEventOnSetValue(true);  
	    filterGroupCombo.setWidth(100); 
   
	    CfAction _action=new CfAction();
	 	  _action.setTime(GWTDateUtils.getTimeStamp());
	 	  
	 	 CfActionType _cfActionType=new CfActionType();
	 	 _cfActionType.setType("REQUEST_FILTER_CONFIGURATION");
	 	 _action.setCfActionType(_cfActionType);
	 	 
	    
		Server.getInstance().processAction("Tool",_action,this);

	    return filterGroupCombo;
	  
  }
  
  
  
  


@Override
public void onFailure(Throwable caught) {
	// TODO Auto-generated method stub
	
}


@Override
public void onSuccess(Configuration result) {
	
	
	final Map<String, IndicatorFilter> confFilters=result.getFilters();

  for(String filtername: confFilters.keySet()){
	    filterGroupCombo.add(filtername);  
	    }
	    
	    
	    filterGroupCombo.addListener(Events.Change, new Listener<FieldEvent>() {  
	      public void handleEvent(FieldEvent be) {  
	        String filterSetKey = filterGroupCombo.getSimpleValue();
	        
	        
	        if(confFilters.containsKey(filterSetKey)){
	        	
	        	IndicatorFilter filter=confFilters.get(filterSetKey);
	        	Map<String, IndicatorFilterItem> _filteritemProperies=filter.getProperties();
	        	store.removeAll();
	        	
	        	for(String _key: _filteritemProperies.keySet()){
	        		IndicatorFilterItem _filterItem=new IndicatorFilterItem();
	        		_filterItem=filter.getFilterItem(_key);
	        		
	        		
	        	     grid.stopEditing();  
	        	     store.insert(_filterItem, 0);  
	        	     grid.startEditing(store.indexOf(_filterItem), 0); 

	        	}
	        }
	        
	        
	      }  
	    });
	    
	    
	
	
	
}





}
