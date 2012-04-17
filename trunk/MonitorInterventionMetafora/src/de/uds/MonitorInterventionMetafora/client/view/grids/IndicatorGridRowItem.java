/*
 * Ext GWT 2.2.5 - Ext for GWT
 * Copyright(c) 2007-2010, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package de.uds.MonitorInterventionMetafora.client.view.grids;

import com.extjs.gxt.ui.client.data.BaseModel;

import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfUser;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CommonFormatStrings;
import de.uds.MonitorInterventionMetafora.shared.utils.GWTUtils;

public class IndicatorGridRowItem extends BaseModel {

	private static final long serialVersionUID = 6376988771728912041L;

	private CfAction indicator;

//public IndicatorGridRowItem() {
//  }

	public IndicatorGridRowItem (CfAction indicator){
		this.indicator = indicator;
		setGridItemProperties();
	}

  
	private void setGridItemProperties(){
		
		String usersString="";
		for(CfUser u : indicator.getCfUsers()){
 		   usersString=usersString+" - "+u.getid();
		}
		
		set("name", usersString);
	    set("actiontype", indicator.getCfActionType().getType());
	    set("classification", indicator.getCfActionType().getClassification());
	    set("description", indicator.getCfContent().getDescription());
	    set("time", GWTUtils.getTime(indicator.getTime()));
	    set("date", GWTUtils.getDate(indicator.getTime()));
	    set("tool", indicator.getCfContent().getPropertyValue("TOOL"));
	}
	
	public String getColor(){
		  return indicator.getCfContent().getPropertyValue(CommonFormatStrings.COLOR);  
	}


}
