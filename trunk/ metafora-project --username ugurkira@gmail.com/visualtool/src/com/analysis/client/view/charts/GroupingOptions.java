package com.analysis.client.view.charts;

import java.util.List;


import com.analysis.client.datamodels.PieChartComboBoxModel;
import com.analysis.shared.communication.objects_old.CfAction;
import com.analysis.shared.communication.objects_old.CommonFormatStrings;
import com.extjs.gxt.ui.client.store.ListStore;



public class GroupingOptions {
	
	
	
	public GroupingOptions(){
	
		
	}
	
	public static ListStore<PieChartComboBoxModel> getFilterTypes(){
		ListStore<PieChartComboBoxModel> properties = new ListStore<PieChartComboBoxModel>();
		properties.add(new PieChartComboBoxModel("1", CommonFormatStrings.C_CONTENT));
		properties.add(new PieChartComboBoxModel("2", CommonFormatStrings.O_OBJECT));
		properties.add(new PieChartComboBoxModel(CommonFormatStrings.A_V_Action, CommonFormatStrings.A_Action));
			
	    return properties;
	}
	//Move configuration to XML
	public static ListStore<PieChartComboBoxModel> getObjectProperties(){
		ListStore<PieChartComboBoxModel> properties = new ListStore<PieChartComboBoxModel>();
		properties.add(new PieChartComboBoxModel(CommonFormatStrings.O_V_MAP_ID, CommonFormatStrings.O_MAP_ID));
		properties.add(new PieChartComboBoxModel(CommonFormatStrings.O_V_ELEMENT_TYPE, CommonFormatStrings.O_ELEMENT_TYPE));
		properties.add(new PieChartComboBoxModel(CommonFormatStrings.O_V_TEXT, CommonFormatStrings.O_TEXT));
		properties.add(new PieChartComboBoxModel(CommonFormatStrings.O_V_USER_NAME, CommonFormatStrings.O_USER_NAME));		 
		return properties;
	}
	
	
	public  static ListStore<PieChartComboBoxModel>  getContentProperties(){
		ListStore<PieChartComboBoxModel> properties = new ListStore<PieChartComboBoxModel>();
		properties.add(new PieChartComboBoxModel(CommonFormatStrings.C_V_INDICATOR_TYPE, CommonFormatStrings.C_INDICATOR_TYPE));
		properties.add(new PieChartComboBoxModel(CommonFormatStrings.C_V_TOOL, CommonFormatStrings.C_TOOL));
		properties.add(new PieChartComboBoxModel(CommonFormatStrings.C_V_ANNOTATION_TYPE, CommonFormatStrings.C_ANNOTATION_TYPE));
		return properties;
	}
	
	public  static ListStore<PieChartComboBoxModel>  getActionProperties(){
		ListStore<PieChartComboBoxModel> properties = new ListStore<PieChartComboBoxModel>();
		properties.add(new PieChartComboBoxModel(CommonFormatStrings.A_V_Classification, CommonFormatStrings.A_Classification));
		properties.add(new PieChartComboBoxModel(CommonFormatStrings.A_V_Type, CommonFormatStrings.A_Type));		
		properties.add(new PieChartComboBoxModel(CommonFormatStrings.A_V_User, CommonFormatStrings.A_User));
		return properties;
	}
	
}
