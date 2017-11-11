package com.bmincey.inet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;


/**
 *
 */
public class InternetMonitor {

    // Internet
    private static final String GOOGLE   = "www.google.com";
    private static final String AMAZON   = "www.amazon.com";

    // Local network
    private static final String srv1   = "192.168.1.199";
    private static final String srv2   = "192.168.1.200";
    private static final String srv3   = "192.168.1.110";

    // Ports
    private static final int SSH_PORT = 22;
    private static final int WEB_PORT = 80;

    private final Logger logger = LoggerFactory.getLogger(InternetMonitor.class);


    /**
     *
     */
    public InternetMonitor() {
        logger.info("InternetMonitor initialized...");
        this.verifyInternet();
        this.verifyNetwork();
    }

    /**
     *
     * @param URL
     * @param port
     */
    public InternetMonitor(String URL, int port) {
       this.monitor(URL,port);
    }


    /**
     *
     */
    private void verifyInternet() {

        Status status = null;

        if(this.monitor(GOOGLE, WEB_PORT) && (this.monitor(AMAZON, WEB_PORT))) {
            logger.info("Internet is UP.");
            status = new Status(Status.INTERNET,Status.UP,new java.util.Date());
            StatusToJsonFile.processObject(status);

        }
        else {
            logger.error("Internet is DOWN.");
            status = new Status(Status.INTERNET,Status.DOWN,new java.util.Date());
            StatusToJsonFile.processObject(status);
        }

    }

    /**
     *
     */
    private void verifyNetwork() {

        Status status = null;

        if(this.monitor(SYNOLOGY, SSH_PORT) && this.monitor(OPSMGR,SSH_PORT) && this.monitor(ROUTER,WEB_PORT)) {
            logger.info("Network is UP.");
            status = new Status(Status.NETWORK,Status.UP,new java.util.Date());
            StatusToJsonFile.processObject(status);
        }
        else {
            logger.error("Network is DOWN.");
            status = new Status(Status.NETWORK,Status.DOWN,new java.util.Date());
            StatusToJsonFile.processObject(status);
        }
    }

    /**
     *
     * @param url
     * @param port
     * @return
     */
    private boolean monitor(final String url, final int port) {

        boolean success = false;

        Socket socket = new Socket();
        InetSocketAddress address = new InetSocketAddress(url,port);

        try {
            socket.connect(address,3000);
            logger.info("Successfully connected to: " + url);
            success = true;
        }
        catch (IOException ioe) {
            logger.error("Error connecting to: " + url + ".  " +ioe.getMessage());
            success = false;
        }
        finally {
            try {
                socket.close();
            }
            catch (IOException ioe) {
                logger.warn("Exception closing socket: " + ioe);
            }
        }

        return success;
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {

        new InternetMonitor();

    }
}

