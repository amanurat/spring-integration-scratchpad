package rabbit.config.processing;

/**
 * Created by ben on 07/04/16.
 */
public class CoreBusinessLogic {

    public static String doSomeWork(String message){
        return message.toUpperCase();
    }
}
