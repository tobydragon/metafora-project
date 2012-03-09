package de.uds.MonitorInterventionMetafora.client.view.widgets;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.TextField;

import de.uds.MonitorInterventionMetafora.client.resources.Resources;

public class ExtendedSaveFilterSet  extends HorizontalPanel{
	
	TextField<String> text;
	Button savebtn;
public ExtendedSaveFilterSet(){
	
	  
	    
		text= new TextField<String>();  
	    text.setAllowBlank(false);
	    text.setWidth("190px");
	    
	    
	    savebtn=new Button("Save", new SelectionListener<ButtonEvent>() {  
	  	  
	        @Override  
	        public void componentSelected(ButtonEvent ce) {  

	        	Info.display("Save","Save");
	        
	        
	        }  
	      });
	   
	   savebtn.setIcon(Resources.ICONS.add());
	   savebtn.setWidth("50px");
	   this.add(text);
	   this.add(savebtn);
	
	
}	

}
