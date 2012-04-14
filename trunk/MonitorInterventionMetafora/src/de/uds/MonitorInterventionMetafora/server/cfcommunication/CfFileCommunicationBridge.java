package de.uds.MonitorInterventionMetafora.server.cfcommunication;

import java.util.Date;
import java.util.Vector;

import org.apache.log4j.Logger;

import de.uds.MonitorInterventionMetafora.server.commonformatparser.CfActionParser;
import de.uds.MonitorInterventionMetafora.server.utils.GeneralUtil;
import de.uds.MonitorInterventionMetafora.server.xml.XmlConfigParser;
import de.uds.MonitorInterventionMetafora.server.xml.XmlFragment;
import de.uds.MonitorInterventionMetafora.server.xml.XmlFragmentInterface;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;

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
		commandConnectionNameIn =GeneralUtil.getAplicationResourceDirectory()+ "conffiles/xml/test/commandChannelInput.xml";
		analysisConnectionNameIn = GeneralUtil.getAplicationResourceDirectory()+"conffiles/xml/test/analysisChannelInput.xml";
		commandConnectionNameOut = GeneralUtil.getAplicationResourceDirectory()+"conffiles/xml/test/commandChannelOutput.xml";
		analysisConnectionNameOut =GeneralUtil.getAplicationResourceDirectory()+ "conffiles/xml/test/analysisChannelOutput.xml";
		
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
		
		System.out.println("trying to get interaction data!!");
		XmlFragmentInterface fragment = XmlFragment.getFragmentFromFile(filename);
		if (fragment == null){
			
			System.out.println(" interaction data fragment is not null!!");
			fragment = new XmlFragment("interactiondata");
			XmlFragment childfragment = new XmlFragment("actions");
			fragment.addContent(childfragment);
		}
		System.out.println(" interaction data fragment!!"+fragment);
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
		
		System.out.println("Sending individual actions!!");
		XmlFragmentInterface actionsFrag = xmlIn.accessChild("actions");
		for (XmlFragmentInterface actionFrag : actionsFrag.getChildren("action")){
			CfAction action = CfActionParser.fromXml(actionFrag);
			if (action != null){
				for (CfCommunicationListener listener : listeners){
					listener.processCfAction("file", action);
					System.out.println("Sent action:"+action.getTime());
				}
			}
		}
		
		System.out.println("All actions are sent successfully!!");
		
	}


}
