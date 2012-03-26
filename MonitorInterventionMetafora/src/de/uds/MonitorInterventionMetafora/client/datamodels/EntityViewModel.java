package de.uds.MonitorInterventionMetafora.client.datamodels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import com.extjs.gxt.ui.client.store.ListStore;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;

import de.uds.MonitorInterventionMetafora.client.communication.servercommunication.ActionMaintenance;
import de.uds.MonitorInterventionMetafora.client.communication.servercommunication.IndicatorFilterer;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfContent;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfObject;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfUser;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CommonFormatStrings;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.FilterAttributeName;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.FilterItemType;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.OperationType;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.IndicatorEntity;

public class EntityViewModel {
	
	
	 List<CfAction> allActions=null;
	 List<CfObject> allObjects=null;
	 List<CfContent> allContents=null;
	Map<String, List<CfContent>>  groupedContents=null;
	Map<String, List<CfObject>>   groupedObjects=null;
	Map<String, List<CfAction>>   groupedActions=null;
	Map<String, List<CfUser>>  	  groupedUsers=null;
	
	Map<Integer, IndicatorEntity>indicatorEntities = null;
	Map<Integer, IndicatorEntity>subsectionValue =null;
	Map<String, String> activeFilters =null;
	ActionMaintenance maintenance=null;
	
	IndicatorFilterer filterer;
	private int maxValue=0;
	public EntityViewModel(ActionMaintenance _maintenance ){
	
		maintenance=_maintenance;
		activeFilters = new HashMap<String, String>();
		subsectionValue= new HashMap<Integer, IndicatorEntity>();
		indicatorEntities=new HashMap<Integer,IndicatorEntity>();
		filterer=new IndicatorFilterer(maintenance);
		sliptActions(false);
		
	}
	
	
	
	
	public void sliptActions(boolean _applyFilter){
		
		allActions= new ArrayList<CfAction>();
		allObjects=new ArrayList<CfObject>();
		allContents=new ArrayList<CfContent>();
	
		
		List<CfAction> _activecfActions=new ArrayList<CfAction>();
		
		if(!_applyFilter){
		_activecfActions.addAll(maintenance.getAllActiveActionList());
		}
		else{
			_activecfActions.addAll(filterer.getFilteredIndicatorList(maintenance.getAllActiveActionList()));
		}
		
		allActions.addAll(_activecfActions);
		for(CfAction _action:_activecfActions){
			
			for(CfObject _obj:_action.getCfObjects()){
				
				_obj.setActionTime(_action.getTime());
				allObjects.add(_obj);
			}
			
			CfContent _cont=_action.getCfContent();
			_cont.setActionTime(_action.getTime());			
			allContents.add(_cont);
			
			
		}
		
		
	}
	
	
	public List<IndicatorEntity>  getIndicatorEntities(){
		
		List<IndicatorEntity> _entityList=new ArrayList<IndicatorEntity>();
		
		
			IndicatorEntity _entity=null;
			
			//Action_Type Attributes
			
			_entity =new IndicatorEntity();
			_entity.setEntityName(CommonFormatStrings.A_V_Type);
			_entity.setDisplayText(CommonFormatStrings.A_Type);
			_entity.setType(FilterItemType.ACTION_TYPE);
			_entityList.add(_entity);
			
			
			_entity =new IndicatorEntity();
			_entity.setEntityName(CommonFormatStrings.A_V_Classification);
			_entity.setDisplayText(CommonFormatStrings.A_Classification);
			_entity.setType(FilterItemType.ACTION_TYPE);
			_entityList.add(_entity);
			
		
			
			_entity =new IndicatorEntity();
			_entity.setEntityName(CommonFormatStrings.A_V_SUCCED);
			_entity.setDisplayText(CommonFormatStrings.A_SUCCED);
			_entity.setType(FilterItemType.ACTION_TYPE);
			_entityList.add(_entity);
			
			//User Attributes
			
			_entity =new IndicatorEntity();
			_entity.setEntityName(CommonFormatStrings.A_V_User);
			_entity.setDisplayText(CommonFormatStrings.A_User);
			_entity.setType(FilterItemType.USER);
			_entityList.add(_entity);
			
			
			_entity =new IndicatorEntity();
			_entity.setEntityName(CommonFormatStrings.A_V_ROLE);
			_entity.setDisplayText(CommonFormatStrings.A_ROLE);
			_entity.setType(FilterItemType.USER);
			_entityList.add(_entity);
					
			//Object Properties			
			_entityList.addAll(getObjectEntities());
			
			//Content Properties
			_entityList.addAll(getContentEntities());
			
			return _entityList;
			
		
	}
	
