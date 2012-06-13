package de.uds.MonitorInterventionMetafora.client.display;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.sencha.gxt.widget.core.client.container.HtmlLayoutContainer;



import de.uds.MonitorInterventionMetafora.client.monitor.datamodel.PropertyGridModel;
import de.uds.MonitorInterventionMetafora.client.monitor.filter.FilterGridRow;

import de.uds.MonitorInterventionMetafora.shared.commonformat.CfObject;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfProperty;

public class CfObjectDisplay  extends ContentPanel{

List<CfObject> objects;
private TabPanel  objectDisplayTab;
	
public CfObjectDisplay(List<CfObject> objects) {
	this.objects=objects;
	objectDisplayTab= new TabPanel();
	objectDisplayTab.setAnimScroll(true);
	objectDisplayTab.setTabScroll(true);
	objectDisplayTab.setCloseContextMenu(true);
	objectDisplayTab.setWidth(555);
	objectDisplayTab.setHeight(470);
	renderTabPanel();
	this.setWidth(547);
	this.setHeight(550);
	this.setCollapsible(true);
	this.setHeading("Indicator Objects");
	this.add(objectDisplayTab);
	
	
	
}


void renderTabPanel(){
	int index=1;
	for(CfObject object:objects){
		 PropertyDisplay dispaly=new PropertyDisplay(object.getProperties(),"Object ID:"+object.getId());
		 dispaly.setCollapsible(true);
		 TabItem item=new TabItem(index +". Object");
		 item.add(dispaly);
		 index++;
		
		 item.setHeight(535);
		 item.setWidth(550);
		 objectDisplayTab.add(item);
	
		
	}
}








}
