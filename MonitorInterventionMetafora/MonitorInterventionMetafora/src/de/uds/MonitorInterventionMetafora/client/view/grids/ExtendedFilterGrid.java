/*
 * Ext GWT 2.2.5 - Ext for GWT
 * Copyright(c) 2007-2010, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package de.uds.MonitorInterventionMetafora.client.view.grids;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;








import com.extjs.gxt.ui.client.Style.HorizontalAlignment;

import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.Store;
import com.extjs.gxt.ui.client.store.StoreEvent;
import com.extjs.gxt.ui.client.widget.BoxComponent;
import com.extjs.gxt.ui.client.widget.ContentPanel;

import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.form.CheckBox;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;

import com.extjs.gxt.ui.client.widget.grid.CellEditor;
import com.extjs.gxt.ui.client.widget.grid.CheckColumnConfig;
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

import de.uds.MonitorInterventionMetafora.client.communication.actionresponses.RequestConfigurationCallBack;
import de.uds.MonitorInterventionMetafora.client.communication.servercommunication.ServerCommunication;
import de.uds.MonitorInterventionMetafora.client.datamodels.IndicatorFilterItemGridRowModel;
import de.uds.MonitorInterventionMetafora.client.resources.Resources;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfActionType;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.Configuration;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.IndicatorEntity;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.IndicatorFilter;
import de.uds.MonitorInterventionMetafora.shared.utils.GWTDateUtils;

public class ExtendedFilterGrid  extends LayoutContainer implements RequestConfigurationCallBack {

	private String groupingItem="";
	
	private Map<String, IndicatorFilterItemGridRowModel> configurationFilters;
	
	 EditorGrid<IndicatorFilterItemGridRowModel> grid;
	 ListStore<IndicatorFilterItemGridRowModel> store;
	 SimpleComboBox<String> filterGroupCombo;
	
	public ExtendedFilterGrid(String _groupingItem){
		
		groupingItem=_groupingItem;
	}
	
	

	public ExtendedFilterGrid(){}
	
  @Override
  protected void onRender(Element parent, int index) {
    super.onRender(parent, index);
    setLayout(new FlowLayout(1));

   store = new ListStore<IndicatorFilterItemGridRowModel>();
 
    
 
    List<IndicatorFilterItemGridRowModel> filters=new ArrayList<IndicatorFilterItemGridRowModel>();
    IndicatorFilterItemGridRowModel ft=new IndicatorFilterItemGridRowModel();
    ft.setProperty("MapID");
    ft.setValue("1");
    ft.setType("Action");
   
    filters.add(ft);
    
    IndicatorFilterItemGridRowModel fts=new IndicatorFilterItemGridRowModel();
    fts.setProperty("User");
    fts.setValue("Ugur");
    fts.setType("Content");
    filters.add(fts);
    
    store.add(filters);
    
    
    ColumnConfig _type = new ColumnConfig("filtertype", "filtertype", 50);
    _type.setHeader("Type");
    _type.setWidth(170);
    
    ColumnConfig _property = new ColumnConfig("property", "property", 50);
    _property.setHeader("Property");  
    _property.setWidth(170);
    
    
    ColumnConfig _value = new ColumnConfig("value", "value", 50);
    _value.setHeader("Value");
    _value.setWidth(170);
    
    
    CheckColumnConfig checkColumn = new CheckColumnConfig("indoor", "Indoor?", 55);  
    CellEditor checkBoxEditor = new CellEditor(new CheckBox());  
    checkColumn.setEditor(checkBoxEditor); 
   
    
    
    
    
    
    
    GridCellRenderer<IndicatorFilterItemGridRowModel> buttonRenderer = new GridCellRenderer<IndicatorFilterItemGridRowModel>() {

        private boolean init;

        public Object render(final IndicatorFilterItemGridRowModel model, String property, ColumnData config, final int rowIndex,
            final int colIndex, final ListStore<IndicatorFilterItemGridRowModel> store, Grid<IndicatorFilterItemGridRowModel> grid) {
          if (!init) {
            init = true;
            grid.addListener(Events.ColumnResize, new Listener<GridEvent<IndicatorFilterItemGridRowModel>>() {

              public void handleEvent(GridEvent<IndicatorFilterItemGridRowModel> be) {
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

            	
            	
            	
            	Info.display(model.getProperty(), "<ul><li>" + model.getValue()+ " is removed!" + "</li></ul>");
            
            	String _key=model.getProperty()+"-"+model.getValue();
            	
            	//DataModel.getActiveFilters().remove(_key);
            	
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
    config.add( buttoncolumn);
    
   // config.add(groupingItem);

     ColumnModel cm = new ColumnModel(config);

     
     grid = new EditorGrid<IndicatorFilterItemGridRowModel>(store, cm);

    grid.setBorders(true);
    grid.setId("_filterItemGrid");
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
   
    grid.getStore().addListener(Store.Add, new Listener<StoreEvent<IndicatorEntity>>() {
          public void handleEvent(StoreEvent<IndicatorEntity> be) {
        	  //Info.display("Info","addedd");
        	  
        	//  filterGroup.clearSelections();
          }
        });
    
    grid.getStore().addListener(Store.Remove, new Listener<StoreEvent<IndicatorEntity>>() {
        public void handleEvent(StoreEvent<IndicatorEntity> be) {
      	 
        	//Info.display("Info","remove");
        	//filterGroup.clearSelections();
        }
      });
    
    ToolBar toolBar = new ToolBar();  
    Button addbtn = new Button("Add Filter");  
    addbtn.addSelectionListener(new SelectionListener<ButtonEvent>() {  
  
      @Override  
      public void componentSelected(ButtonEvent ce) {  
    	  IndicatorFilterItemGridRowModel filter = new IndicatorFilterItemGridRowModel();  
    filter.setProperty("Tool");
    filter.setValue("Lasad");
     
  
        grid.stopEditing();  
        store.insert(filter, 0);  
        grid.startEditing(store.indexOf(filter), 0);  
      }  
  
    });  
    toolBar.add(addbtn);  
    
    
    
    toolBar.add(new LabelToolItem("Filter Set:"));  
     
  
   toolBar.add(FilterSetComboBox());
    
    
    Button clearbtn = new Button("Clear",new SelectionListener<ButtonEvent>() {

		@Override
		public void componentSelected(ButtonEvent ce) {
		

		    	 Info.display("Clear","All filters are removed!!");
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
    panel.setCollapsible(true);
    panel.setFrame(true);
    
    panel.setSize(650, 140);
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
  
  


		
  
  
  SimpleComboBox<String> FilterSetComboBox(){
	  
	  	filterGroupCombo = new SimpleComboBox<String>();  
	    filterGroupCombo.setTriggerAction(TriggerAction.ALL);  
	    filterGroupCombo.setEditable(false);  
	    filterGroupCombo.setFireChangeEventOnSetValue(true);  
	    filterGroupCombo.setWidth(100);
	    filterGroupCombo.setId("_filterGroupCombo");
	    
   
	    CfAction _action=new CfAction();
	 	  _action.setTime(GWTDateUtils.getTimeStamp());
	 	  
	 	 CfActionType _cfActionType=new CfActionType();
	 	 _cfActionType.setType("REQUEST_FILTER_CONFIGURATION");
	 	 _action.setCfActionType(_cfActionType);
	 	 
	    
		ServerCommunication.getInstance().processAction("Tool",_action,this);

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
	        	Map<String, IndicatorEntity> _filteritemProperies=filter.getProperties();
	        	store.removeAll();
	        	
	        	for(String _key: _filteritemProperies.keySet()){
	        		IndicatorFilterItemGridRowModel _filterItem=new IndicatorFilterItemGridRowModel();
	        		//TODO _filterItem=filter.getFilterItem(_key);
	        		
	        		
	        	     grid.stopEditing();  
	        	     store.insert(_filterItem, 0);  
	        	     grid.startEditing(store.indexOf(_filterItem), 0); 

	        	}
	        }
	        
	        
	      }  
	    });
	    
	    
	
	
	
}





}
