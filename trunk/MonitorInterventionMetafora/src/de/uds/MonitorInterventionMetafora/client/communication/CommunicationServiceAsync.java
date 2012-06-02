package de.uds.MonitorInterventionMetafora.client.communication;

import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfInteractionData;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.Configuration;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionFilter;


/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface CommunicationServiceAsync {

	void requestConfiguration(boolean isMainFilters,AsyncCallback<Configuration> callback);

	void sendAction(String _user,CfAction cfAction, AsyncCallback<CfAction> callback);

	void requestUpdate(CfAction _lastcfAction, AsyncCallback<List<CfAction>> callback);
	
	void sendLogAction(CfAction logAction,AsyncCallback<String> callback);
	
	void saveNewFilter(boolean isMainFilters,ActionFilter filter, AsyncCallback<Boolean> callback);
	void removeNewFilter(String filterName, AsyncCallback<String> callback);
	
	void sendNotificationToAgents(CfAction cfAction, AsyncCallback<CfAction> callback);
	
	


	


	
}
