package de.uds.MonitorInterventionMetafora.client.display;

import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.InfoConfig;

public class DisplayUtil {
	
	public static void postNotificationMessage( String message) {
		InfoConfig config = new InfoConfig("Notification", message);
		config.display = 6000;
		Info.display(config);		
	}
}
