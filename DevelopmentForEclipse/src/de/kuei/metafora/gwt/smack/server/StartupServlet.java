package de.kuei.metafora.gwt.smack.server;

import javax.servlet.http.HttpServlet;

public class StartupServlet extends HttpServlet{

	public void init() {
    	try {
			XMPPBridge.getInstance();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}