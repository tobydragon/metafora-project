package de.uds.MonitorInterventionMetafora.shared.suggestedmessages;

import java.util.Date;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;

import de.uds.MonitorInterventionMetafora.shared.commonformat.MetaforaStrings;

public class SuggestedMessagesFileHandler implements RequestCallback{
	
	private SuggestedMessagesFileTextReceiver receiver;
	MessageType messageType;
	Locale locale;
	
	public SuggestedMessagesFileHandler(SuggestedMessagesFileTextReceiver receiver, MessageType messageType, Locale locale){
		this.receiver = receiver;
		this.messageType = messageType;
		this.locale = locale;
	}
	
	public void requestStringFromFile(){
		
		RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.GET,getUrl(messageType, locale));
		try {
			requestBuilder.sendRequest(null, this);
		} catch (RequestException ex) {
			Log.error("[MessageFileHandler.getMessageStringFromFile] Get failed, error:\n "+ ex.toString());
			receiver.newMessagesTextFailed(messageType, locale, ex);
		}
	}

	@Override
	public void onResponseReceived(Request request, Response response) {
		receiver.newMessagesTextReceived(messageType, locale, response.getText());
	}

	@Override
	public void onError(Request request, Throwable exception) {
		Log.error("[MessageFileHandler.onError] Get failed, error:\n "+ exception.toString());
		receiver.newMessagesTextFailed(messageType, locale, exception);
	}
	
	public static String getUrl( MessageType messageType, Locale locale){
		String URL = getPartialFilepath(messageType, locale) + "?nocache=" + new Date().getTime();
		Log.info("[MessageFileHandler.getUrl] URL: "+ URL);
		return URL;
	}
	
	public static String getPartialFilepath( MessageType messageType, Locale locale){
		String URL = "resources/feedback/" + getMessageFileNameStart( messageType) + locale + ".xml";
		return URL;
	}
	
	private static String getMessageFileNameStart(MessageType messageType){
		String messageFileStart = MetaforaStrings.EXTERNAL_MESSAGE_FILE_NAME_START;
		if ( messageType == MessageType.PEER) {
			messageFileStart = MetaforaStrings.PEER_MESSAGE_FILE_NAME_START;
		} 
		return messageFileStart;
	}

}
