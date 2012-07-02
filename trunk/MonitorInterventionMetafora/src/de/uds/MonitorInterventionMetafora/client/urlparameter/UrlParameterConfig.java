package de.uds.MonitorInterventionMetafora.client.urlparameter;

import de.uds.MonitorInterventionMetafora.shared.utils.GWTUtils;



public class UrlParameterConfig {
	

	private String username="";
	private String password="";
	private String configID="";
	private String locale="";
	private String receiver="";

	public UrlParameterConfig(){
		
			username = com.google.gwt.user.client.Window.Location.getParameter("user");
			password = com.google.gwt.user.client.Window.Location.getParameter("pw");
			configID = com.google.gwt.user.client.Window.Location.getParameter("config");
			receiver = com.google.gwt.user.client.Window.Location.getParameter("receiver");
			

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


	public String getReceiver() {
		if (receiver!=null)
		return receiver;
		return "Metafora";
	}

}
