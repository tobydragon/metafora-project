package de.uds.MonitorInterventionMetafora.server.monitor;

import java.util.Collections;
import java.util.List;
import java.util.Vector;

import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.utils.Logger;

public class MonitorModel {
	Logger logger = Logger.getLogger(this.getClass());
	
	List<CfAction> cfActions;
	
	public MonitorModel(){
		cfActions=new Vector<CfAction>();
	}
	
	public void addAction(CfAction action){
		cfActions.add(action);
		Collections.sort(cfActions);
	}

	public List<CfAction> requestUpdate(CfAction cfAction){
		if(cfAction!=null){
			return requestUpdate(cfAction.getTime());
		}
		else {
			logger.info("[requestUpdate] no last action present, sending entire history");
			return cfActions;
		}
	}
	
	public List<CfAction> requestUpdate(long _lastActionTime) {
		System.out.println("DEBUG: [MainServer.getActionUpdates] "+ _lastActionTime);
		List<CfAction> _newActionList=new Vector<CfAction>();
		
		//walk list backwards because very few end action will be new
		//TODO: should stop when first old action is found
		for(int i=cfActions.size()-1;i>=0;i--){
			if(isNewAction(_lastActionTime,cfActions.get(i).getTime())){
				_newActionList.add(cfActions.get(i));
			}
		}
		return _newActionList;	
	}
	
	private boolean isNewAction(long _lastActionTime,long _actionTime){
		
		if(_actionTime>_lastActionTime){		
			return true;
		}
			
		return false;
	}


}
