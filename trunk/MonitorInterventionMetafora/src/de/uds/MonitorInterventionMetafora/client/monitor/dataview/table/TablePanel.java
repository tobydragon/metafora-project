package de.uds.MonitorInterventionMetafora.client.monitor.dataview.table;

import java.util.ArrayList;
import java.util.List;

import com.allen_sauer.gwt.log.client.Log;
import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.Style.SortDir;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.GroupingStore;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.Store;
import com.extjs.gxt.ui.client.store.StoreEvent;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.Label;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.grid.GridGroupRenderer;
import com.extjs.gxt.ui.client.widget.grid.GroupColumnData;
import com.extjs.gxt.ui.client.widget.grid.GroupingView;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.extjs.gxt.ui.client.widget.table.Table;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.view.client.ListDataProvider;
import com.googlecode.gwtTableToExcel.client.TableToExcelClient;

import de.uds.MonitorInterventionMetafora.client.logger.ComponentType;
import de.uds.MonitorInterventionMetafora.client.logger.Logger;
import de.uds.MonitorInterventionMetafora.client.logger.UserActionType;
import de.uds.MonitorInterventionMetafora.client.logger.UserLog;
import de.uds.MonitorInterventionMetafora.client.monitor.datamodel.ClientMonitorDataModel;
import de.uds.MonitorInterventionMetafora.client.monitor.dataview.DataViewPanel;
import de.uds.MonitorInterventionMetafora.client.monitor.dataview.DataViewPanelType;
import de.uds.MonitorInterventionMetafora.client.monitor.dataview.TabbedDataViewPanel;
import de.uds.MonitorInterventionMetafora.client.monitor.filter.FilterGridRow;
import de.uds.MonitorInterventionMetafora.client.monitor.filter.FilterListPanel;
import de.uds.MonitorInterventionMetafora.client.resources.Resources;

import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfActionType;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfContent;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfObject;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfProperty;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfUser;
import de.uds.MonitorInterventionMetafora.shared.monitor.MonitorConstants;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionPropertyRule;

public class TablePanel extends DataViewPanel {

