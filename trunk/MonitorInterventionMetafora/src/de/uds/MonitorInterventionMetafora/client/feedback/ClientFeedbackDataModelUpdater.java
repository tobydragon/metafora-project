package de.uds.MonitorInterventionMetafora.client.feedback;

import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.InfoConfig;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class ClientFeedbackDataModelUpdater {// implements RequestUpdateCallBack{

	private ClientFeedbackDataModel feedbackModel;
//	private SuggestedMessagesController suggestedMessagesController;
	
	public ClientFeedbackDataModelUpdater(ClientFeedbackDataModel feedbackModel /*, SuggestedMessagesController controller*/) {
		this.feedbackModel = feedbackModel;
//		this.suggestedMessagesController = controller;
	}
	
	public void refreshSuggestedMessages(String username) {
		AsyncCallback callback = new AsyncCallback<String>() {
			public void onSuccess(String result) {
				System.out.println("ClientFeedbackDataModelUpdater.refreshSuggestedMessages(): Success");
				if (result != null && !result.equals("")) {
					FeedbackPanelContainer.getTemplatePool().populateTabs(SuggestedMessagesModel.fromXML(result));
					sendNotificationMessage("Notification", "New recommendations received");
				} else {
					sendNotificationMessage("Notification", "Currently there are no recommendations for you");
				}
			}
			
			public void onFailure(Throwable caught) {
				System.err.println("ClientFeedbackDataModelUpdater.refreshSuggestedMessages(): Failure");
			}
		};
		
		feedbackModel.getServiceServlet().requestSuggestedMessages(username, callback);
	}
	
	private void sendNotificationMessage(String title, String message) {
		InfoConfig config = new InfoConfig(title, message);
		config.display = 6000;
		Info.display(config);		
	}
}
