package de.uds.MonitorInterventionMetafora.client.monitor.dataview.table;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.store.GroupingStore;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.grid.GridGroupRenderer;
import com.extjs.gxt.ui.client.widget.grid.GroupColumnData;
import com.extjs.gxt.ui.client.widget.grid.GroupingView;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Element;

import de.uds.MonitorInterventionMetafora.client.monitor.datamodel.ClientMonitorDataModel;
import de.uds.MonitorInterventionMetafora.client.monitor.dataview.DataViewPanel;
import de.uds.MonitorInterventionMetafora.client.monitor.dataview.DataViewPanelType;
import de.uds.MonitorInterventionMetafora.client.resources.Resources;
import de.uds.MonitorInterventionMetafora.shared.monitor.MonitorConstants;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionPropertyRule;

public class TablePanel extends DataViewPanel {

	private  Grid<CfActionGridRow> tableView;
	
	public TablePanel( ClientMonitorDataModel model, ActionPropertyRule groupingProperty) {
		super(groupingProperty, model);
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
	    ColumnModel columnModel = getColumnModel();
	    
	    tableView = new Grid<CfActionGridRow>(model.getTableViewModel(), columnModel);
		tableView.setView(getGridView(columnModel));
		tableView.setWidth(950);
		tableView.setHeight(550);
		
		tableView.addListener(Events.RowClick, new TableRowDisplaySelectionListener());
		//this.setHeight(560);
		this.add(tableView);
		

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
		    username.setWidth(70);
		    username.setRenderer(getbackgroundColorRenderer());
		    
		    ColumnConfig actionType = new ColumnConfig(MonitorConstants.ACTION_TYPE_LABEL, MonitorConstants.ACTION_TYPE_LABEL, 50);
		    actionType.setWidth(70);
		    actionType.setAlignment(HorizontalAlignment.RIGHT);
		    actionType.setRenderer(getbackgroundColorRenderer());
		    
		    ColumnConfig classification = new ColumnConfig(MonitorConstants.ACTION_CLASSIFICATION_LABEL, MonitorConstants.ACTION_CLASSIFICATION_LABEL, 60);
		    classification.setWidth(70);
		    classification.setRenderer(getbackgroundColorRenderer());
		    
		    ColumnConfig description = new ColumnConfig(MonitorConstants.DESCRIPTION_LABEL, MonitorConstants.DESCRIPTION_LABEL, 50);
		    description.setWidth(300);    
		    description.setRenderer(getbackgroundColorRenderer());
		    
		    ColumnConfig tool = new ColumnConfig(MonitorConstants.TOOL_LABEL, MonitorConstants.TOOL_LABEL, 50);
		    tool.setWidth(75);
		    tool.setRenderer(getbackgroundColorRenderer());
		    
		    ColumnConfig time = new ColumnConfig(MonitorConstants.ACTION_TIME_LABEL, MonitorConstants.ACTION_TIME_LABEL, 50);
		    time.setWidth(75);
		    time.setRenderer(getbackgroundColorRenderer());

		    ColumnConfig challengeName = new ColumnConfig(MonitorConstants.CHALLENGE_NAME_LABEL, MonitorConstants.CHALLENGE_NAME_LABEL, 50);
		    time.setWidth(75);
		    time.setRenderer(getbackgroundColorRenderer());
		    
		    ColumnConfig indicatorType = new ColumnConfig(MonitorConstants.INDICATOR_TYPE_LABEL, MonitorConstants.INDICATOR_TYPE_LABEL, 50);
		    time.setWidth(75);
		    time.setRenderer(getbackgroundColorRenderer());

		    List<ColumnConfig> config = new ArrayList<ColumnConfig>();
		    config.add(username);
		    config.add(actionType);
		    config.add(classification);
		    config.add(description);
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
	public DataViewPanelType getViewType() {
		
		return DataViewPanelType.TABLE;
	}

}
