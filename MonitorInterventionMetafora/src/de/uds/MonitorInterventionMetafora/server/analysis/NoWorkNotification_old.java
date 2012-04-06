package de.uds.MonitorInterventionMetafora.server.analysis;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import de.uds.MonitorInterventionMetafora.server.cfcommunication.MetaforaStrings;
import de.uds.MonitorInterventionMetafora.server.utils.ServerFormatStrings;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfActionType;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfContent;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfObject;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfProperty;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfUser;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CommonFormatStrings;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.OperationType;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.IndicatorEntity;
import de.uds.MonitorInterventionMetafora.shared.utils.GWTUtils;


public class NoWorkNotification_old extends TimerTask {

	private CfAction notificationAction;
	private CfAction lastActionInFiltered;
	int counter;
	int _waitmin=2;
	List<CfAction> cfActions;
	Timer notificationController;
	public NoWorkNotification_old(CfAction _notificationAction){
		
		lastActionInFiltered=new CfAction();
		notificationAction=_notificationAction;
		cfActions=new ArrayList<CfAction>();
		notificationController = new Timer();
		counter=0;
		notificationController.schedule(this,0, 10000);
	}
	

	
	List<IndicatorEntity> getFilters(){
		
		List<IndicatorEntity> _filterList=new ArrayList<IndicatorEntity>();
		
		for(String _key:notificationAction.getCfContent().getProperties().keySet()){
			
			IndicatorEntity _entity=new IndicatorEntity();
			_entity.setDisplayText(notificationAction.getCfContent().getProperties().get(_key).getName());
			_entity.setEntityName(notificationAction.getCfContent().getProperties().get(_key).getName());
			_entity.setValue(notificationAction.getCfContent().getProperties().get(_key).getValue());
			_entity.setOperationType(OperationType.getFromString(notificationAction.getCfContent().getProperties().get(_key).getId()));
			_filterList.add(_entity);
			
			
		}
		
		
		return _filterList;
	}
	
	public CfAction getAction(){
		
		return notificationAction;
	}
	
public CfAction getLastActionInFilteredList(){
		
		return lastActionInFiltered;
	}
	public void setLastActionInFilteredList(CfAction _cfAction){
		
		lastActionInFiltered=_cfAction;
	}




	@Override
	public void run() {
		
		if(lastActionInFiltered.getCfActionType()!=null)
			return;
		
		
		int minutesago=(int) GWTUtils.getDifferenceInMinutes(lastActionInFiltered.getTime());
		
		if((minutesago%_waitmin)==0){
			
		AnalysisManager.getAnalysisManagerInstance().sendToAllAgents("Notificationnn", null);
		counter ++;	
		}
		
		
	}
	
	void buildNoWorkLandMark(CfUser _user){
		
		
		
		CfActionType cfActionType = new CfActionType(CommonFormatStrings.LANDMARK, 
				CommonFormatStrings.OTHER, CommonFormatStrings.TRUE);
		CfAction cfAction = new CfAction(System.currentTimeMillis(), cfActionType);
		cfAction.addUser(_user);
		CfContent content=new CfContent("No indicator found for ...:"+_waitmin);
		
		CfProperty property;
		property=new CfProperty();
		property.setName(ServerFormatStrings.IGNORED_LANDMARK);
		property.setValue("Yes");
		content.addProperty(property);
		property=new CfProperty();
		//property.setName(CommonFormatStrings.i);
		property.setValue("Yes");
		content.addProperty(property);
		
		
		//cfAction.addObject(element);
		//return cfAction;
		
		//return null;
		
		
	}
	
}
