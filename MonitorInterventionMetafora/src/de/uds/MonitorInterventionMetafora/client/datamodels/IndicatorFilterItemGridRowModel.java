/*
 * Ext GWT 2.2.5 - Ext for GWT
 * Copyright(c) 2007-2010, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package de.uds.MonitorInterventionMetafora.client.datamodels;




import com.extjs.gxt.ui.client.data.BaseModel;


public class IndicatorFilterItemGridRowModel extends BaseModel {


	
  /**
	 * 
	 */
	private static final long serialVersionUID = -3416203885980295041L;


public IndicatorFilterItemGridRowModel() {
  }

  
  public IndicatorFilterItemGridRowModel(String _property, String _value,String _type,String _displayText) {
	  set("filtertype", _type);
	  set("displaytext", _displayText);
	  set("property", _property);
      set("value", _value);
   
   
  }


  
  public void setDisplayText(String _displayText){
	  
	  set("displaytext", _displayText);
	  
  }
  
  public String getDisplayText(){
	  
	  return (String) get("displaytext");   
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
