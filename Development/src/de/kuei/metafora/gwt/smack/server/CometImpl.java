package de.kuei.metafora.gwt.smack.server;

import java.io.IOException;

import javax.servlet.ServletException;

import net.zschech.gwt.comet.server.CometServlet;
import net.zschech.gwt.comet.server.CometServletResponse;

public class CometImpl extends CometServlet{
	
	@Override
    protected void doComet(CometServletResponse cometResponse) throws ServletException, IOException {
		try {
			if(CometSmackMapping.getInstance() != null){
				CometSmackMapping.getInstance().registerUser(cometResponse);
				cometResponse.write("You are on!");
			}
		} catch (Exception e) {
			System.err.println("Exception: "+e.getMessage());
		}
    }
    
    @Override
    public void cometTerminated(CometServletResponse cometResponse, boolean serverInitiated) {
    	try {
			if(CometSmackMapping.getInstance() != null){
				CometSmackMapping.getInstance().unregisterUser(cometResponse);
			}
		} catch (Exception e) {
			System.err.println("Exception: "+e.getMessage());
		}
    }
}
