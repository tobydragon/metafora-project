package de.uds.MonitorInterventionMetafora.client.monitor;

import com.extjs.gxt.ui.client.widget.Window;
import com.google.gwt.user.client.ui.Widget;

public class PopupWindow  extends Window  {
	
	public PopupWindow(Widget w){
		this(w, 1000, 405);
	}
	
	public PopupWindow(Widget w, int width, int height){
		
		
	      this.add(w);
	      this.setWidth(width);
	      this.setHeight(height);
	      this.setPagePosition(12,15);
	      
//	      w.setExpanded(true);
//	     w.setCollapsible(false);
	}
}
