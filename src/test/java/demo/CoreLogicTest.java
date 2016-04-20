package demo;

import demo.processing.CoreBusinessLogic;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by BWilcock on 20/04/2016.
 */
public class CoreLogicTest {

    @Test
    public void testBusinessLogic(){
        assertEquals("**** A ****", CoreBusinessLogic.doSomeWork("a"));
    }
}
