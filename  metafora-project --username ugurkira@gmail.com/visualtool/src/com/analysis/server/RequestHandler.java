package com.analysis.server;

import java.util.HashMap;
import java.util.Map;
import com.analysis.client.communication.server.CommunicationService;
import com.analysis.server.io.FileOperation;
import com.analysis.server.io.XmlFragment;

import com.analysis.server.io.SourceManager;
import com.analysis.server.utils.ServerFormatStrings;
import com.analysis.server.xmpp.StartupServlet;
import com.analysis.server.xmppsx.XmppActionListener;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class RequestHandler extends RemoteServiceServlet implements
		CommunicationService {


	


	public String inputToServer(String request){
		
		//System.out.println("Request1111111:"+requestType);
		
		//SourceManager.getConfiguration(FileOperation.read("conf/configuration.xml"));

		
		//String serverInfo = getServletContext().getServerInfo();
		
		//System.out.println("Server Result:"+request.get("type"));
		
	
		String userAgent = getThreadLocalRequest().getHeader("User-Agent");

		userAgent = escapeHtml(userAgent);
		//String xml=handleRequest(request.get("type"));


		return  "";

		
	}


	
	public String inputToServer(String requestType,String value){
		
		String userAgent = getThreadLocalRequest().getHeader("User-Agent");

		userAgent = escapeHtml(userAgent);
	
		String xml=handleRequest(requestType,value);
		
		return  xml;

		
	}
	String handleRequest(String requestType){
	
		
		
		System.out.println("From client to server!:"+requestType);
		Map<String, String> activeConf=SourceManager.getConfiguration();
		
		Thread thread = new Thread(new XmppActionListener());
		//thread.start();
		
		//StartupServlet aa=new StartupServlet();
		//aa.init();
		// XmppActionListener xa=new XmppActionListener();
    	// xa.run();
		String result="";

		if(requestType.equalsIgnoreCase("RequestHistory")){
			System.out.println("RH is true!:"+requestType);
			
			if(activeConf.get(ServerFormatStrings.Type).equalsIgnoreCase("xmpp")){
				
				result="xmpp is not supported  yet!";
			}
			else if(activeConf.get(ServerFormatStrings.Type).equalsIgnoreCase("file")){
				
				result=FileOperation.read(activeConf.get(ServerFormatStrings.PATH));
			}
			
			else{
				
				result="No Active Source";
			}
			
		}
		
		return result;
	}
	
	String handleRequest(String requestType,String value){
		String result="";
		if(requestType.equalsIgnoreCase("FileSource")){
			result=FileOperation.read(value);
			
			
		}
		return result;
	}
	
	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;");
	}


	static String conf="conf/filterconfiguration.xml";

	// Comm1

	public String sendToServer(String requestType) {
	
		
		
		System.out.println("From client to server!:"+requestType);
		Map<String, String> activeConf=SourceManager.getConfiguration();
		
		Thread thread = new Thread(new XmppActionListener());
		
		String result="";

		if(requestType.equalsIgnoreCase("RequestHistory")){
			System.out.println("RH is true!:"+requestType);
			
			if(activeConf.get(ServerFormatStrings.Type).equalsIgnoreCase("xmpp")){
				
				result="xmpp is not supported  yet!";
			}
			else if(activeConf.get(ServerFormatStrings.Type).equalsIgnoreCase("file")){
				
				result=FileOperation.read(activeConf.get(ServerFormatStrings.PATH));
			}
			
			else{
				
				result="No Active Source";
			}
			
		}
		
		else if(requestType.equalsIgnoreCase("RequestConfiguration")){
			
			result=FileOperation.read("conf/filterconfiguration.xml");
			
		}
		
		return result;
		
	}
	
	//Comm2
	public Map<String, Map<String, String>> sendToServer(Map<String, String> cr){
		
		
		Map<String, Map<String, String>> configuration=new HashMap<String, Map<String, String>>();
		Map<String, String> filter;
		
		filter=new HashMap<String, String>();
		filter.put("configurationname", "Test");
		configuration.put("Test", filter);
		
		
		filter=new HashMap<String, String>();
		filter.put("configurationname", "Test");
		configuration.put("Test", filter);
		
		
		
		
		return null;
	}


	
	/*
	public  requestFilters(
			Map<String, String> cr) {
	
		
		for(XmlFragment xf : XmlFragment.getFragmentFromFile(conf).getChildren(ServerFormatStrings.HISTORY_SOURCE)){
			
			
		}
		
		
		return null;
	}*/
}
