package de.uds.MonitorInterventionMetafora.client.datamodels;

import java.util.ArrayList;
import java.util.List;



import de.uds.MonitorInterventionMetafora.client.communication.servercommunication.ActionMaintenance;
import de.uds.MonitorInterventionMetafora.client.view.grids.IndicatorGridRowItem;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfUser;
import de.uds.MonitorInterventionMetafora.shared.utils.GWTDateUtils;

public class TableViewModel {
	ActionMaintenance maintenance;
	
	
	public TableViewModel(ActionMaintenance _maintenance){
		
		maintenance=_maintenance;
		
		
	}
	
	public List<IndicatorGridRowItem>  parseToIndicatorGridRowList(){
		
		List<CfAction> _cfActions=new ArrayList<CfAction>();
		if(maintenance.getActiveActionList()!=null){
			_cfActions.addAll(maintenance.getActiveActionList());
			}
		
		
		
		
		List<IndicatorGridRowItem> indicators=new ArrayList<IndicatorGridRowItem>();
			
		

		
		for(CfAction ac: _cfActions){
	
			IndicatorGridRowItem  myindicator=new IndicatorGridRowItem();
			
			
			String usersString="";
			   for(CfUser u : ac.getCfUsers()){
        		   usersString=usersString+" - "+u.getid();
        	   }
        	   
        	   
        	   myindicator.setName(usersString.substring(2,usersString.length()));
        	   myindicator.setActionType(ac.getCfActionType().getType());
        	   myindicator.setClassification(ac.getCfActionType().getClassification());
        	   myindicator.setDescription(ac.getCfContent().getDescription());     	   
        	   myindicator.setTime(GWTDateUtils.getTime(ac.getTime()));
        	   myindicator.setDate(GWTDateUtils.getDate(ac.getTime()));
        	   indicators.add(myindicator);	
		}
		
		return indicators;
		
		
	}
}
