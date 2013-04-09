package de.kuei.metafora.xmppbridge;

import java.util.Vector;

import de.kuei.metafora.xmppbridge.util.MessageConsolePrinter;
import de.kuei.metafora.xmppbridge.xml.Classification;
import de.kuei.metafora.xmppbridge.xml.CommonFormatCreator;
import de.kuei.metafora.xmppbridge.xml.Role;
import de.kuei.metafora.xmppbridge.xml.XMLException;
import de.kuei.metafora.xmppbridge.xmpp.NameConnectionMapper;
import de.kuei.metafora.xmppbridge.xmpp.XmppMUC;
import de.kuei.metafora.xmppbridge.xmpp.XmppMUCManager;

public class XMPPBridgeUsageGuide {

	// address of the test server
	private static String testServer = "metafora.ku-eichstaett.de";
	// address of the productive server
	private static String productiveServer = "metaforaserver.ku.de";

	// xmpp username
	private static String xmppUser = "AXMPPUser";
	// xmpp users password
	private static String xmppPassword = "ThisIsThePasswordForTheXMPPUser";

	// multiple clients with the same XMPP account
	private static String[] clients = new String[] { "Client1", "Client2",
			"Client3" };

	// list of open xmpp connections
	private static Vector<String> connectionNames = new Vector<String>();

	public static void main(String[] args) throws Exception {

		System.err.println("Starting connections to test server");

		// open multiple connections with test server
		connectToTestServer();

		System.err.println("Starting connections to productive server");

		// open multiple connections with test server
		connectToProductiveServer();

		System.err.println("Wait for a minute");

		// wait a minute
		Thread.sleep(60000);

		System.err.println("The minute is over. Close the connections");

		// close connections
		for (String connectionName : connectionNames) {
			NameConnectionMapper.getInstance().getConnection(connectionName)
					.disconnect();

			System.err.println("Closing connection: " + connectionName);
		}

		System.err.println("Test run finished.");
	}

	private static void connectToServer(String connectionName, String server,
			String xmppUser, String xmppPassword, String device,
			String chatAlias) {

		// Prepare a XMPP connection
		// User and password can fixed values, but the device have to be unique
		// per client.
		NameConnectionMapper.getInstance().createConnection(connectionName,
				server, xmppUser, xmppPassword, device);

		// Start the TCP connection with the server
		NameConnectionMapper.getInstance().getConnection(connectionName)
				.connect();

		// Add a PacketListener if you want to receive messages. A
		// PacketListener added to a
		// connection will receive all messages of this connection
		NameConnectionMapper.getInstance().getConnection(connectionName)
				.addPacketListener(new MessageConsolePrinter(connectionName));

		// Login to the XMPP server
		NameConnectionMapper.getInstance().getConnection(connectionName)
				.login();

		// Save connection name for disconnect
		connectionNames.add(connectionName);

		// Prepare joining of command multi user chat
		XmppMUC multiUserChatCommand = XmppMUCManager.getInstance()
				.getMultiUserChat("command", chatAlias, connectionName);
		// Join the command multi user chat. The integer gives the count of
		// channel history messages
		// you want to receive. 0 means you will get no old messages. Server
		// history limit is 200, any
		// bigger number will return last 200 messages.
		multiUserChatCommand.join(0);

		// Prepare and join logger channel.
		XmppMUC multiUserChatLogger = XmppMUCManager.getInstance()
				.getMultiUserChat("logger", chatAlias, connectionName);
		multiUserChatLogger.join(0);

		/*
		 * This split of connection and channel fits to the XMPP design. Multi
		 * user chats are only an extension of the XMPP protocol.
		 */

		//The user which receives the feedback message
		//You can login with user Bob (password Bob) to see the feedback messages
		String user = "Bob";

		String receivingTool = "METAFORA";
		if (server.equals(testServer)) {
			receivingTool = "METAFORA_TEST";
		}

		// generate a feedback message
		String message = generateFeedbackMessage(user, chatAlias + " on "
				+ device + " joined!", "THE_XMPP_TEST", receivingTool);

		// send the feedback message to the command channel
		multiUserChatCommand.sendMessage(message);

		// log the feedback message
		multiUserChatLogger.sendMessage(message);

	}

	public static void connectToTestServer() {
		for (String client : clients) {
			// open multiple XMPP connections for the same user
			connectToServer("testServer" + client, testServer, xmppUser,
					xmppPassword, client + "device", client);
		}
	}

	public static void connectToProductiveServer() {
		for (String client : clients) {
			// open multiple XMPP connections for the same user
			connectToServer("productiveServer" + client, productiveServer,
					xmppUser, xmppPassword, client + "device", client);
		}
	}

	public static String generateFeedbackMessage(String user, String message,
			String sendingTool, String receivingTool) {
		try {
			CommonFormatCreator cfc = new CommonFormatCreator(
					System.currentTimeMillis(), Classification.create,
					"FEEDBACK", true);

			cfc.addUser(user, "", Role.receiver);

			cfc.setObject("0", "MESSAGE");
			cfc.addProperty("INTERRUPTION_TYPE", "no_interruption");
			cfc.addProperty("TEXT", message);

			cfc.setDescription(message);

			cfc.addContentProperty("SENDING_TOOL", sendingTool);
			cfc.addContentProperty("RECEIVING_TOOL", receivingTool);

			return cfc.getDocument();

		} catch (XMLException e) {
			// ignore
		}
		return null;
	}

}
