package com.bmincey.inet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApplicationProperties {

    private static final String APP_PROPERTIES = "application.properties";

    private static final String FILENAME = "output.file.name";
    private static final String COMPLETED_FILES = "completed.files";

    // Internet addresses
    private static final String GOOGLE_ADDR = "google.inet.addr";
    private static final String AMAZON_ADDR = "amazon.inet.addr";

    // Internal network addresses
    private static final String SRV1_ADDR = "srv1.inet.addr";
    private static final String SRV2_ADDR = "srv2.inet.addr";
    private static final String SRV3_ADDR = "srv3.inet.addr";

    private final Logger logger = LoggerFactory.getLogger(ApplicationProperties.class);

    private String jsonFileName;
    private String completedFiles;
    private String amazon;
    private String google;
    private String srv1;
    private String srv2;
    private String srv3;

    /**
     *
     */
    public ApplicationProperties() {

        try {
            Properties properties = new Properties();
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(APP_PROPERTIES);

            if(inputStream != null) {
                properties.load(inputStream);

                jsonFileName = properties.getProperty(FILENAME);
                completedFiles = properties.getProperty(COMPLETED_FILES);
                amazon = properties.getProperty(GOOGLE_ADDR);
                google = properties.getProperty(AMAZON_ADDR);
                srv1 = properties.getProperty(SRV1_ADDR);
                srv2 = properties.getProperty(SRV2_ADDR);
                srv3 = properties.getProperty(SRV3_ADDR);
            }
            else {
                logger.error("Property file not found!");
            }
        }
        catch (IOException ioe) {
            logger.error(ioe.getMessage());
        }
        catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /**
     *
     * @return
     */
    public String getFileName() {

        return this.jsonFileName;
    }

    /**
     *
     * @return
     */
    public String getAmazon() {
        return this.amazon;
    }

    /**
     *
     * @return
     */
    public String getGoogle() {
        return this.google;
    }

    /**
     *
     * @return
     */
    public String getSrv1() {
        return this.srv1;
    }

    /**
     *
     * @return
     */
    public String getSrv2() {
        return this.srv2;
    }

    /**
     *
     * @return
     */
    public String getSrv3() {
        return this.srv3;
    }

    /**
     *
     * @return
     */
    public String getCompletedFiles() {
        return this.completedFiles;
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        ApplicationProperties appProps = new ApplicationProperties();
        System.out.println(appProps.getFileName());
        System.out.println(appProps.getCompletedFiles());
        System.out.println(appProps.getGoogle());
        System.out.println(appProps.getAmazon());
        System.out.println(appProps.getSrv1());
        System.out.println(appProps.getSrv2());
        System.out.println(appProps.getSrv3());

    }
}
