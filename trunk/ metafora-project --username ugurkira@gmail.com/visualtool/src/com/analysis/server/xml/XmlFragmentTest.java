package com.analysis.server.xml;



import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class XmlFragmentTest {
	Log logger = LogFactory.getLog(XmlFragmentTest.class);
	
	public void testXmlFragment(){
		XmlFragment.getFragmentFromString("bad");
	}
	
	public void testConvertBack(){
		String url = "http://web-expresser.appspot.com/?userKey=new&amp;copy=http%3A%2F%2Fweb-expresser.appspot.com%2Fp%2FJkvkM0h8U71hoRBn5z0E7b%2FAlice.xml%3Ftime%3D1314806469248495318";
		System.out.print(XmlFragment.convertSpecialCharacterDescriptionsBack(url));
	}

}
