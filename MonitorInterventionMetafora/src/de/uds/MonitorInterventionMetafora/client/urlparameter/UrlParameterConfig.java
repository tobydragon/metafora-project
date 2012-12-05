package de.uds.MonitorInterventionMetafora.client.urlparameter;

import com.allen_sauer.gwt.log.client.Log;

import de.uds.MonitorInterventionMetafora.shared.utils.GWTUtils;



public class UrlParameterConfig {
	

	private String username="";
	private String password="";
	private String configID="";
	private String locale="en";
	private String receiver="";
	private boolean testServer= false;

	public UrlParameterConfig(){
		
			username = com.google.gwt.user.client.Window.Location.getParameter("user");
			password = com.google.gwt.user.client.Window.Location.getParameter("pw");
			configID = com.google.gwt.user.client.Window.Location.getParameter("config");
			receiver = com.google.gwt.user.client.Window.Location.getParameter("receiver");
			
			String localeStr = com.google.gwt.user.client.Window.Location.getParameter("locale");
			if (localeStr != null){
				locale = localeStr;
			}
			String testServerStr = com.google.gwt.user.client.Window.Location.getParameter("testServer");
			if (testServerStr != null){
			    testServer = Boolean.parseBoolean(testServerStr);
			}
			Log.info("URL Params: User-"+ username + " : mainConfig-"+ configID + " : receiver-"+ receiver + " : locale-"+ locale);
		}
	
	public String getUsername() {
		
		if(username==null || username==""){
			
			username=Long.toString(GWTUtils.getTimeStamp());
		}
		
		return username;
	}


	public String getPassword() {
		return password;
	}
	public String getConfig() {
		if(configID!=null)
		return configID.toLowerCase();
		return null;
	}

	public String getLocale() {
		if (locale!=null)
			return locale;
		return "en";
	}

	public boolean getTestServer() {
	   return testServer; 
	}

	public String getReceiver() {
		if (receiver!=null)
		return receiver;
		return "Metafora";
	}

}
