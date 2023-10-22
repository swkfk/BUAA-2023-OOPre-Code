import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BottleRecoverTest {

    private BottleRecover bottle1;
    private BottleRecover bottle2;

    @Before
    public void setUp() {
        bottle1 = new BottleRecover(1001, "Recovery", 20, 100, 0.4);
        bottle2 = new BottleRecover(1002, "Recovery", 0, 100, 0.4);
    }

    @Test
    public void getRecovery() {
        assertEquals(40, bottle1.getRecovery(102));
        assertEquals(0, bottle2.getRecovery(102));
    }

    @Test
    public void getBelonging() {
        assertEquals("RecoverBottle", bottle1.getBelonging());
    }

    @Test
    public void getCommodity() {
        assertEquals(100, bottle1.getCommodity());
    }
}