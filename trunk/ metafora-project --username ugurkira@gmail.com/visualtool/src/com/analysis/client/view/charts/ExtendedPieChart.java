package com.analysis.client.view.charts;



import java.util.ArrayList;

import java.util.Date;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.analysis.client.communication.resources.DataModel;
import com.analysis.client.components.ActionContent;
import com.analysis.client.components.ActionObject;
import com.analysis.client.datamodels.DefaultModel;
import com.analysis.client.datamodels.ExtendedIndicatorFilterItem;
import com.analysis.client.datamodels.Indicator;
import com.analysis.client.examples.charts.Showcase;
import com.analysis.client.utils.GWTDateUtils;
import com.analysis.client.view.grids.ExtendedFilterGrid;
import com.analysis.client.view.grids.ExtendedGroupedGrid;
import com.analysis.shared.communication.objects.CfAction;
import com.analysis.shared.communication.objects.CfObject;
import com.analysis.shared.communication.objects.CfUser;
import com.analysis.shared.communication.objects.CommonFormatStrings;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.Record;
import com.extjs.gxt.ui.client.util.Format;
import com.extjs.gxt.ui.client.widget.ComponentManager;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.Label;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.VerticalPanel;

import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.Selection;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.events.SelectHandler;

import com.google.gwt.visualization.client.visualizations.corechart.PieChart;
import com.google.gwt.visualization.client.visualizations.corechart.PieChart.PieOptions;

public class ExtendedPieChart extends VerticalPanel {
	
	Map<Integer, String>subsectionProperty = new HashMap<Integer, String>();
	Map<Integer, String>subsectionValue = new HashMap<Integer, String>();
	//Map<Integer, String>subsection = new HashMap<Integer, String>();
	public String Type="";
	public String Item="";
	public  PieChart pie=null;
	
	public ExtendedPieChart(){
		
		createFilterHeader();
	}

public ExtendedPieChart(String title){
	this.setId("interActionForm");
	this.add(new Label(title));
	createFilterHeader();
}
	


	void createFilterHeader(){
	this.setWidth(650);
	HorizontalPanel hp=new HorizontalPanel();
	
	
	 final ComboBox<DefaultModel> comboType = new ComboBox<DefaultModel>();
	    
	long l=(long) 1325841177864.0;
	 comboType.setEmptyText("Select a type");
	  
	  
	    
	    comboType.setDisplayField("name");
	    comboType.setValueField("text");
	    comboType.setWidth(150);
	    comboType.setEditable(false);
	 
	    comboType.setAutoHeight(true);
	    comboType.setId("comboType");
	    comboType.setStore(GroupingOptions.getFilterTypes());
	    comboType.setTypeAhead(true);
	    comboType.setTriggerAction(TriggerAction.ALL);
	  
	   
	    
	    hp.setWidth(600);
	    hp.add(new Label("Type:"));
	    hp.add(comboType);
	    
	    final ComboBox<DefaultModel> comboItem = new ComboBox<DefaultModel>();
	    comboItem.setEmptyText("Select filter type...");
	    comboItem.setDisplayField("name");
	    comboItem.setValueField("text");
	    	    
	    comboItem.setWidth(150);
	    comboItem.setEditable(false);
	 
	    comboItem.setAutoHeight(true);
	    comboItem.setId("comboType2");
	    
	    comboItem.setStore(new ListStore<DefaultModel>());
	    comboItem.setTypeAhead(true);
	    comboItem.setTriggerAction(TriggerAction.ALL);
	    
	    final SelectionChangedListener<DefaultModel> comboListenerItem =new SelectionChangedListener<DefaultModel>(){
	        @Override
	        public void selectionChanged(SelectionChangedEvent<DefaultModel> se) { 

	        
	        	  Item=comboItem.getValue().getText();
	        	
	        	
	     
	        }

	    };
	    
	    
	    
	    
	    SelectionChangedListener<DefaultModel> comboListener =new SelectionChangedListener<DefaultModel>(){
	        @Override
	        public void selectionChanged(SelectionChangedEvent<DefaultModel> se) { 

	        	DefaultModel vg = se.getSelectedItem();   
	        	
	            Record record = GroupingOptions.getObjectProperties().getRecord(vg);  
	            
	              String filter = record.getModel().get("name");
	              Type=filter;
	            
	              
	              
	              comboItem.removeAllListeners();
	              comboItem.clear();            
	              comboItem.setStore(getFilterItems(filter));
	              comboItem.addSelectionChangedListener(comboListenerItem);
	        }

	    };
	    comboType.addSelectionChangedListener(comboListener);
	    comboItem.addSelectionChangedListener(comboListenerItem);
	    
	
	    
	    
	    Button retriveBtn=new Button("Retrive");
	    retriveBtn.setWidth("50px");
	    retriveBtn.setHeight("29px");
	    
	    retriveBtn.addClickHandler(new ClickHandler() {
	        public void onClick(ClickEvent event) {
	   
	        	createPieChart(createDataTable(Type,Item),"Ugur");
	        
	        	
	        	
	          }
	        });
	    

	    hp.add(new Label("Property:"));
	    hp.add(comboItem);
	    hp.add(retriveBtn);

	    this.add(hp);
	    HorizontalPanel space=new HorizontalPanel();
	    space.setWidth(600);
	    space.setHeight(30);
	    this.add(space);
	    //createPieChart());
	    
		
	}
	