	List<IndicatorEntity> getContentEntities(){
		
	   List<IndicatorEntity> _entityList=new ArrayList<IndicatorEntity>();
	 
	   Map<String,String> _entityMap=new HashMap<String,String>();
		
		for(CfContent _content: allContents){
			for(String _entityName:_content.getProperties().keySet()){
				
				IndicatorEntity _entity =new IndicatorEntity();
				
				_entity.setEntityName(_entityName);
				_entity.setType(FilterItemType.CONTENT);
				_entity.setDisplayText(_entityName);
				if(!_entityMap.containsKey(_entity.getEntityName())){
				_entityList.add(_entity);
				_entityMap.put(_entityName, _entityName);
				
				}
			
			}
			}
			
	   
		return _entityList;
	}
	
	
	
	List<IndicatorEntity> getObjectEntities(){
		
		List<IndicatorEntity> _entityList=new ArrayList<IndicatorEntity>();
		
		IndicatorEntity _entity=null;
		
		//Object Attributes
		_entity =new IndicatorEntity();
		_entity.setEntityName(CommonFormatStrings.O_V_OBJECT_ID);
		_entity.setDisplayText(CommonFormatStrings.O_OBJECT_ID);
		_entity.setType(FilterItemType.OBJECT);
		_entityList.add(_entity);
		
		_entity =new IndicatorEntity();
		_entity.setEntityName(CommonFormatStrings.O_V_OBJECT_TYPE);
		_entity.setDisplayText(CommonFormatStrings.O_OBJECT_TYPE);
		_entity.setType(FilterItemType.OBJECT);
		_entityList.add(_entity);
		 Map<String,String> _entityMap=new HashMap<String,String>();
		
	
			for(CfObject _obj: allObjects){
			for(String _entityName:_obj.getProperties().keySet()){
				
			    _entity =new IndicatorEntity();
				
				_entity.setEntityName(_entityName);
				_entity.setType(FilterItemType.OBJECT);
				_entity.setDisplayText(_entityName);
				
				if(!_entityMap.containsKey(_entity.getEntityName())){
					_entityList.add(_entity);
					_entityMap.put(_entityName,_entityName);
					
					}
				
			}
			}
			
			return _entityList;
		
		
	}
	
	
public 	int getMaxValue(){
	
	if(maxValue>100){
		
		maxValue=maxValue+5;
	}
	else if(maxValue>1000){
		maxValue=maxValue+50;
	}
	
	else if(maxValue>10000){
		maxValue=maxValue+500;
	}
	
	else if(maxValue>100000){
		maxValue=maxValue+5000;
	}
	
	else if(maxValue>1000000){
		maxValue=maxValue+50000;
	}
	
	return maxValue;	
	}
	

