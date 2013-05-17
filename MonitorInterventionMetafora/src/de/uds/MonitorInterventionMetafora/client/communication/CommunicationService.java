package de.uds.MonitorInterventionMetafora.client.communication;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.Configuration;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.XmppServerType;
import de.uds.MonitorInterventionMetafora.shared.monitor.UpdateResponse;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionFilter;
import de.uds.MonitorInterventionMetafora.shared.suggestedmessages.Locale;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface CommunicationService extends RemoteService {

	//answered by serverInstance
	void sendAction(String _user, CfAction cfAction);
	void sendAction (XmppServerType serverType, String user, CfAction action);
	
	UpdateResponse requestUpdate(CfAction _lastcfAction);
	UpdateResponse requestUpdate(XmppServerType xmppServerType, CfAction _lastcfAction);
	
	void requestAnalysis(String groupId, Locale locale);
	void requestAnalysis(XmppServerType xmppServerType, String groupId, Locale locale);
	
	String requestSuggestedMessages(String username);
	String requestSuggestedMessages(XmppServerType xmppServerType, String username);

	
	
	//answered by MainServer
	Configuration requestConfiguration();

	void logAction(CfAction logAction);

	boolean saveNewFilter(boolean isMainFilters, ActionFilter filter);

	String removeNewFilter(String filterName);
	
	Configuration requestMainConfiguration();

}
