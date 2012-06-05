/*
 *  Copyright (c), 2009 Carnegie Mellon University.
 *  All rights reserved.
 *  
 *  Use in source and binary forms, with or without modifications, are permitted
 *  provided that that following conditions are met:
 *  
 *  1. Source code must retain the above copyright notice, this list of
 *  conditions and the following disclaimer.
 *  
 *  2. Binary form must reproduce the above copyright notice, this list of
 *  conditions and the following disclaimer in the documentation and/or
 *  other materials provided with the distribution.
 *  
 *  Permission to redistribute source and binary forms, with or without
 *  modifications, for any purpose must be obtained from the authors.
 *  Contact Rohit Kumar (rohitk@cs.cmu.edu) for such permission.
 *  
 *  THIS SOFTWARE IS PROVIDED BY CARNEGIE MELLON UNIVERSITY ``AS IS'' AND
 *  ANY EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 *  THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 *  PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL CARNEGIE MELLON UNIVERSITY
 *  NOR ITS EMPLOYEES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 *  SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 *  LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 *  DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 *  THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 *  OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *  
 */
package de.uds.MonitorInterventionMetafora.server.analysis.tagging;
import java.util.StringTokenizer;

import de.uds.MonitorInterventionMetafora.shared.commonformat.CfProperty;
import de.uds.MonitorInterventionMetafora.shared.monitor.MonitorConstants;

/**
 *
 * @author rohitk
 */
public class WordCountProperty {

	private int wordCount=0;
	private String text="";
   
    public WordCountProperty(String text) {
    
    	this.text=text;
    	wordCount=processText();
    }

    int processText(){
    	 int count=0;
         StringTokenizer stk=new StringTokenizer(text," ");
         while(stk.hasMoreTokens()){
             String token=stk.nextToken();
             count++;
         }
return count;
    }
    
    public String getText() {
    	
    	
    	return text;
    }
   
    public int getWordCount(){
    	
    	return wordCount;
    }
    

    public CfProperty toCfProperty(){
    	
    	CfProperty property=new CfProperty();
    	property.setId(text);
    
    	
    	property.setName(MonitorConstants.WORD_COUNT);
    	property.setValue(Integer.toString(wordCount));
    	return property;
    }
    
  public CfProperty toEmptyCfProperty(){
    	
    	CfProperty property=new CfProperty();
    	property.setId(MonitorConstants.BLANK_PROPERTY_LABEL);

    	
    	property.setName(MonitorConstants.WORD_COUNT);
    	property.setValue("0");
    	return property;
    }
   
}
