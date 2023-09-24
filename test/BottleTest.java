import org.junit.Test;

import static org.junit.Assert.*;

public class BottleTest {

    @Test
    public void getName() {
        Bottle bottle = new Bottle(1001, "potion#1", 5);
        assertEquals("potion#1", bottle.getName());
    }

    @Test
    public void getCapacity() {
        Bottle bottle = new Bottle(1002, "potion#2", 6);
        assertEquals(6, bottle.getCapacity());
    }

    @Test
    public void clearCapacity() {
        Bottle bottle = new Bottle(1003, "potion#3", 7);
        assertEquals(7, bottle.getCapacity());
        bottle.clearCapacity();
        assertEquals(0, bottle.getCapacity());
    }
}