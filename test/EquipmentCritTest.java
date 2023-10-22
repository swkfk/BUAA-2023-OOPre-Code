import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EquipmentCritTest {

    private EquipmentCrit equipment;

    @Before
    public void setUp() {
        equipment = new EquipmentCrit(1000, "equ", 10, 100, 100);
    }

    @Test
    public void getBelonging() {
        assertEquals("CritEquipment", equipment.getBelonging());
    }

    @Test
    public void getCommodity() {
        assertEquals(100, equipment.getCommodity());
    }

    @Test
    public void getDamage() {
        assertEquals(200, equipment.getDamage(10));
    }
}