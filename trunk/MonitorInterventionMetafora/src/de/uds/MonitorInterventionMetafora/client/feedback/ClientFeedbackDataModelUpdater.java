package de.uds.MonitorInterventionMetafora.client.feedback;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class ClientFeedbackDataModelUpdater {// implements RequestUpdateCallBack{

	private ClientFeedbackDataModel feedbackModel;
	private ClientFeedbackController controller;
	
	public ClientFeedbackDataModelUpdater(ClientFeedbackDataModel feedbackModel, ClientFeedbackController controller) {
		this.feedbackModel = feedbackModel;
		this.controller = controller;
	}
	
	public void refreshSuggestedMessages(String username) {
		AsyncCallback callback = new AsyncCallback<String>() {
			public void onSuccess(String result) {
				System.out.println("ClientFeedbackDataModelUpdater.refreshSuggestedMessages(): Success");
				if (result != null && !result.equals(""))
					FeedbackPanelContainer.getTemplatePool().populateTabs(result);
			}
			
			public void onFailure(Throwable caught) {
				System.err.println("ClientFeedbackDataModelUpdater.refreshSuggestedMessages(): Failure");
			}
		};
		
		feedbackModel.getServiceServlet().requestSuggestedMessages(username, callback);
	}
}
