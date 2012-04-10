package de.uds.MonitorInterventionMetafora.client.view.containers;

import de.uds.MonitorInterventionMetafora.client.view.intervention.UserRequest;
import de.uds.MonitorInterventionMetafora.client.view.intervention.Outbox;
import de.uds.MonitorInterventionMetafora.client.view.intervention.RequestResponse;
import de.uds.MonitorInterventionMetafora.client.view.intervention.RequestStack;
import de.uds.MonitorInterventionMetafora.client.view.intervention.TemplatePool;

import com.extjs.gxt.ui.client.widget.Label;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class InterventionPanelContainer extends VerticalPanel {
	private de.uds.MonitorInterventionMetafora.client.view.intervention.Outbox outbox;
	private RequestResponse requestResponse;
	private TemplatePool templatePool;
	private RequestStack requestStack;
	static private InterventionPanelContainer INSTANCE;

	public static String[] userNames = {"Agnes", "Bobby", "Cath"};
	
	public InterventionPanelContainer(){
		INSTANCE = this;
		
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
		

		outbox = new Outbox(leftVPanel);
		requestStack = new RequestStack(leftVPanel);
		requestResponse = new RequestResponse(leftVPanel);
		templatePool = new TemplatePool(rightVPanel);
	}
	
	protected void mockupRequest(String name) {
		UserRequest request = new UserRequest(name);
		requestStack.incomingRequest(request);
	}
	public void declareRequestHandled() {
		requestStack.declareRequestHandled();
		requestResponse.setEnabled(false);
	}



	public static InterventionPanelContainer getInstance() {
		return INSTANCE;
	}

	public static TextBox getMessageTextBox()
	{
		return INSTANCE.outbox.getMessageTextBox();
	}
	public static Outbox getOutbox() {
		return INSTANCE.outbox;
	}
	public static TemplatePool getTemplatePool() {
		return INSTANCE.templatePool;
	}
	public static RequestStack getRequestStack() {
		return INSTANCE.requestStack;
	}
	public static RequestResponse getRequestResponse() {
		return INSTANCE.requestResponse;
	}
}
