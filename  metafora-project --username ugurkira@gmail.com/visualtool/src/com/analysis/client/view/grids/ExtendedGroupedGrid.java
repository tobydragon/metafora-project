/*
 * Ext GWT 2.2.5 - Ext for GWT
 * Copyright(c) 2007-2010, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package com.analysis.client.view.grids;

import java.util.ArrayList;
import java.util.List;


import com.analysis.client.TestData;

import com.analysis.client.datamodels.Indicator;
import com.analysis.client.resources.Resources;
import com.extjs.gxt.ui.client.store.GroupingStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridGroupRenderer;
import com.extjs.gxt.ui.client.widget.grid.GroupColumnData;
import com.extjs.gxt.ui.client.widget.grid.GroupingView;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Element;

public class ExtendedGroupedGrid extends LayoutContainer {

	String groupingItem="";
	List<Indicator> indicators;
	public ExtendedGroupedGrid(String _groupingItem){
		
		groupingItem=_groupingItem;
	}
	
	
public ExtendedGroupedGrid(String _groupingItem,List<Indicator> _indicator){
		
		groupingItem=_groupingItem;
		indicators=_indicator;
	}
	



public ExtendedGroupedGrid(List<Indicator> _indicator){
		
		indicators=_indicator;
	}

	
  @Override
  protected void onRender(Element parent, int index) {
    super.onRender(parent, index);
    setLayout(new FlowLayout(10));

    GroupingStore<Indicator> store = new GroupingStore<Indicator>();
  
    store.add(indicators);  
    store.groupBy("date");

    ColumnConfig username = new ColumnConfig("name", "User", 60);
    ColumnConfig description = new ColumnConfig("description", "Description", 60);
    ColumnConfig time = new ColumnConfig("time", "Time", 60);
    ColumnConfig date = new ColumnConfig("date", "Date", 20);
    date.setDateTimeFormat(DateTimeFormat.getFormat("MM/dd/y"));
    

    List<ColumnConfig> config = new ArrayList<ColumnConfig>();
    config.add(username);
    config.add(description);
    config.add(time);
    config.add(date);
   // config.add(groupingItem);

    final ColumnModel cm = new ColumnModel(config);

    GroupingView view = new GroupingView();
    view.setShowGroupedColumn(false);
    view.setForceFit(true);
    view.setGroupRenderer(new GridGroupRenderer() {
      public String render(GroupColumnData data) {
        String f = cm.getColumnById(data.field).getHeader();
        String l = data.models.size() == 1 ? "Item" : "Items";
        return f + ": " + data.group + " (" + data.models.size() + " " + l + ")";
      }
    });

    Grid<Indicator> grid = new Grid<Indicator>(store, cm);
    view.setShowGroupedColumn(true);
    grid.setView(view);
    grid.setBorders(true);
    

    ContentPanel panel = new ContentPanel();
    panel.setHeading("Event: "+groupingItem);
    panel.setIcon(Resources.ICONS.table());
    panel.setCollapsible(false);
    panel.setFrame(true);
    panel.setSize(700, 500);
    panel.setLayout(new FitLayout());
    panel.add(grid);
    grid.getAriaSupport().setLabelledBy(panel.getHeader().getId() + "-label");
    add(panel);
  }

}
