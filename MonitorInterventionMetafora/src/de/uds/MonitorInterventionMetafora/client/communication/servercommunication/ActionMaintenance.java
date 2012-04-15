package de.uds.MonitorInterventionMetafora.client.communication.servercommunication;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.widget.ComponentManager;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.grid.EditorGrid;
//import com.extjs.gxt.ui.client.widget.button.Button;
//import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.Timer;
import com.extjs.gxt.ui.client.widget.grid.Grid;
//import com.google.gwt.user.client.ui.Button;


import de.uds.MonitorInterventionMetafora.client.actionresponse.RequestUpdateCallBack;
import de.uds.MonitorInterventionMetafora.client.communication.ServerCommunication;
import de.uds.MonitorInterventionMetafora.client.datamodels.EntitiesComboBoxModel;
import de.uds.MonitorInterventionMetafora.client.datamodels.EntityViewModel;
import de.uds.MonitorInterventionMetafora.client.datamodels.IndicatorFilterItemGridRowModel;
import de.uds.MonitorInterventionMetafora.client.datamodels.TableViewModel;
import de.uds.MonitorInterventionMetafora.client.manager.ClientInterfaceManager;
import de.uds.MonitorInterventionMetafora.client.view.charts.ExtendedColumnChart;
import de.uds.MonitorInterventionMetafora.client.view.charts.ExtendedPieChart;
import de.uds.MonitorInterventionMetafora.client.view.grids.IndicatorGridRowItem;
//import de.uds.MonitorInterventionMetafora.client.view.grids.IndicatorEntity;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfUser;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.FilterAttributeName;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.FilterItemType;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.IndicatorEntity;
import de.uds.MonitorInterventionMetafora.shared.utils.GWTUtils;

public class ActionMaintenance extends Timer implements RequestUpdateCallBack{

	public  List<CfAction> activecfActions;
	private ClientInterfaceManager interfaceManager;
	
	
	public ActionMaintenance(){
		
		activecfActions=new ArrayList<CfAction>();
		interfaceManager=new ClientInterfaceManager();
		
		
		
	}
	
	
	public List<CfAction> getAllActiveActionList(){
		
		return activecfActions;
	} 

	public void setActiveActionList(List<CfAction> _activecfActions){
		
		activecfActions.addAll(_activecfActions);
		
	}
	public void startMaintenance(){
		this.scheduleRepeating(5000);
		
		
		
	}
	
	public void stopMaintenance(){
	this.cancel();
		
	}
	
	
	 public void refreshTableView(){
		 //TableViewModel tvm=new TableViewModel( _maintenance);
		 TableViewModel tvm=new TableViewModel(this);
			
		 
		  // Grid<IndicatorGridRowItem> editorGrid = 
		   Grid<IndicatorGridRowItem> _grid = interfaceManager.getTableViewEditorGrid();
		   _grid.getStore().removeAll();
		   _grid.getStore().add(tvm.parseToIndicatorGridRowList(true));
		 
		 //Button _refreshBtn =  (Button) ComponentManager.get().get("_refreshBtn").asWidget();
        // _refreshBtn.s
		 //if(_refreshBtn!=null)
        //_refreshBtn.fireEvent(Events.OnClick);
         
		 
	 }

	 
	 
	
	 public void refreshColumnChart(){
	
		 
		 EntityViewModel model=new EntityViewModel(this);
		 
			ExtendedColumnChart _barChartPanel = interfaceManager.getColumnChart();
    		
    		if( _barChartPanel!=null){
    			model.sliptActions(true);
    			
    			IndicatorEntity _defaltEntity=new IndicatorEntity();
    			_defaltEntity.setEntityName(FilterAttributeName.CLASSIFICATION.toString());
    			_defaltEntity.setType(FilterItemType.ACTION_TYPE);
    			
    			
    			_barChartPanel.getBarChart().draw(model.getEntityDataTable(_defaltEntity),_barChartPanel.getBarChartOptions(model.getMaxValue()));
    			
    		
    			_barChartPanel.layout();
    			
    			
    			VerticalPanel _comboColumnChartpanel = interfaceManager.getColumnChartVerticalPanel();
    			_comboColumnChartpanel.layout();
    		
    			
    			ComboBox<EntitiesComboBoxModel> comboColumnChartType=interfaceManager.getColumnChartGroupingComboBox();

    			comboColumnChartType.clearSelections();
    			
    			TabItem _columnChartTable =interfaceManager.getColumChartViewTabItem();
    			_columnChartTable.layout();
		 
	 }
    		}
	 
	 

	 
	 public void refreshPieChart(){
		 
		 
		 
		 
		 EntityViewModel model=new EntityViewModel(this);
		 
			ExtendedPieChart _pieChartPanel = interfaceManager.getExtendedPieChart();
 		
 		if( _pieChartPanel!=null){
 			model.sliptActions(true);
 			
 			IndicatorEntity _defaltEntity=new IndicatorEntity();
 			_defaltEntity.setEntityName(FilterAttributeName.CLASSIFICATION.toString());
 			_defaltEntity.setType(FilterItemType.ACTION_TYPE);
 			
 			
 			_pieChartPanel.getPieChart().draw(model.getEntityDataTable(_defaltEntity),_pieChartPanel.getPieChartOptions());
 			
 		
 			_pieChartPanel.layout();
 			
 			
 			VerticalPanel _comboPieChartpanel = interfaceManager.getPieChartGroupingComboContainer();
 			_comboPieChartpanel.layout();
 		
 		
 			
			ComboBox<EntitiesComboBoxModel> comboPieChartType=interfaceManager.getPieChartGroupingComboBox();
 			
 			comboPieChartType.clearSelections();
 			
 			TabItem _pieChartTable = interfaceManager.getPieChartViewTabItem();
 			_pieChartTable.layout();
		 
	 }
		 
		 
		 
		 
	 }
	
	
	
	@Override
	public void onFailure(Throwable caught) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSuccess(List<CfAction> result) {
				if(result==null){
					System.out.println("Client: No Action Update Recieved!");
				return;
				}
		activecfActions.addAll(result);
		
	}

	
	
	CfAction getLastAction(){
		
		if(activecfActions.size()<=0)
			return null;
		
		int index=activecfActions.size()-1;
		return activecfActions.get(index);
	}

	int counter=0;
	@Override
	public void run() {
		
		ServerCommunication.getInstance().processAction(getLastAction(),this);
		
	}


	
	
}
