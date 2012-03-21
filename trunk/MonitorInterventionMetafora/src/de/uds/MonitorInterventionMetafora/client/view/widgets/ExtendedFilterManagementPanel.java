package de.uds.MonitorInterventionMetafora.client.view.widgets;

import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.form.ComboBox;

import de.uds.MonitorInterventionMetafora.client.datamodels.EntityListComboBoxModel;

public class ExtendedFilterManagementPanel extends HorizontalPanel{

	private  ComboBox<EntityListComboBoxModel> entityComboBox;
	
	
	public ExtendedFilterManagementPanel(){
		
		entityComboBox = new ComboBox<EntityListComboBoxModel>();	
	}
	
	
	
}
