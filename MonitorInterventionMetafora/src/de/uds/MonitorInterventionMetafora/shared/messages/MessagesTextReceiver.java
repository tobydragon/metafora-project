package de.uds.MonitorInterventionMetafora.shared.messages;

public interface MessagesTextReceiver {
	
	void newMessagesTextReceived(MessageType messageType, Locale locale, String text);

	void newMessagesTextFailed(MessageType messageType, Locale locale, Throwable e);


}
