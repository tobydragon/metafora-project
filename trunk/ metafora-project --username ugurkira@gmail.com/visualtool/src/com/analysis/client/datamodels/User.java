/*
 * Ext GWT 2.2.5 - Ext for GWT
 * Copyright(c) 2007-2010, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package com.analysis.client.datamodels;

import java.util.Date;

import com.extjs.gxt.ui.client.data.BaseModel;
import com.extjs.gxt.ui.client.util.DateWrapper;

public class User extends BaseModel {

  public User() {
  }

  public User(String name, String description,String time, String date, String groupingItem) {
    set("name", name);
    set("description", description);
    set("time",time);
    set("date", date);
    //set("groupingItem", groupingItem);
    
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


  public String getName() {
    return (String) get("name");
  }
  
  public void setName(String name){
	  
	  set("name", name);
  }

 
  //public void setgroupingItem(String groupingItem) {
    //set("groupingItem", groupingItem);
  //}

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
