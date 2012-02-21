package com.analysis.client.communication.server;


import java.util.Map;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;


/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface CommunicationService extends RemoteService {
	
	
	String sendToServer(String cr);

	
	Map<String, Map<String, String>> sendToServer(Map<String, String> cr);
	
	
}