	public static ListStore<DefaultModel> getFilterItems(String Type) {
		ListStore<DefaultModel>  filters = new ListStore<DefaultModel>();
	    
	  if(Type.equalsIgnoreCase(CommonFormatStrings.O_OBJECT)){
		  
		  filters=GroupingOptions.getObjectProperties();
	  }
	  else if(Type.equalsIgnoreCase(CommonFormatStrings.C_CONTENT)){
		  
		  filters=GroupingOptions.getContentProperties();  
	  }
	  else if(Type.equalsIgnoreCase(CommonFormatStrings.A_Action)){
		  
		  
		  filters=GroupingOptions.getActionProperties();
	  }
	     
	    return filters;
	  }
	
	
	
	Map<String, List<ActionContent>>  groupedContent=null;
	Map<String, List<ActionObject>>   groupedObject=null;
	Map<String, List<CfAction>>   groupedAction=null;
	
	public DataTable createDataTable(String myType,String myItem){
		
		DataModel dp=new DataModel();
		DataTable data = DataTable.create();
		
		if(myType.equalsIgnoreCase("") || myType==null){
			return null;
		}
		else if(myItem.equalsIgnoreCase("") || myItem==null){
			
			return null;
		}
		
		
		if(myType.equalsIgnoreCase(CommonFormatStrings.O_OBJECT)){
			groupedObject=new HashMap<String, List<ActionObject>>();
			
			groupedObject=dp.groupObjectByProperty(myItem);
			
			   data.addColumn(ColumnType.STRING, "Task");
			    data.addColumn(ColumnType.NUMBER, "Hours per Day");
			    data.addRows(groupedObject.size());
			    int index=0;
			    for(String key:groupedObject.keySet()){
			    data.setValue(index, 0, key);
			    subsectionProperty.put(index, myItem);
			    subsectionValue.put(index, key);
			    data.setValue(index, 1, groupedObject.get(key).size());
			    index++;
			    }
						
		}
		else if(myType.equalsIgnoreCase(CommonFormatStrings.C_CONTENT)){
			groupedContent=new HashMap<String, List<ActionContent>>(); 
			groupedContent=dp.groupContentByProperty(myItem);	
			    data.addColumn(ColumnType.STRING, "Task");
			    data.addColumn(ColumnType.NUMBER, "Hours per Day");
			    data.addRows(groupedContent.size());
			    int index=0;
			    for(String key:groupedContent.keySet()){
			    data.setValue(index, 0, key);
			    subsectionProperty.put(index, myItem);
			    subsectionValue.put(index, key);
			    data.setValue(index, 1, groupedContent.get(key).size());
			    index++;
		}
	}
		//Here
		else if(myType.equalsIgnoreCase(CommonFormatStrings.A_Action)){
			groupedAction=new HashMap<String, List<CfAction>>(); 
			groupedAction=dp.groupActionByProperty(myItem);
			
			    data.addColumn(ColumnType.STRING, "Task");
			    data.addColumn(ColumnType.NUMBER, "Hours per Day");
			    data.addRows(groupedAction.size());
			    int index=0;
			    for(String key:groupedAction.keySet()){
			    data.setValue(index, 0, key);
			    subsectionProperty.put(index, myItem);
			    subsectionValue.put(index, key);
			    data.setValue(index, 1, groupedAction.get(key).size());
			    index++;
		}
	}

	 
	    return data;
		
	}
	
	 public void createPieChart(DataTable data,String ID) {
		    
		 if(pie!=null) {
				 if(pie.isAttached()){
			 pie.removeFromParent();
				 }
			 
		 }

		    PieOptions options = PieChart.createPieOptions();
		    options.setWidth(500);
		    options.setHeight(400);
		    options.set3D(true);
		    options.setTitle("Indicator Overview");		    
		    pie= new PieChart(data, options);  
		    pie.addSelectHandler(createSelectHandler(pie));
			RootPanel.get().add(pie);
			  }
	 
	 
	 
	 boolean reseted=false;
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
		    	  
		    	    
		    	    
		    	    String property=subsectionProperty.get(selection);
		    	    String value=subsectionValue.get(selection);

