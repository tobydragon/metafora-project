package de.uds.MonitorInterventionMetafora.client.view.widgets;

import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextArea;

import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;

public class PopupIndicatorDisplay  extends PopupPanel {

    public PopupIndicatorDisplay(CfAction actionToDisplay) {
      // PopupPanel's constructor takes 'auto-hide' as its boolean parameter.
      // If this is set, the panel closes itself automatically when the user
      // clicks outside of it.
      super(true);

      // PopupPanel is a SimplePanel, so you have to set it's widget property to
      // whatever you want its contents to be.
      TextArea indicatorDisplay = new TextArea();
      indicatorDisplay.setText(actionToDisplay.toString());
      indicatorDisplay.setSize("45em", "20em");
      indicatorDisplay.setReadOnly(true);
      indicatorDisplay.getElement().setAttribute("wrap", "off");
      setWidget(indicatorDisplay);
      setModal(true);
      setPopupPosition(200, 200);
      
      show();
    }
}
