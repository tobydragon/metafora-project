/*
 * Ext GWT 2.2.5 - Ext for GWT
 * Copyright(c) 2007-2010, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package com.analysis.client.datamodels;

import com.extjs.gxt.ui.client.data.BaseModelData;

public class PieChartComboBoxModel extends BaseModelData {

  public PieChartComboBoxModel() {

  }

  public PieChartComboBoxModel(String text, String name) {
    setText(text);
    setName(name);
    
  }

 

  public String getText() {
    return get("text");
  }

  public void setText(String text) {
    set("text", text);
  }

  public String getName() {
    return get("name");
  }

  public void setName(String name) {
    set("name", name);
  }

}
