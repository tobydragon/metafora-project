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




import com.analysis.client.communication.models.DataModel;
import com.analysis.client.communication.server.Server;
import com.analysis.client.components.ActionObject;
import com.analysis.client.datamodels.IndicatorFilter;
import com.analysis.client.datamodels.ExtendedIndicatorFilterItem;
import com.analysis.client.datamodels.Indicator;
import com.analysis.client.resources.Resources;
import com.analysis.client.view.charts.ExtendedPieChart;
import com.analysis.client.view.widgets.TabDataViewPanel;
import com.analysis.client.xml.GWTXmlFragment;
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

public class ExtendedFilterGrid extends LayoutContainer {

	private String groupingItem="";
	
	private Map<String, List<ExtendedIndicatorFilterItem>> filterSets;
	
	 static EditorGrid<ExtendedIndicatorFilterItem> grid;
	 static ListStore<ExtendedIndicatorFilterItem> store;
	 static SimpleComboBox<String> filterGroup;
	
	public ExtendedFilterGrid(String _groupingItem){
		
		groupingItem=_groupingItem;
	}
	
	
public ExtendedFilterGrid(String _groupingItem,List<Indicator> indicator){
		
		groupingItem=_groupingItem;
	
	}
	

	
  @Override
  protected void onRender(Element parent, int index) {
    super.onRender(parent, index);
    setLayout(new FlowLayout(1));

   store = new ListStore<ExtendedIndicatorFilterItem>();
 
    
 
    List<ExtendedIndicatorFilterItem> filters=new ArrayList<ExtendedIndicatorFilterItem>();
    ExtendedIndicatorFilterItem ft=new ExtendedIndicatorFilterItem();
    ft.setProperty("MapID");
    ft.setValue("1");
    ft.setType("Action");
   
    filters.add(ft);
    
    ExtendedIndicatorFilterItem fts=new ExtendedIndicatorFilterItem();
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
   
    
    
    
    
    
    
    GridCellRenderer<ExtendedIndicatorFilterItem> buttonRenderer = new GridCellRenderer<ExtendedIndicatorFilterItem>() {

        private boolean init;

        public Object render(final ExtendedIndicatorFilterItem model, String property, ColumnData config, final int rowIndex,
            final int colIndex, final ListStore<ExtendedIndicatorFilterItem> store, Grid<ExtendedIndicatorFilterItem> grid) {
          if (!init) {
            init = true;
            grid.addListener(Events.ColumnResize, new Listener<GridEvent<ExtendedIndicatorFilterItem>>() {

              public void handleEvent(GridEvent<ExtendedIndicatorFilterItem> be) {
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
            	filterGroup.clearSelections();
            
            
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

     
     grid = new EditorGrid<ExtendedIndicatorFilterItem>(store, cm);

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
   
    grid.getStore().addListener(Store.Add, new Listener<StoreEvent<ExtendedIndicatorFilterItem>>() {
          public void handleEvent(StoreEvent<ExtendedIndicatorFilterItem> be) {
        	  //Info.display("Info","addedd");
        	  
        	//  filterGroup.clearSelections();
          }
        });
    
    grid.getStore().addListener(Store.Remove, new Listener<StoreEvent<ExtendedIndicatorFilterItem>>() {
        public void handleEvent(StoreEvent<ExtendedIndicatorFilterItem> be) {
      	 
        	//Info.display("Info","remove");
        	//filterGroup.clearSelections();
        }
      });
    
    ToolBar toolBar = new ToolBar();  
    Button add = new Button("Add Filter");  
    add.addSelectionListener(new SelectionListener<ButtonEvent>() {  
  
      @Override  
      public void componentSelected(ButtonEvent ce) {  
    ExtendedIndicatorFilterItem filter = new ExtendedIndicatorFilterItem();  
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
		    filterGroup.clearSelections();
		   
			
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
  
  
  public static EditorGrid<ExtendedIndicatorFilterItem> getExtendedFilterGrid(){
	  
	 return grid;	  
  }
  
  
  public static SimpleComboBox<String> getFilterSetListCombo(){
		
		return filterGroup;
	}

		
  
  
  
  public void setFilterMap(List<IndicatorFilter> filterList){
	filterSets=new   HashMap<String, List<ExtendedIndicatorFilterItem>>();
	  
	  for(IndicatorFilter af: filterList){
		
		  String filtername=af.getName();
		  List<ExtendedIndicatorFilterItem> filterProperties=new ArrayList<ExtendedIndicatorFilterItem>();
		  
		for(String _key:af.getProperties().keySet()){

			ExtendedIndicatorFilterItem filterItem=new ExtendedIndicatorFilterItem();
			filterItem.setProperty(_key);
			filterItem.setValue(af.getProperties().get(_key).getValue());
			filterItem.setType(af.getProperties().get(_key).getType());
			
			
			filterProperties.add(filterItem);
			
		}
		
		filterSets.put(filtername,filterProperties);
		  
	  }

	
	  //System.out.println("fdddddd:");
  }
  SimpleComboBox<String> FilterSetComboBox(){
	  
	  	filterGroup = new SimpleComboBox<String>();  
	    filterGroup.setTriggerAction(TriggerAction.ALL);  
	    filterGroup.setEditable(false);  
	    filterGroup.setFireChangeEventOnSetValue(true);  
	    filterGroup.setWidth(100); 
	    
	    
	    
		 Server.getInstance().sendRequest("RequestConfiguration",new AsyncCallback<String>() {
				public void onFailure(Throwable caught) {
					
					
					
				}

				public void onSuccess(String result) {
					
					GWTXmlFragment gxf=new GWTXmlFragment();
															
					setFilterMap(gxf.getActiveConfiguration(result).getFilters());
					
					
				  for(String key:filterSets.keySet()){
					    filterGroup.add(key);  
					    }
					    
					    
					    filterGroup.addListener(Events.Change, new Listener<FieldEvent>() {  
					      public void handleEvent(FieldEvent be) {  
					        String filterSetKey = filterGroup.getSimpleValue();
					        
					        
					        if(filterSets.containsKey(filterSetKey)){
					        
					        	List<ExtendedIndicatorFilterItem> filterList=new ArrayList<ExtendedIndicatorFilterItem>();
					        	
					        	filterList=filterSets.get(filterSetKey);
					        	store.removeAll();
					        	for(ExtendedIndicatorFilterItem af: filterList){
					        		
					        	     grid.stopEditing();  
					        	     store.insert(af, 0);  
					        	     grid.startEditing(store.indexOf(af), 0); 
					        	}
					        	
					        	
					        	
					        	
					        }
					        
					        
					      }  
					    });
					    
					    
					
										
				}
			});
	    
	    
	    

	  
	  return filterGroup;
	  
  }
/*final SimpleComboBox<String> filterGroup = new SimpleComboBox<String>();  
	    filterGroup.setTriggerAction(TriggerAction.ALL);  
	    filterGroup.setEditable(false);  
	    filterGroup.setFireChangeEventOnSetValue(true);  
	    filterGroup.setWidth(100);  
	    filterGroup.add("Multi");  
	    filterGroup.add("Simple");  
	    filterGroup.setSimpleValue("Multi");  
	    filterGroup.addListener(Events.Change, new Listener<FieldEvent>() {  
	      public void handleEvent(FieldEvent be) {  
	        boolean simple = filterGroup.getSimpleValue().equals("Simple");  
	       
	        if(simple){
	        	
	        	Info.display("Simpke","simple");
	        }
	        else {
	        
	        	Info.display("not","not");
	        }
	        // sm.deselectAll();  
	        //sm.setSelectionMode(simple ? SelectionMode.SIMPLE : SelectionMode.MULTI);  
	      }  
	    });
	  
	  return filterGroup;
*/
}
