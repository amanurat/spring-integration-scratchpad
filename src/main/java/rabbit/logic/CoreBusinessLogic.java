package rabbit.logic;

/**
 * Created by ben on 07/04/16.
 */
public class CoreBusinessLogic {

    public static String doSomeWork(String message){
        return "Upper-cased message: " + message.toUpperCase();
    }
}
