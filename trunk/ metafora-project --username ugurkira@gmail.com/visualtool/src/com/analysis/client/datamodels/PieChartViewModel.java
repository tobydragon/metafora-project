package com.analysis.client.datamodels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.analysis.client.communication.server.ActionMaintenance;

import com.analysis.shared.commonformat.CfAction;
import com.analysis.shared.commonformat.CfContent;
import com.analysis.shared.commonformat.CfObject;
import com.analysis.shared.commonformat.CfUser;
import com.analysis.shared.commonformat.CommonFormatStrings;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;

public class PieChartViewModel {
	
	
	 List<CfAction> _allActions=null;
	 List<CfObject> _allObjects=null;
	 List<CfContent> _allContents=null;
	Map<String, List<CfContent>>  _groupedContent=null;
	Map<String, List<CfObject>>   _groupedObject=null;
	Map<String, List<CfAction>>   _groupedAction=null;
	
	Map<Integer, String>subsectionProperty = null;
	Map<Integer, String>subsectionValue =null;
	Map<String, String> activeFilters =null;
	
	
	public PieChartViewModel(){
	
		_allActions= new ArrayList<CfAction>();
		_allObjects=new ArrayList<CfObject>();
		_allContents=new ArrayList<CfContent>();
		activeFilters = new HashMap<String, String>();
		subsectionValue= new HashMap<Integer, String>();
		subsectionProperty=new HashMap<Integer, String>();
		sliptActions();
		
	}
	
	
	
	
	void sliptActions(){
		List<CfAction> _activecfActions=new ArrayList<CfAction>();
		
		_activecfActions=ActionMaintenance._activecfActions;
		_allActions=_activecfActions;
		for(CfAction _action:_activecfActions){
			
			for(CfObject _obj:_action.getCfObjects()){
				
				_obj.setActionTime(_action.getTime());
				_allObjects.add(_obj);
			}
			
			CfContent _cont=_action.getCfContent();
			_cont.setActionTime(_action.getTime());			
			_allContents.add(_cont);
			
			
		}
		
		
	}
	
	
	public DataTable getPieChartData(String myType,String myItem){
		
		
		DataTable data = DataTable.create();
		
		if(myType.equalsIgnoreCase("") || myType==null){
			return null;
		}
		else if(myItem.equalsIgnoreCase("") || myItem==null){
			
			return null;
		}
		
		
		if(myType.equalsIgnoreCase(CommonFormatStrings.O_OBJECT)){
			_groupedObject=new HashMap<String, List<CfObject>>();
			
			_groupedObject=groupObjectByProperty(myItem);
			
			   data.addColumn(ColumnType.STRING, "Task");
			    data.addColumn(ColumnType.NUMBER, "Hours per Day");
			    data.addRows(_groupedObject.size());
			    int index=0;
			    for(String key:_groupedObject.keySet()){
			    data.setValue(index, 0, key);
			    subsectionProperty.put(index, myItem);
			    subsectionValue.put(index, key);
			    data.setValue(index, 1, _groupedObject.get(key).size());
			    index++;
			    }
						
		}
		else if(myType.equalsIgnoreCase(CommonFormatStrings.C_CONTENT)){
			_groupedContent=new HashMap<String, List<CfContent>>(); 
			_groupedContent=groupContentByProperty(myItem);	
			    data.addColumn(ColumnType.STRING, "Task");
			    data.addColumn(ColumnType.NUMBER, "Hours per Day");
			    data.addRows(_groupedContent.size());
			    int index=0;
			    for(String key:_groupedContent.keySet()){
			    data.setValue(index, 0, key);
			    subsectionProperty.put(index, myItem);
			    subsectionValue.put(index, key);
			    data.setValue(index, 1, _groupedContent.get(key).size());
			    index++;
		}
	}
		
		else if(myType.equalsIgnoreCase(CommonFormatStrings.A_Action)){
			_groupedAction=new HashMap<String, List<CfAction>>(); 
			_groupedAction=groupActionByProperty(myItem);
			
			    data.addColumn(ColumnType.STRING, "Task");
			    data.addColumn(ColumnType.NUMBER, "Hours per Day");
			    data.addRows(_groupedAction.size());
			    int index=0;
			    for(String key:_groupedAction.keySet()){
			    data.setValue(index, 0, key);
			    subsectionProperty.put(index, myItem);
			    subsectionValue.put(index, key);
			    data.setValue(index, 1, _groupedAction.get(key).size());
			    index++;
		}
	}

	 
	    return data;
		
	}
	
	
	public void addActiveFilter(String _key){
		
		
		activeFilters.put(_key, _key);
		
	}
	
	
	public boolean isInFilterList(String _key){
		
		return activeFilters.containsKey(_key);
		
	}
	public String getSubSectionProperty(int _key){
		
		if(subsectionProperty.containsKey(_key))
		return subsectionProperty.get(_key);
		return "";
	}
	
public String getSubSectionValue(int _key){
		
		if(subsectionValue.containsKey(_key))
		return subsectionValue.get(_key);
		return "";
	}
	
	
	public Map<String, List<CfObject>> groupObjectByProperty(String property){
		
		Map<String, List<CfObject>> map = new HashMap<String, List<CfObject>>();
		for (CfObject myobject :_allObjects) {
		   
		   String  key="";
		   if(myobject.getProperties().containsKey(property)){
			   
			key=myobject.getPropertyValue(property);
				    
		   if (map.get(key) == null) {
			   map.put(key, new ArrayList<CfObject>());
		   }
		   
		   map.get(key).add(myobject);
		   }
		}
		outputSorted(map);
		return map;	
	}	
	
	
		
