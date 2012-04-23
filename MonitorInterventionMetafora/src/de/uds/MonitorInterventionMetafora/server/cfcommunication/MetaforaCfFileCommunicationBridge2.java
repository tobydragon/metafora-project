package de.uds.MonitorInterventionMetafora.server.cfcommunication;

import java.util.Vector;

import org.apache.log4j.Logger;

import de.uds.MonitorInterventionMetafora.server.commonformatparser.CfActionParser;
import de.uds.MonitorInterventionMetafora.server.utils.GeneralUtil;
import de.uds.MonitorInterventionMetafora.server.xml.XmlFragment;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;

public class MetaforaCfFileCommunicationBridge2 implements CfCommunicationBridge{
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
	private XmlFragment xmlIn;
	private XmlFragment xmlOut;
	
	private Vector<CfCommunicationListener> listeners;
	
	public MetaforaCfFileCommunicationBridge2(CommunicationChannelType type){
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
	
	private XmlFragment getOrCreateFragment(String filename){
		
		System.out.println("trying to get interaction data!!");
		XmlFragment fragment = XmlFragment.getFragmentFromLocalFile(filename);
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
			XmlFragment actionsFrag = xmlOut.accessChild("actions");
			actionsFrag.addContent(CfActionParser.toXml(actionToSend));
			xmlOut.overwriteFile(channelNameOut);
		}
		
	}
	
	private void sendMessages() {
		
		System.out.println("Sending individual actions!!");
		XmlFragment actionsFrag = xmlIn.accessChild("actions");
		for (XmlFragment actionFrag : actionsFrag.getChildren("action")){
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
