package com.analysis.client.communication.server;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
	
	
	private void getClientHandler(){
		//Logger.log("###getClientHandler getting Connection ID", Logger.DEBUG);
		RemoteEventServiceFactory gwtEventServiceFactory = RemoteEventServiceFactory.getInstance();
		gwtEventServiceFactory.requestClientHandler(new AsyncCallback<ClientHandler>() {
			/** If the servlet call fails, log it. */
			public void onFailure(Throwable caught) {
			//	Logger.log("[lasad.gwt.client.communication.objects.LASADActionSender][sendActionSet] Error: RPC failure", Logger.DEBUG);
				//Logger.log("[Error details] " + caught.toString(), Logger.DEBUG);
			}

			/**
			 * If the servlet call is successful 
			 * do further processing
			 * 
			 * @param result
			 */
			public void onSuccess(ClientHandler result) {
				//Logger.log("###getClientHandler Connection ID" + result.getConnectionId(), Logger.DEBUG);
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
	
	
	public boolean connectEventService() {
		//Logger.log("connectEventService", Logger.DEBUG);
		
	
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
	
	
	public void sendRequest(String _request,AsyncCallback<String> cb) {

		if(connectEventService()) {

			
			myServlet.sendRequest(_request, cb);
		}
			
	}

	
	
// For one side communication
	public void sendRequest(String actionSet) {

		if(connectEventService()) {

			myServlet.sendRequest("RequestHistory", new AsyncCallback<String>() {

				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onSuccess(String result) {
					// TODO Auto-generated method stub
					
				}

			
			});
		}
		else {
			
		}		
	}
	
	
}
