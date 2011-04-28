/*
 * Created on 06 Απρ 2011
 *
 */
package metafora.etl.smacktest;

import javax.swing.JFrame;

import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smackx.muc.MultiUserChat;

public class SmackTest implements PacketListener {

    // public static XmppMessages messages = new XmppMessages();

    private XMPPConnection connection;

    private MultiUserChat logger;

    public void init() {
        JFrame f = new JFrame();
        connection = new XMPPConnection("metafora.ku-eichstaett.de");
System.out.println("connection :"+connection);
        try {
            // You have to put this code before you login
            SASLAuthentication.supportSASLMechanism("PLAIN", 0);
            
            connection.connect();
            System.out.println("connected? :"+connection.getServiceName());
            connection.login("testuser", "didPfT");
            System.out.println("logged in :"+connection.isAuthenticated());
            logger = new MultiUserChat(connection,
                    "logger@conference.metafora.ku-eichstaett.de");
            logger.addMessageListener(this);
            logger.join("Development");

            logger.sendMessage("Hello from ETL!");
            System.out.println("message sent!");

        } catch (XMPPException e) {
            e.printStackTrace();
        }
        f.setSize(400,300);
        f.setVisible(true);
        System.out.println("Terminating, bye!");
    }

    public void processPacket(Packet packet) {
        String name = packet.getFrom().substring(
                packet.getFrom().lastIndexOf('/')+1);
        Message message = (Message) packet;
        String text = message.getBody();
        System.out.println("Incoming :"+text);

        //messages.addMessage(name+": "+text);
    }
    
    public static void main(String[] args){
        new SmackTest().init();
    }

}
