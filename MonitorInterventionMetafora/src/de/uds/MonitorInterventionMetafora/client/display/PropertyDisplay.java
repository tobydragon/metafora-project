package de.uds.MonitorInterventionMetafora.client.display;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.allen_sauer.gwt.log.client.Log;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;



import de.uds.MonitorInterventionMetafora.client.monitor.datamodel.PropertyGridModel;
import de.uds.MonitorInterventionMetafora.client.monitor.filter.FilterGridRow;

import de.uds.MonitorInterventionMetafora.shared.commonformat.CfProperty;

public class PropertyDisplay  extends ContentPanel{

Map<String, CfProperty> properties;

Grid<PropertyGridModel> grid;

ListStore<PropertyGridModel> propertiesStore;
	
public PropertyDisplay(Map<String, CfProperty> properties,String Header) {
	
	this.properties=properties;
	propertiesStore=getStore(properties);
	grid= new Grid<PropertyGridModel>(propertiesStore,getColumnModel());
	grid.setWidth(540);
	grid.setHeight(155);
	grid.setBorders(true);
	this.setHeading(Header);
	this.setWidth(547);
	this.setHeight(160);
	this.add(grid);
	this.setCollapsible(true);
	
	if(getURLs().size()>0){
		this.setHeight(432);
		URLDisplay urlDisplay=new URLDisplay(getURLs());
		this.add(urlDisplay);
	}
}


public PropertyDisplay(Map<String, CfProperty> properties,String Header,PropertyGridModel description) {
	
	this.properties=properties;
	propertiesStore=getStore(properties);
	propertiesStore.add(description);
	grid= new Grid<PropertyGridModel>(propertiesStore,getColumnModel());
	grid.setWidth(540);
	grid.setHeight(155);
	grid.setBorders(true);
	this.setHeading(Header);
	this.setWidth(547);
	this.setHeight(160);
	this.add(grid);
	this.setCollapsible(true);
	
	if(getURLs().size()>0){
		this.setHeight(432);
		URLDisplay urlDisplay=new URLDisplay(getURLs());
		this.add(urlDisplay);
	}
}



 ListStore<PropertyGridModel> getStore(Map<String, CfProperty> properties){
	 ListStore<PropertyGridModel> store=new ListStore<PropertyGridModel>();
	 
	 for(String key:properties.keySet()){
		 
		 PropertyGridModel model=new PropertyGridModel(properties.get(key).getName(), properties.get(key).getValue());
		 store.add(model);
		 
	 }
	 
	return store;
}

 List<String> getURLs(){
	 List<String> urls=new ArrayList<String>();
	 
	 for(String key:properties.keySet()){
		 
		 if(properties.get(key).getName().toLowerCase().contains("url")){
			 
		
			 urls.add(properties.get(key).getValue());
			 
			 
		 }
	 }

		
	 return urls;
	 
 }
ColumnModel getColumnModel(){
ColumnConfig propertyName = new ColumnConfig("PropertyName", "Property", 261);
propertyName.setHeader("Property");
ColumnConfig propertyValue = new ColumnConfig("Value", "Value", 261);
propertyValue.setHeader("Value");  
List<ColumnConfig> config = new ArrayList<ColumnConfig>();
config.add(propertyName);
config.add(propertyValue);
ColumnModel cm = new ColumnModel(config);
return cm;
}










}
