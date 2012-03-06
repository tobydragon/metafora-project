package de.uds.visualizer.client.datamodels;

import java.util.ArrayList;
import java.util.List;


import com.analysis.shared.commonformat.CfAction;
import com.analysis.shared.commonformat.CfUser;
import com.analysis.shared.utils.GWTDateUtils;

import de.uds.visualizer.client.communication.servercommunication.ActionMaintenance;
import de.uds.visualizer.client.view.grids.IndicatorGridRowItem;

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
