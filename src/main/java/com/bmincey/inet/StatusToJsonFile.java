package com.bmincey.inet;


import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class StatusToJsonFile {

    private static final Logger logger = LoggerFactory.getLogger(StatusToJsonFile.class);

    /**
     *
     */
    public StatusToJsonFile(Status status) {
        logger.info("StatusToJsonFile initialized.");
        this.processObject(status);
    }

    /**
     *
     * @param status
     */
    public static void processObject(Status status) {

        ApplicationProperties appProps = new ApplicationProperties();
        String fileName = appProps.getFileName();

        ObjectMapper mapper = new ObjectMapper();

        logger.info(status.toString());

        long timeStamp = System.currentTimeMillis();

        try {
            mapper.writeValue(new File(fileName + timeStamp), status);
            String jsonString = mapper.writeValueAsString(status);
            logger.info("Successfully wrote JSON file.");
            logger.info(jsonString);
        }
        catch (JsonGenerationException jge) {
            logger.error(jge.getMessage());
        }
        catch (JsonMappingException jme) {
            logger.error(jme.getMessage());
        }
        catch (IOException ioe) {
            logger.error(ioe.getMessage());
        }
    }

    public static Status jsonToObject(String jsonString) {
        Status status = null;

        ObjectMapper mapper = new ObjectMapper();

        logger.info("Convert JSON string: " + jsonString);

        try {
            status = mapper.readValue(jsonString, Status.class);
        }
        catch (JsonGenerationException jge) {
            logger.error(jge.getMessage());
        }
        catch (JsonMappingException jme) {
            logger.error(jme.getMessage());
        }
        catch (IOException ioe) {
            logger.error(ioe.getMessage());
        }

        return status;
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
       Status status = new Status(Status.INTERNET, Status.UP, new java.util.Date());
       StatusToJsonFile.processObject(status);

       // test next method - jsontoobject
       String jsonString = "{\"networkType\":\"NETWORK\",\"status\":\"UP\",\"dateTime\":1510338450319}";
       System.out.println(StatusToJsonFile.jsonToObject(jsonString));
    }
}
