package de.uds.MonitorInterventionMetafora.server.analysis.notification;




import java.util.List;

import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.NotificationType;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionFilter;

public class NoWorkNotification  extends  Notification {	
	private static final long serialVersionUID = -8109458748846339834L;

	public NoWorkNotification(){
		type = NotificationType.NOWORK;
//		filter = null;
	}
	
	public NoWorkNotification( ActionFilter filter){
//		this.filter = filter;
	}

	@Override
	public boolean shouldFireNotification(final List<CfAction> cfActions) {
	
//		List<CfAction> filteredActionList = filter.getFilteredList(cfActions);
			
//		if(filteredActionList.size()<=0)
//			return true;
		return false;
	}
	
	
}