	public DataTable getEntityDataTable(IndicatorEntity _entity){
		
		
		DataTable data = DataTable.create();
		
		if(_entity==null || _entity.getEntityName().equalsIgnoreCase("")){
			return null;
		}
		else if(_entity.getType()==null){
			
			return null;
		}
		
		
		switch (_entity.getType()){
		
		case ACTION_TYPE:
			
			groupedActions=new HashMap<String, List<CfAction>>(); 
			groupedActions=groupActionTypesByEntityName(_entity.getEntityName());
			
			    data.addColumn(ColumnType.STRING, "Task");
			    data.addColumn(ColumnType.NUMBER, "Indicator Count");
			    data.addRows(groupedActions.size());
			    int index=0;
			    for(String key:groupedActions.keySet()){
			    data.setValue(index, 0, key);
			    IndicatorEntity _newEntity=new IndicatorEntity();			    
			    _newEntity.setEntityName(_entity.getEntityName());
			    _newEntity.setValue(key);
			    _newEntity.setType(_entity.getType());
			    _newEntity.setOperationType(OperationType.EQUALS);
			    indicatorEntities.put(index, _newEntity);
			 
			    int _size=groupedActions.get(key).size();
			    data.setValue(index, 1,_size);
			    if(_size>maxValue){
			    	
			    	maxValue=_size;		
			    }
			    
			    index++;
		}
			    
			break;
		case USER:
			groupedUsers=new HashMap<String, List<CfUser>>(); 
			groupedUsers=groupUsersByEntityName(_entity.getEntityName());
			
			    data.addColumn(ColumnType.STRING, "Task");
			    data.addColumn(ColumnType.NUMBER, "Indicator Count");
			    data.addRows(groupedUsers.size());
			     index=0;
			    for(String key:groupedUsers.keySet()){
			    data.setValue(index, 0, key);
			    IndicatorEntity _newEntity=new IndicatorEntity();			    
			    _newEntity.setEntityName(_entity.getEntityName());
			    _newEntity.setValue(key);
			    _newEntity.setType(_entity.getType());
			    _newEntity.setOperationType(OperationType.EQUALS);
			    indicatorEntities.put(index, _newEntity);
		
			    int _size=groupedUsers.get(key).size();
			    data.setValue(index, 1, _size);
			    if(_size>maxValue){
			    	
			    	maxValue=_size;		
			    }
			    index++;
			    }
			
			break;
		case OBJECT:
			
		groupedObjects=new HashMap<String, List<CfObject>>();
			
			groupedObjects=groupObjectsByEntityName(_entity.getEntityName());
			
			   data.addColumn(ColumnType.STRING, "Task");
			    data.addColumn(ColumnType.NUMBER, "Indicator Count");
			    data.addRows(groupedObjects.size());
			    index=0;
			    for(String key:groupedObjects.keySet()){
			    data.setValue(index, 0, key);
			    
			    IndicatorEntity _newEntity=new IndicatorEntity();			    
			    _newEntity.setEntityName(_entity.getEntityName());
			    _newEntity.setValue(key);
			    _newEntity.setType(_entity.getType());
			    _newEntity.setOperationType(OperationType.EQUALS);
			    indicatorEntities.put(index, _newEntity);
			    //_entity.setValue(key);
			    //indicatorEntities.put(index,_entity );
			   // subsectionValue.put(index, key);
			    
			    int _size=groupedObjects.get(key).size();
			    data.setValue(index, 1, _size);
			    
			    if(_size>maxValue){
			    	
			    	maxValue=_size;		
			    }
			    index++;
			    }
			    
			break;
			
		case CONTENT:
			groupedContents=new HashMap<String, List<CfContent>>(); 
			groupedContents=groupContentsByEntityName(_entity.getEntityName());	
			    data.addColumn(ColumnType.STRING, "Task");
			    data.addColumn(ColumnType.NUMBER, "Indicators");
			    data.addRows(groupedContents.size());
			    index=0;
			    for(String key:groupedContents.keySet()){
			    data.setValue(index, 0, key);
			    
			    IndicatorEntity _newEntity=new IndicatorEntity();			    
			    _newEntity.setEntityName(_entity.getEntityName());
			    _newEntity.setValue(key);
			    _newEntity.setType(_entity.getType());
			    _newEntity.setOperationType(OperationType.EQUALS);
			    indicatorEntities.put(index, _newEntity);
			    
			    //_entity.setValue(key);
			  //  indicatorEntities.put(index,_entity );
			    //subsectionValue.put(index, key);
			    int _size=groupedContents.get(key).size();
			    data.setValue(index, 1, _size);
			    if(_size>maxValue){
			    	
			    	maxValue=_size;		
			    }
			    index++;
			
			break;
		
			
		}}

	    return data;
		
	}
	
	
	
	 
	 
