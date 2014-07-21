package de.uds.MonitorInterventionMetafora.client.monitor;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.TextBox;
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
	 
	public UploadFormPanel(ClientMonitorDataModelUpdater updater){
		super();
		this.updater = updater;
		setAction(UPLOAD_ACTION_URL);
		setEncoding(FormPanel.ENCODING_MULTIPART);
		setMethod(FormPanel.METHOD_POST);
		
		 VerticalPanel panel = new VerticalPanel();
		 setWidget(panel);
		 
//		 final TextBox tb = new TextBox();
//		 tb.setName("textBoxFormElement");
//		 panel.add(tb);
		 
		// Create a FileUpload widget.
		 FileUpload upload = new FileUpload();
		 upload.setName("uploadFormElement");
		 panel.add(upload);
		 
		 // Add a 'submit' button.
		 panel.add(new Button("Submit", new ClickHandler() {
		     public void onClick(ClickEvent event) {
		         submit();
		     }
		 }));
		 
		 // Add an event handler to the form.
		 addSubmitHandler(new FormPanel.SubmitHandler() {
		     public void onSubmit(SubmitEvent event) {
		         // This event is fired just before the form is submitted. We can
		         // take this opportunity to perform validation.
		    	 Log.info("Submitting form to:" + UPLOAD_ACTION_URL);
//		    	 if (tb.getText().length() == 0) {
//		             Window.alert("The text box must not be empty");
//		             event.cancel();
//		         }
		     }
		 });
		 
		 addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
		     public void onSubmitComplete(SubmitCompleteEvent event) {
		         // When the form submission is successfully completed, this
		         // event is fired. Assuming the service returned a response of type
		         // text/html, we can get the result text here (see the FormPanel
		         // documentation for further explanation).
		    	 System.out.println(event.getResults());
		         Window.alert(event.getResults());
		         //TODO: strip to just filename
		         String filename = "";
		         String result = event.getResults();
		         filename = result.substring(result.indexOf('>')+1,result.lastIndexOf('<'));
		         if (filename.length() > 0){
		        	 UploadFormPanel.this.updater.getDataFromFile(filename);
		         }
		         else{
		        	Log.error("filename not found on response to file upload (after completion)");
		         }
		     }
		 });
	}
	
	
}
