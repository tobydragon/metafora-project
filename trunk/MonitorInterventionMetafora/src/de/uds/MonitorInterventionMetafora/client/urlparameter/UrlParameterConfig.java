package de.uds.MonitorInterventionMetafora.client.urlparameter;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.Window;

import de.uds.MonitorInterventionMetafora.client.urlparameter.UrlParameterConfig.MessageType;
import de.uds.MonitorInterventionMetafora.shared.commonformat.MetaforaStrings;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.XmppServerType;
import de.uds.MonitorInterventionMetafora.shared.utils.GWTUtils;

public class UrlParameterConfig {
	
        public enum MessageType {
            PEER, EXTERNAL
        }

    
        public enum UserType {
            METAFORA_USER, RECOMMENDING_WIZARD, MESSAGING_WIZARD
        }

	private String username;
	private String groupId;
	
	private String receiverIDs;
	private String password;
	private String configID;
	private String locale;
	private String receiver;
	private MessageType messageType;
	private UserType userType;
	private boolean testServer;
	private boolean monitoring;
	//The Metafora XMPP server type (by default is the test)
	private XmppServerType xmppServerType = XmppServerType.TEST;
	private static UrlParameterConfig singletonInstance;
	

	private UrlParameterConfig() {
		
		username = getAndDecodeUrlParam("user");
		groupId = getAndDecodeUrlParam("groupId");
		receiverIDs = getAndDecodeUrlParam("receiverIDs");
		password = getAndDecodeUrlParam("pw");
		configID = getAndDecodeUrlParam("config");
		//Determines which server is used for the messages to be sent and received
		//actually what gets added to the tool
		receiver = getAndDecodeUrlParam("receiver");
		locale = getAndDecodeUrlParam("locale");
		//Used really for determining if things are logged
		String testServerStr = getAndDecodeUrlParam("testServer");
		String monitoringStr = getAndDecodeUrlParam("monitoring");
		String userTypeString = getAndDecodeUrlParam("userType");
		String messageTypeString = getAndDecodeUrlParam("messageType");

		if (userTypeString == null) { 
		    userType = UserType.MESSAGING_WIZARD;
		} else if (userTypeString.equalsIgnoreCase("METAFORA_USER")) {
		    userType = UserType.METAFORA_USER;
		} else if (userTypeString.equalsIgnoreCase("RECOMMENDING_WIZARD")) {
		    userType = UserType.RECOMMENDING_WIZARD;
		} else userType = UserType.MESSAGING_WIZARD;
			
		if (messageTypeString == null) {
		    messageType = MessageType.EXTERNAL;
		} else if (messageTypeString.equalsIgnoreCase("PEER")) {
		    messageType = MessageType.PEER;
		} else messageType = MessageType.EXTERNAL;
		
		receiver = (receiver == null) ? MetaforaStrings.RECEIVER_METAFORA_TEST : receiver;

		if (MetaforaStrings.RECEIVER_METAFORA.equalsIgnoreCase(receiver)){
		    xmppServerType = XmppServerType.DEPLOY;
		} else if (MetaforaStrings.RECEIVER_METAFORA_TEST.equalsIgnoreCase(receiver)){
		    xmppServerType = XmppServerType.TEST;
		} else {
		    //setting this to null (for now) if receiver is not one of the known Metafora ones
		    xmppServerType = null; 
		    String msg = "Unknown receiver, feedback messages will likely be ignored by recipient for receiver - " + receiver;
		    Log.warn("[UrlParameterConfig.contsructor] " + msg);
		    Window.alert(msg);
		} 		
		
		locale = (locale == null) ? "he" : locale;
		testServer = (testServerStr == null) ? true : Boolean.parseBoolean(testServerStr); 
		monitoring = (monitoringStr == null) ? false : Boolean.parseBoolean(monitoringStr); 
				
		Log.info("URL Params: User-" + username + " : mainConfig-" + configID + " : receiver-" + receiver + " : locale-" + locale + " : userType-" + userType + " : receiverIDs-" + receiverIDs);
	}


	private String getAndDecodeUrlParam(String paramKey){
		String value = com.google.gwt.user.client.Window.Location.getParameter(paramKey);
		if (value != null){
			value = URL.decode(value);
		}
		return value;
	}
	
	public static UrlParameterConfig getInstance() {
		if (singletonInstance == null) {
			singletonInstance = new UrlParameterConfig();
		}
		return singletonInstance;
	}
	
	public String getUsername() {
		if (username == null || username == "") {
			username = Long.toString(GWTUtils.getTimeStamp());
		}
		return username;
	}
	
	public String getGroupId() {
		return groupId;
	}

	/**
	 * Returns the receiverIDs parameter (to be used for the IDs of user/receivers) 
	 * No processing is done to the parameter here
	 * @return userIDs
	 */
	public String getReceiverIDs() {
		return receiverIDs;
	}
	
	public String getPassword() {
		return password;
	}

	public String getConfig() {
		if (configID != null)
			return configID.toLowerCase();
		return null;
	}

	public String getLocale() {
		return locale;
	}

	public boolean getTestServer() {
		return testServer;
	}

	public boolean getMonitoring() {
		return monitoring;
	}

	public String getReceiver() {
	    	return receiver;
	}
	
	public UserType getUserType() {
	    return userType;
	}

	public XmppServerType getXmppServerType() {
		return xmppServerType;
	}


	public MessageType getMessageType() {
	    return messageType;
	}
	
	
	
}
