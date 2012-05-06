package de.uds.MonitorInterventionMetafora.server.cfcommunication;

import org.apache.log4j.Logger;

import de.uds.MonitorInterventionMetafora.server.utils.GeneralUtil;

public class MetaforaCfFileCommunicationBridge extends SimpleCfFileCommunicationBridge{
	Logger logger = Logger.getLogger(CfCommunicationBridge.class);
	
	private static String commandConnectionNameIn = null;
	private static String analysisConnectionNameIn = null;
	private static String commandConnectionNameOut = null;
	private static String analysisConnectionNameOut = null;
	
	static {
		commandConnectionNameIn =GeneralUtil.getRealPath("conffiles/xml/test/commandChannelInput.xml");
		analysisConnectionNameIn = GeneralUtil.getRealPath("conffiles/xml/test/analysisChannelInput.xml");
		commandConnectionNameOut = GeneralUtil.getRealPath("conffiles/xml/test/commandChannelOutput.xml");
		analysisConnectionNameOut =GeneralUtil.getRealPath("conffiles/xml/test/analysisChannelOutput.xml");
		
		// TODO: Read filenames from config file, see static in CfXmppCommunicationBridge
	}
	
	
	public MetaforaCfFileCommunicationBridge(CommunicationChannelType type) {

		if (type == CommunicationChannelType.command){
			setup(commandConnectionNameIn, commandConnectionNameOut, CfFileLocation.LOCAL);
		}
		else if (type == CommunicationChannelType.analysis){
			setup(analysisConnectionNameIn, analysisConnectionNameOut, CfFileLocation.LOCAL);
		} 
		else {
			logger.error("[constructor] Uknown connectionType");
			return;
		}
	}

}
