package de.uds.MonitorInterventionMetafora.client.communication;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.Configuration;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.XmppServerType;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionFilter;


/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface CommunicationServiceAsync {

	
	// requests for ServerInstances, all should have counterparts that don't specify a server, and so use default
	void sendAction (String _user, CfAction cfAction, AsyncCallback<Void> callback);
	void sendAction (XmppServerType serverType, String user, CfAction action, AsyncCallback<Void> callback);

	void requestSuggestedMessages(String username, AsyncCallback<String> callback);
	void requestSuggestedMessages(XmppServerType xmppServerType, String username, AsyncCallback<String> callback);
	
	void requestUpdate(CfAction _lastcfAction, AsyncCallback<List<CfAction>> callback);
	void requestUpdate(XmppServerType xmppServerType, CfAction _lastcfAction, AsyncCallback<List<CfAction>> callback);

	void requestAnalysis(String groupId, AsyncCallback<Void> callback);
	void requestAnalysis(XmppServerType xmppServerType, String groupId, AsyncCallback<Void> callback);
	
	//requests for MainServer
	void requestConfiguration(AsyncCallback<Configuration> callback);

	void logAction(CfAction logAction,AsyncCallback<Void> callback);
	
	void saveNewFilter(boolean isMainFilters,ActionFilter filter, AsyncCallback<Boolean> callback);
	
	void removeNewFilter(String filterName, AsyncCallback<String> callback);
	
	void requestMainConfiguration(AsyncCallback<Configuration> callback);
}
