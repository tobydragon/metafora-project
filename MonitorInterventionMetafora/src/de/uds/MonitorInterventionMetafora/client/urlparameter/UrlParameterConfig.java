package de.uds.MonitorInterventionMetafora.client.urlparameter;

import de.uds.MonitorInterventionMetafora.shared.utils.GWTUtils;



public class UrlParameterConfig {
	

	private String username="";
	private String password="";
	

	public UrlParameterConfig(){
		
			username = com.google.gwt.user.client.Window.Location.getParameter("user");
			password = com.google.gwt.user.client.Window.Location.getParameter("pw");
			
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




}
