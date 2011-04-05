package de.kuei.metafora.gwt.smack.client;

import java.util.Date;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.HTML;

public class Development extends TableLayout {

	Development(){
	super();
	
		int value = 1;
		for(int i=1; i<=6; i++){
			addComment(value);
			value++;
		}
		
		
	}

public void add(String s){
	HTML today = new HTML("["+DateTimeFormat.getMediumDateTimeFormat().format(new Date())+"]", true);
	HTML message = new HTML(s);
	box.setWidget(row, column-2, today);
	box.setWidget(row, column-1, message);
	row++;
}
	
public void addComment(int value){
	HTML today = new HTML("["+DateTimeFormat.getMediumDateTimeFormat().format(new Date())+"]", true);
	HTML delete = new HTML("[x]", true);
	delete.addClickHandler(new ClickHandler() {
	      public void onClick(ClickEvent event) {
	    	    try{
	    	  	int removedIndex = box.getCellForEvent(event).getRowIndex(); //schauen, was hier konkret passiert!!
	    	  	box.removeRow(removedIndex);
	    	    }
	    	    catch(Exception e){
	    	    	e.printStackTrace();
	    	    }
		      }
		  });
   
	//if(something happens){
	    if(value==1){ //nur zu Testzwecken erst einmal Werte f�r die if. Sp�ter sollte ein Event mit oder String Bedingung sein (vom Browser an Client -> Meldung, dass anderer Client etwas gemacht hat)
	    	HTML html = new HTML("Group 3 has completed the task");
	    	box.setWidget(row, column-2, today);
	    	box.setWidget(row, column-1, html);
	    	box.setWidget(row, column, delete);
	    }
	    if(value==2){
	    	HTML html = new HTML("Group 2 has completed the first part of the task");
	    	box.setWidget(row, column-2, today);
	    	box.setWidget(row, column-1, html);
	    	box.setWidget(row, column, delete);
	    }
	    if(value==6){
	    	HTML html = new HTML("Jane has uploaded a new file");
	    	box.setWidget(row, column-2, today);
	    	box.setWidget(row, column-1, html);
	    	box.setWidget(row, column, delete);
	    }
		//if(clientOnline){
	    	if(value==3){
	    		HTML html = new HTML("Anne is offline");
	    		box.setWidget(row, column-2, today);
		    	box.setWidget(row, column-1, html);
		    	box.setWidget(row, column, delete);
	    	}
	    	if(value==4){
	    		HTML html = new HTML("Daniel is online");
	    		box.setWidget(row, column-2, today);
		    	box.setWidget(row, column-1, html);
		    	box.setWidget(row, column, delete);
	    	}
	    	if(value==5){
	    		HTML html = new HTML("Keith is now working at task 8");
	    		box.setWidget(row, column-2, today);
		    	box.setWidget(row, column-1, html);
		    	box.setWidget(row, column, delete);
	    	}
	    //}
	//}
	    	row++;
}

}
