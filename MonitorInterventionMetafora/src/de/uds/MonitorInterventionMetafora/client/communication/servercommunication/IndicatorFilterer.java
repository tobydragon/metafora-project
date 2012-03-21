package de.uds.MonitorInterventionMetafora.client.communication.servercommunication;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.widget.ComponentManager;
import com.extjs.gxt.ui.client.widget.grid.EditorGrid;

import de.uds.MonitorInterventionMetafora.client.datamodels.IndicatorFilterItemGridRowModel;
import de.uds.MonitorInterventionMetafora.client.datamodels.attributes.FilterAttributeName;
import de.uds.MonitorInterventionMetafora.client.datamodels.attributes.FilterItemType;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfActionType;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfContent;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfObject;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfUser;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.IndicatorEntity;

public class IndicatorFilterer {

	//List<CfAction> actions;
	//List<IndicatorEntity> filtwers;
	
	ActionMaintenance maintenance;
	public IndicatorFilterer(){}
	
	public IndicatorFilterer(ActionMaintenance _maintenance){
		
		//filters=_filters;
		maintenance=_maintenance;
		
	}
	
	boolean isSatisfyFilter(CfAction _action,IndicatorEntity _entity){
		boolean result=false;
		
		switch(_entity.getType()){
		case ACTION_TYPE:
			
			result=isSatisfyFilter(_action.getCfActionType(),_entity);
			break;
		case USER:
			for(CfUser _user:_action.getCfUsers()){
				
				result=isSatisfyFilter(_user,_entity);
				if(result)
					break;
				
			}
			break;
		
		case OBJECT:
			for(CfObject _obj: _action.getCfObjects()){
				result=isSatisfyFilter(_obj,_entity);
				if(result)
					break;
				
			}
			break;
			
		case CONTENT:
			
			result=isSatisfyFilter(_action.getCfContent(),_entity);
			break;
			
			
			}
		
		
		return result;
	}
	//type="indicator" classification="shouldbeinthemiddleCREATE" succeed="true" 
	
	boolean isSatisfyFilter(CfActionType _actionType,IndicatorEntity _entity){
		
		FilterAttributeName _attribute=FilterAttributeName.getFromString(_entity.getEntityName().toUpperCase());
		boolean result=false;
		if(_attribute==null){
			
			result= false;
		}
		
		else if(_attribute==FilterAttributeName.TYPE){
			
			if(_actionType.getType().equalsIgnoreCase(_entity.getValue())){
				result= true;
			}				
		}
		else if(_attribute==FilterAttributeName.CLASSIFICATION){
			
			if(_actionType.getClassification().equalsIgnoreCase(_entity.getValue())){
				result=true;
			}		
			
		}
		
	else if(_attribute==FilterAttributeName.SUCCEED){
			
			if(_actionType.getSucceed().equalsIgnoreCase(_entity.getValue())){
				result= true;
			}		
			
		}
		
				
		return result;
	}
	
	
	
	
	
	
	
	
	//id="Maria" role="UserID" 
	boolean isSatisfyFilter(CfUser _user,IndicatorEntity _entity){
		
		FilterAttributeName _attribute=FilterAttributeName.getFromString(_entity.getEntityName().toUpperCase());
		boolean result=false;
		if(_attribute==null){
			
			result= false;
		}
		
		else if(_attribute==FilterAttributeName.ID){
			
			if(_user.getid().equalsIgnoreCase(_entity.getValue())){
				result= true;
			}				
		}
		else if(_attribute==FilterAttributeName.ROLE){
			
			if(_user.getrole().equalsIgnoreCase(_entity.getValue())){
				result=true;
			}		
			
		}
		
		
				
		return result;
	}
	
	
	
	
	//id="11" type="Help Request"
	boolean isSatisfyFilter(CfObject _obj,IndicatorEntity _entity){
		
		boolean result=false;
		FilterAttributeName _attribute=FilterAttributeName.getFromString(_entity.getEntityName().toUpperCase());
		if(_attribute==null){
			//it is object properties
			String _objValue=_obj.getPropertyValue(_entity.getEntityName());
			if(_objValue!=null){
				
				if(_objValue.equalsIgnoreCase(_entity.getValue())){
					
					
					result=true;
				}
			}
			
		
		}
		
		else if(_attribute==FilterAttributeName.TYPE){
			
			if(_obj.getType().equalsIgnoreCase(_entity.getValue())){
				result= true;
			}				
		}
		else if(_attribute==FilterAttributeName.ID){
			
			if(_obj.getId().equalsIgnoreCase(_entity.getValue())){
				result= true;
			}		
			
		}
		

		
				
		return result;
	}
	
	
	boolean isSatisfyFilter(CfContent _content,IndicatorEntity _entity){
		boolean result=false;
		String _contentValue=_content.getPropertyValue(_entity.getEntityName());
		if(_contentValue!=null){
			
			
			if(_contentValue.equalsIgnoreCase(_entity.getValue())){
				
				result=true;
			}
		}
		
		
		return result;
	}
	
	
	boolean  isSatisfyFilters(CfAction _action,List<IndicatorEntity> _filters){
		boolean result=false;
		if(_filters.size()<=0)
			return true;
		
		for(int i=0; i<_filters.size();i++){
			//boolean result=false;

			result=isSatisfyFilter(_action,_filters.get(i));

			if(!result){
				
				break;
			}
			int k=_filters.size()-1;
			if(i==k){
				
				result=true;
			}		
					}
		

		return result;
				
	}
	
	
	
	
	
	List<IndicatorEntity> getActiveFiltersFromFilterGrid(){
		List<IndicatorEntity> _activeFilters=new ArrayList<IndicatorEntity>();
		
	    EditorGrid<IndicatorFilterItemGridRowModel> editorGrid = (EditorGrid<IndicatorFilterItemGridRowModel>) ComponentManager.get().get("_filterItemGrid");
		EditorGrid<IndicatorFilterItemGridRowModel> _grid = editorGrid;
		for(int i=0;i<_grid.getStore().getCount();i++){
			
			IndicatorFilterItemGridRowModel _row=_grid.getStore().getAt(i);
			IndicatorEntity _filter=new IndicatorEntity();
			_filter.setDisplayText(_row.getDisplayText());
			_filter.setType(FilterItemType.getFromString(_row.getType().toUpperCase()));
			_filter.setEntityName(_row.getProperty());
			_filter.setValue(_row.getValue());
			_activeFilters.add(_filter);
						
		}

		return _activeFilters;
	}
	
	
	public List<CfAction> getFilteredIndicatorList(){
		
		List<CfAction> _allActions =new ArrayList<CfAction>();
		List<CfAction> _filteredActions =new ArrayList<CfAction>();
		_allActions.addAll(maintenance.getAllActiveActionList());
		
		for(CfAction _action:_allActions){
		if(isSatisfyFilters(_action,getActiveFiltersFromFilterGrid())){
			
			_filteredActions.add(_action);
		}		
		}		
	
		return _filteredActions;
	}
	
	
public List<CfAction> getFilteredIndicatorList(List<CfAction>  _allActions){
		
		
		List<CfAction> _filteredActions =new ArrayList<CfAction>();
		
		for(CfAction _action:_allActions){
		if(isSatisfyFilters(_action,getActiveFiltersFromFilterGrid())){
			
			_filteredActions.add(_action);
		}		
		}		
	
		return _filteredActions;
	}
	
	
}
