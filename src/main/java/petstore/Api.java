package petstore;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Api {

    private static Logger logger = LogManager.getLogger();
    public static void main(String[] args)
    {
        System.out.println("Log4j2 sample logging");

        logger.debug("This is a debug message");
        logger.info("This is an info message");
        logger.warn("This is a warn message");
        logger.error("This is an error message");
        logger.fatal("This is a fatal message");

    }
}
