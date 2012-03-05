package com.analysis.client.datamodels;

import java.util.ArrayList;
import java.util.List;

import com.analysis.client.communication.server.ActionMaintenance;
import com.analysis.client.view.grids.IndicatorGridRowItem;

import com.analysis.shared.commonformat.CfAction;
import com.analysis.shared.commonformat.CfUser;
import com.analysis.shared.utils.GWTDateUtils;

public class TableViewModel {

	
	
	
	public List<IndicatorGridRowItem>  parseToIndicatorGridRowList(){
		
		List<CfAction> _cfActions=new ArrayList<CfAction>();
		if(ActionMaintenance._activecfActions!=null){
			_cfActions=ActionMaintenance._activecfActions;
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
