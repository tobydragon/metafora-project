/*
 * Ext GWT 2.2.5 - Ext for GWT
 * Copyright(c) 2007-2010, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package de.uds.MonitorInterventionMetafora.client.view.grids;

import java.util.Date;

import com.extjs.gxt.ui.client.data.BaseModel;
import com.extjs.gxt.ui.client.util.DateWrapper;

public class IndicatorGridRowItem extends BaseModel {

  public IndicatorGridRowItem() {
  }

  public IndicatorGridRowItem(String name,String actiontype,String classification, String description,String time, String date, String groupingItem) {
    set("name", name);
    set("actiontype", actiontype);
    set("classification", classification);
    set("description", description);
    set("time",time);
    set("date", date);
    
    //set("groupingItem", groupingItem);
    
  }

public void setColor(String _color){
	
	set("color", _color);
}

public String getColor(){
	  
	  return (String) get("color");  
}


  public String getTime(){
	  
	  return (String) get("time");  
  }
  
  public void setTime(String time){
	  set("time",time); 
  }
  
  public void setDate(String date){
	  
	  set("date", date);
  }
  
  public String getDate(){
	  
	  return get("date");
	  
  }
 // public String getgroupingItem() {
   // return get("groupingItem");
  //}
  
//public void setgroupingItem(String groupingItem) {
  //set("groupingItem", groupingItem);
//}


  public String getName() {
    return (String) get("name");
  }
  
  public void setName(String name){
	  
	  set("name", name);
  }

public void setClassification(String classification){
	  
	  set("classification", classification);
  }

  public String getClassification(){
	  return (String) get("classification");
  }
  
  public String getActionType() {
	  return (String) get("actionType");  
	  }
	  
	  public void setActionType(String actiontype){
		  
		  set("actiontype", actiontype);
	  }
  

public String getDescription(){
	
	return get("description");
}
public void setDescription(String description){
	
	set("description", description);
}


  
  public String toString() {
    return getName();
  }



}
