package de.uds.MonitorInterventionMetafora.client.urlparameter;

import com.allen_sauer.gwt.log.client.Log;

import de.uds.MonitorInterventionMetafora.shared.utils.GWTUtils;

public class UrlParameterConfig {
	
	private String username;
	private String password;
	private String configID;
	private String locale;
	private String receiver;
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
		password = com.google.gwt.user.client.Window.Location.getParameter("pw");
		configID = com.google.gwt.user.client.Window.Location.getParameter("config");
		receiver = com.google.gwt.user.client.Window.Location.getParameter("receiver");
		locale = com.google.gwt.user.client.Window.Location.getParameter("locale");

		username = (username == null) ? "" : username;
		password = (password == null) ? "" : password;
		configID = (configID == null) ? "" : configID;
		receiver = (receiver == null) ? "" : receiver;
		locale = (locale == null) ? "" : locale;
		
		String testServerStr = com.google.gwt.user.client.Window.Location.getParameter("testServer");
		testServer = (testServerStr == null) ? false : Boolean.parseBoolean(testServerStr); 

		Log.info("URL Params: User-" + username + " : mainConfig-" + configID + " : receiver-" + receiver + " : locale-" + locale);
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

	public String getPassword() {
		return password;
	}

	public String getConfig() {
		if (configID != null)
			return configID.toLowerCase();
		return null;
	}

	public String getLocale() {
		if (locale != null)
			return locale;
		return "en";
	}

	public boolean getTestServer() {
		return testServer;
	}

	public String getReceiver() {
		if (receiver != null)
			return receiver;
		return "Metafora";
	}
}
