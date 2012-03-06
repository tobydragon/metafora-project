package com.analysis.client.communication.servercommunication;


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
	
	CfAction sendAction(String _user,CfAction cfAction);
	
	List<CfAction> sendRequestHistoryAction(String _user, CfAction cfAction);
	
	List<CfAction> requestUpdate(CfAction _lastcfAction);

	Configuration sendRequestConfiguration(String _user,CfAction cfAction);
	
	
	
	//Map<String, Map<String, String>> sendRequest(Map<String, String> cr);
	
	
}
