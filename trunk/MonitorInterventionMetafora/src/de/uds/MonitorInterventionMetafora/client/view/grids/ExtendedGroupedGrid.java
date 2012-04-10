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



import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.data.ChangeEvent;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.GridEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.GroupingStore;
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
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridGroupRenderer;
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


import de.uds.MonitorInterventionMetafora.client.communication.servercommunication.ActionMaintenance;
import de.uds.MonitorInterventionMetafora.client.datamodels.TableViewModel;
import de.uds.MonitorInterventionMetafora.client.resources.Resources;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.IndicatorEntity;


public class ExtendedGroupedGrid extends  LayoutContainer {
	public CheckBox autoRefresh;
	private boolean  ignoreNotifications;
	Timer tableViewTimer;
	List<IndicatorGridRowItem> indicators;
	TableViewModel tvm;
	ActionMaintenance maintenance;
	Label _indicatorCount;
	GroupingStore<IndicatorGridRowItem> store;
	
	public ExtendedGroupedGrid(ActionMaintenance _maintenance){
		maintenance=_maintenance;
		tvm=new TableViewModel(maintenance);
		_indicatorCount=new Label();
		store = new GroupingStore<IndicatorGridRowItem>();
		ignoreNotifications=false;
	    indicators=tvm.parseToIndicatorGridRowList(false);
		
	}

public ExtendedGroupedGrid(List<IndicatorGridRowItem> _indicator){
		
		indicators=_indicator;
	}

	

@Override
  protected void onRender(Element parent, int index) {
    super.onRender(parent, index);
    setLayout(new FlowLayout(5));

    

    store.add(indicators);  
    
    
    store.groupBy("classification");

    ColumnConfig username = new ColumnConfig("name", "User", 50);
    username.setWidth(70);
    ColumnConfig actionType = new ColumnConfig("actiontype", "Action", 50);
    actionType.setWidth(70);
    actionType.setAlignment(HorizontalAlignment.RIGHT);
    ColumnConfig classification = new ColumnConfig("classification", "Classification", 60);
    classification.setWidth(70);
    ColumnConfig description = new ColumnConfig("description", "Description", 50);
    description.setWidth(250);
    ColumnConfig time = new ColumnConfig("time", "Time", 50);
    time.setWidth(75);
    ColumnConfig date = new ColumnConfig("date", "Date", 20);
    date.setWidth(75);
    
    date.setDateTimeFormat(DateTimeFormat.getFormat("MM/dd/y"));
    

    List<ColumnConfig> config = new ArrayList<ColumnConfig>();
    config.add(username);
    config.add(actionType);
    config.add(classification);
    config.add(description);
    config.add(time);
    config.add(date);
  

    final ColumnModel cm = new ColumnModel(config);

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

    
    
    
    Grid<IndicatorGridRowItem> grid = new Grid<IndicatorGridRowItem>(store, cm);
    view.setShowGroupedColumn(true);
    grid.setView(view);
    grid.setBorders(true);
    grid.setId("_tableViewGrid");
    
    grid.getStore().addListener(Store.Add, new Listener<StoreEvent<IndicatorGridRowItem>>() {
        public void handleEvent(StoreEvent<IndicatorGridRowItem> be) {
      	
        	_indicatorCount.setText("Total Indicador Count: "+store.getCount());
        	
        }
      });
    

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
    		store.add(tvm.parseToIndicatorGridRowList(true));
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
    toolBar.add(refreshbtn);
    toolBar.add(ignoreNotification);
   
   ToolBar _buttomBar=new ToolBar();
   _indicatorCount.setPosition(550, 0);
   _buttomBar.add(_indicatorCount);
    
    ContentPanel panel = new ContentPanel();
    panel.setHeading("Indicator List");
    panel.setIcon(Resources.ICONS.table());
    panel.setCollapsible(false);
    panel.setFrame(true);
    panel.setSize(590, 335);
    panel.setLayout(new FitLayout());
    grid.setWidth(590);
    grid.setHeight(330);
    panel.add(grid);
    
    
    
    panel.setTopComponent(toolBar);
    panel.setBottomComponent(_buttomBar);
   
   
  
    grid.getAriaSupport().setLabelledBy(panel.getHeader().getId() + "-label");
    add(panel);
    
   
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
