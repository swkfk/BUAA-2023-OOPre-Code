import org.junit.Test;

import static org.junit.Assert.*;

public class BottleTest {

    @Test
    public void getName() {
        Bottle bottle = new Bottle(1001, "potion#1", 5);
        assertEquals("potion#1", bottle.getName());
    }
}