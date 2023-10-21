import javafx.util.Pair;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AdventurerTest {

    private Adventurer adventurer;

    @Before
    public void buildAdventurer() {
        adventurer = new Adventurer(1000, "TheHero");
    }

    @Test
    public void getId() {
        assertEquals(1000, adventurer.getId());
    }

    @Test
    public void getName() {
        assertEquals("TheHero", adventurer.getName());
    }

    @Test
    public void checkBottle() {
        assertFalse(adventurer.checkBottle("potion"));
        adventurer.obtainBottle(1002, new Bottle(1002, "potion", 8, 0));
        assertFalse(adventurer.checkBottle("potion"));
        adventurer.fetchBottle(1002);
        assertTrue(adventurer.checkBottle("potion"));
        assertFalse(adventurer.checkBottle("Potion"));
        adventurer.dropBottle(1002, false);
        assertFalse(adventurer.checkBottle("potion"));
    }

    @Test
    public void checkEquipment() {
        assertFalse(adventurer.checkEquipment("armor"));
        adventurer.obtainEquipment(1002, new Equipment(1002, "armor", 8, 100));
        assertFalse(adventurer.checkEquipment("armor"));
        adventurer.fetchEquipment(1002);
        assertTrue(adventurer.checkEquipment("armor"));
        assertFalse(adventurer.checkEquipment("Armor"));
        adventurer.dropEquipment(1002);
        assertFalse(adventurer.checkEquipment("armor"));
    }

    @Test
    public void useBottleInFight() {
        adventurer.obtainBottle(1111, new Bottle(1111, "potion", 5, 111));
        adventurer.obtainBottle(1122, new Bottle(1122, "potion", 6, 122));
        adventurer.fetchBottle(1122);
        adventurer.fetchBottle(1111);

        adventurer.useBottleInFight("potion");
        assertEquals(506, adventurer.getPower());
        adventurer.useBottleInFight("potion");
        assertEquals(506, adventurer.getPower());

        adventurer.fetchBottle(1111);
        adventurer.useBottleInFight("potion");
        assertEquals(511, adventurer.getPower());
    }

    @Test
    public void useEquipmentInFight() {
        adventurer.obtainEquipment(1201, new Equipment(1201, "Armor", 6, 100));
        adventurer.enhanceEquipment(1201);
        adventurer.fetchEquipment(1201);
        assertEquals(7, adventurer.useEquipmentInFight("Armor", 0));
        adventurer.enhanceLevel(9);
        assertEquals(70, adventurer.useEquipmentInFight("Armor", 0));
    }

    @Test
    public void obtainBottle() {
        adventurer.obtainBottle(1002, new Bottle(1002, "HealPotion", 8, 100));
        adventurer.obtainBottle(1003, new Bottle(1003, "FirePotion", 8, 111));
        assertEquals(2, adventurer.getBottles().size());
        adventurer.dropBottle(1003);
        assertEquals(1, adventurer.getBottles().size());
        adventurer.dropBottle(1002);
        assertEquals(0, adventurer.getBottles().size());
        adventurer.obtainBottle(1122, new Bottle(1122, "WaterPotion", 5, 121));
        assertEquals(1, adventurer.getBottles().size());
    }

    @Test
    public void obtainEquipment() {
        adventurer.obtainEquipment(1101, new Equipment(1101, "Helmet", 5, 111));
        assertEquals(1, adventurer.getEquipments().size());
        adventurer.dropEquipment(1101);
        assertEquals(0, adventurer.getEquipments().size());
        adventurer.obtainEquipment(1102, new Equipment(1102, "Armor", 10, 222));
        assertEquals(1, adventurer.getEquipments().size());
        assertEquals("Armor", adventurer.getEquipments().get(1102).getName());
        adventurer.obtainEquipment(1111, new Equipment(1111, "Shoes", 20, 133));
        assertEquals(2, adventurer.getEquipments().size());
        assertEquals(20, adventurer.getEquipments().get(1111).getStar());
    }

    @Test
    public void obtainFood() {
        assertEquals(0, adventurer.getFoods().size());
        adventurer.obtainFood(1000, new Food(1000, "candy", 10, 100));
        assertEquals(1, adventurer.getFoods().size());
        adventurer.obtainFood(1002, new Food(1002, "meat", 50, 133));
        assertEquals(2, adventurer.getFoods().size());
        adventurer.dropFood(1002);
        assertEquals(1, adventurer.getFoods().size());
        assertEquals("candy", adventurer.getFoods().get(1000).getName());
    }

    @Test
    public void enhanceEquipment() {
        adventurer.obtainEquipment(1201, new Equipment(1201, "Armor", 6, 122));
        adventurer.enhanceEquipment(1201);
        adventurer.obtainEquipment(1202, new Equipment(1202, "Helmet", 10, 222));
        adventurer.enhanceEquipment(1201);
        assertEquals(8, adventurer.getEquipments().get(1201).getStar());
        assertEquals(10, adventurer.getEquipments().get(1202).getStar());
    }

    @Test
    public void fetchBottle() {
        adventurer.obtainBottle(1111, new Bottle(1111, "potion", 5, 121));
        adventurer.obtainBottle(1122, new Bottle(1122, "potion", 6, 211));
        adventurer.fetchBottle(1111);
        adventurer.fetchBottle(1122);  // Will Fail
        assertEquals(1, adventurer.getBackpack().getBottles().get("potion").size());
        adventurer.useBottle("potion");
        assertEquals(505, adventurer.getPower());
    }

    @Test
    public void fetchEquipment() {
        adventurer.obtainEquipment(2222, new Equipment(2222, "Helmet", 10, 322));
        adventurer.obtainEquipment(2223, new Equipment(2223, "Helmet", 5, 222));
        adventurer.obtainEquipment(3333, new Equipment(3333, "Boots", 11, 144));
        adventurer.fetchEquipment(2222);
        assertEquals(2222, adventurer.getBackpack().getEquipments().get("Helmet").intValue());
        adventurer.fetchEquipment(2223);
        assertEquals(2223, adventurer.getBackpack().getEquipments().get("Helmet").intValue());
        adventurer.fetchEquipment(3333);
        assertEquals(2223, adventurer.getBackpack().getEquipments().get("Helmet").intValue());
        assertEquals(3333, adventurer.getBackpack().getEquipments().get("Boots").intValue());
    }


    @Test
    public void fetchFood() {
        adventurer.obtainFood(4444, new Food(4444, "Candy", 4, 321));
        adventurer.obtainFood(5555, new Food(5555, "Candy", 5, 312));
        adventurer.obtainFood(3333, new Food(3333, "Candy", 3, 321));
        adventurer.fetchFood(4444);
        assertEquals(1, adventurer.getBackpack().getFoods().get("Candy").size());
        adventurer.fetchFood(5555);
        adventurer.fetchFood(3333);
        assertEquals(3, adventurer.getBackpack().getFoods().get("Candy").size());
        adventurer.useFood("Candy");
        assertEquals(2, adventurer.getBackpack().getFoods().get("Candy").size());
        assertEquals(4, adventurer.getLevel());
        adventurer.useFood("Candy");
        assertEquals(8, adventurer.getLevel());
        adventurer.useFood("Candy");
        assertEquals(13, adventurer.getLevel());
        assertEquals(0, adventurer.getBackpack().getFoods().get("Candy").size());
    }

    @Test
    public void useBottle() {
        adventurer.obtainBottle(1111, new Bottle(1111, "potion", 5, 132));
        adventurer.obtainBottle(1122, new Bottle(1122, "potion", 6, 333));
        adventurer.fetchBottle(1122);
        adventurer.fetchBottle(1111);  // Too many

        adventurer.useBottle("potion");
        assertEquals(506, adventurer.getPower());
        assertEquals(1, adventurer.getBackpack().getBottles().get("potion").size());

        adventurer.useBottle("potion");
        assertEquals(506, adventurer.getPower());
        assertEquals(0, adventurer.getBackpack().getBottles().get("potion").size());

        adventurer.useBottle("potion");  // Fail to use

        adventurer.fetchBottle(1111);
        assertEquals(1, adventurer.getBackpack().getBottles().get("potion").size());
        adventurer.useBottle("potion");
        assertEquals(1, adventurer.getBackpack().getBottles().get("potion").size());
        assertEquals(511, adventurer.getPower());

        adventurer.obtainBottle(2222, new Bottle(2222, "##", 100, 122));
        adventurer.useBottle("potion");  // Fail to use
        assertEquals(511, adventurer.getPower());
    }

    @Test
    public void useFood() {
        adventurer.obtainFood(4444, new Food(4444, "Candy", 4, 123));
        adventurer.obtainFood(5555, new Food(5555, "Candy", 5, 321));
        adventurer.obtainFood(3333, new Food(3333, "Candy", 3, 222));

        adventurer.fetchFood(4444);
        adventurer.useFood("Candy");
        assertEquals(5, adventurer.getLevel());

        adventurer.fetchFood(5555);
        adventurer.fetchFood(3333);
        assertEquals(2, adventurer.getBackpack().getFoods().get("Candy").size());

        adventurer.obtainFood(6666, new Food(6666, "##", 6, 123));
        adventurer.obtainFood(7777, new Food(7777, "##", 7, 321));
        adventurer.fetchFood(7777);
        adventurer.useFood("##");
        adventurer.useFood("##");  // Fail to use
        assertEquals(12, adventurer.getLevel());
        adventurer.useFood("Candy");
        assertEquals(15, adventurer.getLevel());
    }
}