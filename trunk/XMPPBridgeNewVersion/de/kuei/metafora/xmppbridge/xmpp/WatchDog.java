package de.kuei.metafora.xmppbridge.xmpp;

import org.apache.log4j.Logger;

public class WatchDog implements Runnable {

	private static final Logger log = Logger.getLogger(WatchDog.class);

	public static final String testAddress = "dogmaster@metaforaserver.ku-eichstaett.de";

	private ServerConnection connection;
	private boolean run = true;

	public WatchDog(ServerConnection connection) {
		this.connection = connection;
		log.debug("Watchdog created!");
	}


	public boolean isRunning(){
		return run;
	}
	
	public void reset() {
		this.run = true;
	}

	public void stop() {
		this.run = false;
	}

	@Override
	public void run() {
		log.debug("Watchdog started!");
		while (run) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				log.warn(e.getMessage(), e);
			}
			if (run) {
				log.debug("Watchdog run.");
				if (connection.wasReady()) {
					log.debug("Watchdog: Connection was ready.");
					if (!connection.isConnected()
							|| !connection.isAuthenticated()) {
						connection.refreshConnection();
					}
				} else {
					log.debug("Watchdog: Connection wasn't ready.");
				}
			}
		}
		log.debug("Watchdog stopped!");
	}

}
