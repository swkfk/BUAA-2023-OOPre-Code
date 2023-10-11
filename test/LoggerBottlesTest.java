import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LoggerBottlesTest {

    private LoggerBottles loggerBottles;

    @Before
    public void setUp(){
        loggerBottles = new LoggerBottles("2023/11", "Link", "Potion");
    }

    @Test
    public void testToString() {
        assertEquals("2023/11 Link used Potion", loggerBottles.toString());
    }
}