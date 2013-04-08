package de.uds.MonitorInterventionMetafora.client.feedback;

import java.util.Date;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.uds.MonitorInterventionMetafora.client.communication.CommunicationServiceAsync;
import de.uds.MonitorInterventionMetafora.client.urlparameter.UrlParameterConfig;

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
		
		final VerticalPanel top1VPanel = new VerticalPanel();
		final VerticalPanel leftVPanel = new VerticalPanel();
		leftVPanel.setWidth("450px");
		final VerticalPanel rightVPanel = new VerticalPanel();
		final HorizontalPanel top2HPanel = new HorizontalPanel();
		
		add(top1VPanel);
			
		//left and right half of page
		top1VPanel.add(top2HPanel);
		top2HPanel.add(leftVPanel);
		top2HPanel.add(rightVPanel);

		outbox = new Outbox(leftVPanel, updater);
		
		Date date = new Date();
		String locale = UrlParameterConfig.getInstance().getLocale();
		String URL = "resources/feedback/sample-messages_" + locale + ".xml?nocache=" + date.getTime();
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
					templatePool = new SuggestedMessagesView(rightVPanel, suggestedMessagesModel, suggestedMessagesController, updater);
					outbox.setSuggestedMessagesController(suggestedMessagesController);
				}
			});
		} catch (RequestException ex) {
			requestFailed(ex);
		}
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
