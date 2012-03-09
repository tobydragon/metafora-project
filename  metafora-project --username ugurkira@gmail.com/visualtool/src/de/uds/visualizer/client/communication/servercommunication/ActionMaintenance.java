package de.uds.visualizer.client.communication.servercommunication;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.Timer;

import de.uds.visualizer.client.communication.actionresponses.RequestUpdateCallBack;
import de.uds.visualizer.shared.commonformat.CfAction;

public class ActionMaintenance extends Timer implements RequestUpdateCallBack{

	public  List<CfAction> activecfActions;
	
	
	public ActionMaintenance(){
		
		activecfActions=new ArrayList<CfAction>();
		
		
		
	}
	
	
	public List<CfAction> getActiveActionList(){
		
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
		System.out.println("Client Update Request:"+counter);
		ServerCommunication.getInstance().processAction(getLastAction(),this);
		
	}


	
	
}
