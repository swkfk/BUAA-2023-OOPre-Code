import org.junit.Test;

import static org.junit.Assert.*;

public class EquipmentRegularTest {

    @Test
    public void getBelonging() {
        assertEquals("RegularEquipment", new EquipmentRegular(1000, "Equ", 10, 100).getBelonging());
    }
}