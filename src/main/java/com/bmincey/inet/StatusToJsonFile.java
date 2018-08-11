package com.bmincey.inet;


import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

public class StatusToJsonFile {

    private static final String DONE = ".DONE";

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
        String completedFiles = appProps.getCompletedFiles();

        ObjectMapper mapper = new ObjectMapper();

        logger.info(status.toString());

        long timeStamp = System.currentTimeMillis();

        try {

            File jsonFile = new File(fileName + timeStamp);

            mapper.writeValue(new File(fileName + timeStamp), status);
            String jsonString = mapper.writeValueAsString(status);
            logger.info("Successfully wrote JSON file.");
            logger.info(jsonString);

            // rename file for next step in processing by Camel
            logger.info("Renaming file: " + jsonFile);
            File doneFile = new File(completedFiles + timeStamp + DONE);
            jsonFile.renameTo(doneFile);
            logger.info("Renamed file to: " + doneFile);

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

    /**
     *
     * @param jsonString
     * @return
     */
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
        Status status = new Status(Status.INTERNET, Status.UP, LocalDateTime.now());
        StatusToJsonFile.processObject(status);

        // test next method - jsontoobject
        //String jsonString = "{\"networkType\":\"NETWORK\",\"status\":\"UP\",\"dateTime\":1510338450319}";
        //System.out.println(StatusToJsonFile.jsonToObject(jsonString));
    }
}
