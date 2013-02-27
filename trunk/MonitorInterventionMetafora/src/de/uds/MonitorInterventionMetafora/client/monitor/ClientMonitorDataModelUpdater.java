package de.uds.MonitorInterventionMetafora.client.monitor;

import java.util.List;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.Timer;

import de.uds.MonitorInterventionMetafora.client.communication.actionresponses.RequestUpdateCallBack;
import de.uds.MonitorInterventionMetafora.client.monitor.datamodel.ClientMonitorDataModel;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;

public class ClientMonitorDataModelUpdater extends Timer implements RequestUpdateCallBack{

	private ClientMonitorDataModel clientDataModel;
	private ClientMonitorController controller;
	

	public ClientMonitorDataModelUpdater(ClientMonitorDataModel monitorModel, ClientMonitorController controller) {
		this.clientDataModel = monitorModel;
		this.controller = controller;
		
	}
	
	public void receiveUpdate(List<CfAction> actions){
		clientDataModel.addData(actions);
		Log.debug("Step 4: Data has been added to model");
		controller.refreshViews();
		Log.debug("Step 5: views updated");
	}

	public void startUpdates(){
		this.scheduleRepeating(25000);
	}
	
	public void stopUpdates(){
		this.cancel();	
	}
	
	@Override
	public void onFailure(Throwable caught) {
		System.out.println("ERROR [UpdatingDataModel.onFailure] call failed "+ caught);
	}

	@Override
	public void onSuccess(List<CfAction> actions) {
		
		Log.info("Step 3: Update Response is recieved from the model.Action Size:"+actions.size());
		if(actions!=null && actions.size() > 0){
			receiveUpdate(actions);
			Log.debug("Step 6: Everything updated.");
		}
		else{
			Log.debug("No Actions returned to update");
		}
	}
	
	public void getUpdate(){
		Log.debug("Step 1: Update Request is sent from model!");
		clientDataModel.getServiceServlet().requestUpdate(clientDataModel.getLastAction(),this);
	}

	@Override
	public void run() {
		getUpdate();
	}

	public ClientMonitorDataModel getClientDataModel() {
		return clientDataModel;
	}
	
}
