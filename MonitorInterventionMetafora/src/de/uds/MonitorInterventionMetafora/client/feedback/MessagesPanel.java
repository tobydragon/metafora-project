package de.uds.MonitorInterventionMetafora.client.feedback;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.uds.MonitorInterventionMetafora.client.communication.CommunicationServiceAsync;
import de.uds.MonitorInterventionMetafora.client.urlparameter.UrlParameterConfig;
import de.uds.MonitorInterventionMetafora.shared.suggestedmessages.Locale;
import de.uds.MonitorInterventionMetafora.shared.suggestedmessages.SuggestedMessage;
import de.uds.MonitorInterventionMetafora.shared.suggestedmessages.SuggestedMessagesFileHandler;
import de.uds.MonitorInterventionMetafora.shared.suggestedmessages.MessageType;
import de.uds.MonitorInterventionMetafora.shared.suggestedmessages.SuggestedMessagesFileTextReceiver;
import de.uds.MonitorInterventionMetafora.shared.suggestedmessages.SuggestedMessagesModel;

public class MessagesPanel extends VerticalPanel implements SuggestedMessagesFileTextReceiver{
	private MessageSendingPanel messageSendingPanel;
	private SuggestedMessagesPanel suggestedMessagesPanel;
	static private MessagesPanel INSTANCE;

	private static String[] userIDsArray = {"Alan", "Mary", "David"};	
	
	CommunicationServiceAsync commServiceServlet;
	
	public MessagesPanel(CommunicationServiceAsync commServiceServlet) {
		INSTANCE = this;
		this.commServiceServlet = commServiceServlet;
		
//		updater = new ClientFeedbackDataModelUpdater(monitoringViewServiceServlet);
		
		String configUserIDs = UrlParameterConfig.getInstance().getReceiverIDs();
		if ((configUserIDs != null) && (configUserIDs != "")) {
		    userIDsArray= parseStringToArray(configUserIDs);
		}

		messageSendingPanel = new MessageSendingPanel(commServiceServlet, userIDsArray);
		this.add(messageSendingPanel);
		this.setSpacing(5);
		
		SuggestedMessagesFileHandler messageFileHandler = new SuggestedMessagesFileHandler(this, UrlParameterConfig.getInstance().getMessageType(), UrlParameterConfig.getInstance().getLocale());
		messageFileHandler.requestStringFromFile();
	}
	
	/**
	 * Accepts a string separated with | and returns an array
	 * @param s
	 */
	private String[] parseStringToArray(String listOfStrings) {
	    String delimiter = "\\|";
	    return listOfStrings.split(delimiter);	    
	}
	
	@Override
	public void newMessagesTextReceived(MessageType messageType, Locale locale, String text) {
		//TODO: this shouldn't build a new controller and model each time, it should just refresh model through a controller method
		SuggestedMessagesModel suggestedMessagesModel = SuggestedMessagesModelParserForClient.fromXML(text);
		SuggestedMessagesController suggestedMessagesController = new SuggestedMessagesController(suggestedMessagesModel, commServiceServlet);
		suggestedMessagesPanel = new SuggestedMessagesPanel(suggestedMessagesModel, suggestedMessagesController);
		suggestedMessagesController.setView(suggestedMessagesPanel);
		insert(suggestedMessagesPanel, 0);
		messageSendingPanel.setSuggestedMessagesController(suggestedMessagesController);
	}
	
	@Override
	public void newMessagesTextFailed(MessageType messageType, Locale locale, Throwable exception) {
		Window.alert("Failed to send the message: " + exception.getMessage());
	}

	public static MessagesPanel getInstance() {
		return INSTANCE;
	}

//	public static TextArea getMessageTextArea() {
//		return INSTANCE.messageSendingPanel.getMessageTextArea();
//	}
	
	public static void suggestedMessageSelected(SuggestedMessage message){
		INSTANCE.messageSendingPanel.suggestedMessageSelected(message);
	}
	
	public static MessageSendingPanel getOutbox() {
		return INSTANCE.messageSendingPanel;
	}
	
	public static SuggestedMessagesPanel getTemplatePool() {
		return INSTANCE.suggestedMessagesPanel;
	}


	
}
