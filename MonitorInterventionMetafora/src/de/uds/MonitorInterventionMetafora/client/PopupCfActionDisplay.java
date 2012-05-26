package de.uds.MonitorInterventionMetafora.client;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextArea;

import de.uds.MonitorInterventionMetafora.client.resources.Resources;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;

public class PopupCfActionDisplay  extends PopupPanel {
	private Button closeBtn;

    public PopupCfActionDisplay(CfAction actionToDisplay) {
      // PopupPanel's constructor takes 'auto-hide' as its boolean parameter.
      // If this is set, the panel closes itself automatically when the user
      // clicks outside of it.
      super(true);

      // PopupPanel is a SimplePanel, so you have to set it's widget property to
      // whatever you want its contents to be.
      
      VerticalPanel vPanel=new VerticalPanel();
      TextArea indicatorDisplay = new TextArea();
      indicatorDisplay.setText(actionToDisplay.toString());
      indicatorDisplay.setSize("44em", "20em");
      indicatorDisplay.setReadOnly(true);
      indicatorDisplay.getElement().setAttribute("wrap", "off");
      vPanel.setWidth("45em");
      vPanel.setHeight("23em");
      
      closeBtn = new Button("",new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				
				PopupCfActionDisplay.this.hide();
				
			    
			} });
      closeBtn.setIcon(Resources.ICONS.close());
      
      vPanel.add(closeBtn);
      vPanel.add(indicatorDisplay);
      vPanel.setHorizontalAlign(HorizontalAlignment.RIGHT);
      

      this.setWidget(vPanel);
      this.setModal(true);
      this.setPopupPosition(200, 200);
      
      this.show();
    }
}
