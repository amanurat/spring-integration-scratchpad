package demo;

import demo.logic.BusinessLogic;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by BWilcock on 20/04/2016.
 */
public class CoreLogicTest {

    @Test
    public void testBusinessLogic(){
        assertEquals("**** A ****", BusinessLogic.doSomeWork("a"));
    }
}