			          String _key=property+"-"+value;
			        if(!DataModel.getActiveFilters().containsKey(_key) && value!=null){
			        DataModel.getActiveFilters().put(_key,_key);
			       
			        ExtendedIndicatorFilterItem _filter = new ExtendedIndicatorFilterItem();  
			        _filter.setProperty(property);
			        _filter.setValue(value);
			        _filter.setType(Type);
			         
			    	Info.display("Info","Filter for "+ value+" is added!");
			        
			        if(!reseted){
			        	
			        	reseted=true;
			        	// ExtendedFilterGrid.getFilterSetListCombo().clearSelections();
			        	// ExtendedFilterGrid.getExtendedFilterGrid().getStore().removeAll();
			        	 
			        }
			    	
			    	
			        ExtendedFilterGrid.getExtendedFilterGrid().stopEditing();  
			        ExtendedFilterGrid.getExtendedFilterGrid().getStore().insert(_filter, 0);  
			        ExtendedFilterGrid.getExtendedFilterGrid().startEditing(ExtendedFilterGrid.getExtendedFilterGrid().getStore().indexOf(_filter), 0); 
			        ExtendedFilterGrid.getFilterSetListCombo().clearSelections();
				        
			        }
			        else {
			        	
			        	
			        	Info.display("Info","Selection is<ul><li> already in Filter List</li></ul>");
			        }
			        
			            
			            

			        
		        /*
		        
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
		            
		              String type=subsection.get(selection);
		              if(type==null)
		            	  type="";
		            
		              List<Indicator> _indicators=new ArrayList<Indicator>();
		              
		               if(groupedContent!=null && groupedContent.containsKey(type)){
		            	   
		            	   
		            	   for(ActionContent ac: groupedContent.get(type)){
		            	   Indicator myindicator=new Indicator();
		            	   String usersString="";
		            	   for(CfUser u : ac.ActionUsers){
		            		   usersString=usersString+" - "+u.getid();
		            	   }
		            	   
		            	   
		            	   myindicator.setName(usersString.substring(2,usersString.length()));
		            	   myindicator.setDescription(ac.Description);
		            	
		            	   
		            	   myindicator.setTime(GWTDateUtils.getTime(ac.time));
		            	   myindicator.setDate(GWTDateUtils.getDate(ac.time));
		            	   _indicators.add(myindicator);
		            	   }
		            	   
		               }
		               else if(groupedObject!=null && groupedObject.containsKey(type)){
		            	   
		            	   for(ActionObject ac: groupedObject.get(type)){
			            	   Indicator indicator=new Indicator();
			            	   String usersString="";
			            	   for(CfUser u : ac.ActionUsers){
			            		   usersString=usersString+" - "+u.getid();
			            	   
			            	   
			            	   }
			            	   
			            	   indicator.setName(usersString.substring(2,usersString.length()));
			            	   indicator.setDescription(ac.description);
			            	   indicator.setTime(GWTDateUtils.getTime(ac.time));
			            	   indicator.setDate(GWTDateUtils.getDate(ac.time));
			            	   _indicators.add(indicator);
			            	   }		            	
		               }
		               
		               else if(groupedAction!=null && groupedAction.containsKey(type)){
		            	   
		            	   
		            	  
		            	 //  for(int i=0;i<groupedAction.size();i++){
		            		   
		            		List<CfAction> actionLists=groupedAction.get(type);
		            		  
		            		for(int k=0;k<actionLists.size();k++){
		            			CfAction activeAction=actionLists.get(k);
		            			
		            			List<CfUser> myusers=	activeAction.getCfUsers();
		            			Indicator myuser=new Indicator();
		            		   String usersString="";
		            		
		            		
		            			for(CfUser uk : myusers){
		            			
		            			usersString=usersString+" - "+uk.getid();
		            		}
		            		
		            			
		            			
		            		
		            		  myuser.setName(usersString.substring(2,usersString.length()));
		            		   
			            		
							   myuser.setDescription(activeAction.getDescription());
			            	   myuser.setTime(GWTDateUtils.getTime(activeAction.getTime()));
			            	   myuser.setDate(GWTDateUtils.getDate(activeAction.getTime()));
			            	   
			            	   _indicators.add(myuser);
			            	   
		            		}
		            		   
		            		   
		            		   
		            	//   }
		            	   
		               }
		      		
		               
		              ExtendedGroupedGrid aa =new ExtendedGroupedGrid(type,_indicators);
		              aa.setStyleName("grid", true);
		              button.setStyleName("button", true);
		              button.setWidth("200px");
		              vp.add(aa);
		              vp.add(button);
		              vp.setStyleName("verticalpanel", true);
		           
		              p.setWidget(vp); 
		               p.center();
		              p.show();*/
		      }
		    };
		  }

	  
	
		

		
}
