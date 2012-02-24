package com.analysis.server.xmppmanager;

import java.util.Date;
import java.util.Vector;

import org.apache.log4j.Logger;
/*
import de.dfki.lasad.util.ErrorUtil;
import de.kuei.metafora.xmpp.XMPPBridge;
import de.kuei.metafora.xmpp.XMPPMessageListener;
import de.uds.commonformat.CfAction;
import de.uds.xml.XmlConfigParser;
import de.uds.xml.XmlFragment;*/

import com.analysis.server.xml.XmlFragment;
import com.analysis.shared.communication.objects.CfAction;

public class CfFileCommunicationBridge implements CfCommunicationBridge{
	Logger logger = Logger.getLogger(CfCommunicationBridge.class);
	
	private static String commandConnectionNameIn = null;
	private static String analysisConnectionNameIn = null;
	private static String commandConnectionNameOut = null;
	private static String analysisConnectionNameOut = null;
	
	static {
		commandConnectionNameIn = "resources/xml/test/commandChannelInput.xml";
		analysisConnectionNameIn = "resources/xml/test/analysisChannelInput.xml";
		commandConnectionNameOut = "resources/xml/test/commandChannelOutput.xml";
		analysisConnectionNameOut = "resources/xml/test/analysisChannelOutput.xml";
		
		// TODO: Read filenames from config file, see static in CfXmppCommunicationBridge
	}
	
	
	private String channelNameIn;
	private String channelNameOut;
	private XmlFragment xmlIn;
	private XmlFragment xmlOut;
	
	private Vector<CfCommunicationListener> listeners;
	
	public CfFileCommunicationBridge(CommunicationChannelType type){
		listeners = new Vector<CfCommunicationListener>();

		if (type == CommunicationChannelType.command){
			channelNameIn = commandConnectionNameIn;
			channelNameOut = commandConnectionNameOut;
		}
		else if (type == CommunicationChannelType.analysis){
			channelNameIn = analysisConnectionNameIn;
			channelNameOut = analysisConnectionNameOut;
		} 
		else {
			logger.error("[constructor] Uknown connectionType");
			return;
		}
		
		xmlIn = getOrCreateFragment(channelNameIn);
		xmlOut = getOrCreateFragment(channelNameOut);
		
		
		sendMessages();
		
	}
	
	private XmlFragment getOrCreateFragment(String filename){
		XmlFragment fragment = XmlFragment.getFragmentFromFile(filename);
		if (fragment == null){
			fragment = new XmlFragment("interactiondata");
			XmlFragment childfragment = new XmlFragment("actions");
			fragment.addContent(childfragment);
		}
		
		return fragment;
	}
	
	@Override
	public void registerListener(CfCommunicationListener listener) {
		listeners.add(listener);
	}

	@Override
	public void sendAction(CfAction actionToSend) {
		XmlFragment actionsFrag = xmlOut.accessChild("actions");
		//actionsFrag.addContent(actionToSend.toXml());
		actionsFrag.addContent("");
		xmlOut.overwriteFile(channelNameOut);
		
	}
	
	private void sendMessages() {
		// TODO Auto-generated method stub
		
	}


}
