package de.uds.MonitorInterventionMetafora.server;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class InitServlet extends HttpServlet {
	
static final Logger logger = Logger.getLogger(InitServlet.class);
private static ServletContext staticServletContext = null;

	public void init() throws ServletException {
		//set the static context of this Servlet
		//to be accessed throughout the webapp for getting the real path of files
		staticServletContext = getServletContext(); 
		String log4jfile = getInitParameter("log4j-properties");
		//System.out.println("log4jfile: "+log4jfile);
		if (log4jfile != null) {
		  String propertiesFilename = staticServletContext.getRealPath(log4jfile);
		  PropertyConfigurator.configure(propertiesFilename);
		  logger.info("MonitorInterventionMetafora logger configured according to " + propertiesFilename);
		} else {
		  System.err.println("Error setting up logger for MonitorInterventionMetafora. " +
		  		"Check that the log4j-properties in init is set to a file.");
		}
	}

	public static ServletContext getStaticServletContext() {
		return staticServletContext;
	}

}

