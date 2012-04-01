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


import de.uds.MonitorInterventionMetafora.client.communication.actionresponses.RequestUpdateCallBack;
import de.uds.MonitorInterventionMetafora.client.datamodels.EntitiesComboBoxModel;
import de.uds.MonitorInterventionMetafora.client.datamodels.EntityViewModel;
import de.uds.MonitorInterventionMetafora.client.datamodels.IndicatorFilterItemGridRowModel;
import de.uds.MonitorInterventionMetafora.client.datamodels.TableViewModel;
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
	
	
	public ActionMaintenance(){
		
		activecfActions=new ArrayList<CfAction>();
		
		
		
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
			
		 
		   Grid<IndicatorGridRowItem> editorGrid = (Grid<IndicatorGridRowItem>) ComponentManager.get().get("_tableViewGrid");
		   Grid<IndicatorGridRowItem> _grid = editorGrid;
		   _grid.getStore().removeAll();
		   _grid.getStore().add(tvm.parseToIndicatorGridRowList(true));
		 
		 //Button _refreshBtn =  (Button) ComponentManager.get().get("_refreshBtn").asWidget();
        // _refreshBtn.s
		 //if(_refreshBtn!=null)
        //_refreshBtn.fireEvent(Events.OnClick);
         
		 
	 }

	 
	 
	
	 public void refreshColumnChart(){
	
		 
		 EntityViewModel model=new EntityViewModel(this);
		 
			ExtendedColumnChart _barChartPanel = (ExtendedColumnChart) ComponentManager.get().get("barChartVerticalPanel");
    		
    		if( _barChartPanel!=null){
    			model.sliptActions(true);
    			
    			IndicatorEntity _defaltEntity=new IndicatorEntity();
    			_defaltEntity.setEntityName(FilterAttributeName.CLASSIFICATION.toString());
    			_defaltEntity.setType(FilterItemType.ACTION_TYPE);
    			
    			
    			_barChartPanel.getBarChart().draw(model.getEntityDataTable(_defaltEntity),_barChartPanel.getBarChartOptions(model.getMaxValue()));
    			
    		
    			_barChartPanel.layout();
    			
    			
    			VerticalPanel _comboColumnChartpanel = (VerticalPanel) ComponentManager.get().get("barChartFilterPanel");
    			_comboColumnChartpanel.layout();
    		
    			
    			ComboBox<EntitiesComboBoxModel> comboColumnChartType=(ComboBox<EntitiesComboBoxModel>) ComponentManager.get().get("comboColumnChartType");
     			
    			//comboColumnChartType.clear();
    			comboColumnChartType.clearSelections();
    			
    		
    			TabItem _columnChartTable = (TabItem) ComponentManager.get().get("barChartViewTab");
    			_columnChartTable.layout();
		 
	 }
    		}
	 
	 

	 
	 public void refreshPieChart(){
		 
		 
		 
		 
		 EntityViewModel model=new EntityViewModel(this);
		 
			ExtendedPieChart _pieChartPanel = (ExtendedPieChart) ComponentManager.get().get("pieChartVerticalPanel");
 		
 		if( _pieChartPanel!=null){
 			model.sliptActions(true);
 			
 			IndicatorEntity _defaltEntity=new IndicatorEntity();
 			_defaltEntity.setEntityName(FilterAttributeName.CLASSIFICATION.toString());
 			_defaltEntity.setType(FilterItemType.ACTION_TYPE);
 			
 			
 			_pieChartPanel.getPieChart().draw(model.getEntityDataTable(_defaltEntity),_pieChartPanel.getPieChartOptions());
 			
 		
 			_pieChartPanel.layout();
 			
 			
 			VerticalPanel _comboPieChartpanel = (VerticalPanel) ComponentManager.get().get("pieChartFilterPanel");
 			_comboPieChartpanel.layout();
 		
 		
 			ComboBox<EntitiesComboBoxModel> comboBox = (ComboBox<EntitiesComboBoxModel>) ComponentManager.get().get("comboPieChartType");
			ComboBox<EntitiesComboBoxModel> comboPieChartType=comboBox;
 			
 			comboPieChartType.clearSelections();
 			
 			TabItem _pieChartTable = (TabItem) ComponentManager.get().get("pieChartViewTab");
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
