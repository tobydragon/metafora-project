package de.uds.MonitorInterventionMetafora.client.urlparameter;

import java.util.List;
import java.util.Vector;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.Window;

import de.uds.MonitorInterventionMetafora.shared.commonformat.MetaforaStrings;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.XmppServerType;
import de.uds.MonitorInterventionMetafora.shared.suggestedmessages.Locale;
import de.uds.MonitorInterventionMetafora.shared.suggestedmessages.MessageType;
import de.uds.MonitorInterventionMetafora.shared.utils.GWTUtils;

public class UrlParameterConfig {
	
        public enum UserType {
            METAFORA_USER, RECOMMENDING_WIZARD, MESSAGING_WIZARD
        }

	private String username;
	private String groupId;
	
	private String receiverIDs;
	private String password;
	private String configID;
	private Locale locale;
	private String receiver;
	private MessageType messageType;
	private UserType userType;
	private boolean testServer;
	private boolean monitoring;
	private boolean complexDataViews;
	
	private List<String> loggedInUsers;

	//The Metafora XMPP server type (by default is the test)
	private XmppServerType xmppServerType = XmppServerType.METAFORA_TEST;
	private static UrlParameterConfig singletonInstance;
	
	

	private UrlParameterConfig() {
		loggedInUsers = new Vector<String>();
		username = getAndDecodeUrlParam("user");
		if (username != null){
			loggedInUsers.add(username);
		}
		for (int i=1; i<10; i++){
			String nextUsername = getAndDecodeUrlParam( ("otherUser"+i) );
			if (nextUsername != null){
				loggedInUsers.add(nextUsername);
			}
			else {
				break;
			}
		}
		
		groupId = getAndDecodeUrlParam("groupId");
		receiverIDs = getAndDecodeUrlParam("receiverIDs");
		password = getAndDecodeUrlParam("pw");
		configID = getAndDecodeUrlParam("config");
		//Determines which server is used for the messages to be sent and received
		//actually what gets added to the tool
		receiver = getAndDecodeUrlParam("receiver");
		//Used really for determining if things are logged
		String testServerStr = getAndDecodeUrlParam("testServer");
		String monitoringStr = getAndDecodeUrlParam("monitoring");
		String comeplexDataViewsString = getAndDecodeUrlParam("complexDataViews");
		String userTypeString = getAndDecodeUrlParam("userType");
		String messageTypeString = getAndDecodeUrlParam("messageType");
		String localeStr =  getAndDecodeUrlParam("locale");

		if (userTypeString == null) { 
		    userType = UserType.MESSAGING_WIZARD;
		} else if (userTypeString.equalsIgnoreCase("METAFORA_USER")) {
		    userType = UserType.METAFORA_USER;
		} else if (userTypeString.equalsIgnoreCase("RECOMMENDING_WIZARD")) {
		    userType = UserType.RECOMMENDING_WIZARD;
		} else userType = UserType.MESSAGING_WIZARD;
			
		if (messageTypeString == null ){
			if (userType == UserType.METAFORA_USER) {
				messageType = MessageType.PEER;
			}
			else {
				messageType = MessageType.EXTERNAL;
			}
		} else if (messageTypeString.equalsIgnoreCase("PEER") ) {
		    messageType = MessageType.PEER;
		} else {
			messageType = MessageType.EXTERNAL;
		}
		
		locale = Locale.en;
		if (localeStr != null){
			try {
				locale = Locale.valueOf(localeStr);
			}
			catch (Exception e){
				Log.warn("[UrlParameterConfig.constructor: unknown locale:"+ localeStr +", using default lacale: " + locale);
			}
		}
		
		receiver = (receiver == null) ? MetaforaStrings.RECEIVER_METAFORA_TEST : receiver;

		if (MetaforaStrings.RECEIVER_METAFORA.equalsIgnoreCase(receiver)){
		    xmppServerType = XmppServerType.METAFORA;
		} else if (MetaforaStrings.RECEIVER_METAFORA_TEST.equalsIgnoreCase(receiver)){
		    xmppServerType = XmppServerType.METAFORA_TEST;
		} else {
		    //setting this to null (for now) if receiver is not one of the known Metafora ones
		    xmppServerType = null; 
		    String msg = "Unknown receiver, feedback messages go to default XMPP server, but will likely be ignored by recipient for receiver - " + receiver;
		    Log.warn("[UrlParameterConfig.contsructor] " + msg);
		    Window.alert(msg);
		} 		
		
		testServer = (testServerStr == null) ? true : Boolean.parseBoolean(testServerStr); 
		monitoring = (monitoringStr == null) ? false : Boolean.parseBoolean(monitoringStr);
		complexDataViews = (comeplexDataViewsString == null) ? false : Boolean.parseBoolean(comeplexDataViewsString); 

				
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

	public Locale getLocale() {
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


	public boolean isComplexDataViews() {
		return complexDataViews;
	}


	public List<String> getLoggedInUsers() {
		return loggedInUsers;
	}
	
	
	
}
