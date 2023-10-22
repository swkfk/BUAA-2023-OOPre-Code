import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EquipmentEpicTest {
    private EquipmentEpic equipment;

    @Before
    public void setUp() {
        equipment = new EquipmentEpic(1000, "equ", 10, 100, 0.22);
    }

    @Test
    public void getBelonging() {
        assertEquals("EpicEquipment", equipment.getBelonging());
    }

    @Test
    public void getCommodity() {
        assertEquals(100, equipment.getCommodity());
    }

    @Test
    public void getDamage() {
        assertEquals(48, equipment.getDamage(222));
    }
}