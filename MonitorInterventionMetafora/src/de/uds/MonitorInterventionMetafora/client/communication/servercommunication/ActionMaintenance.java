package de.uds.MonitorInterventionMetafora.client.communication.servercommunication;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.widget.ComponentManager;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.grid.EditorGrid;
//import com.extjs.gxt.ui.client.widget.button.Button;
//import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.Timer;
import com.extjs.gxt.ui.client.widget.grid.Grid;
//import com.google.gwt.user.client.ui.Button;


import de.uds.MonitorInterventionMetafora.client.communication.actionresponses.RequestUpdateCallBack;
import de.uds.MonitorInterventionMetafora.client.datamodels.IndicatorFilterItemGridRowModel;
import de.uds.MonitorInterventionMetafora.client.datamodels.TableViewModel;
import de.uds.MonitorInterventionMetafora.client.view.grids.IndicatorGridRowItem;
//import de.uds.MonitorInterventionMetafora.client.view.grids.IndicatorEntity;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfUser;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.IndicatorEntity;
import de.uds.MonitorInterventionMetafora.shared.utils.GWTDateUtils;

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
	
	
	 public void refreshTableView(ActionMaintenance _maintenance){
		 TableViewModel tvm=new TableViewModel( _maintenance);
			
		 
		   Grid<IndicatorGridRowItem> editorGrid = (Grid<IndicatorGridRowItem>) ComponentManager.get().get("_tableViewGrid");
		   Grid<IndicatorGridRowItem> _grid = editorGrid;
		   _grid.getStore().removeAll();
		   _grid.getStore().add(tvm.parseToIndicatorGridRowList(true));
		 
		 //Button _refreshBtn =  (Button) ComponentManager.get().get("_refreshBtn").asWidget();
        // _refreshBtn.s
		 //if(_refreshBtn!=null)
        //_refreshBtn.fireEvent(Events.OnClick);
         
		 
	 }

	
	
	/*
	public List<IndicatorEntity>  parseToIndicatorEntityList(){
		
		List<CfAction> _cfActions=new ArrayList<CfAction>();
		if(activecfActions!=null){
			_cfActions.addAll(activecfActions);
			}
		
		
		
		
		List<IndicatorEntity> indicatorEntities=new ArrayList<IndicatorEntity>();
			
		

		
		for(CfAction ac: _cfActions){
	
			IndicatorEntity  myindicator=new IndicatorEntity();
			
			
			String usersString="";
			   for(CfUser u : ac.getCfUsers()){
        		   usersString=usersString+" - "+u.getid();
        	   }
        	   
        	   
        	   myindicator.setName(usersString.substring(2,usersString.length()));
        	   myindicator.setActionType(ac.getCfActionType().getType());
        	   myindicator.setClassification(ac.getCfActionType().getClassification());
        	   myindicator.setDescription(ac.getCfContent().getDescription());     	   
        	   myindicator.setTime(GWTDateUtils.getTime(ac.getTime()));
        	   myindicator.setDate(GWTDateUtils.getDate(ac.getTime()));
        	  
        	   
        	   indicatorEntities.add(myindicator);	
		}
		
		return indicatorEntities;
		
		
	}
	*/
	
	
	
	
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
