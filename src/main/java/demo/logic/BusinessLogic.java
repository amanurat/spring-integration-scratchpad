package demo.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ben on 07/04/16.
 */
public class BusinessLogic {

    private static final Logger LOG = LoggerFactory.getLogger(BusinessLogic.class);
    private static int count = 0;

    public static String doSomeWork(String message){
        LOG.info("Processing message {}: '{}'", count, message);
        String result = "**** ("+count+") " +message.toUpperCase() + " ****";
        LOG.info("Processed message is: {}", result);
        count++;
        return result;
    }
}
