package de.uds.MonitorInterventionMetafora.client.feedback;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.uds.MonitorInterventionMetafora.client.communication.CommunicationServiceAsync;
import de.uds.MonitorInterventionMetafora.client.urlparameter.UrlParameterConfig;
import de.uds.MonitorInterventionMetafora.client.urlparameter.UrlParameterConfig.UserType;
import de.uds.MonitorInterventionMetafora.shared.suggestedmessages.Locale;
import de.uds.MonitorInterventionMetafora.shared.suggestedmessages.SuggestedMessagesFileHandler;
import de.uds.MonitorInterventionMetafora.shared.suggestedmessages.MessageType;
import de.uds.MonitorInterventionMetafora.shared.suggestedmessages.SuggestedMessagesFileTextReceiver;
import de.uds.MonitorInterventionMetafora.shared.suggestedmessages.SuggestedMessagesModel;

public class MessagesPanel extends VerticalPanel implements SuggestedMessagesFileTextReceiver{
	private de.uds.MonitorInterventionMetafora.client.feedback.MessageSendingPanel outbox;
	private SuggestedMessagesPanel templatePool;
	static private MessagesPanel INSTANCE;

	public static String[] userIDsArray = {"Alan", "Mary", "David"};	
	
	CommunicationServiceAsync commServiceServlet;
	
	public MessagesPanel(CommunicationServiceAsync commServiceServlet) {
		INSTANCE = this;
		this.commServiceServlet = commServiceServlet;
		
//		updater = new ClientFeedbackDataModelUpdater(monitoringViewServiceServlet);
		
		//TOODO: this is awful and only a shortcut now
		String configUserIDs = UrlParameterConfig.getInstance().getReceiverIDs();
		if ((configUserIDs != null) && (configUserIDs != "")) {
		    userIDsArray= parseStringToArray(configUserIDs);
		}

		outbox = new MessageSendingPanel(commServiceServlet);
		this.add(outbox);
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
		SuggestedMessagesModel suggestedMessagesModel = SuggestedMessagesModel.fromXML(text);
		SuggestedMessagesController suggestedMessagesController = new SuggestedMessagesController(suggestedMessagesModel, commServiceServlet);
		templatePool = new SuggestedMessagesPanel(suggestedMessagesModel, suggestedMessagesController);
		suggestedMessagesController.setView(templatePool);
		insert(templatePool, 0);
		outbox.setSuggestedMessagesController(suggestedMessagesController);
	}
	
	@Override
	public void newMessagesTextFailed(MessageType messageType, Locale locale, Throwable exception) {
		Window.alert("Failed to send the message: " + exception.getMessage());
	}

	public static MessagesPanel getInstance() {
		return INSTANCE;
	}

	public static TextArea getMessageTextArea() {
		return INSTANCE.outbox.getMessageTextArea();
	}
	
	public static MessageSendingPanel getOutbox() {
		return INSTANCE.outbox;
	}
	
	public static SuggestedMessagesPanel getTemplatePool() {
		return INSTANCE.templatePool;
	}


	
}
