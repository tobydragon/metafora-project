package com.analysis.server.xmppmanager;


import com.analysis.shared.communication.objects.CfAction;


public class CfCommunicationBridgeTest {
	
	public void testFileCommuncation(){
	//	CfAction action  = MetaforaCfFactory.buildCreateMapMessage("toby", "new map", "template");
	//	CfAction action2  = MetaforaCfFactory.buildCreateMapMessage("tim", "new map2", "template2");

		CfCommunicationBridge fileBridge = new CfFileCommunicationBridge(CommunicationChannelType.analysis);
		
		//fileBridge.sendAction(action);
		//fileBridge.sendAction(action2);
		//fileBridge.sendAction(action);
	}

}
