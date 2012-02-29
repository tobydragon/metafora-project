package com.analysis.shared.commonformat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CommonFormatUtil {
	static Log logger = LogFactory.getLog(CommonFormatUtil.class);
	
	public static long getTime(String timeStr){
		try {
			long time = Long.valueOf(timeStr);
			return time;
		}
		catch (NumberFormatException e){
			logger.error("[getTime] Bad format of time string - " + timeStr);
			return 0;
		}
	}

}
