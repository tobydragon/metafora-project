package de.uds.MonitorInterventionMetafora.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import java.util.List;






import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.uds.MonitorInterventionMetafora.client.communication.CommunicationService;
import de.uds.MonitorInterventionMetafora.server.analysis.manager.AnalysisManager;
import de.uds.MonitorInterventionMetafora.server.cfcommunication.CfAgentCommunicationManager;
import de.uds.MonitorInterventionMetafora.server.cfcommunication.CfCommunicationListener;
import de.uds.MonitorInterventionMetafora.server.cfcommunication.CommunicationChannelType;
import de.uds.MonitorInterventionMetafora.server.cfcommunication.CommunicationMethodType;
import de.uds.MonitorInterventionMetafora.server.utils.GeneralUtil;
import de.uds.MonitorInterventionMetafora.server.xml.XmlConfigParser;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfActionType;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfObject;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfProperty;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.Configuration;
import de.uds.MonitorInterventionMetafora.shared.utils.GWTUtils;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class MainServer extends RemoteServiceServlet implements CommunicationService,CfCommunicationListener,Comparator<CfAction> {


	String configFilepath = GeneralUtil.getAplicationResourceDirectory()+"conffiles/toolconf/configuration.xml";
	public Configuration _configuration;
	List<CfAction> cfActions;
	
	
	CfAgentCommunicationManager communicationManager;
	AnalysisManager analysisManager;
	
	public MainServer(){		
		cfActions=new ArrayList<CfAction>();
				
		XmlConfigParser connectionParser = new XmlConfigParser(configFilepath);
		_configuration=connectionParser.toActiveConfiguration();		
		communicationManager = CfAgentCommunicationManager.getInstance(getCommunicationType(_configuration.getActionSource()), CommunicationChannelType.analysis);				
		communicationManager.register(this);
		
//		analysisManager=AnalysisManager.getAnalysisManagerInstance();
//		analysisManager.setActions(cfActions);
//		analysisManager.register(this);
		
//		analysisManager.sendToAllAgents("Uguran", null);
		
		
		if(_configuration.getActionSource().equalsIgnoreCase("file")){
			CfAction _action=new CfAction();
		 	  _action.setTime(GWTUtils.getTimeStamp());
		 	  
		 	 CfActionType _cfActionType=new CfActionType();
		 	 _cfActionType.setType("START_FILE_INPUT");
		 	_cfActionType.setClassification("Test");
		 	_cfActionType.setSucceed("true");
		 	_cfActionType.setLogged("true");
	    	_action.setCfActionType(_cfActionType);
	    	
			 communicationManager.sendMessage(_action);
		}
					
	}
	
	

	@Override
	public CfAction sendAction(String _user,CfAction cfAction) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Configuration sendRequestConfiguration(String _user,CfAction cfAction) {

		System.out.println("Requesting configuration!");
		return _configuration;
	}



	@Override
	public List<CfAction> sendRequestHistoryAction(String _user,CfAction cfAction) {

		System.out.println("Requesting history!");
		return cfActions;
	}

	@Override
	public List<CfAction> requestUpdate(CfAction cfAction) {
		
		if(cfAction!=null){
			return getActionUpdates(cfAction.getTime());
		}
		else {
			System.out.println("INFO: [MainServer.requestUpdate] no last action present, sending entire history");
			return cfActions;
		}
	}

	@Override
	public void processCfAction(String user, CfAction action) {
		
		System.out.println("DEBUG: [MainServer.processCfAction] new action: user=" + user);

		cfActions.add(action);
		Collections.sort(cfActions,this);
//		analysisManager.setActions(cfActions);
	}
	
public CommunicationMethodType getCommunicationType(String _type){
		
		if(_type.equalsIgnoreCase("file"))
			return CommunicationMethodType.file;
		else if(_type.equalsIgnoreCase("xmpp"))
		return CommunicationMethodType.xmpp;		
		return null;
	}



@Override
public int compare(CfAction action1, CfAction action2) {
	
	int dif=(int) (action1.getTime()-action2.getTime());
	return dif;
}







List<CfAction>  getActionUpdates(long _lastActionTime){
	System.out.println("DEBUG: [MainServer.getActionUpdates] "+ _lastActionTime);
	List<CfAction> _newActionList=new ArrayList<CfAction>();
	
	//walk list backwards because very few end action will be new
	//TODO: should stop when first old action is found
	for(int i=cfActions.size()-1;i>=0;i--){
		if(isNewAction(_lastActionTime,cfActions.get(i).getTime())){
			_newActionList.add(cfActions.get(i));
		}
	}
	
	return _newActionList;
}


boolean isNewAction(long _lastActionTime,long _actionTime){
	
	if(_actionTime>_lastActionTime){		
		return true;
	}
		
	return false;
}






@Override
public CfAction sendNotificationToAgents(CfAction cfAction) {

	AnalysisManager.getAnalysisManagerInstance().sendToAllAgents("Notification",cfAction);

	System.out.println("Notifications are send to the agentss!!");
	return new CfAction();
}






	
}
