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
	
	public synchronized void addAction(CfAction action){
		if (action.getTime() > System.currentTimeMillis()){
			logger.error("[addAction] Bad timestamp, changing to current time for action:\n"+ action);
			action.setTime(System.currentTimeMillis());
		}
		cfActions.add(action);
		Collections.sort(cfActions);
	}

	public List<CfAction> requestUpdate(CfAction cfAction){
		if(cfAction!=null){
			if (cfAction.getTime()>System.currentTimeMillis()){
				logger.error("[requestUpdate] bad action time, newer than current time for action\n" + cfAction);
			}
			return requestUpdate(cfAction.getTime());
		}
		else {
			logger.info("[requestUpdate] no last action present, sending entire history");
			return cfActions;
		}
	}
	
	public List<CfAction> requestUpdate(long _lastActionTime) {
		System.out.println("DEBUG: [MonitorModel.requestUpdate] "+ _lastActionTime);
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
