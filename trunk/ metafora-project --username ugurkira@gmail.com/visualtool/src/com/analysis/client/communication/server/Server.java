package com.analysis.client.communication.server;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.analysis.client.communication.actionresponses.CfActionCallBack;
import com.analysis.client.communication.actionresponses.RequestConfigurationCallBack;
import com.analysis.client.communication.actionresponses.RequestHistoryCallBack;
import com.analysis.client.utils.ClientFormatStrings;
import com.analysis.shared.commonformat.CfAction;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import de.novanic.eventservice.client.ClientHandler;
import de.novanic.eventservice.client.event.Event;
import de.novanic.eventservice.client.event.RemoteEventService;
import de.novanic.eventservice.client.event.RemoteEventServiceFactory;
import de.novanic.eventservice.client.event.listener.RemoteEventListener;
import de.novanic.eventservice.client.event.listener.unlisten.DefaultUnlistenEvent;
import de.novanic.eventservice.client.event.listener.unlisten.UnlistenEvent;
import de.novanic.eventservice.client.event.listener.unlisten.UnlistenEventListener.Scope;
import de.novanic.eventservice.client.event.listener.unlisten.UnlistenEventListenerAdapter;

public class Server {
	
	private RemoteEventService myGWTEventService=null;	
	private RemoteEventListener myListener = null;
	private static Server myInstance = null;
	private  CommunicationServiceAsync myServlet = GWT
	.create(CommunicationService.class);
	
	public static Server getInstance() {
		if (myInstance == null) {
			myInstance = new Server();
		}
		return myInstance;
	}
	
	// not used for now
	private void getClientHandler(){
	
		RemoteEventServiceFactory gwtEventServiceFactory = RemoteEventServiceFactory.getInstance();
		gwtEventServiceFactory.requestClientHandler(new AsyncCallback<ClientHandler>() {
			
			public void onFailure(Throwable caught) {
			}

			public void onSuccess(ClientHandler result) {
				
				
				System.out.println("Client Success:"+result.getConnectionId());
			}
		});
	}

	/**
	 * Private constructor to support Singleton
	 */
	private Server() {
		getClientHandler();

		RemoteEventServiceFactory GWTEventServiceFactory = RemoteEventServiceFactory.getInstance();
		myGWTEventService = GWTEventServiceFactory.getRemoteEventService();

		myListener = new RemoteEventListener() {

			// Event from Server
			@Override
			public void apply(Event anEvent) {
			
				System.out.println("Event:" + anEvent.toString());
				
			}
		};
	}
	
	//not used for now
	public boolean connectEventService() {
		
		if(myGWTEventService.isActive() && myGWTEventService.getActiveDomains().size() == 2) {

			System.out.println("connected:true");
			
			return true;
		}
		
		else {
			System.out.println("connected:false");
			
myGWTEventService.removeListeners();
			
// Add the default listener for server events. This could take some time, thus, we will have to wait for it...
myGWTEventService.addListener(null, myListener);
			
			
			return true;
		}
		
	}

	
public void processAction(CfAction cfAction,CfActionCallBack actionCallBack)
{
	myServlet.sendAction(cfAction,actionCallBack);
	
}
	
public void processAction(CfAction cfAction,RequestHistoryCallBack historyCallback) {
		
		myServlet.sendRequestHistoryAction(cfAction,historyCallback);
	
}
 
public void processAction(CfAction cfAction,RequestConfigurationCallBack configurationCallback) {
		
		myServlet.sendRequestConfiguration(cfAction,configurationCallback);
	
}


 
 
 
 
}
