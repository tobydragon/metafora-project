
package de.uds.MonitorInterventionMetafora.client.monitor.dataview.table;

import java.util.Date;

import com.extjs.gxt.ui.client.data.BaseModel;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;

import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfUser;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CommonFormatStrings;
import de.uds.MonitorInterventionMetafora.shared.monitor.MonitorConstants;
import de.uds.MonitorInterventionMetafora.shared.utils.GWTUtils;
import de.uds.MonitorInterventionMetafora.shared.utils.Logger;

public class CfActionGridRow extends BaseModel {
	private Logger logger = Logger.getLogger(this.getClass());
	
	private static final long serialVersionUID = 6376988771728912041L;

	private CfAction indicator;

	public CfActionGridRow (CfAction indicator){
		this.indicator = indicator;
		setGridItemProperties();
	}

	private void setGridItemProperties(){
		
		try {
			String usersString="";
			for(CfUser u : indicator.getCfUsers()){
	 		   usersString=usersString+" - "+u.getid();
			}
			
			set(MonitorConstants.USER_ID_LABEL, usersString);
		    set(MonitorConstants.ACTION_TYPE_LABEL, indicator.getCfActionType().getType());
		    set(MonitorConstants.ACTION_CLASSIFICATION_LABEL, indicator.getCfActionType().getClassification());
		    if (indicator.getCfContent() != null){
			    set(MonitorConstants.DESCRIPTION_LABEL, indicator.getCfContent().getDescription());
			    set(MonitorConstants.TOOL_LABEL, indicator.getCfContent().getPropertyValue("TOOL"));
			    set(MonitorConstants.CHALLENGE_NAME_LABEL, indicator.getCfContent().getPropertyValue("CHALLENGE_NAME"));
			    set(MonitorConstants.INDICATOR_TYPE_LABEL, indicator.getCfContent().getPropertyValue("INDICATOR_TYPE"));
		    }
		    else {
		    	set(MonitorConstants.DESCRIPTION_LABEL, MonitorConstants.BLANK_PROPERTY_LABEL);
		    	set(MonitorConstants.TOOL_LABEL, MonitorConstants.BLANK_PROPERTY_LABEL);
		    }
		    set(MonitorConstants.ACTION_TIME_LABEL, DateTimeFormat.getFormat(PredefinedFormat.DATE_TIME_SHORT).format (new Date(indicator.getTime())) );
		}
		catch (Exception e){
			logger.error("[setGridItemProperties] missing attributes, row not added for indicator: " +indicator.toString());
		}
	}
	
	public String getColor(){
		if (indicator.getCfContent() != null){
		  return indicator.getCfContent().getPropertyValue(CommonFormatStrings.COLOR);
		}
		return null;
	}

	public CfAction getIndicator() {
		return indicator;
	}

}
