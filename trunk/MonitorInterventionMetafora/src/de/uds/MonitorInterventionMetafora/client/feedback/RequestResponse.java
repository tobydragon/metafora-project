package de.uds.MonitorInterventionMetafora.client.feedback;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class RequestResponse {
	private VerticalPanel vpanel;
//	private String sectionLabelTextIdle = "No requests from users at the moment";
//	private String sectionLabelTextRequest = "Request from student ";
	private VerticalPanel mainColumn;
//	private Label sectionLabel;
	public RequestResponse(ComplexPanel parent)
	{
		vpanel = new VerticalPanel();
//		vpanel.setBorderWidth(5);
		vpanel.setSpacing(10);
		parent.add(vpanel);
		
		//section label
//		sectionLabel = new Label(sectionLabelTextIdle);
//		sectionLabel.setStyleName("sectionLabel");
//		vpanel.add(sectionLabel);

		//main column
		mainColumn = new VerticalPanel();
		vpanel.add(mainColumn);
		
		//tool selection
		HorizontalPanel getHelpForHPanel = new HorizontalPanel();
		final CheckBox getHelpForCheckbox = new CheckBox("Get help for tool...");
		final VerticalPanel getHelpForOptionsPanel = new VerticalPanel();
		getHelpForOptionsPanel.setVisible(false);
		getHelpForOptionsPanel.add(new CheckBox("eXpresser"));
		getHelpForOptionsPanel.add(new CheckBox("planning tool"));
		getHelpForOptionsPanel.add(new CheckBox("discussion tool"));
		getHelpForHPanel.add(getHelpForCheckbox);
		getHelpForHPanel.add(getHelpForOptionsPanel);

		getHelpForCheckbox.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				getHelpForOptionsPanel.setVisible(getHelpForCheckbox.getValue());
			}
		});
		mainColumn.add(getHelpForHPanel);

		//discussion
		final CheckBox discussionCheckbox = new CheckBox("Ask help from other students in the discussion space");
		mainColumn.add(discussionCheckbox);
		//chat
		final CheckBox chatCheckbox = new CheckBox("Ask help in the chat");
		mainColumn.add(chatCheckbox);
		//teacher
		final CheckBox teacherCheckbox = new CheckBox("Notify the teacher that we are stuck");
		mainColumn.add(teacherCheckbox);
		//students
		final CheckBox studentsCheckbox = new CheckBox("See what other students are doing");
		mainColumn.add(studentsCheckbox);
		
		setEnabled(false);
	}
	public void setEnabled(boolean b) {
		mainColumn.setVisible(b);
	}
	public void requestSelected(String userName) {
//		sectionLabel.setText(sectionLabelTextRequest+userName);
		setEnabled(true);
	}
}