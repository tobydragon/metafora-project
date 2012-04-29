package de.uds.MonitorInterventionMetafora.client.view.widgets;

import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import de.uds.MonitorInterventionMetafora.client.datamodels.PropertyComboBoxItemModel;

public class ActionPropertyComboBox extends ComboBox<PropertyComboBoxItemModel>{
	
	public ActionPropertyComboBox(ListStore<PropertyComboBoxItemModel> items, String panelId){
		super();
		setId(panelId);
		
		setEmptyText("Select property:");
	    setDisplayField("displayText");
	    setWidth(150);
	    setEditable(false);
	    setAutoHeight(true);
	    setStore(items);
	    setTriggerAction(TriggerAction.ALL);
	    
//	    PropertyComboBoxItemModel firstSelected = items.findModel("displayText", groupingProperty.getDisplayText());
//	    if (firstSelected != null){
//	    	comboType.select(firstSelected);
//	    }
	}
	
}
