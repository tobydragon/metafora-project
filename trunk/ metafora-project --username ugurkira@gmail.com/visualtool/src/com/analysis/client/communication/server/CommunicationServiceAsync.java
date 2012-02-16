package com.analysis.client.communication.server;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface CommunicationServiceAsync {
	void inputToServer(String input, AsyncCallback<String> callback);

	void inputToServer(String input, String value,
			AsyncCallback<String> callback);


	
}
