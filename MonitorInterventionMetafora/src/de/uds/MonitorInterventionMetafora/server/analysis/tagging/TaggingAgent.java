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


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.uds.MonitorInterventionMetafora.server.utils.GeneralUtil;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfProperty;

/**
 *
 * @author rohitk
 */
public class TaggingAgent
{

	static TaggingAgent instance; 
    private Map<String, List<String>> dictionaries = new HashMap<String, List<String>>();

    public TaggingAgent() {
         
        File dir = new File(GeneralUtil.getRealPath("dictionaries"));

        File[] dictNames = dir.listFiles(new FilenameFilter() { 
                 public boolean accept(File dir, String filename)
                      { return filename.endsWith(".txt"); }
        } );
        
        for(File dictFile : dictNames)
        {
            String name = dictFile.getName().replace(".txt", "").toUpperCase();
          
            dictionaries.put(name, loadDictionary(dictFile));
        }
        

    }
    
    
    public static TaggingAgent getTaggingAgentInstance(){
    	
    	if(instance==null)
    		instance= new TaggingAgent();
    	return instance;
    }


   

    private List<String> loadDictionary(File dict) {
        List<String> dictionary = new ArrayList<String>();
        try {
            BufferedReader fr = new BufferedReader(new FileReader(dict));

            String line = fr.readLine();
            while (line != null) {
                line = line.trim();
                if (line.length() > 0) {
                    dictionary.add(line.trim());
                }
                line = fr.readLine();
            }
            fr.close();
        } catch (Exception e) {
            System.err.println("Error while reading Dictionary: " + dict.getName() + " (" + e.toString() + ")");
        }
        return dictionary;
    }

    private List<String> matchDictionary(String text, List<String> dictionary) {
        text = " " + text;
        
        List<String> matchedTerms = new ArrayList<String>();
        for (int j = 0; j < dictionary.size(); j++) {
            if (text.contains(" " + dictionary.get(j))) {
                matchedTerms.add(dictionary.get(j));
                
                
            }
        }
        return matchedTerms;
    }



    public TagProperty tag(CfProperty _property) {
        String text =_property.getValue();
        if(text==null)
        	text="";
        String normalizedText = normalize(text);
        TagProperty tagHandler = new TagProperty(normalizedText);
        
         for(String key : dictionaries.keySet())
        {
            List<String> dictionary = dictionaries.get(key);

            List<String> namesFound = matchDictionary(normalizedText, dictionary);
    
            if (namesFound.size() > 0) 
            {
            	tagHandler.addTag(key, namesFound);
            }
        }
        

         
         return tagHandler;
        }

    
    
    
    
    
    public TagProperty tag(String textValue) {
        String text=textValue;
        if(text==null)
        	text="";
        String normalizedText = normalize(text);
        TagProperty tagContainer = new TagProperty(normalizedText);
        
         for(String key : dictionaries.keySet())
        {
            List<String> dictionary = dictionaries.get(key);

            List<String> namesFound = matchDictionary(normalizedText, dictionary);
    
            if (namesFound.size() > 0) 
            {
            
            	tagContainer.addTag(key, namesFound);
            }
        }
    
         return tagContainer;
        }

    
    
    public  String normalize(String text) {
        String rettext = text.replace(",", " , ");
        rettext = rettext.replace(".", " . ");
        rettext = rettext.replace("?", " ? ");
        rettext = rettext.replace("!", " ! ");
        rettext = rettext.replace("\'", " \' ");
        rettext = rettext.trim();
        rettext = rettext.replace("  ", " ");
        rettext = rettext.replace("  ", " ");
        rettext = rettext.replace("  ", " ");
        rettext = rettext.replace("\t", " ");
        rettext = rettext.toLowerCase();
        
        
        return rettext;
    }

	public Class[] getPreprocessorEventClasses()
	{
		return new Class[]{TagProperty.class};
	}

}
