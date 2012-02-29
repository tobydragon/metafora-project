package com.analysis.shared.commonformat;

import com.analysis.server.xml.XmlFragment;
import com.analysis.server.xml.XmlFragmentInterface;

public class CfUser {
	
	private String id, role;

	public CfUser(String id, String role) {
		this.id = id;
		this.role = role;
	}

	public String getid() {
		return id;
	}

	public void setid(String id) {
		this.id = id;
	}

	public String getrole() {
		return role;
	}

	public void setrole(String role) {
		this.role = role;
	}
	
//	public XmlFragment toXml(){
//		XmlFragment xmlFragment = new XmlFragment(CommonFormatStrings.USER_STRING);
//		xmlFragment.setAttribute(CommonFormatStrings.ID_STRING, getid());
//		if (role != null){
//			xmlFragment.setAttribute(CommonFormatStrings.ROLE_STRING, getrole());
//		}
//		return xmlFragment;
//	}
//	
//	public static CfUser fromXml(XmlFragmentInterface xmlFragment){
//		String id = xmlFragment.getAttributeValue(CommonFormatStrings.ID_STRING);
//		String role = xmlFragment.getAttributeValue(CommonFormatStrings.ROLE_STRING);
//		return new CfUser(id, role);
//	}

}
