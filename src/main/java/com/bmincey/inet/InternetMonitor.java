package com.bmincey.inet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.time.LocalDateTime;


/**
 *
 */
public class InternetMonitor {

    // Ports
    private static final int SSH_PORT = 22;
    private static final int WEB_PORT = 80;

    private final Logger logger = LoggerFactory.getLogger(InternetMonitor.class);

    // load values from Application Properties
    private String amazon;
    private String google;
    private String srv1;
    private String srv2;
    private String srv3;


    /**
     *
     */
    public InternetMonitor() {
        logger.info("InternetMonitor initializing...");

        // load app props
        ApplicationProperties appProps = new ApplicationProperties();
        this.amazon = appProps.getAmazon();
        this.google = appProps.getGoogle();
        this.srv1 = appProps.getSrv1();
        this.srv2 = appProps.getSrv2();
        this.srv3 = appProps.getSrv3();

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

        LocalDateTime currentTime = LocalDateTime.now();

        if(this.monitor(this.google, WEB_PORT) && (this.monitor(this.amazon, WEB_PORT))) {
            logger.info("Internet is UP.");
            status = new Status(Status.INTERNET,Status.UP,currentTime);
            StatusToJsonFile.processObject(status);

        }
        else {
            logger.error("Internet is DOWN.");
            status = new Status(Status.INTERNET,Status.DOWN,currentTime);
            StatusToJsonFile.processObject(status);
        }

    }

    /**
     *
     */
    private void verifyNetwork() {

        Status status = null;

        LocalDateTime currentTime = LocalDateTime.now();

        if(this.monitor(this.srv1, SSH_PORT) && this.monitor(this.srv2,SSH_PORT) && this.monitor(this.srv3,WEB_PORT)) {
            logger.info("Network is UP.");
            status = new Status(Status.NETWORK,Status.UP,currentTime);
            StatusToJsonFile.processObject(status);
        }
        else {
            logger.error("Network is DOWN.");
            status = new Status(Status.NETWORK,Status.DOWN,currentTime);
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
            logger.error("Error connecting to: " + url + ".  " + ioe.getMessage());
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

