package de.uds.MonitorInterventionMetafora.server.json;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph.NodeAndLinkLists;
import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph.NodesAndIDLinks;

public class JsonImportExport {
	public static void toJson(String fileName, NodesAndIDLinks toBeJsoned) {
		ObjectMapper mapper = new ObjectMapper();
		try {

			// convert user object to json string, and save to a file
			mapper.writeValue(new File(fileName+".json"), toBeJsoned);

			// display to console (temp)
			System.out.println(mapper.writeValueAsString(toBeJsoned));

		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static NodeAndLinkLists fromJson (String fullFileName) {
		ObjectMapper mapper = new ObjectMapper();
		// Read in JSON and build nodes and edges lists (class)
        try {
        	NodeAndLinkLists lists = mapper.readValue(new File(fullFileName), NodeAndLinkLists.class);
        	return lists;
        } 
        catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
	}
}
