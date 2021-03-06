package de.uds.MonitorInterventionMetafora.client.feedback;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.ToggleButton;

public class SentMessagesTabWidget extends SuggestedMessageButtonsTabWidget {
	private TextArea xmlCodeArea;
	private ToggleButton xmlToggleButton;

	public SentMessagesTabWidget(String title) {
		super(title);
		xmlCodeArea = new TextArea();
		xmlCodeArea.setWidth("480px");
		xmlCodeArea.setHeight("450px");
		xmlToggleButton = new ToggleButton("");
		toggleCodeView(false);
		xmlToggleButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (xmlToggleButton.isDown()) {
					toggleCodeView(true);
				} else {
					toggleCodeView(false);
				}
			}
		});
		headerVPanel.add(xmlToggleButton);
		headerVPanel.add(xmlCodeArea);
	}

	private void toggleCodeView(boolean codeViewEnabled) {
		xmlToggleButton.setText(codeViewEnabled ? "view as buttons"	: "view as XML code");
		xmlToggleButton.setWidth("150px");
		updateXmlCodeArea();
		buttonsVPanel.setVisible(!codeViewEnabled);
		xmlCodeArea.setVisible(codeViewEnabled);
	}

	public void updateXmlCodeArea() {
		String code = "";
		for (int i = 0; i < buttonsVPanel.getWidgetCount(); i++) {
			HorizontalPanel hpanel = (HorizontalPanel) buttonsVPanel.getWidget(i);
			
			String msg = ((Button) hpanel.getWidget(hpanel.getWidgetCount()-1)).getText().replaceAll("[\n\r]", " ");
			code += "<message>" + msg + "</message>\n";
		}
		xmlCodeArea.setText(code);
	}
}