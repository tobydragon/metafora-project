package de.uds.MonitorInterventionMetafora.client.feedback;

import de.uds.MonitorInterventionMetafora.client.communication.CommunicationServiceAsync;

public class ClientFeedbackDataModel {
	private CommunicationServiceAsync feedbackServiceServlet;
	
	public ClientFeedbackDataModel(CommunicationServiceAsync serviceServlet) {
		this.feedbackServiceServlet = serviceServlet;
	}
	
	public CommunicationServiceAsync getServiceServlet() {
		return feedbackServiceServlet;
	}
}
