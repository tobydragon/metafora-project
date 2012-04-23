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
		commandConnectionNameIn =GeneralUtil.getAplicationResourceDirectory()+ "conffiles/xml/test/commandChannelInput.xml";
		analysisConnectionNameIn = GeneralUtil.getAplicationResourceDirectory()+"conffiles/xml/test/analysisChannelInput.xml";
		commandConnectionNameOut = GeneralUtil.getAplicationResourceDirectory()+"conffiles/xml/test/commandChannelOutput.xml";
		analysisConnectionNameOut =GeneralUtil.getAplicationResourceDirectory()+ "conffiles/xml/test/analysisChannelOutput.xml";
		
		// TODO: Read filenames from config file, see static in CfXmppCommunicationBridge
	}
	
	
	public MetaforaCfFileCommunicationBridge(CommunicationChannelType type) {

		if (type == CommunicationChannelType.command){
			setup(commandConnectionNameIn, commandConnectionNameOut);
		}
		else if (type == CommunicationChannelType.analysis){
			setup(analysisConnectionNameIn, analysisConnectionNameOut);
		} 
		else {
			logger.error("[constructor] Uknown connectionType");
			return;
		}
	}

}
