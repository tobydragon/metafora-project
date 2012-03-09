/*
 * Ext GWT 2.2.5 - Ext for GWT
 * Copyright(c) 2007-2010, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package de.uds.visualizer.client.datamodels;

import com.extjs.gxt.ui.client.data.BaseModelData;

import de.uds.visualizer.shared.interactionmodels.FilterItemType;
import de.uds.visualizer.shared.interactionmodels.IndicatorEntity;

public class PieChartComboBoxModel extends BaseModelData {

  /**
	 * 
	 */
	private static final long serialVersionUID = -8119756981959764189L;

public PieChartComboBoxModel() {

  }


public PieChartComboBoxModel(IndicatorEntity _entity) {
    setEntityName(_entity.getEntityName());
    setDisplayText(_entity.getDisplayText());
    setItemType(_entity.getType());
    setValue(_entity.getValue());
}
    

  public PieChartComboBoxModel(String _entityName, String _displayText,FilterItemType _itemType, String _value) {
    setEntityName(_entityName);
    setDisplayText(_displayText);
    setItemType(_itemType);
    setValue(_value);
    
  }

 
public void setValue(String _value){
	set("value",_value);
	
}

public String getValue(){
	
	return get("value");
	
}
public void setItemType(FilterItemType _itemType){
	
	set("itemtype",_itemType.toString());
}

public FilterItemType getItemType(){
	
	return FilterItemType.valueOf(get("itemtype").toString());
} 
  public String getEntityName() {
    return get("entityname");
  }

  public void setEntityName(String _entityName) {
    set("entityname", _entityName);
  }

  
  
  public String getDisplayText() {
    return get("displaytext");
  }

  public void setDisplayText(String _displayText) {
    set("displaytext", _displayText);
  }
  
  
  

}
