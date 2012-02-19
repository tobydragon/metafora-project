package com.analysis.client.components;

import java.util.HashMap;
import java.util.Map;


import com.analysis.client.view.grids.ExtendedGroupedGrid;
import com.google.gwt.core.client.JsArray;

//import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Button;

import com.google.gwt.user.client.ui.HTML;

import com.google.gwt.user.client.ui.PopupPanel;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.Selection;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.events.SelectHandler;

import com.google.gwt.visualization.client.visualizations.corechart.PieChart;
import com.google.gwt.visualization.client.visualizations.corechart.PieChart.PieOptions;


public class Annotater {

	//private   
	
	Map<Integer, String> subsection;
	public Annotater(){
		
		subsection = new HashMap<Integer, String>();
		
	}
	
	
	 public Widget createPieChart() {
		    /* create a datatable */
		    DataTable data = DataTable.create();
		    data.addColumn(ColumnType.STRING, "Task");
		    data.addColumn(ColumnType.NUMBER, "Hours per Day");
		    data.addRows(3);
		    data.setValue(0, 0, "UserEvents");
		    subsection.put(0, "UserEvents");
		    data.setValue(0, 1, 2);
		    data.setValue(1, 0, "ObjectEvents");
		    subsection.put(1,"ObjectEvents");
		    data.setValue(1, 1, 5);
		    data.setValue(2, 0, "MonitorEvents");
		    subsection.put(2,"MonitorEvents");
		    data.setValue(2, 1, 1);
		    //data.setValue(3, 0, "Referencing");
		    //subsection.put(3,"Referencing");
		    //data.setValue(3, 1, 1);
		   // data.setValue(4, 0, "Listening");
		    //subsection.put(4,"Listening");
		    //data.setValue(4, 1, 1);

		    /* create pie chart */

		    PieOptions options = PieChart.createPieOptions();
		    options.setWidth(500);
		    options.setHeight(400);
		    options.set3D(true);
		   
		    options.setTitle("Activity Overview");
		    
		    PieChart pie = new PieChart(data, options);
		    
		    
		    pie.addSelectHandler(createSelectHandler(pie));
		    
		    return  pie;
		    //return new PieChart(data, options);
		  }
	 
	 private SelectHandler createSelectHandler(final PieChart chart) {
		    return new SelectHandler() {
		      @Override
		      public void onSelect(SelectEvent event) {
		     
		    	   StringBuffer b = new StringBuffer();
		    	   int selection=-1;
		    	    JsArray<Selection> s = chart.getSelections();
		    	    for (int i = 0; i < s.length(); ++i) {
		    	      if (s.get(i).isCell()) {
		    	        b.append(" cell ");
		    	        b.append(s.get(i).getRow());
		    	        b.append(":");
		    	        b.append(s.get(i).getColumn());
		    	      } else if (s.get(i).isRow()) {
		    	       // b.append(" row ");
		    	      selection=s.get(i).getRow();
		    	      } else {
		    	        b.append(" column ");
		    	        b.append(s.get(i).getColumn());
		    	      }
		    	    }
		    	    
		        
		        
		              final PopupPanel p = new PopupPanel();
		              p.setStyleName("demo-popup", true);
		            
		             VerticalPanel vp=new VerticalPanel();
		     
		              
		              HTML contents = new HTML("");
		              contents.setWidth("400px");
		              
		              ClickListener   listener = new ClickListener()
				        {
				            public void onClick(Widget sender)
				            {
				               p.hide();
				            }
				        };
				        
				        
				      Button button = new Button("Close", listener);
		             // vp.add(contents);
		              String type=subsection.get(selection);
		              if(type==null)
		            	  type="";
		            
		              ExtendedGroupedGrid aa =new ExtendedGroupedGrid(type);
		              aa.setStyleName("grid", true);
		              button.setStyleName("button", true);
		              button.setWidth("200px");
		              vp.add(aa);
		              vp.add(button);
		              vp.setStyleName("verticalpanel", true);
		           
		              p.setWidget(vp); 
		               p.center();
		              p.show();
		           
		        
		        
		        
		        //popup.show();
		      // Window.alert(message);
		      }
		    };
		  }

	  
	
}
