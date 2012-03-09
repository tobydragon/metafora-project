/*
 * Ext GWT 2.2.5 - Ext for GWT
 * Copyright(c) 2007-2010, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package de.uds.visualizer.shared.interactionmodels;




import com.extjs.gxt.ui.client.data.BaseModel;


public class IndicatorEntity extends BaseModel {


	
  /**
	 * 
	 */
	private static final long serialVersionUID = -3416203885980295041L;

	private String entityName="";
	private String displayText="";

	private FilterItemType type;
	private String value="";
public IndicatorEntity() {
  }

  
  public IndicatorEntity(String _entityName, String _value,FilterItemType _type) {
   
	  entityName=_entityName;
	  value=_value;
	  type=_type;
	  
  }


  
  public String getEntityName(){
	  
	  return entityName; 
  }
  
  public void setEntityName(String _entityName){
	  entityName=_entityName; 
  }
  


  public String getValue() {
    return value;
  }
  
  public void setValue(String _value){
	  
	  value= _value;
  }

  
  public void setType(FilterItemType _type){
	  
	  type= _type;
	  }
  public FilterItemType getType() {
	    return type;
	  }
   
  public void setDisplayText(String _text){
	  
	  displayText=_text;
  }
  
  public String getDisplayText(){
	  
	  return displayText;
  }
  
 


}
