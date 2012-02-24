/*
 * Ext GWT 2.2.5 - Ext for GWT
 * Copyright(c) 2007-2010, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package com.analysis.client.datamodels;


import com.extjs.gxt.ui.client.data.BaseModel;


public class ExtendedIndicatorFilterItem extends BaseModel {

	
  public ExtendedIndicatorFilterItem() {
  }

  
  public ExtendedIndicatorFilterItem(String _property, String _value,String _type) {
    set("property", _property);
    set("value", _value);
    set("filtertype", _type);
  }


  public String getProperty(){
	  
	  return (String) get("property");  
  }
  
  public void setProperty(String _property){
	  set("property",_property); 
  }
  


  public String getValue() {
    return (String) get("value");
  }
  
  public void setValue(String _value){
	  
	  set("value", _value);
  }

  
  public void setType(String _type){
	  
	  set("filtertype", _type);
  }
  public String getType() {
	    return (String) get("filtertype");
	  }
   
  
  public String toString() {
    return getProperty();
  }



}
