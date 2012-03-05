package com.analysis.client.communication.models;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import com.analysis.client.components.ActionContent;
import com.analysis.client.components.ActionObject;

import com.analysis.client.view.grids.IndicatorGridRowItem;
import com.analysis.client.xml.GWTXmlFragment;
import com.analysis.shared.communication.objects_old.CfAction;
import com.analysis.shared.communication.objects_old.CfContent;
import com.analysis.shared.communication.objects_old.CfInteractionData;
import com.analysis.shared.communication.objects_old.CfObject;
import com.analysis.shared.communication.objects_old.CfUser;
import com.analysis.shared.communication.objects_old.CommonFormatStrings;
import com.analysis.shared.interactionmodels.IndicatorFilterItem;
import com.analysis.shared.interactionmodels.IndicatorFilter;
import com.analysis.shared.utils.GWTDateUtils;
import com.google.gwt.i18n.client.DateTimeFormat;

public class DataModel {
	public static List<CfAction> Actions;
	public static List<ActionObject> allObjects;
	public static List<ActionContent> allContents;
	public static Map<String,String> activefilters=new HashMap<String,String>();

	public DataModel(){
		
		
	}
	
	
	public static Map<String,String> getActiveFilters(){
		
		return activefilters;
	}
	
	public static void initializeInterActionHistory(String data){
		
		Actions= new ArrayList<CfAction>();
	     
	     GWTXmlFragment xf=new GWTXmlFragment();
	     
	    Actions=xf.processActions(data);
		allObjects=new ArrayList<ActionObject>();
		allContents=new ArrayList<ActionContent>();
	    processData();
	}
	
	
	
	
	public void ouputValues() {
		
	System.out.println("Object count:"+allObjects.size());
	System.out.println("Content count:"+allContents.size());
	for(ActionObject o : allObjects){
	System.out.println("Oaction:"+o.time);
	//System.out.println("Ouser:"+o.ActionUser.getid());
	
	}
	for(ActionContent o : allContents){
		System.out.println("Caction:"+o.time);
		//System.out.println("Cuser:"+o.ActionUser.getid());
		
		}
		
	}
	public void outputSorted(Map<String, List<ActionObject>> data){
		
		for(String mykey:data.keySet()){
			
		System.out.println("SortKey:"+mykey);
		System.out.println("ItemCount:"+data.get(mykey).size());
			
		}
		
	}
	
public void outputSortedC(Map<String, List<ActionContent>> data){
		
		for(String mykey:data.keySet()){
			
		System.out.println("SortKey:"+mykey);
		System.out.println("ItemCount:"+data.get(mykey).size());
			
		}
		
	}
	
	public List<ActionObject>  getObjects(){
					
	return allObjects;
		}
	
	
	public List<ActionContent>  getContents(){
		
		return allContents;
			}
	
	
	
	static void processData(){
		
		int index=0;
		for (CfAction action : Actions){

//			CfUser user=action.getCfUsers().get(index);
			//index++;
			for(CfObject myobject : action.getCfObjects()){				
		
				ActionObject ao=new ActionObject();
				ao.actionObjectProperties=myobject.getObjectProperties();
				ao.description=action.getDescription();
				ao.time=action.getTime();
				//ao.ActionUsers.add(user);															
				
				ao.ActionUsers=action.getCfUsers();
				allObjects.add(ao);		
			}
			
			CfContent content=action.getCfContent();
			
			ActionContent ac=new ActionContent();
			
			ac.actionContentProperties=content.getContentProperties();
			//ac.ActionUsers.add(user);
			
			ac.ActionUsers=action.getCfUsers();
			ac.time=action.getTime();
			ac.Description=action.getCfContent().getDescription();
			allContents.add(ac);	
				
	}
		
	}

	public Map<String, List<ActionObject>> groupObjectByProperty(String property){
	
	Map<String, List<ActionObject>> map = new HashMap<String, List<ActionObject>>();
	for (ActionObject myobject :getObjects()) {
	   
	   String  key="";
	   if(myobject.actionObjectProperties.containsKey(property)){
		   
		key=myobject.actionObjectProperties.get(property).getValue();
			    
	   if (map.get(key) == null) {
	      map.put(key, new ArrayList<ActionObject>());
	   }
	    map.get(key).add(myobject);
	   
	   }
	}

	outputSorted(map);
	
	return map;	
}	
	
	public Map<String, List<ActionContent>> groupContentByProperty(String property){
		
		Map<String, List<ActionContent>> map = new HashMap<String, List<ActionContent>>();
		for (ActionContent myobject :getContents()) {
			 String  key="";
		   if(myobject.actionContentProperties.containsKey(property)){
		   key=myobject.actionContentProperties.get(property).getValue();
		   
		   if (map.get(key) == null) {
		      map.put(key, new ArrayList<ActionContent>());
		   }
		   map.get(key).add(myobject);
		   }  
		}
		System.out.println("Content");
		outputSortedC(map);
		return map;	
	}

	
	
