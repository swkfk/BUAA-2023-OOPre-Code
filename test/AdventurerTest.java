import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AdventurerTest {

    private Adventurer adventurer;

    @Before
    public void buildAdventurer() {
        adventurer = new Adventurer(1000, "TheHero");
    }

    @Test
    public void obtainBottle() {
        adventurer.obtainBottle(1002, "HealPotion", 8);
        adventurer.obtainBottle(1003, "FirePotion", 8);
        assertEquals(2, adventurer.getBottles().size());
        adventurer.dropBottle(1003);
        assertEquals(1, adventurer.getBottles().size());
        adventurer.dropBottle(1002);
        assertEquals(0, adventurer.getBottles().size());
    }

    @Test
    public void obtainEquipment() {
        adventurer.obtainEquipment(1101, "Helmet", 5);
        assertEquals(1, adventurer.getEquipments().size());
        adventurer.dropEquipment(1101);
        assertEquals(0, adventurer.getEquipments().size());
        adventurer.obtainEquipment(1102, "Armor", 10);
        assertEquals(1, adventurer.getEquipments().size());
        assertEquals("Armor", adventurer.getEquipments().get(1102).getName());
    }

    @Test
    public void enhanceEquipment() {
        adventurer.obtainEquipment(1201, "Armor", 6);
        adventurer.enhanceEquipment(1201);
        adventurer.obtainEquipment(1202, "Helmet", 10);
        adventurer.enhanceEquipment(1201);
        assertEquals(8, adventurer.getEquipments().get(1201).getStar());
        assertEquals(10, adventurer.getEquipments().get(1202).getStar());
    }
}