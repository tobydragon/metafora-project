package de.kuei.metafora.xmppbridge;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import de.kuei.metafora.xmppbridge.util.MessageConsolePrinter;
import de.kuei.metafora.xmppbridge.xmpp.NameConnectionMapper;
import de.kuei.metafora.xmppbridge.xmpp.ServerConnection;
import de.kuei.metafora.xmppbridge.xmpp.XmppMUC;
import de.kuei.metafora.xmppbridge.xmpp.XmppMUCManager;

public class Start {

	private static final Logger log = Logger.getLogger(Start.class);

	public static void main(String[] args) throws Exception {
		// load log4j configuration
		PropertyConfigurator.configure("log4j.properties");

		// test output
		log.debug("debug");
		log.info("info");
		log.warn("warn");
		log.error("error");
		log.fatal("fatal");

		MessageConsolePrinter packetPrinter = new MessageConsolePrinter("Testprinter");
		
		NameConnectionMapper.getInstance().createConnection("metafora", "metafora.ku-eichstaett.de", "planningsolocommand", "didPfPSC", "planningToolAtThomasThinkpad");
		
		ServerConnection connection = NameConnectionMapper.getInstance().getConnection("metafora");
		
		connection.addPacketListener(packetPrinter);
		
		connection.login();
		
		XmppMUC muc = XmppMUCManager.getInstance().getMultiUserChat("logger", "planningToolThomasThinkpad", "metafora");
		muc.join(0);
		
		while(true){
			Thread.sleep(1000);
			muc.sendMessage("bin da!");
		}
		
		/*
		MessageConsolePrinter packetPrinter = new MessageConsolePrinter("Testprinter");
		
		NameConnectionMapper.getInstance().createConnection("metaforadebianvirtual", "metaforadebianvirtual.ku.de", "testuser", "testpasswort", "testdevice");
		
		ServerConnection connection = NameConnectionMapper.getInstance().getConnection("metaforadebianvirtual");
		
		connection.addPacketListener(packetPrinter);
		
		connection.login();
		
		XmppMUC muc = XmppMUCManager.getInstance().getMultiUserChat("testroom", "testuser", "metaforadebianvirtual");
		muc.join(0);
		
		while(true){
			Thread.sleep(1000);
			muc.sendMessage("bin da!");
		}
		
		/*
		MessageConsolePrinter packetPrinter = new MessageConsolePrinter("NotNullPrinter");
		MessageConsolePrinter packetPrinter2 = new MessageConsolePrinter("RegExpPrinter");
		MessageConsolePrinter packetPrinter3 = new MessageConsolePrinter("NotNullPrinter2");
		
		NameConnectionMapper.getInstance().createConnection("metaforaserver", "tom", "didPfT", "SystemWatcherTest");
		NameConnectionMapper.getInstance().createConnection("metafora", "metafora.ku-eichstaett.de", "tttom", "didPfT", "SystemWatcherTest");
		NameConnectionMapper.getInstance().createConnection("metaforaserver2", "tom2", "didPfT", "SystemWatcherTest");
		
		NameConnectionMapper.getInstance().getConnection("metaforaserver").addPacketListener(packetPrinter, new NotNullMessageFilter());
		NameConnectionMapper.getInstance().getConnection("metaforaserver2").addPacketListener(packetPrinter3, new NotNullMessageFilter());
		NameConnectionMapper.getInstance().getConnection("metafora").addPacketListener(packetPrinter2, new RegExpFilter(".*[tT][eE][sS][tT].*"));
		
		NameConnectionMapper.getInstance().getConnection("metaforaserver").login();
		NameConnectionMapper.getInstance().getConnection("metaforaserver2").login();
		NameConnectionMapper.getInstance().getConnection("metafora").login();
		
		new RosterConsolePrinter(NameConnectionMapper.getInstance().getConnection("metaforaserver"));
		NameConnectionMapper.getInstance().getConnection("metaforaserver").printRoster();
		
		XmppMUC muc = XmppMUCManager.getInstance().getMultiUserChat("tomsroom", "tom", "metaforaserver");
		muc.join("password", 0);
		
		muc.inviteUser("tom2@metaforaserver.ku.de", "There is no real reason...");
		
		try {
			Thread.sleep(35000);
		} catch (InterruptedException e) {
			log.error(e.getMessage(), e);
		}
		
		muc.sendMessage("Hello world!");
		
		Chat chat = UserToUserChatManager.getInstance().openChatWithUser("tttom@metafora.ku-eichstaett.de", "metaforaserver");
		chat.sendMessage("tttestmessage!");
		
		Chat chat2 = UserToUserChatManager.getInstance().openChatWithUser("tom2@metaforaserver.ku.de", "metaforaserver");
		chat2.sendMessage("A user to user message!");
		
		try {
			Thread.sleep(35000);
		} catch (InterruptedException e) {
			log.error(e.getMessage(), e);
		}
		
		ServerConnectionManager.getInstance().closeAllConnections();
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			log.error(e.getMessage(), e);
		}
		
		log.info("Test run complete.");*/
	}

}
