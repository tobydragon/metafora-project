package de.uds.MonitorInterventionMetafora.client.monitor.dataview.table;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.Style.SortDir;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.store.GroupingStore;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.grid.GridGroupRenderer;
import com.extjs.gxt.ui.client.widget.grid.GroupColumnData;
import com.extjs.gxt.ui.client.widget.grid.GroupingView;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Element;

import de.uds.MonitorInterventionMetafora.client.monitor.datamodel.ClientMonitorDataModel;
import de.uds.MonitorInterventionMetafora.client.monitor.dataview.DataViewPanel;
import de.uds.MonitorInterventionMetafora.client.monitor.dataview.DataViewPanelType;
import de.uds.MonitorInterventionMetafora.client.monitor.dataview.TabbedDataViewPanel;
import de.uds.MonitorInterventionMetafora.client.monitor.filter.FilterListPanel;

import de.uds.MonitorInterventionMetafora.shared.monitor.MonitorConstants;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionPropertyRule;

public class TablePanel extends DataViewPanel {

	private  Grid<CfActionGridRow> tableView;
	private FilterListPanel filterPanel;
	private TabbedDataViewPanel tabbedDataViewPanel;
	
	public TablePanel( ClientMonitorDataModel model, ActionPropertyRule groupingProperty,FilterListPanel filterPanel
			,TabbedDataViewPanel tabbedDataViewPanel) {
		super(groupingProperty, model);
		
		 ColumnModel columnModel = getColumnModel();
		    
		    tableView = new Grid<CfActionGridRow>(model.getTableViewModel(), columnModel);
			tableView.setView(getGridView(columnModel));
			tableView.setWidth(950);
			
		
//			tableView.getStore().sort(MonitorConstants.ACTION_TIME_LABEL, SortDir.DESC);
	
			this.setHeight(560);
			this.filterPanel=filterPanel;
			this.tabbedDataViewPanel=tabbedDataViewPanel;
			tableView.addListener(Events.RowClick, new TableRowDisplaySelectionListener());
			//this.setHeight(560);
			this.add(tableView);
			enableAdjustSize();
		
	}

	@Override
	public int getSelectedRow() {
		
		return 0;
	}
	
	public void refresh() {
		if (groupingProperty != null && !groupingProperty.equals("") ){
			model.getTableViewModel().groupBy(groupingProperty.getDisplayText());
			
		}
		else {
			model.getTableViewModel().clearGrouping();
		}
	}
	
