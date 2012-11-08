package de.uds.MonitorInterventionMetafora.server.xml;

import java.net.URLDecoder;

import org.apache.log4j.Logger;

import de.uds.MonitorInterventionMetafora.server.utils.ErrorUtil;



public class XmlUtil {
	private static Logger logger = Logger.getLogger(XmlUtil.class);

	public static String convertSpecialCharactersToDescripitons(String toConvert){
		return toConvert.replaceAll("&", "&amp;");
	}
	
	public  static String convertSpecialCharacterDescriptionsBack(String toConvertBack){
		
		try {
			toConvertBack = toConvertBack.replaceAll("&amp;", "&");
			return URLDecoder.decode(toConvertBack, "UTF-8");
		}
		catch(Exception e){
			logger.error("[convertSpecialCharactersBack] String not changed due to following:\n" + ErrorUtil.getStackTrace(e));
			return toConvertBack;
		}
	}

}