	public void addActiveFilter(String _key){
		
		
		activeFilters.put(_key, _key);
		
	}
	
	
	public boolean isInFilterList(String _key){
		
		return activeFilters.containsKey(_key);
		
	}
	public IndicatorEntity getIndicatorEntity(int _key){
		
		if(indicatorEntities.containsKey(_key))
		return indicatorEntities.get(_key);
		return null;
	}

	
	
public Map<String, List<CfObject>> groupObjectByProperty(String _property){
	Map<String, List<CfObject>> map = new HashMap<String, List<CfObject>>();
	for (CfObject myobject :allObjects) {
		
		
		   String  key="";
		   if(myobject.getProperties().containsKey(_property)){
			   
			key=myobject.getPropertyValue(_property);
				    
		   if (map.get(key) == null) {
			   map.put(key, new ArrayList<CfObject>());
		   }
		   
		   map.get(key).add(myobject);
		   }
		}
	
return map;	
}


 Map<String, List<CfObject>> groupObjectByAttribute(String _attribute){

	 
	 
	 
	 Map<String, List<CfObject>> map = new HashMap<String, List<CfObject>>();
		for (CfObject _obj :allObjects) {
			
				   String  key="";
				   
				   if(_attribute.equalsIgnoreCase(CommonFormatStrings.O_V_OBJECT_TYPE)){
					   
					   key=_obj.getType();
					   if (map.get(key) == null) {
						      map.put(key, new ArrayList<CfObject>());
						   }
						   map.get(key).add(_obj);

				   }
				   
				   else if(_attribute.equalsIgnoreCase(CommonFormatStrings.O_V_OBJECT_ID)){
					   
					   key=_obj.getId();
					   if (map.get(key) == null) {
						      map.put(key, new ArrayList<CfObject>());
						   }
						   map.get(key).add(_obj);

				   }
		
		}
			
	 
	 
	 
	
	return map;
}



	public Map<String, List<CfObject>> groupObjectsByEntityName(String _entityName){
		
		
		Map<String, List<CfObject>> map = new HashMap<String, List<CfObject>>();
		
	
		
		if(FilterAttributeName.getFromString(_entityName.toUpperCase())==null){
			map=groupObjectByProperty(_entityName);

		}
		else{
			
		
			map=groupObjectByAttribute(_entityName);
			
		}
			
		
		
		//outputSorted(map);
		return map;	
	}	
	
	
		
		 Map<String, List<CfContent>> groupContentsByEntityName(String property){
			
			Map<String, List<CfContent>> map = new HashMap<String, List<CfContent>>();
			for (CfContent mycontent :allContents) {
				 String  key="";
			   if(mycontent.getProperties().containsKey(property)){
			   key=mycontent.getPropertyValue(property);
			   
			   if (map.get(key) == null) {
				   
				   map.put(key, new ArrayList<CfContent>());
				   
			   }
			   map.get(key).add(mycontent);
			   }  
			}
			System.out.println("Content");
			//outputSortedC(map);
			return map;	
		}

		
		 Map<String, List<CfUser>>  groupUsersByEntityName(String _entityName){
			 
		
			 Map<String, List<CfUser>> map = new HashMap<String, List<CfUser>>();
				for (CfAction _myaction :allActions) {
					for(CfUser _user: _myaction.getCfUsers()){
						   String  key="";
						   
						   if(_entityName.equalsIgnoreCase(CommonFormatStrings.A_V_ROLE)){
							   
							   key=_user.getrole();
							   if (map.get(key) == null) {
								      map.put(key, new ArrayList<CfUser>());
								   }
								   map.get(key).add(_user);

						   }
						   
						   else if(_entityName.equalsIgnoreCase(CommonFormatStrings.A_V_User)){
							   
							   key=_user.getid();
							   if (map.get(key) == null) {
								      map.put(key, new ArrayList<CfUser>());
								   }
								   map.get(key).add(_user);

						   }
				
				}
					}
				
				
				
				
				return map;	 
				   	
			 
		 }
		
