package de.uds.MonitorInterventionMetafora.client.display;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.allen_sauer.gwt.log.client.Log;
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

import de.uds.MonitorInterventionMetafora.shared.commonformat.CfProperty;

public class URLDisplay  extends VerticalPanel{

List<String> urlList;
private TabPanel  urlDisplayTab;
	
public URLDisplay(List<String> urlList) {
	this.urlList=urlList;
	urlDisplayTab= new TabPanel();
	urlDisplayTab.setAnimScroll(true);
	urlDisplayTab.setTabScroll(true);
	urlDisplayTab.setCloseContextMenu(true);
	urlDisplayTab.setWidth(540);
	urlDisplayTab.setHeight(235);
	urlList=new ArrayList<String>();
	renderTabPanel();
	//this.setHeading("URL Views");
	this.setWidth(547);
	this.setHeight(245);
	this.add(urlDisplayTab);
	
	
	
}


void renderTabPanel(){
	int index=1;
	for(String url:urlList){
		 HtmlLayoutContainer htmlPanel = new HtmlLayoutContainer("<iframe width='400' height='225'  src='"+url+"'></iframe> ");
		 TabItem item=new TabItem(index +". URL View");
		 index++;
		 item.add(htmlPanel);
		 item.setHeight(245);
		 
		 Log.info("URL View is added for:"+url);
		 urlDisplayTab.add(item);
	
		
	}
}








}
