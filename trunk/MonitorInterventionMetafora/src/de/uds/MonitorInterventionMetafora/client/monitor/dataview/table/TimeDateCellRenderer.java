package de.uds.MonitorInterventionMetafora.client.monitor.dataview.table;

import java.util.Date;

import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.google.gwt.i18n.client.DateTimeFormat;

import de.uds.MonitorInterventionMetafora.shared.monitor.filter.StandardRuleType;

public class TimeDateCellRenderer implements GridCellRenderer<CfActionGridRow>{

	@Override
	public Object render(CfActionGridRow model, String property,
							ColumnData config, int rowIndex, int colIndex,
							ListStore<CfActionGridRow> store, Grid<CfActionGridRow> grid) {
		
		String valueOfCell = model.getGridItemPropertySingleValue(StandardRuleType.TIME);
		String rendered = DateTimeFormat.getFormat("HH:mm:ss '@' dd'/'MM").format (new Date(new Long(valueOfCell)));

		return rendered;
	}

}
