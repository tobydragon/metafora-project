package de.uds.MonitorInterventionMetafora.server.cfcommunication;

import java.util.Vector;


import de.uds.MonitorInterventionMetafora.server.commonformatparser.CfActionParser;
import de.uds.MonitorInterventionMetafora.server.xml.XmlFragment;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.utils.Logger;



public class SimpleCfFileCommunicationBridge implements CfCommunicationBridge{
	Logger logger = Logger.getLogger(CfCommunicationBridge.class);
	
	
	
	private String channelNameIn;
	private String channelNameOut;
	private XmlFragment xmlIn;
	private XmlFragment xmlOut;
	
	private Vector<CfCommunicationListener> listeners;
	
	public SimpleCfFileCommunicationBridge(){ }
	
	public SimpleCfFileCommunicationBridge(String inputFilename, String outputFilename, CfFileLocation fileLocation){
		setup(inputFilename, outputFilename, fileLocation);	
	}
	
	protected void setup(String inputFilename, String outputFilename, CfFileLocation fileLocation){
		listeners = new Vector<CfCommunicationListener>();

		channelNameIn = inputFilename;
		channelNameOut = outputFilename;
		
		xmlIn = getOrCreateFragment(channelNameIn, fileLocation);
		if (fileLocation != CfFileLocation.REMOTE){
			xmlOut = getOrCreateFragment(channelNameOut, fileLocation);
		}
	}
	
	private XmlFragment getOrCreateFragment(String filename, CfFileLocation fileLocation){
		
		logger.info("[getOrCreateFragement] get from file:" + filename);
		
		XmlFragment fragment;
		if (fileLocation == CfFileLocation.LOCAL){
			fragment = XmlFragment.getFragmentFromLocalFile(filename);
		}
		else if (fileLocation == CfFileLocation.REMOTE){
			fragment = XmlFragment.getFragmentFromRemoteFile(filename);
		}
		else {
			logger.error("[getOrCreateFragment] Unrecognized CfFIleLocation - " + fileLocation);
			fragment = null;
		}
		
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
			XmlFragment actionsFrag = xmlOut.accessChild("actions");
			actionsFrag.addContent(CfActionParser.toXml(actionToSend));
			if (xmlOut != null){
				xmlOut.overwriteFile(channelNameOut);
			}
			else {
				logger.error("Attempted to print to a null file, probably printing to REMOTE");
			}
		}
		
	}
	
	public void sendMessages() {
		
		logger.info("[sendMessages] Sending all actions from file: " + channelNameIn);
		XmlFragment actionsFrag = xmlIn.accessChild("actions");
		for (XmlFragment actionFrag : actionsFrag.getChildren("action")){
			CfAction action = CfActionParser.fromXml(actionFrag);
			if (action != null){
				for (CfCommunicationListener listener : listeners){
					listener.processCfAction("file", action);
					logger.debug("[sendMessages] sent action : time="+action.getTime());
				}
			}
			else {
				logger.info("[sendMessages] actionFragment couldn't be parsed: " + actionFrag);
			}
		}
		logger.info("[sendMessages] All actions sent succesfully");
	}


}
