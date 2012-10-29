package de.uds.MonitorInterventionMetafora.server.analysis.notification;




import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;


import de.uds.MonitorInterventionMetafora.server.analysis.manager.AnalysisManager;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfActionType;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfContent;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfObject;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfProperty;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfUser;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CommonFormatStrings;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.NotificationType;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.OperationType;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.IndicatorFilter;

public class NoWorkNotification  implements  Notification {


	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8109458748846339834L;
	IndicatorFilter filter;
	String color="";

	public NoWorkNotification(){
		
	
	}
	
	
	
	@Override
	public void sendNotification() {
		
		
	AnalysisManager.getAnalysisManagerInstance().sendToAllAgents("Notification", createNotificationCfAction());
		
	}
	
	
	
	@Override
	public void setFilter(final IndicatorFilter _filter) {
		filter=new IndicatorFilter();
	    
	 filter=_filter;
		
	}
	@Override
	public IndicatorFilter getFilter() {
		//return null;
		return filter;
	}

	
	
	
	@Override
	public boolean shouldFireNotification(final List<CfAction> cfActions) {
	
		List<CfAction> filteredActionList=new ArrayList<CfAction>();
		
//		IndicatorFilterer filterer =new IndicatorFilterer();
//		filteredActionList=filterer.getFilteredIndicatorList(cfActions,filter.toIndicatorEntityList());
		
		if(filteredActionList.size()<=0)
			return true;
		return false;
	}
	
	
	@Override
	public void setType(final NotificationType _type) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public NotificationType getType() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getDescriptionString() {
		
		String _description="No work  found for last "+filter.getIndicatorEntities().get(CommonFormatStrings.A_V_TIME.toUpperCase()).getValue()+" min. Filter entities; ";
		
	
		//String _description="";
		for(final String _key:filter.getIndicatorEntities().keySet()){
		
		_description=_description+filter.getIndicatorEntities().get(_key).getPropertyName()+"="+filter.getIndicatorEntities().get(_key).getValue()+", ";
		}
		
		return _description;
	}
	@Override
	public CfAction createNotificationCfAction() {
		
		
		
	
		
		//CfContent content=new CfContent("No indicator found for ...:"+_waitmin);
		
		final CfContent content=new CfContent(getDescriptionString());
		
		CfProperty property;
	
		
		property=new CfProperty();
		property.setName(CommonFormatStrings.INDICATOR_TYPE);
		property.setValue(CommonFormatStrings.ACTIVITY);
		content.addProperty(property);
		
		
		property=new CfProperty();
		property.setName(CommonFormatStrings.TOOL);
		property.setValue(CommonFormatStrings.VISAULIZER_ANALYZER);
		content.addProperty(property);
		
		
		property=new CfProperty();
		property.setName(CommonFormatStrings.ANALYSIS_TYPE);
		property.setValue(CommonFormatStrings.NOTIFICATION);
		content.addProperty(property);
		
		if(color!=null && color!=""){
		property=new CfProperty();
		property.setName(CommonFormatStrings.COLOR);
		property.setValue(color);
		content.addProperty(property);
		}
		
		
		final CfActionType cfActionType = new CfActionType(CommonFormatStrings.LANDMARK, 
				CommonFormatStrings.OTHER, CommonFormatStrings.TRUE);
		
		final CfUser _user=new CfUser();
		_user.setid("NotificationManager");
		_user.setrole("Manager");
		final List<CfUser> _users=new ArrayList<CfUser>();
		_users.add(_user);
		
		final List<CfObject> _objects=new ArrayList<CfObject>();
		return new CfAction(System.currentTimeMillis(), cfActionType,_users,_objects,content);
	
	}



	@Override
	public void setColor(String _color) {
		color=_color;
		
	}



	@Override
	public String getColor() {
		
		return color;
	}








	
	

}
