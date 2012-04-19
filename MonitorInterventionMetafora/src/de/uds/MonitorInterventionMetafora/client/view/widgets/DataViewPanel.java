package de.uds.MonitorInterventionMetafora.client.view.widgets;

import com.extjs.gxt.ui.client.widget.VerticalPanel;

import de.uds.MonitorInterventionMetafora.client.datamodels.GroupedByPropertyModel;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.IndicatorProperty;

public abstract class DataViewPanel extends VerticalPanel{

	public abstract void changeGroupingProperty(IndicatorProperty newPropToGroupBy);

	public abstract void refresh(GroupedByPropertyModel modelUpdate);

}
