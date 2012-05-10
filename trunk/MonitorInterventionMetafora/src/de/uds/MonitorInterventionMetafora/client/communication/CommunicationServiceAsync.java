package de.uds.MonitorInterventionMetafora.client.communication;

import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfInteractionData;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.Configuration;


/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface CommunicationServiceAsync {
	//void inputToServer(Map<String, String> cr, AsyncCallback<String> callback);

	//void sendRequest(String cr, AsyncCallback<String> callback);

	//void sendRequest(Map<String, String> cr,AsyncCallback<Map<String, Map<String, String>>> callback);

	//void sendRequestHistoryAction(CfAction cfAction,
		//	AsyncCallback<List<CfAction>> callback);

	void sendRequestConfiguration(CfAction cfAction,
			AsyncCallback<Configuration> callback);

	void sendAction(String _user,CfAction cfAction, AsyncCallback<CfAction> callback);

	void requestUpdate(CfAction _lastcfAction,
			AsyncCallback<List<CfAction>> callback);
	
	void sendNotificationToAgents(CfAction cfAction, AsyncCallback<CfAction> callback);
	
	


	


	
}
