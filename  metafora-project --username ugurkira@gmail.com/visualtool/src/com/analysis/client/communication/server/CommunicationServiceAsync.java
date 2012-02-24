package com.analysis.client.communication.server;

import java.util.Map;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface CommunicationServiceAsync {
	//void inputToServer(Map<String, String> cr, AsyncCallback<String> callback);

	void sendRequest(String cr, AsyncCallback<String> callback);

	void sendRequest(Map<String, String> cr,AsyncCallback<Map<String, Map<String, String>>> callback);


	


	
}
