package de.uds.MonitorInterventionMetafora.client.datamodels;

import java.util.ArrayList;
import java.util.List;



import de.uds.MonitorInterventionMetafora.client.communication.servercommunication.ActionMaintenance;
import de.uds.MonitorInterventionMetafora.client.view.grids.IndicatorGridRowItem;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfUser;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CommonFormatStrings;
import de.uds.MonitorInterventionMetafora.shared.utils.GWTUtils;
import de.uds.MonitorInterventionMetafora.shared.utils.IndicatorFilterer;

public class TableViewModel {
	ActionMaintenance maintenance;
	IndicatorFilterer filterer;
	
	public TableViewModel(ActionMaintenance _maintenance){
		
		maintenance=_maintenance;
		filterer=new IndicatorFilterer(maintenance);
		
		
	}
	
	public TableViewModel(){
		filterer=new IndicatorFilterer();
	}
	
	public List<IndicatorGridRowItem>  parseToIndicatorGridRowList(boolean _applyFilter){
		
		List<CfAction> _cfActions=new ArrayList<CfAction>();
		if(maintenance.getAllActiveActionList()!=null){
			
			if(!_applyFilter)
		_cfActions.addAll(maintenance.getAllActiveActionList());
			else{
				_cfActions.addAll(filterer.getFilteredIndicatorList());					
			}
			
		//_cfActions.addAll(filterer.getFilteredIndicatorList());
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
        	   myindicator.setTime(GWTUtils.getTime(ac.getTime()));
        	   myindicator.setDate(GWTUtils.getDate(ac.getTime()));
        	   
        	   if(ac.getCfContent().getProperty(CommonFormatStrings.COLOR)!=null){
        		   
        		   myindicator.setColor(ac.getCfContent().getProperty(CommonFormatStrings.COLOR).getValue()); 
        		   
        	   }
        	   
        	   indicators.add(myindicator);	
		}
		
		return indicators;
		
		
	}
	
	
	
	
	
	public List<IndicatorGridRowItem>  parseToIndicatorGridRowList(boolean _applyFilter,boolean _ignoreNotification){
		
		List<CfAction> _cfActions=new ArrayList<CfAction>();
		if(maintenance.getAllActiveActionList()!=null){
			
			if(!_applyFilter)
		_cfActions.addAll(maintenance.getAllActiveActionList());
			else{
				_cfActions.addAll(filterer.getFilteredIndicatorList());					
			}
			
		//_cfActions.addAll(filterer.getFilteredIndicatorList());
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
        	   myindicator.setTime(GWTUtils.getTime(ac.getTime()));
        	   myindicator.setDate(GWTUtils.getDate(ac.getTime()));
        	         	   
        	   if(ac.getCfContent().getProperty(CommonFormatStrings.COLOR)!=null){
        		   
        		   myindicator.setColor(ac.getCfContent().getProperty(CommonFormatStrings.COLOR).getValue()); 
        		   
        	   }
        	   
        	   
        	   
        	   if(!_ignoreNotification){
        	   indicators.add(myindicator);
        	   }
        	   else{
        		if(ac.getCfContent().getPropertyValue(CommonFormatStrings.ANALYSIS_TYPE)==null || !ac.getCfContent().getPropertyValue(CommonFormatStrings.ANALYSIS_TYPE).equalsIgnoreCase(CommonFormatStrings.NOTIFICATION)){
        			
        			 indicators.add(myindicator);
        		}
        		   
        		
        	   }
        	   
		}
		
		return indicators;
		
		
	}
	
	
	
	
	
	
public List<IndicatorGridRowItem>  parseToIndicatorGridRowList(List<CfAction> _cfActions,boolean _applyFilter){
		
	
		
		if( _cfActions!=null){
			
			if(!_applyFilter)
		_cfActions.addAll(_cfActions);
			else{
				_cfActions.addAll(filterer.getFilteredIndicatorList(_cfActions));					
			}
			
		//_cfActions.addAll(filterer.getFilteredIndicatorList());
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
        	   myindicator.setTime(GWTUtils.getTime(ac.getTime()));
        	   myindicator.setDate(GWTUtils.getDate(ac.getTime()));
        	   if(ac.getCfContent().getProperty(CommonFormatStrings.COLOR)!=null){
        		   
        		   myindicator.setColor(ac.getCfContent().getProperty(CommonFormatStrings.COLOR).getValue()); 
        		   
        	   }
        	   
        	   
        	   indicators.add(myindicator);	
		}
		
		return indicators;
		
		
	}
	
	
	
	
	
}
