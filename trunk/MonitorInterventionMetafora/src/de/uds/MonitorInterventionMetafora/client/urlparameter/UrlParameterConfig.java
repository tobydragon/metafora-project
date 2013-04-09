package de.uds.MonitorInterventionMetafora.client.urlparameter;

import com.allen_sauer.gwt.log.client.Log;

import de.uds.MonitorInterventionMetafora.shared.commonformat.MetaforaStrings;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.XmppServerType;
import de.uds.MonitorInterventionMetafora.shared.utils.GWTUtils;

public class UrlParameterConfig {
	

        public enum UserType {
            METAFORA_USER, STANDARD_WIZARD, POWER_WIZARD, METAFORA_RECOMMENDATIONS
        }

	private String username;
	private String receiverIDs;
	private String password;
	private String configID;
	private String locale;
	private String receiver;
	private UserType userType;
	private boolean testServer;
	private XmppServerType xmppServerType;
	private static UrlParameterConfig singletonInstance;
	

	private UrlParameterConfig() {
		
		username = com.google.gwt.user.client.Window.Location.getParameter("user");
		receiverIDs = com.google.gwt.user.client.Window.Location.getParameter("receiverIDs");
		password = com.google.gwt.user.client.Window.Location.getParameter("pw");
		configID = com.google.gwt.user.client.Window.Location.getParameter("config");
		receiver = com.google.gwt.user.client.Window.Location.getParameter("receiver");
		locale = com.google.gwt.user.client.Window.Location.getParameter("locale");
		String testServerStr = com.google.gwt.user.client.Window.Location.getParameter("testServer");
		String userTypeString = com.google.gwt.user.client.Window.Location.getParameter("userType");

		if (userTypeString == null) { 
		    userType = UserType.STANDARD_WIZARD;
		} else if (userTypeString.equals("METAFORA_USER")) {
		    userType = UserType.METAFORA_USER;
		} else if (userTypeString.equals("POWER")) {
	        userType = UserType.POWER_WIZARD;
		} else if (userTypeString.equals("RECOMMENDATIONS")) {
			userType = UserType.METAFORA_RECOMMENDATIONS;
		} else userType = UserType.STANDARD_WIZARD;
		
		//TD commented this out.  We depend on recieverIDs to be null when not included. These others don't seem better with empty defaults than null.
//		username = (username == null) ? "" : username;
//		receiverIDs = (receiverIDs == null) ? "" : receiverIDs;
//		password = (password == null) ? "" : password;
//		configID = (configID == null) ? "" : configID;
		
		//xmppServeType should be null if receiver is not included, so that default is used
		if (receiver != null){
			if (MetaforaStrings.RECEIVER_METAFORA.equalsIgnoreCase(receiver)){
				xmppServerType = XmppServerType.DEPLOY;
			}
			else if (MetaforaStrings.RECEIVER_METAFORA_TEST.equalsIgnoreCase(receiver)){
				xmppServerType = XmppServerType.TEST;
			}
			else {
				Log.warn("[UrlParameterConfig.contsructor] Unknown receiver, feedback messages will likely be ignored by recipient for receiver - " + receiver);
			}
		}
		
		
		receiver = (receiver == null) ? MetaforaStrings.RECEIVER_METAFORA_TEST : receiver;
		locale = (locale == null) ? "en" : locale;
		testServer = (testServerStr == null) ? false : Boolean.parseBoolean(testServerStr); 
		
		
		

		Log.info("URL Params: User-" + username + " : mainConfig-" + configID + " : receiver-" + receiver + " : locale-" + locale + " : userType-" + userType + " : receiverIDs-" + receiverIDs);
	}

	/**
	 * Singleton pattern implementation
	 * 
	 * @return
	 */
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

	public String getReceiver() {
	    	return receiver;
	}
	
	public UserType getUserType() {
	    return userType;
	}

	public XmppServerType getXmppServerType() {
		return xmppServerType;
	}
	
	
	
}
