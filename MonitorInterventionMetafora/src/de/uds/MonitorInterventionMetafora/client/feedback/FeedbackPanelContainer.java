package de.uds.MonitorInterventionMetafora.client.feedback;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class FeedbackPanelContainer extends VerticalPanel {
	private de.uds.MonitorInterventionMetafora.client.feedback.Outbox outbox;
	private TemplatePool templatePool;
	static private FeedbackPanelContainer INSTANCE;

	public static String[] userNames = {"Agnes", "Bobby", "Cath"};
	
	public FeedbackPanelContainer(){
		
		
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
		
		
		 RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.GET,
	        "resources/feedback/sample-messages.xml");

	    try {
	      requestBuilder.sendRequest(null, new RequestCallback() {
	        public void onError(Request request, Throwable exception) {
	          requestFailed(exception);
	        }

	        public void onResponseReceived(Request request, Response response) {
	    		templatePool = new TemplatePool(rightVPanel, response.getText());
	        }
	      });
	    } catch (RequestException ex) {
	      requestFailed(ex);
	    }
	}
	

	  private void requestFailed(Throwable exception) {
	    Window.alert("Failed to send the message: "
	        + exception.getMessage());
	  }
	  



	public static FeedbackPanelContainer getInstance() {
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
}
