/*
 * Ext GWT 2.2.5 - Ext for GWT
 * Copyright(c) 2007-2010, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package com.analysis.client.datamodels;


import com.extjs.gxt.ui.client.data.BaseModel;


public class ExtendedActionFilter extends BaseModel {

  public ExtendedActionFilter() {
  }

  public ExtendedActionFilter(String _property, String _value) {
    set("property", _property);
    set("value", _value);    
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

   
  public String toString() {
    return getProperty();
  }



}
