import org.junit.Test;

import static org.junit.Assert.*;

public class EquipmentTest {

    @Test
    public void getName() {
        Equipment equipment = new Equipment(2, "Helmet", 100, 123);
        assertEquals("Helmet", equipment.getName());
    }

    @Test
    public void enhanceStar() {
        Equipment equipment = new Equipment(1, "equipment", 10, 321);
        assertEquals(10, equipment.getStar());
        for (int i = 0; i < 100; i++) {
            equipment.enhanceStar();
        }
        assertEquals(110, equipment.getStar());
    }
}