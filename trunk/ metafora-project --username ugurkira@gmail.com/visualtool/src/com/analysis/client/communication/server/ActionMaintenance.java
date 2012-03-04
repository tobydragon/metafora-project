package com.analysis.client.communication.server;

import java.util.ArrayList;
import java.util.List;

import com.analysis.client.communication.actionresponses.RequestUpdateCallBack;
import com.analysis.shared.commonformat.CfAction;
import com.google.gwt.user.client.Timer;

public class ActionMaintenance extends Timer implements RequestUpdateCallBack{

	public static List<CfAction> _activecfActions;
	
	
	public ActionMaintenance(){
		
		_activecfActions=new ArrayList<CfAction>();
		//requestUpdateTimer = new Timer();
		
		
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
		_activecfActions.addAll(result);
		
	}

	
	
	CfAction getLastAction(){
		
		if(_activecfActions.size()<=0)
			return null;
		
		int index=_activecfActions.size()-1;
		return _activecfActions.get(index);
	}

	int counter=0;
	@Override
	public void run() {
		System.out.println("Client Update Request:"+counter);
		Server.getInstance().processAction(getLastAction(),this);
		
	}


	
	
}
