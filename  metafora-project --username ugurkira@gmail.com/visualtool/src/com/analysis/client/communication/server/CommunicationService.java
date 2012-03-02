package com.analysis.client.communication.server;


import java.util.List;
import java.util.Map;

import com.analysis.shared.commonformat.CfAction;
import com.analysis.shared.commonformat.CfInteractionData;
import com.analysis.shared.interactionmodels.Configuration;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;


/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface CommunicationService extends RemoteService {
	
	CfAction sendAction(CfAction cfAction);
	
	CfInteractionData sendRequestHistoryAction(CfAction cfAction);

	Configuration sendRequestConfiguration(CfAction cfAction);
	
	
	
	//Map<String, Map<String, String>> sendRequest(Map<String, String> cr);
	
	
}
