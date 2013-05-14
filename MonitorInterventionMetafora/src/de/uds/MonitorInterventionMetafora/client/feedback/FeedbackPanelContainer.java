package de.uds.MonitorInterventionMetafora.client.feedback;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.uds.MonitorInterventionMetafora.client.communication.CommunicationServiceAsync;
import de.uds.MonitorInterventionMetafora.client.urlparameter.UrlParameterConfig;
import de.uds.MonitorInterventionMetafora.client.urlparameter.UrlParameterConfig.UserType;
import de.uds.MonitorInterventionMetafora.shared.messages.Locale;
import de.uds.MonitorInterventionMetafora.shared.messages.MessageFileHandler;
import de.uds.MonitorInterventionMetafora.shared.messages.MessageType;
import de.uds.MonitorInterventionMetafora.shared.messages.MessagesTextReceiver;

public class FeedbackPanelContainer extends VerticalPanel implements MessagesTextReceiver{
	private de.uds.MonitorInterventionMetafora.client.feedback.Outbox outbox;
	private SuggestedMessagesView templatePool;
	static private FeedbackPanelContainer INSTANCE;

	public static String[] userIDsArray = {"Alan", "Mary", "David"};	
	
	private ClientFeedbackDataModel feedbackModel;
	private ClientFeedbackDataModelUpdater updater;
	CommunicationServiceAsync monitoringViewServiceServlet;
	
	public FeedbackPanelContainer(CommunicationServiceAsync monitoringViewServiceServlet) {
		INSTANCE = this;
		this.monitoringViewServiceServlet = monitoringViewServiceServlet;
		
		feedbackModel = new ClientFeedbackDataModel(monitoringViewServiceServlet);
		updater = new ClientFeedbackDataModelUpdater(feedbackModel);
		
		//TOODO: this is awful and only a shortcut now
		String configUserIDs = UrlParameterConfig.getInstance().getReceiverIDs();
		if ((configUserIDs != null) && (configUserIDs != "")) {
		    userIDsArray= parseStringToArray(configUserIDs);
		}

		outbox = new Outbox(updater);
		this.add(outbox);
		this.setSpacing(5);
		
		MessageFileHandler messageFileHandler = new MessageFileHandler(this, UrlParameterConfig.getInstance().getMessageType(), UrlParameterConfig.getInstance().getLocale());
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
		SuggestedMessagesModel suggestedMessagesModel = SuggestedMessagesModel.fromXML(text);
		SuggestedMessagesController suggestedMessagesController = new SuggestedMessagesController(suggestedMessagesModel);
		templatePool = new SuggestedMessagesView(suggestedMessagesModel, suggestedMessagesController, updater);
		suggestedMessagesController.setView(templatePool);
		insert(templatePool, 0);
		outbox.setSuggestedMessagesController(suggestedMessagesController);
	}
	
	@Override
	public void newMessagesTextFailed(MessageType messageType, Locale locale, Throwable exception) {
		Window.alert("Failed to send the message: " + exception.getMessage());
	}

	public static FeedbackPanelContainer getInstance() {
		return INSTANCE;
	}

	public static TextArea getMessageTextArea() {
		return INSTANCE.outbox.getMessageTextArea();
	}
	
	public static Outbox getOutbox() {
		return INSTANCE.outbox;
	}
	
	public static SuggestedMessagesView getTemplatePool() {
		return INSTANCE.templatePool;
	}


	
}
