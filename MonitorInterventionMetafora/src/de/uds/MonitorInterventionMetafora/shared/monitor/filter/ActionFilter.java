package de.uds.MonitorInterventionMetafora.shared.monitor.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.extjs.gxt.ui.client.widget.ComponentManager;
import com.extjs.gxt.ui.client.widget.grid.EditorGrid;

import de.uds.MonitorInterventionMetafora.client.monitor.filter.IndicatorFilterItemGridRowModel;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.ActionElementType;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.OperationType;

public class ActionFilter {
	
	public List<CfAction> getFilteredListfromCurrentFilterGrid(List<CfAction> listToFilter){
		return getFilteredList(listToFilter, getActiveFiltersFromFilterGrid());
	}
	
	public List<CfAction> getFilteredList(List<CfAction> listToFilter, List<ActionPropertyRule> rules){
		List<CfAction> filteredList = new Vector<CfAction>();
		for (CfAction action : listToFilter){
			if (filterIncludesAction(action, rules)){
				filteredList.add(action);
			}
		}
		return filteredList;
	}
	
	public boolean currentFilterGridIncludesAction(CfAction action){
		return filterIncludesAction(action, getActiveFiltersFromFilterGrid());
	}
	
	public boolean filterIncludesAction(CfAction action, List<ActionPropertyRule> rules){
		for (ActionPropertyRule rule : rules){
			if (! rule.ruleIncludesAction(action)){
				return false;
			}
		}
		return true;
	}
	
	
	// TODO: Get rid to this, a filter should maintain it's own list of filters, which can be updated by the FilterItemGrid, if this is where the list comes from
	List<ActionPropertyRule> getActiveFiltersFromFilterGrid(){
		List<ActionPropertyRule> _activeFilters=new ArrayList<ActionPropertyRule>();
		
	    EditorGrid<IndicatorFilterItemGridRowModel> _grid = (EditorGrid<IndicatorFilterItemGridRowModel>) ComponentManager.get().get("_filterItemGrid");
		if (_grid != null && _grid.getStore() != null){
			for(int i=0;i<_grid.getStore().getCount();i++){
				
				IndicatorFilterItemGridRowModel _row=_grid.getStore().getAt(i);
				ActionPropertyRule _filter=new ActionPropertyRule();
				_filter.setDisplayText(_row.getDisplayText());
				_filter.setType(ActionElementType.getFromString(_row.getType().toUpperCase()));
				_filter.setOperationType(OperationType.getFromString(_row.getOperation().toUpperCase()));
				_filter.setEntityName(_row.getProperty());
				_filter.setValue(_row.getValue());
				_activeFilters.add(_filter);
							
			}
		}

		return _activeFilters;
	}
}
