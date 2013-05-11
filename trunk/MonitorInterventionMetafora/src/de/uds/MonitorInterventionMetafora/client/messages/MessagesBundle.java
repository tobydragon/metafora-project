
package de.uds.MonitorInterventionMetafora.client.messages;

import com.google.gwt.i18n.client.Constants;

/**
 * GWT client interface to the messages bundle
 * 
 * property files can be defined in different languages
 * 
 * @author Ken Kahn
 *
 */
public interface MessagesBundle extends Constants {
    String GetRecommendationsButton();
    
    String MessagesInstruction();
    
    String ToolLabel();
    
    String EditInstructions(); 
    
    String To();
    String All(); 
    String None(); 
    String Edit(); 
    String Send();
    String Sent();
    
}