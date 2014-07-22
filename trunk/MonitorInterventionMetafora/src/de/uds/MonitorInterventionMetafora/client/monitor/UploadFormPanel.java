package de.uds.MonitorInterventionMetafora.client.monitor;

import com.allen_sauer.gwt.log.client.Log;
import com.extjs.gxt.ui.client.widget.Window;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/* tdragon 6/21/2014
 * Client side of simple file upload, mostly taken from:
 * http://www.gwtproject.org/javadoc/latest/com/google/gwt/user/client/ui/FileUpload.html
 * and
 * http://www.jroller.com/hasant/entry/fileupload_with_gwt
 */
public class UploadFormPanel extends FormPanel{
	 private static final String UPLOAD_ACTION_URL = GWT.getModuleBaseURL() + "upload";
	
	 private ClientMonitorDataModelUpdater updater;
	 
	 private Window myContainingWindow;
	 
	public UploadFormPanel(ClientMonitorDataModelUpdater updater){
		super();
		this.updater = updater;
		myContainingWindow = null;
		setAction(UPLOAD_ACTION_URL);
		setEncoding(FormPanel.ENCODING_MULTIPART);
		setMethod(FormPanel.METHOD_POST);
		
		 VerticalPanel panel = new VerticalPanel();
		 setWidget(panel);
	
		
		 FileUpload upload = new FileUpload();
		 upload.setName("uploadFormElement");
		 panel.add(upload);
		 
		 panel.add(new Button("Submit", new ClickHandler() {
		     public void onClick(ClickEvent event) {
		         submit();
		     }
		 }));
		 
		 // Add an event handler to the form.
		 addSubmitHandler(new FormPanel.SubmitHandler() {
		     public void onSubmit(SubmitEvent event) {
		         // This event is fired just before the form is submitted. 
		    	 Log.info("Submitting form to:" + UPLOAD_ACTION_URL);
		    	 
		     }
		 });
		 
		 addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
		     public void onSubmitComplete(SubmitCompleteEvent event) {
		         // When the form submission is successfully completed, this
		         // event is fired.
		    	 Log.debug(event.getResults());
		         
		         // strip filename out of result html
		         String result = event.getResults();
		         String filename = result.substring(result.indexOf('>')+1,result.lastIndexOf('<'));
		         if (filename.length() > 0){
		        	 UploadFormPanel.this.updater.getDataFromFile(filename);
		        	 if (myContainingWindow != null){
			    		 myContainingWindow.hide();
			    	 }
		         }
		         else{
		        	Log.error("filename not found on response to file upload (after completion)");
		         }
		     }
		 });
	}

	public void setContainingWindow(Window window) {
		myContainingWindow = window;
	}
	
}
