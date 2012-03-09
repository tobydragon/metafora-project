package de.uds.MonitorInterventionMetafora.server.utils;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

public class ErrorUtil {
	
	public static String getStackTrace(Throwable throwable) {
	    Writer writer = new StringWriter();
	    PrintWriter printWriter = new PrintWriter(writer);
	    throwable.printStackTrace(printWriter);
	    return writer.toString();
    }

}
