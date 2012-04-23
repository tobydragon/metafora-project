package de.uds.MonitorInterventionMetafora.server.cfcommunication;

import java.util.Vector;


import de.uds.MonitorInterventionMetafora.server.commonformatparser.CfActionParser;
import de.uds.MonitorInterventionMetafora.server.utils.Logger;
import de.uds.MonitorInterventionMetafora.server.xml.XmlFragment;
import de.uds.MonitorInterventionMetafora.server.xml.XmlFragmentInterface;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;



public class SimpleCfFileCommunicationBridge implements CfCommunicationBridge{
	Logger logger = Logger.getLogger(CfCommunicationBridge.class);
	
	
	
	private String channelNameIn;
	private String channelNameOut;
	private XmlFragmentInterface xmlIn;
	private XmlFragmentInterface xmlOut;
	
	private Vector<CfCommunicationListener> listeners;
	
	public SimpleCfFileCommunicationBridge(String inputFilename, String outputFilename){
		listeners = new Vector<CfCommunicationListener>();

		channelNameIn = inputFilename;
		channelNameOut = outputFilename;
		
		xmlIn = getOrCreateFragment(channelNameIn);
		xmlOut = getOrCreateFragment(channelNameOut);
		
	}
	
	private XmlFragmentInterface getOrCreateFragment(String filename){
		
		logger.info("[getOrCreateFragement] get from file:" + filename);
		XmlFragmentInterface fragment = XmlFragment.getFragmentFromFile(filename);
		if (fragment == null){
			
			logger.info("[getOrCreateFragement] no file read from fragment, creating new fragment");
			fragment = new XmlFragment("interactiondata");
			XmlFragment childfragment = new XmlFragment("actions");
			fragment.addContent(childfragment);
		}
//		logger.debug("[getOrCreateFragement] fragment:\n"+fragment);
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
		
		logger.info("[sendMessages] Sending all actions from file: " + channelNameIn);
		XmlFragmentInterface actionsFrag = xmlIn.accessChild("actions");
		for (XmlFragmentInterface actionFrag : actionsFrag.getChildren("action")){
			CfAction action = CfActionParser.fromXml(actionFrag);
			if (action != null){
				for (CfCommunicationListener listener : listeners){
					listener.processCfAction("file", action);
					logger.debug("[sendMessages] sent action : time="+action.getTime());
				}
			}
		}
		logger.info("[sendMessages] All actions sent succesfully");
	}


}
