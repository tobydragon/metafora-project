package de.uds.MonitorInterventionMetafora.client.communication;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.Configuration;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.XmppServerType;
import de.uds.MonitorInterventionMetafora.shared.monitor.UpdateResponse;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionFilter;
import de.uds.MonitorInterventionMetafora.shared.suggestedmessages.Locale;


/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface CommunicationServiceAsync {

	
	// requests for ServerInstance should have counterparts that don't specify a server, and so use default
	void sendMessage (CfAction cfAction, AsyncCallback<Void> callback);
	void sendMessage (XmppServerType serverType, CfAction action, AsyncCallback<Void> callback);
	
	void sendSuggestedMessages (CfAction cfAction, AsyncCallback<Void> callback);
	void sendSuggestedMessages (XmppServerType serverType, CfAction action, AsyncCallback<Void> callback);
	
	void requestSuggestedMessages(String username, AsyncCallback<String> callback);
	void requestSuggestedMessages(XmppServerType xmppServerType, String username, AsyncCallback<String> callback);
	
	void requestUpdate(CfAction _lastcfAction, AsyncCallback<UpdateResponse> callback);
	void requestUpdate(XmppServerType xmppServerType, CfAction _lastcfAction, AsyncCallback<UpdateResponse> callback);

	void requestAnalysis(String groupId, Locale locale, AsyncCallback<Void> callback);
	void requestAnalysis(XmppServerType xmppServerType, String groupId, Locale locale, AsyncCallback<Void> callback);
	
	void requestClearAllAnalysis(AsyncCallback<Void> callback);
	void requestClearAllAnalysis(XmppServerType xmppServerType, AsyncCallback<Void> callback);
	
	
	//requests for MainServer
	void requestConfiguration(AsyncCallback<Configuration> callback);

	void logAction(CfAction logAction,AsyncCallback<Void> callback);
	
	void saveNewFilter(boolean isMainFilters,ActionFilter filter, AsyncCallback<Boolean> callback);
	
	void removeNewFilter(String filterName, AsyncCallback<String> callback);
	
	void requestMainConfiguration(AsyncCallback<Configuration> callback);
}
