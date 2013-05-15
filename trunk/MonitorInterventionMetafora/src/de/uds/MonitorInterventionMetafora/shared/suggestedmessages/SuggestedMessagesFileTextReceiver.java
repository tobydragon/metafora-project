package de.uds.MonitorInterventionMetafora.shared.suggestedmessages;

public interface SuggestedMessagesFileTextReceiver {
	
	void newMessagesTextReceived(MessageType messageType, Locale locale, String text);

	void newMessagesTextFailed(MessageType messageType, Locale locale, Throwable e);


}
