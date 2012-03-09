package de.uds.MonitorInterventionMetafora.client.communication.servercommunication;


import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfInteractionData;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.Configuration;


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
