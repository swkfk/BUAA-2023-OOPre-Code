import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BottleReinforcedTest {

    private BottleReinforced bottle;

    @Before
    public void setUp() {
        bottle = new BottleReinforced(1000, "Bottle", 100, 2147483648L, 0.43);
    }

    @Test
    public void getRecovery() {
        assertEquals(143, bottle.getRecovery(0));
    }

    @Test
    public void getBelonging() {
        assertEquals("ReinforcedBottle", bottle.getBelonging());
    }

    @Test
    public void getCommodity() {
        assertEquals(2147483648L, bottle.getCommodity());
    }
}