/*
 * Ext GWT 2.2.5 - Ext for GWT
 * Copyright(c) 2007-2010, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package com.analysis.client.view.grids;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import com.analysis.client.TestData;

import com.analysis.client.components.ActionObject;
import com.analysis.client.datamodels.ExtendedActionFilter;
import com.analysis.client.datamodels.User;
import com.analysis.client.resources.Resources;
import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.store.GroupingStore;
import com.extjs.gxt.ui.client.store.ListStore;
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
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.GridEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.button.Button;

public class ExtendedFilterGrid extends LayoutContainer {

	private String groupingItem="";
	private List<User> users;
	
	private Map<String, List<ExtendedActionFilter>> filterSets;
	
	 EditorGrid<ExtendedActionFilter> grid;
	 ListStore<ExtendedActionFilter> store;
	
	public ExtendedFilterGrid(String _groupingItem){
		
		groupingItem=_groupingItem;
	}
	
	
public ExtendedFilterGrid(String _groupingItem,List<User> myusers){
		
		groupingItem=_groupingItem;
		users=myusers;
	}
	
	
	
  @Override
  protected void onRender(Element parent, int index) {
    super.onRender(parent, index);
    setLayout(new FlowLayout(1));

   store = new ListStore<ExtendedActionFilter>(); 
    
 
    List<ExtendedActionFilter> filters=new ArrayList<ExtendedActionFilter>();
    ExtendedActionFilter ft=new ExtendedActionFilter();
    ft.setProperty("MapID");
    ft.setValue("1");
    filters.add(ft);
    ExtendedActionFilter fts=new ExtendedActionFilter();
    fts.setProperty("User");
    fts.setValue("Ugur");
    filters.add(fts);
    
    store.add(filters);  
    ColumnConfig _property = new ColumnConfig("property", "property", 50);
    _property.setHeader("Property");  
    
    
    ColumnConfig _value = new ColumnConfig("value", "value", 50);
    _value.setHeader("Value");
    
    
    CheckColumnConfig checkColumn = new CheckColumnConfig("indoor", "Indoor?", 55);  
    CellEditor checkBoxEditor = new CellEditor(new CheckBox());  
    checkColumn.setEditor(checkBoxEditor); 
   
    
    
    
    
    
    
    GridCellRenderer<ExtendedActionFilter> buttonRenderer = new GridCellRenderer<ExtendedActionFilter>() {

        private boolean init;

        public Object render(final ExtendedActionFilter model, String property, ColumnData config, final int rowIndex,
            final int colIndex, final ListStore<ExtendedActionFilter> store, Grid<ExtendedActionFilter> grid) {
          if (!init) {
            init = true;
            grid.addListener(Events.ColumnResize, new Listener<GridEvent<ExtendedActionFilter>>() {

              public void handleEvent(GridEvent<ExtendedActionFilter> be) {
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
            
            	store.remove(model);
            	
            
            
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
      buttoncolumn.setWidth(34);
      buttoncolumn.setRenderer(buttonRenderer);
     

    
    
    List<ColumnConfig> config = new ArrayList<ColumnConfig>();
    config.add(_property);
    config.add(_value);
    //config.add(checkColumn);
    config.add( buttoncolumn);
    
   // config.add(groupingItem);

     ColumnModel cm = new ColumnModel(config);
/*
     GroupingView view = new GroupingView();
    view.setShowGroupedColumn(false);
    view.setForceFit(true);
    
    view.setGroupRenderer(new GridGroupRenderer() {
      public String render(GroupColumnData data) {
        String f = cm.getColumnById(data.field).getHeader();
        String l = data.models.size() == 1 ? "Item" : "Items";
        return f + ": " + data.group + " (" + data.models.size() + " " + l + ")";
      }
    });
*/
     grid = new EditorGrid<ExtendedActionFilter>(store, cm);
//    view.setShowGroupedColumn(true);
  //  grid.setView(view);
    grid.setBorders(true);
   
    
    
    
    ToolBar toolBar = new ToolBar();  
    Button add = new Button("Add Filter");  
    add.addSelectionListener(new SelectionListener<ButtonEvent>() {  
  
      @Override  
      public void componentSelected(ButtonEvent ce) {  
    ExtendedActionFilter filter = new ExtendedActionFilter();  
    filter.setProperty("Tool");
    filter.setValue("Lasad");
     
  
        grid.stopEditing();  
        store.insert(filter, 0);  
        grid.startEditing(store.indexOf(filter), 0);  
      }  
  
    });  
    toolBar.add(add);  
    
    
    
    toolBar.add(new LabelToolItem("Filter Set:"));  
     
  
   // toolBar.add(FilterSetComboBox());
    
    
    Button clearbtn = new Button("Clear");  
    add.addSelectionListener(new SelectionListener<ButtonEvent>() {  
  
      @Override  
      public void componentSelected(ButtonEvent ce) {  
    	  
    	  Info.display("Clear","Clear");
     
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
    panel.setSize(260, 160);
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
  
  
  
  public void setFilterSet(Map<String, List<ExtendedActionFilter>> _filterSet){
	  
	  filterSets=_filterSet;
	  
  }
  SimpleComboBox<String> FilterSetComboBox(){
	  
	  final SimpleComboBox<String> filterGroup = new SimpleComboBox<String>();  
	    filterGroup.setTriggerAction(TriggerAction.ALL);  
	    filterGroup.setEditable(false);  
	    filterGroup.setFireChangeEventOnSetValue(true);  
	    filterGroup.setWidth(100); 
	    
	    
	    for(String key:filterSets.keySet()){
	    filterGroup.add(key);  
	    }
	    //filterGroup.add("Simple");  
	   // filterGroup.setSimpleValue("Multi");  
	    
	    
	    filterGroup.addListener(Events.Change, new Listener<FieldEvent>() {  
	      public void handleEvent(FieldEvent be) {  
	        String filterSetKey = filterGroup.getSimpleValue();
	        
	        
	        if(filterSets.containsKey(filterSetKey)){
	        
	        	List<ExtendedActionFilter> filterList=new ArrayList<ExtendedActionFilter>();
	        	
	        	filterList=filterSets.get(filterSetKey);
	        	
	        	for(ExtendedActionFilter af: filterList){
	        		
	        	     grid.stopEditing();  
	        	     store.insert(af, 0);  
	        	     grid.startEditing(store.indexOf(af), 0); 
	        	}
	        	
	        	
	        	
	        	
	        }
	        
	        
	        
	        
	        /*if(simple){
	        	
	        	Info.display("Simpke","simple");
	        }
	        else {
	        
	        	Info.display("not","not");
	        }
	        */
	        // sm.deselectAll();  
	        //sm.setSelectionMode(simple ? SelectionMode.SIMPLE : SelectionMode.MULTI);  
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
