package de.uds.MonitorInterventionMetafora.shared.utils;

public class Logger {
	private static LogLevel defaultLogLevel = LogLevel.DEBUG;
	
	private String className="Unknown Class";
	private LogLevel logLevel = LogLevel.DEBUG;
	
	
	public static Logger getLogger(Class classToLog){
		return new Logger(classToLog.getName());
	}
	
	public static Logger getLogger(Class classToLog, LogLevel classLogLevel){
		return new Logger(classToLog.getName(), classLogLevel);
	}
	
	public Logger(){}
	
	public Logger (String className){
		this.className = className;
		logLevel = defaultLogLevel;
	}
	
	public Logger (String className, LogLevel classLogLevel){
		this.className = className;
		logLevel = classLogLevel;
	}
	
	public void debug(String message){
		if (logLevel.ordinal()<=LogLevel.DEBUG.ordinal()){
			System.out.println("DEBUG:\t\t [" + className + "] "+ message);
		}
	}
	
	public void info(String message){
		if (logLevel.ordinal()<=LogLevel.INFO.ordinal()){
			System.out.println("INFO:\t\t [" + className + "] "+ message);
		}
	}
	
	public void warn(String message){
		if (logLevel.ordinal()<=LogLevel.WARN.ordinal()){
			System.out.println("WARN:\t\t [" + className + "] "+ message);
		}
	}
	
	public void error(String message){
		if (logLevel.ordinal()<=LogLevel.ERROR.ordinal()){
			System.err.println("ERROR:\t\t [" + className + "] "+ message);
		}
	}

}
