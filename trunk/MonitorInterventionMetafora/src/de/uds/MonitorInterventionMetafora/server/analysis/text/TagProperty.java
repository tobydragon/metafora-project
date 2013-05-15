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
package de.uds.MonitorInterventionMetafora.server.analysis.text;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfProperty;
import de.uds.MonitorInterventionMetafora.shared.monitor.MonitorConstants;

/**
 *
 * @author rohitk
 */
public class TagProperty {

	private Map<String, List<String>> tags;
	private String taggedText="";
   
    public TagProperty(String taggedText) {
    
    	this.taggedText=taggedText;
    }

  
    

    public void addTag(String a, List<String> ps) {
        if (tags == null) {
            tags = new Hashtable<String, List<String>>();
        }
        tags.put(a.trim(), ps);
    }

    public void removeTag(String a) {
        if (tags != null) {
            tags.remove(a);
        }
    }

    public String getTags() {
        String ret = "";
        if (tags != null) {
            String[] keys = tags.keySet().toArray(new String[0]);
            for (int i = 0; i < keys.length; i++) {
                if (i != 0) {
                    ret += ",";
                }
                ret += keys[i];
            }
        }

        return ret;
    }

    
    public String getTaggedText() {
    	
    	
    	return taggedText;
    }
   
    
    public String[] checkTag(String a) {
        if (tags != null) {
            List<String> ps;
            if ((ps = tags.get(a)) != null) {
                return ps.toArray(new String[0]);
            } else {
                return null;
            }
        }
        return null;
    }


    
    public CfProperty toCfProperty(){
    	
    	CfProperty property=new CfProperty();
    	property.setId(taggedText);
    	
    	property.setName(MonitorConstants.TAGS);
    	property.setValue(getTags());
    	
    	return property;
    }
    
 public CfProperty toEmptyCfProperty(){
    	
    	CfProperty property=new CfProperty();
    	property.setId(MonitorConstants.BLANK_PROPERTY_LABEL);
    	
    	property.setName(MonitorConstants.TAGS);
    	property.setValue(MonitorConstants.BLANK_PROPERTY_LABEL);
    	return property;
    }
    
    @Override
    public String toString() {
   return getTags();
    	
    }
}
