package de.uds.MonitorInterventionMetafora.client.communication.servercommunication;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.Timer;
import de.uds.MonitorInterventionMetafora.client.actionresponse.RequestUpdateCallBack;
import de.uds.MonitorInterventionMetafora.client.communication.ServerCommunication;
import de.uds.MonitorInterventionMetafora.client.manager.ClientMonitorController;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;

public class UpdatingDataModel extends Timer implements RequestUpdateCallBack{

	public  List<CfAction> activecfActions;
	private ClientMonitorController interfaceManager;
	
	
	public UpdatingDataModel(){
		activecfActions=new ArrayList<CfAction>();
//		interfaceManager=new FilteredDataViewManager(this);
	}
	
	
	public ClientMonitorController getInterfaceManager() {
		return interfaceManager;
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
	
	
	@Override
	public void onFailure(Throwable caught) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSuccess(List<CfAction> result) {
		if(result!=null){	
			activecfActions.addAll(result);
			interfaceManager.refreshViews();
			System.out.println("DEBUG [UpdatingDataModel.onSuccess] Action recieved and list updated");
		}
		else{
			System.out.println("DEBUG [UpdatingDataModel.onSuccess] No Action Update Recieved");
		}
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
