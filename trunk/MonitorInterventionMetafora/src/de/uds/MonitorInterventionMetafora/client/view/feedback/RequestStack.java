package de.uds.MonitorInterventionMetafora.client.view.feedback;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.uds.MonitorInterventionMetafora.client.view.containers.FeedbackPanelContainer;


public class RequestStack {
	private VerticalPanel vpanel;
	private ListBox listbox;
	private TextArea detailsTextArea;
//	private ArrayList<UserRequest> pendingRequests;

	public RequestStack(ComplexPanel parent)
	{
		vpanel = new VerticalPanel();
		//vpanel.setBorderWidth(5);
		//vpanel.setSpacing(10);
		parent.add(vpanel);
		
		//section label
		Label sectionLabel = new Label("Pending requests from students");
		sectionLabel.setStyleName("sectionLabel");
		vpanel.add(sectionLabel);

		//left-right
		HorizontalPanel row = new HorizontalPanel();
		row.setSpacing(5);
		//listbox
		listbox = new ListBox();
		listbox.setPixelSize(100, 60);
		listbox.setVisibleItemCount(5);
		listbox.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				String userName = listbox.getItemText(listbox.getSelectedIndex());
				detailsTextArea.setText("Name: "+userName+"\nno other info implemented");
				VerticalPanel vp = FeedbackPanelContainer.getOutbox().recipientNamesColumn;
				for(Widget w : vp)
				{
					CheckBox cb = (CheckBox) w;
					cb.setValue(cb.getText().equals(userName));
				}
				FeedbackPanelContainer.getOutbox().sendModeRadioButtonResponse.setValue(true);
				FeedbackPanelContainer.getRequestResponse().requestSelected(userName);
			}
		});
		row.add(listbox);
		
		//detailsLabel
		detailsTextArea = new TextArea();
		detailsTextArea.setPixelSize(300, 60);
		row.add(detailsTextArea);
		vpanel.add(row);
	}
	public void incomingRequest(UserRequest r) {
		listbox.addItem(r.name);
	}
	public void declareRequestHandled() {
		int selectedIndex = listbox.getSelectedIndex();
		listbox.removeItem(selectedIndex);
		clearDetails();
		FeedbackPanelContainer.getOutbox().sendModeRadioButtonPopup.setValue(true);
	}
	public void clearDetails()
	{
		detailsTextArea.setText("");
	}
}
