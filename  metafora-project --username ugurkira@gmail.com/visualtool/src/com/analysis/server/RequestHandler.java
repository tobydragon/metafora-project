package com.analysis.server;

import java.util.Map;



import com.analysis.client.communication.server.CommunicationService;

import com.analysis.server.io.FileOperation;

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

String datam="<interactiondata>" +
	
	"<action time='1327015888359'>" +
	"<actiontype classification='create' type='indicator' logged='false'/>" +
	"<user id='Bob' user_role='student'/>" +
	"<object id='1' type='Element'>" +
	"<properties>" +
	"<property name='MAP_ID' value='1'/>" +
	"<property name='TEXT' value='I think my model is perfect, but it keeps telling me its not general.'/>" +
	"<property name='USERNAME' value='Bob'/>" +
	"<property name='ELEMENT_TYPE' value='Help Request'/>" +
	"</properties>" +
	"</object>" +
	"<content>" +
	"<description><![CDATA[Help Request 1 created: 'I think my model is perfect, but it keeps telling me its not general.']]></description>" +
	"<properties>" +
	"<property name='INDICATOR_TYPE' value='create'/>" +
	"<property name='TOOL' value='LASAD'/>" +
	"</properties>" +
	"</content>" +
	"</action>" +	
	
	"<action time='1327015888359'>" +
	"<actiontype classification='other' type='indicator' logged='false'/>" +
	"<user id='Bob' user_role='student'/>" +
	"<object id='1' type='Element'>" +
	"<properties>" +
	"<property name='MAP_ID' value='1'/>" +
	"<property name='TEXT' value='I think my model is perfect, but it keeps telling me its not general.'/>" +
	"<property name='USERNAME' value='Bob'/>" +
	"<property name='ELEMENT_TYPE' value='Help Request'/>" +
	"</properties>" +
	"</object>" +
	"<content>" +
	"<description><![CDATA[Positivity in Help Request 1: 'I think my model is perfect, but it keeps telling me its not general.']]></description>" +
	"<properties>" +
	"<property name='INDICATOR_TYPE' value='annotation'/>" +
	"<property name='ANNOTATION_TYPE' value='positivity'/>" +
	"<property name='TOOL' value='LASAD'/>" +
	"</properties>" +
	"</content>" +
	"</action>" +
	
	"<action time='1327015888359'>" +
	"<actiontype classification='other' type='indicator' logged='false'/>" +
	"<user id='Bob' user_role='student'/>" +
	"<object id='1' type='Element'>" +
	"<properties>" +
	"<property name='MAP_ID' value='1'/>" +
	"<property name='TEXT' value='I think my model is perfect, but it keeps telling me its not general.'/>" +
	"<property name='USERNAME' value='Bob'/>" +
	"<property name='ELEMENT_TYPE' value='Help Request'/>" +
	"</properties>" +
	"</object>" +
	"<content>" +
	"<description><![CDATA[Microworld talk in Help Request 1: 'I think my model is perfect, but it keeps telling me its not general.']]></description>" +
	"<properties>" +
	"<property name='INDICATOR_TYPE' value='annotation'/>" +
	"<property name='ANNOTATION_TYPE' value='microworld talk'/>" +
	"<property name='TOOL' value='LASAD'/>" +
	"</properties>" +
	"</content>" +
	"</action>" +
	
	
	
	"<action time='1327015893359'>" +
	"<actiontype classification='other' type='indicator' logged='false'/>" +
	"<user id='Alice' user_role='student'/>" +
	"<object id='2' type='Element'>" +
	"<properties>" +
	"<property name='MAP_ID' value='4'/>" +
	"<property name='TEXT' value='I do not understand this stupid thing'/>" +
	"<property name='USERNAME' value='Alice'/>" +
	"<property name='ELEMENT_TYPE' value='Comment'/>" +
	"</properties>" +
	"</object>" +
	"<content>" +
	"<description><![CDATA[Negativity in comment 2: 'I do not understand this stupid thing']]></description>" +
	"<properties>" +
	"<property name='INDICATOR_TYPE' value='annotation'/>" +
	"<property name='ANNOTATION_TYPE' value='negativity'/>" +
	"<property name='TOOL' value='LASAD'/>" +
	"</properties>" +
	"</content>" +
	"</action>" +

	"<action time='1327015893359'>" +
	"<actiontype classification='other' type='indicator' logged='false'/>" +
	"<user id='Alice' user_role='student'/>" +
	"<object id='2' type='Element'>" +
	"<properties>" +
	"<property name='MAP_ID' value='4'/>" +
	"<property name='TEXT' value='I do not understand this stupid thing'/>" +
	"<property name='USERNAME' value='Alice'/>" +
	"<property name='ELEMENT_TYPE' value='Comment'/>" +
	"</properties>" +
	"</object>" +
	"<content>" +
	"<description><![CDATA[Abusive talk in comment 2: 'I do not understand this stupid thing']]></description>" +
	"<properties>" +
	"<property name='INDICATOR_TYPE' value='annotation'/>" +
	"<property name='ANNOTATION_TYPE' value='abusive talk'/>" +
	"<property name='TOOL' value='LASAD'/>" +
	"</properties>" +
	"</content>" +
	"</action>" +
	
	
	
	
	"<action time='1325841177864'>" +
	"<actiontype classification='create' type='indicator' logged='false'/>" +
	"<user id='Alice' user_role='student'/>" +
	"<object id='2' type='Element'>" +
	"<properties>" +
	"<property name='MAP_ID' value='4'/>" +
	"<property name='USERNAME' value='Alice'/>" +
	"<property name='ELEMENT_TYPE' value='Brainstorm'/>" +
	"</properties>" +
	"</object>" +
	"<content>" +
	"<description><![CDATA[Brainstorm activity stage created]]></description>" +
	"<properties>" +
	"<property name='INDICATOR_TYPE' value='create'/>" +
	"<property name='TOOL' value='Planning Tool'/>" +
	"</properties>" +
	"</content>" +
	"</action>" +	
	
	"<action time='1325841187864'>" +
	"<actiontype classification='other' type='indicator' logged='false'/>" +
	"<user id='Alice' user_role='student'/>" +
	"<user id='Bob' user_role='student'/>" +
	"<user id='Maria' user_role='student'/>" +
	"<object id='2' type='Element'>" +
	"<properties>" +
	"<property name='MAP_ID' value='4'/>" +
	"<property name='USERNAME' value='Alice'/>" +
	"<property name='ELEMENT_TYPE' value='Brainstorm'/>" +
	"</properties>" +
	"</object>" +
	"<content>" +
	"<description><![CDATA[This plan diverges at the brainstorm stage, but does not converge in the future.]]></description>" +
	"<properties>" +
	"<property name='INDICATOR_TYPE' value='diverge-without-converge'/>" +
	"<property name='TOOL' value='Planning Tool'/>" +
	"</properties>" +
	"</content>" +
	"</action>" +

	"<action time='1325841197864'>" +
	"<actiontype classification='Delete' type='indicator' logged='false'/>" +
	"<user id='Maria' user_role='student'/>" +
	"<object id='2' type='Element'>" +
	"<properties>" +
	"<property name='MAP_ID' value='4'/>" +
	"<property name='USERNAME' value='Maria'/>" +
	"<property name='ELEMENT_TYPE' value='comment'/>" +
	"</properties>" +
	"</object>" +
	"<content>" +
	"<description><![CDATA[Maria deleted comment 2: 'I do not understand this stupid thing' ]]></description>" +
	"<properties>" +
	"<property name='INDICATOR_TYPE' value='create'/>" +
	"<property name='TOOL' value='LASAD'/>" +
	"</properties>" +
	"</content>" +
	"</action>" +

	"<action time='1325841207864'>" +
	"<actiontype classification='Modify' type='indicator' logged='false'/>" +
	"<user id='Alice' user_role='student'/>" +
	"<object id='2' type='Element'>" +
	"<properties>" +
	"<property name='MAP_ID' value='4'/>" +
	"<property name='USERNAME' value='Alice'/>" +
	"<property name='ELEMENT_TYPE' value='Brainstorm'/>" +
	"</properties>" +
	"</object>" +
	"<content>" +
	"<description><![CDATA[Alice added text to Brainstorm: 'This is dumb.  I want to go home.']]></description>" +
	"<properties>" +
	"<property name='INDICATOR_TYPE' value='modify'/>" +
	"<property name='TOOL' value='Planning Tool'/>" +
	"</properties>" +
	"</content>" +
	"</action>" +
	
	
	"<action time='1325841207864'>" +
	"<actiontype classification='other' type='indicator' logged='false'/>" +
	"<user id='Alice' user_role='student'/>" +
	"<object id='2' type='Element'>" +
	"<properties>" +
	"<property name='MAP_ID' value='4'/>" +
	"<property name='USERNAME' value='Alice'/>" +
	"<property name='ELEMENT_TYPE' value='Brainstorm'/>" +
	"</properties>" +
	"</object>" +
	"<content>" +
	"<description><![CDATA[Abusive Talk in Brainstorm: 'This is dumb.  I want to go home.']]></description>" +
	"<properties>" +
	"<property name='INDICATOR_TYPE' value='annotation'/>" +
	"<property name='TOOL' value='Planning Tool'/>" +
	"<property name='ANNOTATION_TYPE' value='abusive talk'/>" +
	"</properties>" +
	"</content>" +
	"</action>" +
	
	"<action time='1327015893359'>" +
	"<actiontype classification='create' type='indicator' logged='false'/>" +
	"<user id='Alice' user_role='student'/>" +
	"<object id='2' type='Element'>" +
	"<properties>" +
	"<property name='MAP_ID' value='4'/>" +
	"<property name='TEXT' value='I do not understand this stupid thing'/>" +
	"<property name='USERNAME' value='Alice'/>" +
	"<property name='ELEMENT_TYPE' value='Comment'/>" +
	"</properties>" +
	"</object>" +
	"<content>" +
	"<description><![CDATA[Comment 2 created: 'I do not understand this stupid thing']]></description>" +
	"<properties>" +
	"<property name='INDICATOR_TYPE' value='create'/>" +
	"<property name='TOOL' value='LASAD'/>" +
	"</properties>" +
	"</content>" +
	"</action>" +
	
	
	

	"</interactiondata>";
	


	public String inputToServer(String requestType){
		
		//System.out.println("Request1111111:"+requestType);
		//SourceManager.getConfiguration(FileOperation.read("conf/configuration.xml"));

		
		//String serverInfo = getServletContext().getServerInfo();
		
		System.out.println("Server Result:"+requestType);
		handleRequest(requestType);
	
		String userAgent = getThreadLocalRequest().getHeader("User-Agent");

		userAgent = escapeHtml(userAgent);
		String xml=datam;


		return  xml;

		
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
		thread.start();
		
		//StartupServlet aa=new StartupServlet();
		//aa.init();
		// XmppActionListener xa=new XmppActionListener();
    	// xa.run();
		String result="";
		if(requestType.equalsIgnoreCase("RequestHistory")){
			
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
}
