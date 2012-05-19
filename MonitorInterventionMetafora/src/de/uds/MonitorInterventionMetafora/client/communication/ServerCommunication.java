package de.uds.MonitorInterventionMetafora.client.communication;




import java.io.Serializable;

import com.google.gwt.core.client.GWT;

import de.uds.MonitorInterventionMetafora.client.communication.actionresponses.CfActionCallBack;
import de.uds.MonitorInterventionMetafora.client.communication.actionresponses.RequestUpdateCallBack;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;

public class ServerCommunication implements Serializable {
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 2406920495105425871L;
	private static ServerCommunication communicationInstance = null;
	private  CommunicationServiceAsync serviceServlet = GWT.create(CommunicationService.class);

	public static ServerCommunication getInstance() {
		if (communicationInstance == null) {
			communicationInstance = new ServerCommunication();
		}
		return communicationInstance;
	}
	


	
public void processAction(String _user,CfAction cfAction,CfActionCallBack actionCallBack)
{
	System.out.println("ServerCommunication processaction is called");
	serviceServlet.sendAction(_user,cfAction,actionCallBack);
	
}
	

 



}
