/*
 * Ext GWT 2.2.5 - Ext for GWT
 * Copyright(c) 2007-2010, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package de.uds.MonitorInterventionMetafora.client.monitor.dataview.table;

import com.extjs.gxt.ui.client.data.BaseModel;

import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfUser;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CommonFormatStrings;
import de.uds.MonitorInterventionMetafora.shared.monitor.MonitorConstants;
import de.uds.MonitorInterventionMetafora.shared.utils.GWTUtils;
import de.uds.MonitorInterventionMetafora.shared.utils.Logger;

public class IndicatorGridRowItem extends BaseModel {
	private Logger logger = Logger.getLogger(this.getClass());
	
	private static final long serialVersionUID = 6376988771728912041L;

	private CfAction indicator;

	public IndicatorGridRowItem (CfAction indicator){
		this.indicator = indicator;
		setGridItemProperties();
	}

	private void setGridItemProperties(){
		
		try {
			String usersString="";
			for(CfUser u : indicator.getCfUsers()){
	 		   usersString=usersString+" - "+u.getid();
			}
			
			set("name", usersString);
		    set("actiontype", indicator.getCfActionType().getType());
		    set("classification", indicator.getCfActionType().getClassification());
		    if (indicator.getCfContent() != null){
			    set("description", indicator.getCfContent().getDescription());
			    set("tool", indicator.getCfContent().getPropertyValue("TOOL"));
		    }
		    else {
		    	set("desription", MonitorConstants.BLANK_PROPERTY);
		    	set("tool", MonitorConstants.BLANK_PROPERTY);
		    }
		    set("time", GWTUtils.getTime(indicator.getTime()));
		    set("date", GWTUtils.getDate(indicator.getTime()));
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
