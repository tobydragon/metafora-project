package de.uds.MonitorInterventionMetafora.client.urlparameter;

import com.allen_sauer.gwt.log.client.Log;

import de.uds.MonitorInterventionMetafora.shared.commonformat.MetaforaStrings;
import de.uds.MonitorInterventionMetafora.shared.utils.GWTUtils;

public class UrlParameterConfig {
	

        public enum UserType {
            METAFORA_USER, STANDARD_WIZARD, POWER_WIZARD
        }

	private String username;
	private String receiverIDs;
	private String password;
	private String configID;
	private String locale;
	private String receiver;
	private UserType userType;
	private boolean testServer;
	private static UrlParameterConfig singletonInstance;

	private UrlParameterConfig() {
		/*
		 * TODO: use default values if url params are not provided
		 * if URL params are not NULL - read url params
		 * else if xml_file_is_found - use xml
		 * else - use hard-coded values
		 */
		username = com.google.gwt.user.client.Window.Location.getParameter("user");
		receiverIDs = com.google.gwt.user.client.Window.Location.getParameter("receiverIDs");
		password = com.google.gwt.user.client.Window.Location.getParameter("pw");
		configID = com.google.gwt.user.client.Window.Location.getParameter("config");
		receiver = com.google.gwt.user.client.Window.Location.getParameter("receiver");
		locale = com.google.gwt.user.client.Window.Location.getParameter("locale");
		
		String userTypeString = com.google.gwt.user.client.Window.Location.getParameter("userType");

		if (userTypeString == null) { 
		    userType = UserType.METAFORA_USER;
		} else if (userTypeString.equals("METAFORA_USER")) {
		    userType = UserType.METAFORA_USER;
		} else if (userTypeString.equals("POWER")) {
	        	userType = UserType.POWER_WIZARD;
		} else userType = UserType.STANDARD_WIZARD;
		
		username = (username == null) ? "" : username;
		receiverIDs = (receiverIDs == null) ? "" : receiverIDs;
		password = (password == null) ? "" : password;
		configID = (configID == null) ? "" : configID;
		receiver = (receiver == null) ? MetaforaStrings.RECEIVER_METAFORA : receiver;
		locale = (locale == null) ? "en" : locale;
		
		String testServerStr = com.google.gwt.user.client.Window.Location.getParameter("testServer");
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
	
}
