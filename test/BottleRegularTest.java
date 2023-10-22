import org.junit.Test;

import static org.junit.Assert.*;

public class BottleRegularTest {

    @Test
    public void getBelonging() {
        assertEquals("RegularBottle", new BottleRegular(1000, "Bot", 11, 100).getBelonging());
    }
}