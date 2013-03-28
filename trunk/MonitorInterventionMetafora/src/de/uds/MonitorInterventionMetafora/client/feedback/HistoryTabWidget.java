package de.uds.MonitorInterventionMetafora.client.feedback;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.ToggleButton;

public class HistoryTabWidget extends TabWidget {
	private TextArea xmlCodeArea;
	private ToggleButton xmlToggleButton;

	public HistoryTabWidget(String title) {
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
			String msg = ((Button) buttonsVPanel.getWidget(i)).getText().replaceAll("[\n\r]", " ");
			code += "<message>" + msg + "</message>\n";
		}
		xmlCodeArea.setText(code);
	}
}