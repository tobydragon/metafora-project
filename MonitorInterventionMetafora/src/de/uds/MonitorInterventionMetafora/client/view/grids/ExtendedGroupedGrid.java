/*
 * Ext GWT 2.2.5 - Ext for GWT
 * Copyright(c) 2007-2010, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package de.uds.MonitorInterventionMetafora.client.view.grids;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;



import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.data.BaseModel;
import com.extjs.gxt.ui.client.data.ChangeEvent;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.EventType;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.GridEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedListener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.GroupingStore;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.Store;
import com.extjs.gxt.ui.client.store.StoreEvent;
import com.extjs.gxt.ui.client.widget.BoxComponent;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.Label;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.CheckBox;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.form.SimpleComboValue;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.grid.GridGroupRenderer;
import com.extjs.gxt.ui.client.widget.grid.GridSelectionModel;
import com.extjs.gxt.ui.client.widget.grid.GroupColumnData;
import com.extjs.gxt.ui.client.widget.grid.GroupingView;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Timer;
//import com.google.gwt.user.client.ui.CheckBox;

import com.google.gwt.user.client.ui.ClickListener;


import de.uds.MonitorInterventionMetafora.client.communication.servercommunication.UpdatingDataModel;
import de.uds.MonitorInterventionMetafora.client.datamodels.DefaultModel;
import de.uds.MonitorInterventionMetafora.client.datamodels.EntitiesComboBoxModel;
import de.uds.MonitorInterventionMetafora.client.datamodels.IndicatorFilterItemGridRowModel;
import de.uds.MonitorInterventionMetafora.client.datamodels.OperationsComboBoxModel;
import de.uds.MonitorInterventionMetafora.client.datamodels.TableViewModel;
import de.uds.MonitorInterventionMetafora.client.manager.FilteredDataViewManager;
import de.uds.MonitorInterventionMetafora.client.resources.Resources;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.IndicatorProperty;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.IndicatorFilter;


public class ExtendedGroupedGrid extends  LayoutContainer {
	private CheckBox autoRefresh;
	private boolean  ignoreNotifications;
	private Timer tableViewTimer;
	private List<IndicatorGridRowItem> indicators;
	private TableViewModel tvm;
	private UpdatingDataModel maintenance;
	private Label _indicatorCount;
	private  Grid<IndicatorGridRowItem> grid;
	private	GroupingStore<IndicatorGridRowItem> store;
	public ColumnModel cm;
	private FilteredDataViewManager interfaceManager;
	//private SimpleComboBox<ColumnConfig> groupingComboBox;
	
	public ExtendedGroupedGrid(UpdatingDataModel _maintenance, FilteredDataViewManager controller){
		maintenance=_maintenance;
		tvm=new TableViewModel(maintenance);
		_indicatorCount=new Label();
		interfaceManager= controller;
		store = new GroupingStore<IndicatorGridRowItem>();
		ignoreNotifications=false;
	    indicators=tvm.parseToIndicatorGridRowList(false, false);
	   	
	}

	public ExtendedGroupedGrid(List<IndicatorGridRowItem> _indicator){
		
		indicators=_indicator;
	}


	ComboBox<DefaultModel> renderGroupingComboBox(){
		ComboBox<DefaultModel> groupingComboBox=new ComboBox<DefaultModel>();;
		groupingComboBox.setTriggerAction(TriggerAction.ALL);  
		groupingComboBox.setEditable(false);  
		groupingComboBox.setFireChangeEventOnSetValue(true);  
		groupingComboBox.setWidth(100);
		groupingComboBox.setId("_groupingComboBox");
		
		
		groupingComboBox.setDisplayField("displaytext");
		groupingComboBox.setValueField("value");
		ListStore<DefaultModel> _columns=new ListStore<DefaultModel>();
		_columns.add(new DefaultModel("Un Group","nogrouping"));
		for(ColumnConfig _column:cm.getColumns()){
			_columns.add(new DefaultModel(_column.getHeader(),_column.getId()));
		}

	
		final SelectionChangedListener<DefaultModel> comboListenerItem =new SelectionChangedListener<DefaultModel>(){
	        @Override
	        public void selectionChanged(SelectionChangedEvent<DefaultModel> se) { 
	
	        	String _groupbyColumn=se.getSelectedItem().getValue();
	        	
	        	if(_groupbyColumn.equalsIgnoreCase("nogrouping")){
	        		store.clearGrouping();
	        		return;
	        	}
	        	store.groupBy(_groupbyColumn);
	        //  	store.clearGrouping();
	        	grid.disableEvents(true);
	        	grid.setView(getGridView());
	        }
		};
    
		groupingComboBox.setStore(_columns);
		groupingComboBox.addSelectionChangedListener(comboListenerItem);
		return groupingComboBox;
	}
	

	GroupingView getGridView(){
		GroupingView view = new GroupingView();
		    view.setShowGroupedColumn(true);
		    view.setForceFit(true);
		    view.setGroupRenderer(new GridGroupRenderer() {
		      public String render(GroupColumnData data) {
		        String f = cm.getColumnById(data.field).getHeader();
		        String l = data.models.size() == 1 ? "Indicator" : "Indicators";
		        return f + ": " + data.group + " (" + data.models.size() + " " + l + ")";
		      }
		    });
		    
		    
		    view.setShowGroupedColumn(true);
		
		return view;
	}


GridCellRenderer<IndicatorGridRowItem>  getbackgroundColorRenderer(){
	
	
	GridCellRenderer<IndicatorGridRowItem> ColoredGrid = new GridCellRenderer<IndicatorGridRowItem>() {

        @Override
        public String render(IndicatorGridRowItem model,
                String property, ColumnData _config,
                int rowIndex, int colIndex,
                ListStore<IndicatorGridRowItem> store,
                Grid<IndicatorGridRowItem> grid) {

        	String  valueOfCell =  model.get(property); 
            
            if(model.getColor()!=null && model.getColor()!=""){
            	
            	//System.out.println("Setting background:"+model.getColor());
            	
            return "<span style='background-color:" +model.getColor()+ "'>" + valueOfCell+ "</span>";
            		
            }
            
           return valueOfCell; 



        }    

        };
        
        return ColoredGrid;
	
}



@Override
  protected void onRender(Element parent, int index) {
    super.onRender(parent, index);
    setLayout(new FlowLayout(5));

    

    store.add(indicators);  
    
    
    store.groupBy("classification");

    ColumnConfig username = new ColumnConfig("name", "User", 50);
    username.setWidth(70);
    username.setRenderer(getbackgroundColorRenderer());
    
    
     
    
    
   /* username.setRenderer(new GridCellRenderer<BaseModel>() {   
        @Override
        public Object render(BaseModel model, String property, ColumnData config,
                             int rowIndex, int colIndex, ListStore<BaseModel> store,
                             Grid<BaseModel> grid) {
              config.style = "background-color:yellow;";
              Object value = model.get(property);
              return value;         
         }
    });*/
    
    
    ColumnConfig actionType = new ColumnConfig("actiontype", "Action", 50);
    actionType.setWidth(70);
    actionType.setAlignment(HorizontalAlignment.RIGHT);
    actionType.setRenderer(getbackgroundColorRenderer());
    
    ColumnConfig classification = new ColumnConfig("classification", "Classification", 60);
    classification.setWidth(70);
    classification.setRenderer(getbackgroundColorRenderer());
    
    ColumnConfig description = new ColumnConfig("description", "Description", 50);
    description.setWidth(500);    
    description.setRenderer(getbackgroundColorRenderer());
    
    ColumnConfig time = new ColumnConfig("time", "Time", 50);
    time.setWidth(75);
    time.setRenderer(getbackgroundColorRenderer());
    
    ColumnConfig date = new ColumnConfig("date", "Date", 20);
    date.setWidth(75);
    
    date.setDateTimeFormat(DateTimeFormat.getFormat("MM/dd/y"));
    date.setRenderer(getbackgroundColorRenderer());

    List<ColumnConfig> config = new ArrayList<ColumnConfig>();
    config.add(username);
    config.add(actionType);
    config.add(classification);
    config.add(description);
    config.add(time);
    config.add(date);
  

    cm = new ColumnModel(config);

       
    
    
    grid= new Grid<IndicatorGridRowItem>(store, cm);
    
    
    grid.setView(getGridView());
    grid.setBorders(true);
    grid.setId("_tableViewGrid");
    //grid.getSelectionModel().setLocked(true);
    
    grid.getStore().addListener(Store.Add, new Listener<StoreEvent<IndicatorGridRowItem>>() {
        public void handleEvent(StoreEvent<IndicatorGridRowItem> be) {
      	
        	_indicatorCount.setText("Total Indicator Count: "+store.getCount());
        	
        }
      });
    
    System.out.println("Adding grid listener");
    grid.addListener(Events.RowClick, new GridSelectionPopUpListener());
    

    ToolBar toolBar = new ToolBar();  
    Button refreshbtn = new Button();
    refreshbtn.setId("_refreshBtn");
   
    _indicatorCount.setToolTip("Indicator Count");
    _indicatorCount.setId("_countLableID");
    refreshbtn.setToolTip("Refresh");
    refreshbtn.setIcon(Resources.ICONS.refresh());
    refreshbtn.addSelectionListener(new SelectionListener<ButtonEvent>() {  
    	  
        @Override  
        public void componentSelected(ButtonEvent ce) {  

        	
        	store.removeAll(); 
    		store.add(tvm.parseToIndicatorGridRowList(true, false));
    		 _indicatorCount.setText("Total Indicador Count: "+store.getCount());
        	
        	
        }  
    
      });  
    
    
    
    autoRefresh = new CheckBox();
    autoRefresh.setBoxLabel("Auto Refresh");
    autoRefresh.setValue(true);
    
    
    autoRefresh.addListener(Events.Change, new Listener<BaseEvent>()
            {
        public void handleEvent(BaseEvent be)
        {
            
            if (autoRefresh.getValue())
            {
            	
            	 tableViewTimer.scheduleRepeating(10000);
            	 MessageBox.alert("Info", "Auto refresh  is enabled!",null);
            	
            } else
            {
            	tableViewTimer.cancel();
            	 MessageBox.alert("Info", "Auto refresh  is disabled!",null);
            }
            
           
        }
    });    
    
    
    
    
    final CheckBox  ignoreNotification = new CheckBox();
    ignoreNotification.setBoxLabel("Ignore Notifications");
    ignoreNotification.setValue(false);
    
    
    ignoreNotification.addListener(Events.Change, new Listener<BaseEvent>()
            {
        public void handleEvent(BaseEvent be)
        {
            
            if (ignoreNotification.getValue())
            {
            	
            	ignoreNotifications=true;
            	
            } else
            {
            	ignoreNotifications=false;
            	
            }
            
            store.removeAll(); 
    		store.add(tvm.parseToIndicatorGridRowList(true,ignoreNotifications));
    		 _indicatorCount.setText("Total Indicador Count: "+store.getCount());
            
           
        }
    });    
    
    
    
   
    _indicatorCount.setText("Total Indicador Count: "+store.getCount());

    toolBar.add(autoRefresh);
    toolBar.add(ignoreNotification);
    toolBar.add(refreshbtn);
    toolBar.add(renderGroupingComboBox());
    
   ToolBar _buttomBar=new ToolBar();
   _indicatorCount.setPosition(550, 0);
   _buttomBar.add(_indicatorCount);
    
    ContentPanel panel = new ContentPanel();
    panel.setHeading("Indicator List");
    panel.setIcon(Resources.ICONS.table());
    panel.setCollapsible(false);
    panel.setFrame(true);
    panel.setId("_groupedGridPanel");
    //CC remove
    panel.setWidth(960);
   
    
    // panel.setSize(590, 335);
    // panel.setWidth("100%");
    // panel.setHeight("100%");
  
   
   
    
    grid.setWidth(950);
    //CCgrid.setHeight(326);
    grid.setHeight(540);
   // grid.setWidth("100%");
   // grid.setHeight("100%");
    
   //grid.setAutoHeight(true);
   // grid.setAutoWidth(true);
    panel.add(grid);
    
    
    
    panel.setTopComponent(toolBar);
    panel.setBottomComponent(_buttomBar);
   
  
    
    //panel.setLayout(new FitLayout());
   // panel.setAutoHeight(true);
   // panel.setAutoWidth(true);
   this.add(panel);
   //this.setAutoHeight(true);
   //this.setAutoWidth(true);
    
   
    tableViewTimer=new Timer(){
    	@Override
    	public void run() {
    		store.removeAll(); 
    		store.add(tvm.parseToIndicatorGridRowList(true,ignoreNotifications));
    		 _indicatorCount.setText("Total Indicador Count: "+store.getCount());
    		
          }
        };
        
       tableViewTimer.scheduleRepeating(10000); 
  }



}