	@Override
	  protected void onRender(Element parent, int index) {
	    super.onRender(parent, index);


	}
	
//---------------------------------------------
	GroupingView getGridView(final ColumnModel cm){
		GroupingView view = new GroupingView();
		    view.setShowGroupedColumn(false);
		    view.setForceFit(true);
		    
		    //straight from - http://dev.sencha.com/deploy/gxt-2.2.5/docs/api/index.html	
		    view.setGroupRenderer(new GridGroupRenderer() {
		      public String render(GroupColumnData data) {
		    	  String f="None";
		    	  if (data.field != null &&  !data.field.equals("")){
		    		  try {
		    			  f = cm.getColumnById(data.field).getHeader();
		    		  }
		    		  catch (Exception e) {
		    			  System.err.println("ERROR\t\tTablePanel.getGridView: no column for field:" + data.field + ":");
		    		  }
		    	  }
		    	  else {
	    			  System.err.println("ERROR\t\tTablePanel.getGridView: no column for field:" + data.field + ":");  
		    	  }
		        
		        String l = data.models.size() == 1 ? "Indicator" : "Indicators";
		        return f + ": " + data.group + " (" + data.models.size() + " " + l + ")";
		      }
		    });		
		return view;
	}
	
	
	private ColumnModel getColumnModel(){
		 ColumnConfig username = new ColumnConfig(MonitorConstants.USER_ID_LABEL, MonitorConstants.USER_ID_LABEL, 50);
		    username.setWidth(50);
		   // username.setRenderer(getbackgroundColorRenderer());
		    
		    ColumnConfig actionType = new ColumnConfig(MonitorConstants.ACTION_TYPE_LABEL, MonitorConstants.ACTION_TYPE_LABEL, 50);
		    actionType.setWidth(50);
		    actionType.setAlignment(HorizontalAlignment.RIGHT);
		    //actionType.setRenderer(getbackgroundColorRenderer());
		    
		    ColumnConfig classification = new ColumnConfig(MonitorConstants.ACTION_CLASSIFICATION_LABEL, MonitorConstants.ACTION_CLASSIFICATION_LABEL, 60);
		    classification.setWidth(50);
		    //classification.setRenderer(getbackgroundColorRenderer());
		    
		    ColumnConfig description = new ColumnConfig(MonitorConstants.DESCRIPTION_LABEL, MonitorConstants.DESCRIPTION_LABEL, 50);
		    description.setWidth(250);    
		    //description.setRenderer(getbackgroundColorRenderer());
		    
		    ColumnConfig tool = new ColumnConfig(MonitorConstants.TOOL_LABEL, MonitorConstants.TOOL_LABEL, 50);
		    tool.setWidth(50);
		    //tool.setRenderer(getbackgroundColorRenderer());
		    

		    ColumnConfig challengeName = new ColumnConfig(MonitorConstants.CHALLENGE_NAME_LABEL, MonitorConstants.CHALLENGE_NAME_LABEL, 50);
		    challengeName.setWidth(75);
		    //challengeName.setRenderer(getbackgroundColorRenderer());
		   
		    
		    ColumnConfig time = new ColumnConfig(MonitorConstants.ACTION_TIME_LABEL, MonitorConstants.ACTION_TIME_LABEL, 50);
		    time.setWidth(50);
		    //time.setRenderer(getbackgroundColorRenderer());
		    time.setDateTimeFormat(DateTimeFormat.getFormat("yyyy-MM-dd G HH:mm:ss"));
		    

	        
		    ColumnConfig tags = new ColumnConfig(MonitorConstants.TAGS_LABEL, MonitorConstants.TAGS_LABEL, 50);
		    tags.setWidth(50);
		    tags.setRenderer(getbackgroundColorRenderer());
		    
		    ColumnConfig wordcount = new ColumnConfig(MonitorConstants.WORD_COUNT_LABEL, MonitorConstants.WORD_COUNT_LABEL, 30);
		    wordcount.setWidth(50);
		    
		    
		    
		    ColumnConfig indicatorType = new ColumnConfig(MonitorConstants.INDICATOR_TYPE_LABEL, MonitorConstants.INDICATOR_TYPE_LABEL, 50);
		    indicatorType.setWidth(75);
		   // indicatorType.setRenderer(getbackgroundColorRenderer());

		    List<ColumnConfig> config = new ArrayList<ColumnConfig>();
		    config.add(username);
		    config.add(actionType);
		    config.add(classification);
		    config.add(description);
		    config.add(tags);
		    config.add(wordcount);
		    config.add(tool);
		    config.add(time);
		    config.add(challengeName);
		    config.add(indicatorType);

		    return new ColumnModel(config);
	}
	
	GridCellRenderer<CfActionGridRow>  getbackgroundColorRenderer(){
		
		
		GridCellRenderer<CfActionGridRow> ColoredGrid = new GridCellRenderer<CfActionGridRow>() {

	        @Override
	        public String render(CfActionGridRow model,
	                String property, ColumnData _config,
	                int rowIndex, int colIndex,
	                ListStore<CfActionGridRow> store,
	                Grid<CfActionGridRow> grid) {

	        	String  valueOfCell ="";
	        	valueOfCell=  model.getTags();
	            
	        	
	        	if(valueOfCell!=null){
	        	
	            if(valueOfCell.toLowerCase().contains("abuse")){
	   
	            	
	            return "<span style='background-color:" +"#FA0053"+ "'>" + valueOfCell+ "</span>";
	            		
	            }
	            else if(valueOfCell.toLowerCase().contains("negati")){
	          
	            	
	            return "<span style='background-color:" +"#D2FF3C"+ "'>" + valueOfCell+ "</span>";
	            		
	            }
	        }
	            
	           return valueOfCell; 



	        }    

	        };
	        
	        return ColoredGrid;
		
	}

	@Override
	public DataViewPanelType getViewType() {
		
		return DataViewPanelType.TABLE;
	}

	@Override
	public void enableAdjustSize() {
		
		
		filterPanel.addListener(Events.Collapse, new Listener<BaseEvent>()
		        {

		            public void handleEvent(BaseEvent be)
		            {
		            	tabbedDataViewPanel.addjustSize(false);
		            	tableView.setHeight(500);
		            	TablePanel.this.setHeight(503);
		            	TablePanel.this.refresh();
		            	
		            	
		            	
		            };
		        });
				 
		filterPanel.addListener(Events.Expand, new Listener<BaseEvent>()
		        {

		            public void handleEvent(BaseEvent be)
		            {
		            	
		            	tabbedDataViewPanel.addjustSize(true);
		            	
		            	tableView.setHeight(300);
		           
		            	TablePanel.this.setHeight(302);
		            	TablePanel.this.refresh();
		            	
		            };
		        });
	
		
		
	}

}
