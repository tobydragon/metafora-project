package de.uds.MonitorInterventionMetafora.client.communication;


import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfInteractionData;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.Configuration;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionFilter;


/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface CommunicationService  extends RemoteService  {
	
	CfAction sendAction(String _user,CfAction cfAction);
	
	List<CfAction> requestUpdate(CfAction _lastcfAction);
	
	Configuration requestConfiguration(boolean isMainFilters);
	
	CfAction sendNotificationToAgents(CfAction cfAction);
	
	
	String sendLogAction(CfAction logAction);

	boolean saveNewFilter(boolean isMainFilters, ActionFilter filter);

	String removeNewFilter(String filterName);

	
	
}
