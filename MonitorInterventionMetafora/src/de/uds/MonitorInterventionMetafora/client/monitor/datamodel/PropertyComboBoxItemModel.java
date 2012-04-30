/*
 * Ext GWT 2.2.5 - Ext for GWT
 * Copyright(c) 2007-2010, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package de.uds.MonitorInterventionMetafora.client.monitor.datamodel;

import com.extjs.gxt.ui.client.data.BaseModelData;

import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionPropertyRule;

public class PropertyComboBoxItemModel extends BaseModelData {

	private static final long serialVersionUID = 4167864850318391330L;
	private ActionPropertyRule propertyRule;

	public PropertyComboBoxItemModel(ActionPropertyRule propertyRule) {
	    this.propertyRule = propertyRule;
	    set("displayText", propertyRule.getDisplayText());

	}
	
	public ActionPropertyRule getActionPropertyRule(){
		return propertyRule;
	}


}
