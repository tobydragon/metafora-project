package de.uds.MonitorInterventionMetafora.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import java.util.List;






import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.uds.MonitorInterventionMetafora.client.communication.servercommunication.CommunicationService;
import de.uds.MonitorInterventionMetafora.server.cfcommunication.CfAgentCommunicationManager;
import de.uds.MonitorInterventionMetafora.server.cfcommunication.CfCommunicationListener;
import de.uds.MonitorInterventionMetafora.server.cfcommunication.CommunicationChannelType;
import de.uds.MonitorInterventionMetafora.server.cfcommunication.CommunicationMethodType;
import de.uds.MonitorInterventionMetafora.server.xml.XmlConfigParser;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfActionType;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfObject;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfProperty;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.Configuration;
import de.uds.MonitorInterventionMetafora.shared.utils.GWTDateUtils;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class MainServer extends RemoteServiceServlet implements
		CommunicationService,CfCommunicationListener,Comparator<CfAction> {


	String configFilepath = "conf/toolconf/configuration.xml";
	public Configuration _configuration;
	List<CfAction> _cfActions;
	
	CfAgentCommunicationManager communicationManager;
	
	public MainServer(){		
		_cfActions=new ArrayList<CfAction>();
				
		XmlConfigParser connectionParser = new XmlConfigParser(configFilepath);
		_configuration=connectionParser.toActiveConfiguration();		
		communicationManager = CfAgentCommunicationManager.getInstance(getCommunicationType(_configuration.getActionSource()), CommunicationChannelType.analysis);				
		communicationManager.register(this);
		if(_configuration.getActionSource().equalsIgnoreCase("file")){
		CfAction _action=new CfAction();
	 	  _action.setTime(GWTDateUtils.getTimeStamp());
	 	  
	 	 CfActionType _cfActionType=new CfActionType();
	 	 _cfActionType.setType("START_FILE_INPUT");
	 	_cfActionType.setClassification("Test");
	 	_cfActionType.setSucceed("true");
	 	_cfActionType.setLogged("true");
    	_action.setCfActionType(_cfActionType);
		
	
	 	 //_pathProperty.setValue(_configuration.getActionSource())
	 	 
	 	//_configurationObject.a
	 	//_action.
	
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

		return _configuration;
	}



	@Override
	public List<CfAction> sendRequestHistoryAction(String _user,CfAction cfAction) {

		 
		return _cfActions;
	}

	@Override
	public List<CfAction> requestUpdate(CfAction cfAction) {
		
		if(cfAction!=null){
		
		return getActionUpdates(cfAction.getTime());
		
		
		}
		
		return null;
	}


	@Override
	public void processCfAction(String user, CfAction action) {
		
		_cfActions.add(action);
	
	 Collections.sort(_cfActions,this);
	
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







List<CfAction>  getActionUpdates(long _lasActionTime){
	
	List<CfAction> _newActionList=new ArrayList<CfAction>();
	
	for(int i=_cfActions.size()-1;i>=0;i--){
		
		if(isNewAction(_lasActionTime,_cfActions.get(i).getTime())){
			
			_newActionList.add(_cfActions.get(i));
			
		}
		
	}
	
	System.out.println(_newActionList.size()+" New Action Updates!!");
	return _newActionList;
	
	
}


boolean isNewAction(long _lastActionTime,long _actionTime){
	
	if(_actionTime>_lastActionTime){		
		return true;
	}
		
	return false;
}


	
}