		public Map<String, List<CfContent>> groupContentByProperty(String property){
			
			Map<String, List<CfContent>> map = new HashMap<String, List<CfContent>>();
			for (CfContent mycontent :_allContents) {
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
			outputSortedC(map);
			return map;	
		}

		
		
		public Map<String, List<CfAction>> groupActionByProperty(String property){
			
			Map<String, List<CfAction>> map = new HashMap<String, List<CfAction>>();
			
			for (CfAction myaction : _allActions) {
				
				
			   String  key="";
			   
			   if(property.equalsIgnoreCase(CommonFormatStrings.A_V_Type)){
				   
				   key=myaction.getCfActionType().getType();
				   if (map.get(key) == null) {
					      map.put(key, new ArrayList<CfAction>());
					   }
					   map.get(key).add(myaction);

			   }
			   
			   else if(property.equalsIgnoreCase(CommonFormatStrings.A_V_Classification)){
	   
				   key=myaction.getCfActionType().getClassification();
				   if (map.get(key) == null) {
					      map.put(key, new ArrayList<CfAction>());
					   }
					   map.get(key).add(myaction);

			   }  
			   
			   else if(property.equalsIgnoreCase(CommonFormatStrings.A_V_User)){
				   
				  for(CfUser user:myaction.getCfUsers()){
					  
					  System.out.println("User:"+user.getid());
					  key=user.getid();
					  if (map.get(key) == null) {
						  System.out.println("New user :"+user.getid() +" is found added a new list");
					      map.put(key, new ArrayList<CfAction>());
					   }
					   map.get(key).add(myaction);
				  }

			   }  
			   		   
			   
			   
			   
			}

			outputSortedA(map);
			return map;	
		}
		
		
	
		public void ouputValues() {
			
			System.out.println("Object count:"+_allObjects.size());
			System.out.println("Content count:"+_allContents.size());
			for(CfObject o : _allObjects){
			System.out.println("Oaction:"+o.getActionTime());
			//System.out.println("Ouser:"+o.ActionUser.getid());
			
			}
			for(CfContent o : _allContents){
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
			
			
public void outputSortedA(Map<String, List<CfAction>> data){
				
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
	

}
