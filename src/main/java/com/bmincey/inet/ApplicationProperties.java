package com.bmincey.inet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApplicationProperties {

    private static final String APP_PROPERTIES = "application.properties";

    private static final String FILENAME = "output.file.name";

    private final Logger logger = LoggerFactory.getLogger(ApplicationProperties.class);

    private String jsonFileName;

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
     * @param args
     */
    public static void main(String[] args) {
        ApplicationProperties appProps = new ApplicationProperties();
        System.out.println(appProps.getFileName());
    }
}
