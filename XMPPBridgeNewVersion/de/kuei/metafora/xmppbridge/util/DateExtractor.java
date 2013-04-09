package de.kuei.metafora.xmppbridge.util;

import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.PacketExtension;
import org.jivesoftware.smackx.packet.DelayInfo;
import org.jivesoftware.smackx.packet.DelayInformation;

public class DateExtractor {

	public static Date getMessageExtensionDate(Packet packet) {
		Date time = new Date();

		Collection<PacketExtension> extensions = packet.getExtensions();
		for (PacketExtension e : extensions) {
			if (e instanceof DelayInfo) {
				DelayInfo d = (DelayInfo) e;
				time = d.getStamp();
				break;
			} else if (e instanceof DelayInformation) {
				DelayInformation d = (DelayInformation) e;
				time = d.getStamp();
				break;
			}
		}

		return time;
	}

	public static Date getXMLActionDate(Packet packet) {
		// TODO: implement
		return null;
	}

	public static String getTimeString(Date date) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date);

		String text = calendar.get(GregorianCalendar.DAY_OF_MONTH) + "."
				+ (calendar.get(GregorianCalendar.MONTH) + 1) + "."
				+ calendar.get(GregorianCalendar.YEAR) + " "
				+ calendar.get(GregorianCalendar.HOUR_OF_DAY) + ":";

		int min = calendar.get(GregorianCalendar.MINUTE);
		if (min < 10) {
			text += "0" + min;
		} else {
			text += min;
		}

		return text;
	}

}
