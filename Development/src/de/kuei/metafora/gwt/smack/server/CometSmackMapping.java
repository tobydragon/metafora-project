package de.kuei.metafora.gwt.smack.server;

import java.io.IOException;
import java.util.Vector;

import net.zschech.gwt.comet.server.CometServletResponse;
import de.kuei.metafora.gwt.smack.server.xmpp.XMPPMessageListener;

public class CometSmackMapping implements XMPPMessageListener{

	private static CometSmackMapping mapping = null;
	
	public static CometSmackMapping getInstance() throws Exception{
		if(mapping == null){
			mapping = new CometSmackMapping();
		}
		return mapping;
	}
	
	private Vector<CometServletResponse> clientChannels;
	
	public CometSmackMapping() throws Exception{
		if(mapping != null){
			throw new Exception("Something went wrong. There is already a mapping class.");
		}
		clientChannels = new Vector<CometServletResponse>();
		XMPPBridge.getInstance().registerListener(this);
	}
	
	public void registerUser(CometServletResponse response){
		clientChannels.add(response);
	}
	
	public void unregisterUser(CometServletResponse response){
		//This method is called after every write process so it is no
		//good idea to close the xmpp connection if there is no client!
		//TODO: Find a solution with a timeout of a few seconds.
		clientChannels.remove(response);
	}

	@Override
	public void newMessage(String user, String message, String chat) {
		for(int i=0; i<clientChannels.size(); i++){
			try {
				clientChannels.get(i).write(user+": "+message);
			} catch (IOException e) {
				System.err.println("Comet Error: "+e.getMessage());
			}
		}
	}
}
