package de.uds.MonitorInterventionMetafora.client.monitor.datamodel;

import java.util.ArrayList;
import java.util.List;



import de.uds.MonitorInterventionMetafora.client.monitor.ClientMonitorDataModel;
import de.uds.MonitorInterventionMetafora.client.monitor.dataview.table.IndicatorGridRowItem;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CommonFormatStrings;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.IndicatorFilterer;

public class TableViewModel {
	ClientMonitorDataModel maintenance;
	IndicatorFilterer filterer;
	
	public TableViewModel(ClientMonitorDataModel _maintenance){
		
		maintenance=_maintenance;
		filterer=new IndicatorFilterer(maintenance);
		
		//TODO: should be using the already grouped, filtered list, not making it's own
		//this.groupedByPropertyModel = groupedPropertyModel;
		
	}
	
	public TableViewModel(){
		filterer=new IndicatorFilterer();
	}
	
	public List<IndicatorGridRowItem>  parseToIndicatorGridRowList(boolean _applyFilter, boolean ignoreNotification){
		//TODO: this should not make a new list, but shoudl keep updated with current model
		List<CfAction> cfActions=new ArrayList<CfAction>();
		if(maintenance.getAllActions()!=null){	
			if(!_applyFilter){
				cfActions.addAll(maintenance.getAllActions());
			}
			else{
				cfActions.addAll(filterer.getFilteredIndicatorList());					
			}
		}

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
