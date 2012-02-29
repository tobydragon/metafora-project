package com.analysis.server.cfcommunication;

import java.util.Date;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.analysis.server.xml.XmlConfigParser;
import com.analysis.server.xml.XmlFragment;
import com.analysis.server.xml.XmlFragmentInterface;
import com.analysis.shared.commonformat.CfAction;
import com.analysis.server.commonformatparser.CfActionParser;

//import de.dfki.lasad.util.ErrorUtil;
//import de.kuei.metafora.xmpp.XMPPBridge;
//import de.kuei.metafora.xmpp.XMPPMessageListener;

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
	private XmlFragmentInterface xmlIn;
	private XmlFragmentInterface xmlOut;
	
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
		
	}
	
	private XmlFragmentInterface getOrCreateFragment(String filename){
		XmlFragmentInterface fragment = XmlFragment.getFragmentFromFile(filename);
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
		//to trigger input
		if (actionToSend.getCfActionType().getType().equalsIgnoreCase("START_FILE_INPUT")){
			sendMessages();
		}
		else {
			XmlFragmentInterface actionsFrag = xmlOut.accessChild("actions");
			actionsFrag.addContent(CfActionParser.toXml(actionToSend));
			xmlOut.overwriteFile(channelNameOut);
		}
		
	}
	
	private void sendMessages() {
		XmlFragmentInterface actionsFrag = xmlIn.accessChild("actions");
		for (XmlFragmentInterface actionFrag : actionsFrag.getChildren("action")){
			CfAction action = CfActionParser.fromXml(actionFrag);
			if (action != null){
				for (CfCommunicationListener listener : listeners){
					listener.processCfAction("file", action);
				}
			}
		}
		
	}


}
