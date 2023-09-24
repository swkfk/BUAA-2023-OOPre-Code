import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BackpackTest {

    private Backpack backpack;

    @Before
    public void buildBackpack() {
        backpack = new Backpack(1);
    }

    @Test
    public void tryAddEquipment() {
        backpack.tryAddEquipment(1001, "Helmet");
        backpack.tryAddEquipment(1002, "Helmet");
        assertEquals(1002, backpack.getEquipments().get("Helmet").intValue());

        backpack.tryAddEquipment(1003, "Armor");
        assertEquals(1002, backpack.getEquipments().get("Helmet").intValue());
        assertEquals(1003, backpack.getEquipments().get("Armor").intValue());
    }

    @Test
    public void tryAddBottle() {
        backpack.tryAddBottle(1001, "#1");
        backpack.tryAddBottle(1002, "#1");  // Too many!
        assertEquals(1, backpack.getBottles().get("#1").size());

        backpack.tryAddBottle(1003, "#2");
        assertEquals(1, backpack.getBottles().get("#2").size());

        backpack.updateMaxBottles(5);  // max: 2
        backpack.tryAddBottle(1002, "#1");
        assertEquals(2, backpack.getBottles().get("#1").size());
        assertEquals(1, backpack.getBottles().get("#2").size());
        assertEquals(-1, backpack.getBottleId("##"));  // Not exist

        backpack.useBottle("#2");  // Tested below, use up the #2 bottle
        assertEquals(-1, backpack.getBottleId("#2"));
        assertEquals(1001, backpack.getBottleId("#1"));

        backpack.tryAddBottle(1004, "#1");
        assertEquals(1001, backpack.getBottleId("#1"));

    }

    @Test
    public void tryAddFood() {
        backpack.tryAddFood(1002, "Meat");
        backpack.tryAddFood(1001, "Meat");
        assertEquals(2, backpack.getFoods().get("Meat").size());
        assertEquals(1001, backpack.getFoods().get("Meat").peek().intValue());

        backpack.tryAddFood(1000, "Candy");
        assertEquals(1001, backpack.getFoodId("Meat"));
        assertEquals(1000, backpack.getFoodId("Candy"));

        assertEquals(-1, backpack.getFoodId("Carrot"));

        backpack.useFood("Candy");
        assertEquals(-1, backpack.getFoodId("Candy"));
    }

    @Test
    public void useBottle() {
        backpack.tryAddBottle(1000, "potion");
        backpack.useBottle("potion");
        assertEquals(0, backpack.getBottles().get("potion").size());

        backpack.updateMaxBottles(5);
        backpack.tryAddBottle(1001, "potion");
        backpack.tryAddBottle(1002, "potion");
        backpack.tryAddBottle(1004, "potion");
        backpack.tryAddBottle(1003, "potion#1");
        assertEquals(2, backpack.getBottles().get("potion").size());

        backpack.useBottle("potion#1");
        assertEquals(2, backpack.getBottles().get("potion").size());

        backpack.useBottle("potion");
        assertEquals(1, backpack.getBottles().get("potion").size());
    }

    @Test
    public void useFood() {
        backpack.tryAddFood(1001, "Candy");
        assertEquals(1, backpack.getFoods().get("Candy").size());

        backpack.useFood("Candy");
        assertEquals(0, backpack.getFoods().get("Candy").size());

        backpack.tryAddFood(1002, "Meat");
        assertEquals(0, backpack.getFoods().get("Candy").size());
    }

    @Test
    public void levelToCapacity() {
        assertEquals(1, Backpack.levelToCapacity(1));
        assertEquals(1, Backpack.levelToCapacity(4));
        assertEquals(2, Backpack.levelToCapacity(5));
        assertEquals(2, Backpack.levelToCapacity(9));
        assertEquals(3, Backpack.levelToCapacity(10));
        assertEquals(3, Backpack.levelToCapacity(11));
        assertEquals(3, Backpack.levelToCapacity(14));
        assertEquals(21, Backpack.levelToCapacity(100));
        assertEquals(21, Backpack.levelToCapacity(101));
    }
}