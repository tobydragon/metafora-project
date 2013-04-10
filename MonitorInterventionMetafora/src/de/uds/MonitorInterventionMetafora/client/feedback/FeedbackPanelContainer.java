package de.uds.MonitorInterventionMetafora.client.feedback;

import java.util.Date;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.uds.MonitorInterventionMetafora.client.communication.CommunicationServiceAsync;
import de.uds.MonitorInterventionMetafora.client.urlparameter.UrlParameterConfig;
import de.uds.MonitorInterventionMetafora.client.urlparameter.UrlParameterConfig.UserType;

public class FeedbackPanelContainer extends VerticalPanel {
	private de.uds.MonitorInterventionMetafora.client.feedback.Outbox outbox;
	private SuggestedMessagesView templatePool;
	static private FeedbackPanelContainer INSTANCE;

	public static String[] userIDsArray = {"Alan", "Mary", "David"};
	
	// models
	private ClientFeedbackDataModel feedbackModel;
	
	// controllers
	private ClientFeedbackDataModelUpdater updater;
//	ClientFeedbackController controller;
	
	CommunicationServiceAsync monitoringViewServiceServlet;
	
	public FeedbackPanelContainer(CommunicationServiceAsync monitoringViewServiceServlet) {
		INSTANCE = this;
		this.monitoringViewServiceServlet = monitoringViewServiceServlet;
		
		feedbackModel = new ClientFeedbackDataModel(monitoringViewServiceServlet);
//		controller = new ClientFeedbackController();
		updater = new ClientFeedbackDataModelUpdater(feedbackModel);
		
		//TOODO: this is awful and only a shortcut now
		String configUserIDs = UrlParameterConfig.getInstance().getReceiverIDs();
		if ((configUserIDs != null) && (configUserIDs != "")) {
		    userIDsArray= parseStringToArray(configUserIDs);
		}
		

		outbox = new Outbox(updater);
		this.add(outbox);
		this.setSpacing(5);
		
		Date date = new Date();
		String locale = UrlParameterConfig.getInstance().getLocale();
		
		String URL = "resources/feedback/" + getMessageFileNameStart() + locale + ".xml?nocache=" + date.getTime();
		Log.info("[constructor] feedback panel URL: "+ URL);
		RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.GET,URL);

		try {
			requestBuilder.sendRequest(null, new RequestCallback() {
				public void onError(Request request, Throwable exception) {
					requestFailed(exception);
				}

				public void onResponseReceived(Request request, Response response) {
					SuggestedMessagesModel suggestedMessagesModel = SuggestedMessagesModel.fromXML(response.getText());
					SuggestedMessagesController suggestedMessagesController = new SuggestedMessagesController(suggestedMessagesModel);
					templatePool = new SuggestedMessagesView(suggestedMessagesModel, suggestedMessagesController, updater);
					suggestedMessagesController.setView(templatePool);
					insert(templatePool, 0);
					outbox.setSuggestedMessagesController(suggestedMessagesController);
				}
			});
		} catch (RequestException ex) {
			requestFailed(ex);
		}
	}

	public String getMessageFileNameStart(){
		String messageFileStart = "external-messages_";
		if (UrlParameterConfig.getInstance().getUserType() == UserType.METAFORA_USER ||
			UrlParameterConfig.getInstance().getUserType() == UserType.METAFORA_RECOMMENDATIONS ||
			UrlParameterConfig.getInstance().getUserType() == UserType.POWER_WIZARD ) {
			
			messageFileStart = "peer-messages_";
		}
		return messageFileStart;
	}
	
	/**
	 * Accepts a string separated with | and returns an array
	 * @param s
	 */
	private String[] parseStringToArray(String listOfStrings) {
	    String delimiter = "\\|";
	    return listOfStrings.split(delimiter);	    
	}
	
	private void requestFailed(Throwable exception) {
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
