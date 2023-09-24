import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class GameManagerTest {
    private GameManager GameMgr;

    @Before
    public void buildGameMgr() {
        GameMgr = new GameManager();
    }

    @Test
    public void addAdventurerTest() {
        assertEquals(0, GameMgr.getAdventurers().size());
        GameMgr.update(new ArrayList<>(Arrays.asList("1", "1001", "Link")));
        assertEquals(1, GameMgr.getAdventurers().size());
        GameMgr.update(new ArrayList<>(Arrays.asList("1", "1002", "Zelda")));
        GameMgr.update(new ArrayList<>(Arrays.asList("1", "1003", "Musha")));
        assertEquals(3, GameMgr.getAdventurers().size());
        assertTrue(GameMgr.getAdventurers().containsKey(1002));
    }

    @Test
    public void bottleTest() {
        GameMgr.update(new ArrayList<>(Arrays.asList("1", "1001", "Link")));
        GameMgr.update(new ArrayList<>(Arrays.asList("2", "1001", "2001", "HealPotion", "10")));
        GameMgr.update(new ArrayList<>(Arrays.asList("2", "1001", "2002", "FirePotion", "5")));
        assertEquals("FirePotion", GameMgr.getAdventurers().get(1001).getBottles().get(2002).getName());
        GameMgr.update(new ArrayList<>(Arrays.asList("3", "1001", "2002")));
        assertFalse(GameMgr.getAdventurers().get(1001).getBottles().containsKey(2002));
        assertEquals(1, GameMgr.getAdventurers().get(1001).getBottles().size());
        GameMgr.update(new ArrayList<>(Arrays.asList("1", "1002", "Zelda")));
        GameMgr.update(new ArrayList<>(Arrays.asList("2", "1001", "2003", "ThunderPotion", "5")));
        assertEquals(0, GameMgr.getAdventurers().get(1002).getBottles().size());
        assertEquals(2, GameMgr.getAdventurers().get(1001).getBottles().size());
    }

    @Test
    public void equipmentTest() {
        GameMgr.update(new ArrayList<>(Arrays.asList("1", "1001", "Link")));
        GameMgr.update(new ArrayList<>(Arrays.asList("1", "1002", "Zelda")));
        GameMgr.update(new ArrayList<>(Arrays.asList("4", "1001", "3001", "Bow", "5")));
        GameMgr.update(new ArrayList<>(Arrays.asList("4", "1001", "3002", "Sword", "5")));
        GameMgr.update(new ArrayList<>(Arrays.asList("4", "1002", "3003", "Triforce", "2147483646")));
        assertEquals(2, GameMgr.getAdventurers().get(1001).getEquipments().size());
        assertEquals(1, GameMgr.getAdventurers().get(1002).getEquipments().size());
        GameMgr.update(new ArrayList<>(Arrays.asList("6", "1001", "3001")));
        GameMgr.update(new ArrayList<>(Arrays.asList("6", "1002", "3003")));
        assertEquals(
                2147483647,
                GameMgr.getAdventurers().get(1002).getEquipments().get(3003).getStar()
        );
        assertEquals(
                6,
                GameMgr.getAdventurers().get(1001).getEquipments().get(3001).getStar()
        );
        GameMgr.update(new ArrayList<>(Arrays.asList("5", "1001", "3001")));
        GameMgr.update(new ArrayList<>(Arrays.asList("5", "1002", "3003")));
        assertEquals(1, GameMgr.getAdventurers().get(1001).getEquipments().size());
        assertEquals(0, GameMgr.getAdventurers().get(1002).getEquipments().size());
    }

    @Test
    public void foodTest() {
        GameMgr.update(new ArrayList<>(Arrays.asList("1", "1001", "Link")));
        GameMgr.update(new ArrayList<>(Arrays.asList("7", "1001", "1101", "Candy", "1")));
        GameMgr.update(new ArrayList<>(Arrays.asList("7", "1001", "1102", "Candy", "2")));
        GameMgr.update(new ArrayList<>(Arrays.asList("7", "1001", "1103", "Candy", "3")));
        assertEquals("Candy", GameMgr.getAdventurers().get(1001).getFoods().get(1103).getName());

        GameMgr.update(new ArrayList<>(Arrays.asList("8", "1001", "1103")));
        assertEquals(2, GameMgr.getAdventurers().get(1001).getFoods().size());

        GameMgr.update(new ArrayList<>(Arrays.asList("1", "1002", "Zelda")));
        GameMgr.update(new ArrayList<>(Arrays.asList("7", "1001", "1109", "Candy", "9")));
        assertEquals(0, GameMgr.getAdventurers().get(1002).getFoods().size());
        assertEquals(3, GameMgr.getAdventurers().get(1001).getFoods().size());
    }

    @Test
    public void useBottleTest() {
        GameMgr.update(new ArrayList<>(Arrays.asList("1", "1001", "Link")));
        GameMgr.update(new ArrayList<>(Arrays.asList("1", "1002", "Zelda")));
        GameMgr.update(new ArrayList<>(Arrays.asList("2", "1001", "2001", "Potion", "10")));
        GameMgr.update(new ArrayList<>(Arrays.asList("2", "1001", "2002", "Potion", "6")));
        GameMgr.update(new ArrayList<>(Arrays.asList("10", "1001", "2001")));
        GameMgr.getAdventurers().get(1001).enhanceLevel(10);  // max: 3 bottles
        GameMgr.update(new ArrayList<>(Arrays.asList("10", "1001", "2002")));
        GameMgr.update(new ArrayList<>(Arrays.asList("10", "1001", "2002")));  // Multiple add!

        assertEquals(
                2, GameMgr.getAdventurers().get(1001).getBackpack().getBottles().get("Potion").size()
        );

        GameMgr.update(new ArrayList<>(Arrays.asList("2", "1002", "2004", "Potion", "5")));
        GameMgr.update(new ArrayList<>(Arrays.asList("10", "1002", "2004")));
        assertEquals(
                2, GameMgr.getAdventurers().get(1001).getBackpack().getBottles().get("Potion").size()
        );

        GameMgr.update(new ArrayList<>(Arrays.asList("2", "1001", "2003", "Potion", "5")));
        GameMgr.update(new ArrayList<>(Arrays.asList("10", "1001", "2003")));
        assertEquals(
                3, GameMgr.getAdventurers().get(1001).getBackpack().getBottles().get("Potion").size()
        );

        GameMgr.update(new ArrayList<>(Arrays.asList("12", "1001", "Potion")));  // Will use 2001(10)
        assertEquals(510, GameMgr.getAdventurers().get(1001).getPower());
        assertEquals(0, GameMgr.getAdventurers().get(1001).getBottles().get(2001).getCapacity());

        GameMgr.update(new ArrayList<>(Arrays.asList("12", "1001", "Potion")));  // Will use 2001(0), drop it!
        assertEquals(510, GameMgr.getAdventurers().get(1001).getPower());
        assertEquals(
                2, GameMgr.getAdventurers().get(1001).getBackpack().getBottles().get("Potion").size()
        );
        assertEquals(2, GameMgr.getAdventurers().get(1001).getBottles().size());

        GameMgr.update(new ArrayList<>(Arrays.asList("3", "1001", "2002")));  // Drop the bottle directly
        assertEquals(
                1, GameMgr.getAdventurers().get(1001).getBackpack().getBottles().get("Potion").size()
        );

        GameMgr.update(new ArrayList<>(Arrays.asList("12", "1001", "Potion")));  // Will use 2003(5)
        assertEquals(515, GameMgr.getAdventurers().get(1001).getPower());

        GameMgr.update(new ArrayList<>(Arrays.asList("12", "1002", "Potion")));  // Will use 2004(5)
        assertEquals(505, GameMgr.getAdventurers().get(1002).getPower());

        GameMgr.update(new ArrayList<>(Arrays.asList("2", "1002", "2005", "Potion", "55")));
        GameMgr.update(new ArrayList<>(Arrays.asList("10", "1002", "2005")));  // Fail to add
        GameMgr.update(new ArrayList<>(Arrays.asList("12", "1002", "Potion")));  // Will use 2004(0)
        assertEquals(505, GameMgr.getAdventurers().get(1002).getPower());

        GameMgr.update(new ArrayList<>(Arrays.asList("12", "1002", "Potion")));  // Fail to use
        assertEquals(505, GameMgr.getAdventurers().get(1002).getPower());
    }

    @Test
    public void useEquipmentTest() {
        GameMgr.update(new ArrayList<>(Arrays.asList("1", "1001", "Link")));
        GameMgr.update(new ArrayList<>(Arrays.asList("4", "1001", "1011", "Sword", "11")));
        GameMgr.update(new ArrayList<>(Arrays.asList("4", "1001", "1012", "Sword", "12")));
        GameMgr.update(new ArrayList<>(Arrays.asList("4", "1001", "1022", "Shield", "12")));

        GameMgr.update(new ArrayList<>(Arrays.asList("9", "1001", "1012")));
        assertEquals(
                1012, GameMgr.getAdventurers().get(1001).getBackpack().getEquipments().get("Sword").intValue()
        );

        GameMgr.update(new ArrayList<>(Arrays.asList("9", "1001", "1012")));  // Duplicated add
        assertEquals(
                1012, GameMgr.getAdventurers().get(1001).getBackpack().getEquipments().get("Sword").intValue()
        );

        GameMgr.update(new ArrayList<>(Arrays.asList("9", "1001", "1011")));
        assertEquals(
                1011, GameMgr.getAdventurers().get(1001).getBackpack().getEquipments().get("Sword").intValue()
        );

        GameMgr.update(new ArrayList<>(Arrays.asList("5", "1001", "1011")));  // Drop the equipment
        assertNull(GameMgr.getAdventurers().get(1001).getBackpack().getEquipments().get("Sword"));

        GameMgr.update(new ArrayList<>(Arrays.asList("9", "1001", "1022")));
        assertNull(GameMgr.getAdventurers().get(1001).getBackpack().getEquipments().get("Sword"));

        GameMgr.update(new ArrayList<>(Arrays.asList("9", "1001", "1012")));
        assertEquals(
                1012, GameMgr.getAdventurers().get(1001).getBackpack().getEquipments().get("Sword").intValue()
        );
    }

    @Test
    public void useFoodTest() {
        GameMgr.update(new ArrayList<>(Arrays.asList("1", "1001", "Link")));
        GameMgr.update(new ArrayList<>(Arrays.asList("1", "1002", "Zelda")));
        GameMgr.update(new ArrayList<>(Arrays.asList("7", "1001", "1101", "Candy", "1")));
        GameMgr.update(new ArrayList<>(Arrays.asList("7", "1002", "1102", "Candy", "2")));
        GameMgr.update(new ArrayList<>(Arrays.asList("7", "1002", "1112", "Milk", "12")));
        GameMgr.update(new ArrayList<>(Arrays.asList("7", "1001", "1103", "Candy", "3")));

        GameMgr.update(new ArrayList<>(Arrays.asList("11", "1001", "1103")));
        GameMgr.update(new ArrayList<>(Arrays.asList("11", "1001", "1101")));
        assertEquals(
                2, GameMgr.getAdventurers().get(1001).getBackpack().getFoods().get("Candy").size()
        );

        GameMgr.update(new ArrayList<>(Arrays.asList("13", "1001", "Candy")));
        assertEquals(
                1, GameMgr.getAdventurers().get(1001).getBackpack().getFoods().get("Candy").size()
        );
        assertEquals(2, GameMgr.getAdventurers().get(1001).getLevel());

        GameMgr.update(new ArrayList<>(Arrays.asList("13", "1001", "Candy")));
        assertEquals(5, GameMgr.getAdventurers().get(1001).getLevel());

        GameMgr.update(new ArrayList<>(Arrays.asList("13", "1001", "Candy")));  // Fail to eat
        assertEquals(
                0, GameMgr.getAdventurers().get(1001).getBackpack().getFoods().get("Candy").size()
        );
        assertEquals(5, GameMgr.getAdventurers().get(1001).getLevel());

        GameMgr.update(new ArrayList<>(Arrays.asList("11", "1002", "1102")));
        GameMgr.update(new ArrayList<>(Arrays.asList("11", "1002", "1112")));
        GameMgr.update(new ArrayList<>(Arrays.asList("8", "1002", "1102")));  // Drop it directly
        GameMgr.update(new ArrayList<>(Arrays.asList("13", "1002", "Candy")));  // Fail to use
        assertEquals(
                0, GameMgr.getAdventurers().get(1002).getBackpack().getFoods().get("Candy").size()
        );
        assertEquals(1, GameMgr.getAdventurers().get(1002).getLevel());

        GameMgr.update(new ArrayList<>(Arrays.asList("13", "1002", "Milk")));
        assertEquals(
                0, GameMgr.getAdventurers().get(1002).getBackpack().getFoods().get("Milk").size()
        );
        assertEquals(13, GameMgr.getAdventurers().get(1002).getLevel());
    }

    @Test
    public void comprehensiveTest3_1() {
        GameMgr.update(new ArrayList<>(Arrays.asList("1", "1001", "Link")));
        GameMgr.update(new ArrayList<>(Arrays.asList("1", "1002", "Zelda")));
        GameMgr.update(new ArrayList<>(Arrays.asList("4", "1001", "1101", "Sword", "100")));
        GameMgr.update(new ArrayList<>(Arrays.asList("4", "1002", "1102", "Shield", "99")));

        // Fetch the equipment
        GameMgr.update(new ArrayList<>(Arrays.asList("9", "1002", "1102")));
        assertNull(GameMgr.getAdventurers().get(1001).getBackpack().getEquipments().get("Sword"));
        assertNull(GameMgr.getAdventurers().get(1001).getBackpack().getEquipments().get("Shield"));
        assertNotNull(GameMgr.getAdventurers().get(1002).getBackpack().getEquipments().get("Shield"));

        // Drop the equipment directly
        GameMgr.update(new ArrayList<>(Arrays.asList("5", "1002", "1102")));
        assertNull(GameMgr.getAdventurers().get(1002).getBackpack().getEquipments().get("Shield"));

        // Fetch more bottles
        GameMgr.update(new ArrayList<>(Arrays.asList("2", "1001", "2001", "Potion", "10")));
        GameMgr.update(new ArrayList<>(Arrays.asList("2", "1001", "2002", "Potion", "6")));
        GameMgr.update(new ArrayList<>(Arrays.asList("2", "1001", "2003", "Potion", "2")));
        GameMgr.update(new ArrayList<>(Arrays.asList("10", "1001", "2002")));
        GameMgr.update(new ArrayList<>(Arrays.asList("10", "1001", "2002")));  // Duplicated
        GameMgr.update(new ArrayList<>(Arrays.asList("10", "1001", "2001")));  // More
        assertEquals(
                1, GameMgr.getAdventurers().get(1001).getBackpack().getBottles().get("Potion").size()
        );

        // Use the bottle
        GameMgr.update(new ArrayList<>(Arrays.asList("12", "1001", "Potion")));
        assertEquals(506, GameMgr.getAdventurers().get(1001).getPower());

        // Fetch the food
        GameMgr.update(new ArrayList<>(Arrays.asList("7", "1001", "3001", "Candy", "11")));
        GameMgr.update(new ArrayList<>(Arrays.asList("7", "1001", "3002", "Candy", "22")));
        GameMgr.update(new ArrayList<>(Arrays.asList("7", "1001", "3003", "Candy", "33")));
        GameMgr.update(new ArrayList<>(Arrays.asList("11", "1001", "3003")));
        GameMgr.update(new ArrayList<>(Arrays.asList("11", "1001", "3001")));

        // Use the food
        GameMgr.update(new ArrayList<>(Arrays.asList("13", "1001", "Candy")));
        assertEquals(12, GameMgr.getAdventurers().get(1001).getLevel());

        // More bottles
        GameMgr.update(new ArrayList<>(Arrays.asList("10", "1001", "2001")));
        GameMgr.update(new ArrayList<>(Arrays.asList("12", "1001", "Potion")));  // Will use 2001(10)
        assertEquals(516, GameMgr.getAdventurers().get(1001).getPower());
        assertEquals(
                2, GameMgr.getAdventurers().get(1001).getBackpack().getBottles().get("Potion").size()
        );  // There are two empty bottles

        // Use empty bottles
        GameMgr.update(new ArrayList<>(Arrays.asList("12", "1001", "Potion")));
        assertEquals(516, GameMgr.getAdventurers().get(1001).getPower());
        assertEquals(
                1, GameMgr.getAdventurers().get(1001).getBackpack().getBottles().get("Potion").size()
        );

        // Drop bottles directly
        GameMgr.update(new ArrayList<>(Arrays.asList("3", "1001", "2002")));
        assertEquals(
                0, GameMgr.getAdventurers().get(1001).getBackpack().getBottles().get("Potion").size()
        );
    }
}