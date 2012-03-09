package de.uds.MonitorInterventionMetafora.client.communication.servercommunication;




import com.google.gwt.core.client.GWT;





import de.uds.MonitorInterventionMetafora.client.communication.actionresponses.CfActionCallBack;
import de.uds.MonitorInterventionMetafora.client.communication.actionresponses.RequestConfigurationCallBack;
import de.uds.MonitorInterventionMetafora.client.communication.actionresponses.RequestHistoryCallBack;
import de.uds.MonitorInterventionMetafora.client.communication.actionresponses.RequestUpdateCallBack;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;

public class ServerCommunication {
	
	

	private static ServerCommunication communicationInstance = null;
	private  CommunicationServiceAsync serviceServlet = GWT
	.create(CommunicationService.class);
	//private RemoteEventService myGWTEventService=null;	
	public static ServerCommunication getInstance() {
		if (communicationInstance == null) {
			communicationInstance = new ServerCommunication();
		}
		return communicationInstance;
	}
	

	private ServerCommunication() {
		getClientHandler();

	//	RemoteEventServiceFactory GWTEventServiceFactory = RemoteEventServiceFactory.getInstance();
		//myGWTEventService = GWTEventServiceFactory.getRemoteEventService();
	}

	
public void processAction(String _user,CfAction cfAction,CfActionCallBack actionCallBack)
{
	serviceServlet.sendAction(_user,cfAction,actionCallBack);
	
}
	
public void processAction(String _user,CfAction cfAction,RequestHistoryCallBack historyCallback) {
		
		serviceServlet.sendRequestHistoryAction(_user, cfAction,historyCallback);
	
}
 
public void processAction(String _user,CfAction cfAction,RequestConfigurationCallBack configurationCallback) {
		
		serviceServlet.sendRequestConfiguration(_user,cfAction,configurationCallback);
	
}

public void processAction(CfAction _lastcfAction,RequestUpdateCallBack updateCallback) {
	
	serviceServlet.requestUpdate(_lastcfAction,updateCallback);

}


// not used for now
private void getClientHandler(){
/*
	RemoteEventServiceFactory gwtEventServiceFactory = RemoteEventServiceFactory.getInstance();
	gwtEventServiceFactory.requestClientHandler(new AsyncCallback<ClientHandler>() {
		
		public void onFailure(Throwable caught) {
		}

		public void onSuccess(ClientHandler result) {
			
			
			System.out.println("Client Success:"+result.getConnectionId());
		}
	});*/
}

/**
 * Private constructor to support Singleton
 */
 
//private RemoteEventListener myListener = null;
 
 
}
