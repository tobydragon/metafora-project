package de.uds.MonitorInterventionMetafora.client.monitor.datamodel;

import java.util.ArrayList;
import java.util.List;



import de.uds.MonitorInterventionMetafora.client.monitor.dataview.table.IndicatorGridRowItem;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CommonFormatStrings;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.IndicatorFilterer;

public class TableViewModel {
	ClientMonitorDataModel maintenance;
	
	public TableViewModel(ClientMonitorDataModel _maintenance){
		maintenance=_maintenance;
	}
	
	//TODO remove _applyFilter and ignoreNotification.  Data should be always filtered and ignoreNotification should just be another filter...
	public List<IndicatorGridRowItem>  parseToIndicatorGridRowList(boolean _applyFilter, boolean ignoreNotification){
//		List<CfAction> cfActions=new ArrayList<CfAction>();
//		if(maintenance.getAllActions()!=null){	
//			if(!_applyFilter){
//				cfActions.addAll(maintenance.getAllActions());
//			}
//			else{
//				cfActions.addAll(filterer.getFilteredIndicatorList());					
//			}
//		}

		List<CfAction> cfActions = maintenance.getFilteredActions();
		List<IndicatorGridRowItem> indicators=new ArrayList<IndicatorGridRowItem>();
		
		for(CfAction ac: cfActions){
			if(!ignoreNotification){
				indicators.add(new IndicatorGridRowItem(ac));
			}
			else {
        		String analysisType = ac.getCfContent().getPropertyValue(CommonFormatStrings.ANALYSIS_TYPE);
				if(analysisType == null || !analysisType.equalsIgnoreCase(CommonFormatStrings.NOTIFICATION)){
					indicators.add(new IndicatorGridRowItem(ac));
        		}
			}
		}
		return indicators;
		
		
	}
}
