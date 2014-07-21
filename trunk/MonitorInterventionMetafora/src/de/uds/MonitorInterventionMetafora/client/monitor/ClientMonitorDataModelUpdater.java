package de.uds.MonitorInterventionMetafora.client.monitor;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;

import de.uds.MonitorInterventionMetafora.client.communication.actionresponses.NoActionResponse;
import de.uds.MonitorInterventionMetafora.client.monitor.datamodel.ClientMonitorDataModel;
import de.uds.MonitorInterventionMetafora.client.urlparameter.UrlParameterConfig;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.XmppServerType;
import de.uds.MonitorInterventionMetafora.shared.monitor.UpdateResponse;
import de.uds.MonitorInterventionMetafora.shared.suggestedmessages.Locale;

public class ClientMonitorDataModelUpdater extends Timer implements AsyncCallback<UpdateResponse>{

	private ClientMonitorDataModel clientDataModel;
	private ClientMonitorController controller;
	

	public ClientMonitorDataModelUpdater(ClientMonitorDataModel monitorModel, ClientMonitorController controller) {
		this.clientDataModel = monitorModel;
		this.controller = controller;
		
	}
	
	public void receiveUpdate(UpdateResponse actions){
		clientDataModel.addData(actions);
		Log.debug("Step 4: Data has been added to model");
		controller.refreshViews(actions.getAssociatedGroups());
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
	public void onSuccess(UpdateResponse updateResponse) {
		Log.info("Step 3: Update Response is recieved from the model.");
		receiveUpdate(updateResponse);
		Log.debug("Step 6: Update Complete.");
	}
	
	public void getUpdate(){
		Log.debug("Step 1: Update Request sent to server");
		getActionsAfterThisAction(clientDataModel.getLastAction(), this);
	}
	
	public void getActionsAfterThisAction(CfAction thisAction, AsyncCallback<UpdateResponse> callback){
		Log.debug("Step 1: Update Request sent to server");
		XmppServerType xmppServerType = UrlParameterConfig.getInstance().getXmppServerType();
		if (xmppServerType != null){
			clientDataModel.getServiceServlet().requestUpdate(xmppServerType, thisAction, callback);
	 	 }
	 	 else {
	 		 //going to have this for getting data from file, use the same callback
	 		clientDataModel.getServiceServlet().requestUpdate(clientDataModel.getLastAction(), callback);
	 	 }
	}

	@Override
	public void run() {
		getUpdate();
	}

	public ClientMonitorDataModel getClientDataModel() {
		return clientDataModel;
	}

	public void analyzeGroup(String groupId) {
		XmppServerType xmppServerType = UrlParameterConfig.getInstance().getXmppServerType();
		Locale locale = UrlParameterConfig.getInstance().getLocale();
		
	 	 if (xmppServerType != null){
	 		clientDataModel.getServiceServlet().requestAnalysis(xmppServerType, groupId, locale, new NoActionResponse());
	 	 }
	 	 else {
	 		clientDataModel.getServiceServlet().requestAnalysis(groupId, locale, new NoActionResponse());
	 	 }
	}
	
	public void clearAllAnalysis() {
		XmppServerType xmppServerType = UrlParameterConfig.getInstance().getXmppServerType();
		
	 	 if (xmppServerType != null){
	 		clientDataModel.getServiceServlet().requestClearAllAnalysis(xmppServerType, new NoActionResponse() );
	 	 }
	 	 else {
	 		clientDataModel.getServiceServlet().requestClearAllAnalysis(new NoActionResponse());
	 	 }
	}

	public void getDataFromFile(String filename) {
		clientDataModel.removeData();
		clientDataModel.getServiceServlet().requestDataFromFile(filename, this);
	}
	
	
	
	
}
