package de.uds.MonitorInterventionMetafora.client.urlparameter;

import de.uds.MonitorInterventionMetafora.shared.utils.GWTUtils;



public class UrlParameterConfig {
	

	private String username="";
	private String password="";
	private String configID="";
	

	public UrlParameterConfig(){
		
			username = com.google.gwt.user.client.Window.Location.getParameter("user");
			password = com.google.gwt.user.client.Window.Location.getParameter("pw");
			configID = com.google.gwt.user.client.Window.Location.getParameter("config");
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
		return configID;
	}



}
