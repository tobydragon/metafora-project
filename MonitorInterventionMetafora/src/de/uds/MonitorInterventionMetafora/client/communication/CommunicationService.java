package de.uds.MonitorInterventionMetafora.client.communication;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.Configuration;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.XmppServerType;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionFilter;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface CommunicationService extends RemoteService {

	//answered by serverInstance
	CfAction sendAction(String _user, CfAction cfAction);
	void sendAction (XmppServerType serverType, String user, CfAction action);
	
	List<CfAction> requestUpdate(CfAction _lastcfAction);
	
	String requestSuggestedMessages(String username);
	
	
	//answered by MainServer
	Configuration requestConfiguration();

	String sendLogAction(CfAction logAction);

	boolean saveNewFilter(boolean isMainFilters, ActionFilter filter);

	String removeNewFilter(String filterName);

	CfAction sendNotificationToAgents(CfAction cfAction);
	
	Configuration requestMainConfiguration();

	
}