		 Map<String, List<CfAction>> groupActionTypesByEntityName(String _entityName){
			
			Map<String, List<CfAction>> map = new HashMap<String, List<CfAction>>();
			
			for (CfAction myaction : allActions) {
				
				
			   String  key="";
			   
			   if(_entityName.equalsIgnoreCase(CommonFormatStrings.A_V_Type)){
				   
				   key=myaction.getCfActionType().getType();
				   if (map.get(key) == null) {
					      map.put(key, new ArrayList<CfAction>());
					   }
					   map.get(key).add(myaction);

			   }
			   
			   else if(_entityName.equalsIgnoreCase(CommonFormatStrings.A_V_Classification)){
	   
				   key=myaction.getCfActionType().getClassification();
				   if (map.get(key) == null) {
					      map.put(key, new ArrayList<CfAction>());
					   }
					   map.get(key).add(myaction);

			   }  
			   
			   
			   
			   else if(_entityName.equalsIgnoreCase(CommonFormatStrings.A_V_SUCCED)){
				   
				   key=myaction.getCfActionType().getSucceed();
				   if (map.get(key) == null) {
					      map.put(key, new ArrayList<CfAction>());
					   }
					   map.get(key).add(myaction);

			   }  

			   
			}

			//outputSortedA(map);
			return map;	
		}
		
		
		 
	
		 void ouputValues() {
			
			System.out.println("Object count:"+allObjects.size());
			System.out.println("Content count:"+allContents.size());
			for(CfObject o : allObjects){
			System.out.println("Oaction:"+o.getActionTime());
			//System.out.println("Ouser:"+o.ActionUser.getid());
			
			}
			for(CfContent o : allContents){
				System.out.println("Caction:"+o.getActionTime());
				//System.out.println("Cuser:"+o.ActionUser.getid());
				
				}
				
			}
			public void outputSorted(Map<String, List<CfObject>> data){
				
				for(String mykey:data.keySet()){
					
				System.out.println("SortKey:"+mykey);
				System.out.println("ItemCount:"+data.get(mykey).size());
					
				}
				
			}
			
			
 void outputSortedA(Map<String, List<CfAction>> data){
				
				for(String mykey:data.keySet()){
					
				System.out.println("SortKey:"+mykey);
				System.out.println("ItemCount:"+data.get(mykey).size());
					
				}
				
			}
		public void outputSortedC(Map<String, List<CfContent>> data){
				
				for(String mykey:data.keySet()){
					
				System.out.println("SortKey:"+mykey);
				System.out.println("ItemCount:"+data.get(mykey).size());
					
				}
				
			}
		
		
		 public ActionMaintenance getActionMaintenance(){
				
				return maintenance;
			}

		 
		public ListStore<EntitiesComboBoxModel> getComboBoxEntities(){
			sliptActions(false);
			 return toComboBoxEntities(getIndicatorEntities());
		 }
		 
			ListStore<EntitiesComboBoxModel> toComboBoxEntities(List<IndicatorEntity>  _entityList) {
				ListStore<EntitiesComboBoxModel>  _comboBoxModelList = new ListStore<EntitiesComboBoxModel>();
			    for(IndicatorEntity _ent: _entityList){
			    	EntitiesComboBoxModel _comboBoxItem=new EntitiesComboBoxModel(_ent);
			    	_comboBoxModelList.add(_comboBoxItem);
			    	
			    	
			    }
			    return  _comboBoxModelList;
			  }
	

}
