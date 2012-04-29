package de.uds.MonitorInterventionMetafora.client.view.widgets;

import com.extjs.gxt.ui.client.widget.VerticalPanel;

import de.uds.MonitorInterventionMetafora.client.datamodels.GroupedByPropertyModel;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.ActionPropertyRule;

public abstract class DataViewPanel2 extends VerticalPanel{

	public abstract void changeGroupingProperty(ActionPropertyRule newPropToGroupBy);

	public abstract void refresh();

}