	public Map<String, List<CfAction>> groupActionByProperty(String property){
		
		Map<String, List<CfAction>> map = new HashMap<String, List<CfAction>>();
		
		
		for (CfAction myaction : Actions) {
			
			
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
				  
				  key=user.getid();
				  if (map.get(key) == null) {
				      map.put(key, new ArrayList<CfAction>());
				   }
				   map.get(key).add(myaction);
			  }

		   }  
		   		   
		   
		   
		   
		}

		return map;	
	}
	
	
	
	

	
	public static List<IndicatorGridRowItem> getIndicatorList(Map<String, IndicatorFilterItem> _filterItems){
		
		List<IndicatorGridRowItem> indicators=new ArrayList<IndicatorGridRowItem>();
		
		for(CfAction ac: Actions){
	
			//ac.g
		
			for(String _key:_filterItems.keySet()){
			IndicatorFilterItem	 activeFilterItem=_filterItems.get(_key);
			IndicatorGridRowItem  myindicator=new IndicatorGridRowItem();
			
			if(activeFilterItem.getType().equalsIgnoreCase(CommonFormatStrings.CONTENT_STRING)){
							
			if(ac.getCfContent().getContentProperties().containsKey(_key)){
			if(ac.getCfContent().getContentProperties().get(_key).getValue().equalsIgnoreCase(activeFilterItem.getValue())){
				
			String usersString="";
			   for(CfUser u : ac.getCfUsers()){
        		   usersString=usersString+" - "+u.getid();
        	   }
        	   
        	   
        	   myindicator.setName(usersString.substring(2,usersString.length()));
        	   myindicator.setActionType(ac.getCfActionType().getType());
        	   myindicator.setClassification(ac.getCfActionType().getClassification());
        	  
        	   myindicator.setDescription(ac.getDescription());     	   
        	   myindicator.setTime(GWTDateUtils.getTime(ac.getTime()));
        	   myindicator.setDate(GWTDateUtils.getDate(ac.getTime()));
        	   indicators.add(myindicator);	
			}
			}
			}
			
			
			
			else if(activeFilterItem.getType().equalsIgnoreCase(CommonFormatStrings.OBJECT_STRING)){
				
		
				
				for(CfObject ob: ac.getCfObjects()){
					
					if(ob.getObjectProperties().containsKey(_key)){
						
					if(ob.getObjectProperties().get(_key).getValue().equalsIgnoreCase(activeFilterItem.getValue())){
							
							
						String usersString="";
						   for(CfUser u : ac.getCfUsers()){
			        		   usersString=usersString+" - "+u.getid();
			        	   }
			        	   
			        	   
			        	   myindicator.setName(usersString.substring(2,usersString.length()));
			        	   myindicator.setActionType(ac.getCfActionType().getType());
			        	   myindicator.setClassification(ac.getCfActionType().getClassification());
			        	  
			        	   myindicator.setDescription(ac.getDescription());     	   
			        	   myindicator.setTime(GWTDateUtils.getTime(ac.getTime()));
			        	   myindicator.setDate(GWTDateUtils.getDate(ac.getTime()));
			        	   indicators.add(myindicator);	
							
						break;
						}
						
					}
					
					
					
					
				}				
				}
			
			
			else	if(activeFilterItem.getType().equalsIgnoreCase(CommonFormatStrings.ACTION_STRING)){
				
				if(activeFilterItem.getProperty().equalsIgnoreCase(CommonFormatStrings.A_V_User)){
			
				
					
					
					for(CfUser user:ac.getCfUsers()){
						
						if(user.getid().equalsIgnoreCase(activeFilterItem.getValue())){
							
							String usersString="";
							   for(CfUser u : ac.getCfUsers()){
				        		   usersString=usersString+" - "+u.getid();
				        	   }
				        	   
				        	   
				        	   myindicator.setName(usersString.substring(2,usersString.length()));
				        	   myindicator.setActionType(ac.getCfActionType().getType());
				        	   myindicator.setClassification(ac.getCfActionType().getClassification());
				        	  
				        	   myindicator.setDescription(ac.getDescription());     	   
				        	   myindicator.setTime(GWTDateUtils.getTime(ac.getTime()));
				        	   myindicator.setDate(GWTDateUtils.getDate(ac.getTime()));
				        	   indicators.add(myindicator);	
							break;
						}
				
						
					}

				}
				
				else if(activeFilterItem.getProperty().equalsIgnoreCase(CommonFormatStrings.ACTION_TYPE_STRING)){
					
					
					if(activeFilterItem.getValue().equalsIgnoreCase(ac.getCfActionType().getType())){
						
						
						
						String usersString="";
						   for(CfUser u : ac.getCfUsers()){
			        		   usersString=usersString+" - "+u.getid();
			        	   }
			        	   
			        	   
			        	   myindicator.setName(usersString.substring(2,usersString.length()));
			        	   myindicator.setActionType(ac.getCfActionType().getType());
			        	   myindicator.setClassification(ac.getCfActionType().getClassification());
			        	  
			        	   myindicator.setDescription(ac.getDescription());     	   
			        	   myindicator.setTime(GWTDateUtils.getTime(ac.getTime()));
			        	   myindicator.setDate(GWTDateUtils.getDate(ac.getTime()));
			        	   indicators.add(myindicator);	
						
					}
					
					
				}
				
				else if(activeFilterItem.getProperty().equalsIgnoreCase(CommonFormatStrings.A_V_Classification)){
					
					
					
					if(activeFilterItem.getValue().equalsIgnoreCase(ac.getCfActionType().getClassification())){
						
						
						String usersString="";
						   for(CfUser u : ac.getCfUsers()){
			        		   usersString=usersString+" - "+u.getid();
			        	   }
			        	   
			        	   
			        	   myindicator.setName(usersString.substring(2,usersString.length()));
			        	   myindicator.setActionType(ac.getCfActionType().getType());
			        	   myindicator.setClassification(ac.getCfActionType().getClassification());
			        	  
			        	   myindicator.setDescription(ac.getDescription());     	   
			        	   myindicator.setTime(GWTDateUtils.getTime(ac.getTime()));
			        	   myindicator.setDate(GWTDateUtils.getDate(ac.getTime()));
			        	   indicators.add(myindicator);	
						
					}
							
					
					
				}
				
				}
			
			
			
			
		}
			}
		
		return indicators;
		
	}
	
	
	
}
