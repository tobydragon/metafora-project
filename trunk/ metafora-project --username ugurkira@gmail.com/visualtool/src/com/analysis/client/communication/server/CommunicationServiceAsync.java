package com.analysis.client.communication.server;

import java.util.List;
import java.util.Map;

import com.analysis.shared.commonformat.CfAction;
import com.analysis.shared.commonformat.CfInteractionData;
import com.analysis.shared.interactionmodels.Configuration;
import com.google.gwt.user.client.rpc.AsyncCallback;


/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface CommunicationServiceAsync {
	//void inputToServer(Map<String, String> cr, AsyncCallback<String> callback);

	//void sendRequest(String cr, AsyncCallback<String> callback);

	//void sendRequest(Map<String, String> cr,AsyncCallback<Map<String, Map<String, String>>> callback);

	//void sendRequestHistoryAction(CfAction cfAction,
		//	AsyncCallback<List<CfAction>> callback);

	void sendRequestConfiguration(String _user,CfAction cfAction,
			AsyncCallback<Configuration> callback);

	void sendAction(String _user,CfAction cfAction, AsyncCallback<CfAction> callback);

	void sendRequestHistoryAction(String _user,CfAction cfAction,
			AsyncCallback<CfInteractionData> callback);


	


	
}
