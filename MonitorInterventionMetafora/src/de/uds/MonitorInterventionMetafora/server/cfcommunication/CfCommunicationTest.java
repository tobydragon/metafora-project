package de.uds.MonitorInterventionMetafora.server.cfcommunication;

import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfActionType;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfCommunicationMethodType;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.XmppServerType;

import junit.framework.TestCase;

public class CfCommunicationTest extends TestCase {
	
	public void testFileCommuncation(){
		CfAction action  = MetaforaCfFactory.buildCreateMapMessage("toby", "new map", "template");
		CfAction action2  = MetaforaCfFactory.buildCreateMapMessage("tim", "new map2", "template2");

		CfCommunicationBridge fileBridge = new MetaforaCfFileCommunicationBridge(CommunicationChannelType.analysis);
		
		fileBridge.sendAction(action);
		fileBridge.sendAction(action2);
		fileBridge.sendAction(action);
	}
	
	public void testFileInput(){
		CfCommunicationListenerTest mytest = new CfCommunicationListenerTest();
		CfAgentCommunicationManager manager = CfAgentCommunicationManager.getInstance(CfCommunicationMethodType.file, CommunicationChannelType.analysis, XmppServerType.TEST);
		manager.register(mytest);
		manager.sendMessage(new CfAction(0, new CfActionType("START_FILE_INPUT", null, null)));
	}
	
	public void testSimpleFileInput(){
		SimpleCfFileCommunicationBridge simpleBridge = new SimpleCfFileCommunicationBridge("war/conffiles/xml/test/analysisChannelOutput-PlaTo.xml", "war/conffiles/xml/test/analysisChannelOutput.xml", CfFileLocation.LOCAL);
		simpleBridge.sendAction(new CfAction(0, new CfActionType("START_FILE_INPUT", null, null)));

	}

}