	private  Grid<CfActionGridRow> tableView;
	private FilterListPanel filterPanel;
	private TabbedDataViewPanel tabbedDataViewPanel;
	private ContentPanel tableViewPanel;
	private Button exportBtn;
	private Window popupWaitPanel;

	
	public TablePanel( final ClientMonitorDataModel model, ActionPropertyRule groupingProperty,FilterListPanel filterPanel
			,TabbedDataViewPanel tabbedDataViewPanel) {
		super(groupingProperty, model);
		

		 ColumnModel columnModel = getColumnModel();
		    
		    tableView = new Grid<CfActionGridRow>(model.getTableViewModel(), columnModel);
			tableView.setView(getGridView(columnModel));
			tableView.setWidth(950);
			
		
        	tableView.getStore().sort(MonitorConstants.ACTION_TIME_LABEL, SortDir.DESC);
	
			this.setHeight(560);
			this.filterPanel=filterPanel;
			this.tabbedDataViewPanel=tabbedDataViewPanel;
			tableView.addListener(Events.RowClick, new TableRowDisplaySelectionListener());
			
			
			tableViewPanel=new ContentPanel();
			tableViewPanel.setHeaderVisible(false);
			tableViewPanel.setHeight(545);
			
			popupWaitPanel=new Window();
			popupWaitPanel.setWidth("560");
			popupWaitPanel.setHeight("670");
			popupWaitPanel.setModal(true);
			//popupPanel.setResizable(false);
			popupWaitPanel.setPagePosition(150,60);
			popupWaitPanel.add(new Label("Initilizing  indicator export.Please wait..."));
			popupWaitPanel.setClosable(false);
			
			
			 exportBtn = new Button("Export To Excel",new SelectionListener<ButtonEvent>() {
					@Override
					public void componentSelected(ButtonEvent ce) {
						
						//popupWaitPanel.show();
						exportBtn.setEnabled(false);
						Window popupPanel=new Window();
						popupPanel.setWidth("560");
						popupPanel.setHeight("670");
						popupPanel.setModal(true);
						TableToExcelClient tableToExcelClient = new TableToExcelClient(getFlexTable(model.getFilteredActions()),"Export to EXCEL","indicators.xls");

						popupPanel.add(new Label("Please click following link to export the indicators:"));
						popupPanel.add(tableToExcelClient.build());
						
						popupPanel.setPagePosition(150,50);
						//popupWaitPanel.hide();
						popupPanel.show();
						exportBtn.setEnabled(true);
						
						
					} });
			 
			 exportBtn.setIcon(Resources.ICONS.excel());
			 exportBtn.setToolTip("Export to Excel");
			 

				tableViewPanel.add(tableView);
				tableViewPanel.add(exportBtn);
				this.add(tableViewPanel);
				
			 
			
			
			this.refresh();
			
			
			//this.add(model.getExelClient());
			enableAdjustSize();
			
		
	}

	
	
	
	
	
	@Override
	public int getSelectedRow() {
		
		return 0;
	}
	
	
	public FlexTable getFlexTable(List<CfAction> actions){
		
		
		FlexTable flexTable = new FlexTable();
		
		
		   flexTable.setText(0,0, "Users");
           flexTable.setText(0,1, "Action Type");
           flexTable.setText(0,2, "Classification");
           flexTable.setText(0,3, "Description");
           flexTable.setText(0,4, "Tags");
           flexTable.setText(0,5, "Word Count");
           flexTable.setText(0,6, "Tool");
           flexTable.setText(0,7, "Time");
           flexTable.setText(0,8, "Challenge");
           flexTable.setText(0,9, "Indicator Type");
		
           int rowindex=0;
		for (int i = 0; i < actions.size(); i++) {
			CfActionGridRow row = new CfActionGridRow(actions.get(i));
          
            rowindex++;
            
			
                    flexTable.setText(rowindex,0, row.getUsers());
                    flexTable.setText(rowindex,1, row.getActionType());
                    flexTable.setText(rowindex,2, row.getClassification());
                    flexTable.setText(rowindex,3, row.getDescription());
                    flexTable.setText(rowindex,4, row.getTags());
                    flexTable.setText(rowindex,5, row.getWordCount());
                    flexTable.setText(rowindex,6, row.getTool());
                    flexTable.setText(rowindex,7, row.getTime());
                    flexTable.setText(rowindex,8, row.getChallengeName());
                    flexTable.setText(rowindex,9, row.getIndicatorType());
            
    }

		
		return flexTable;

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
		            	tableViewPanel.setHeight("520");
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
		            	tableViewPanel.setHeight("298");
		           
		            	TablePanel.this.setHeight(302);
		            	TablePanel.this.refresh();
		            	
		            };
		        });
	
		
		
	}

	
	CfActionGridRow getTableHeaders(){
		
		CfAction action=new CfAction();
		action.getCfUsers().add(new CfUser("ID", "ID"));
		action.setCfActionType(new CfActionType(MonitorConstants.ACTION_TYPE_LABEL,MonitorConstants.ACTION_CLASSIFICATION_LABEL, "false"));
		action.setCfContent(new CfContent(MonitorConstants.DESCRIPTION_LABEL));
		action.getCfContent().addProperty(new CfProperty("TOOL", "TOOL"));
		action.getCfContent().addProperty(new CfProperty("CHALLENGE_NAME", "CHALLENGE_NAME"));
		action.getCfContent().addProperty(new CfProperty("INDICATOR_TYPE",MonitorConstants.INDICATOR_TYPE_LABEL));
		action.addObject(new CfObject("Object", "Object"));
		action.getCfObjects().get(0).addProperty(new CfProperty(MonitorConstants.TAGS,MonitorConstants.TAGS));
		action.getCfObjects().get(0).addProperty(new CfProperty(MonitorConstants.WORD_COUNT, MonitorConstants.WORD_COUNT_LABEL));
		action.setTime(0);
		CfActionGridRow headers=new CfActionGridRow(action);
		
		return headers;
	}
}
