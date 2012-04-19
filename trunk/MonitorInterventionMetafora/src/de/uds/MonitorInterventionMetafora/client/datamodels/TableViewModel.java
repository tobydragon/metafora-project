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
	GroupedByPropertyModel groupedByPropertyModel;
	
	public TableViewModel(ActionMaintenance _maintenance){
		
		maintenance=_maintenance;
		filterer=new IndicatorFilterer(maintenance);
		
		//TODO: should be using the already grouped, filtered list, not making it's own
		//this.groupedByPropertyModel = groupedPropertyModel;
		
	}
	
	public TableViewModel(){
		filterer=new IndicatorFilterer();
	}
	
	public List<IndicatorGridRowItem>  parseToIndicatorGridRowList(boolean _applyFilter, boolean ignoreNotification){
		
		List<CfAction> cfActions=new ArrayList<CfAction>();
		if(maintenance.getAllActiveActionList()!=null){	
			if(!_applyFilter){
				cfActions.addAll(maintenance.getAllActiveActionList());
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
