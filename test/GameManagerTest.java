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
        GameMgr.update(new ArrayList<>(Arrays.asList("2", "1001", "2001", "HealPotion", "10", "100", "RegularEquipment")));
        GameMgr.update(new ArrayList<>(Arrays.asList("2", "1001", "2002", "FirePotion", "5", "100", "RegularEquipment")));
        assertEquals("FirePotion", GameMgr.getAdventurers().get(1001).getBottles().get(2002).getName());
        GameMgr.update(new ArrayList<>(Arrays.asList("3", "1001", "2002")));
        assertFalse(GameMgr.getAdventurers().get(1001).getBottles().containsKey(2002));
        assertEquals(1, GameMgr.getAdventurers().get(1001).getBottles().size());
        GameMgr.update(new ArrayList<>(Arrays.asList("1", "1002", "Zelda")));
        GameMgr.update(new ArrayList<>(Arrays.asList("2", "1001", "2003", "ThunderPotion", "5", "100", "RegularEquipment")));
        assertEquals(0, GameMgr.getAdventurers().get(1002).getBottles().size());
        assertEquals(2, GameMgr.getAdventurers().get(1001).getBottles().size());
    }

    @Test
    public void equipmentTest() {
        GameMgr.update(new ArrayList<>(Arrays.asList("1", "1001", "Link")));
        GameMgr.update(new ArrayList<>(Arrays.asList("1", "1002", "Zelda")));
        GameMgr.update(new ArrayList<>(Arrays.asList("4", "1001", "3001", "Bow", "5", "100", "RegularEquipment")));
        GameMgr.update(new ArrayList<>(Arrays.asList("4", "1001", "3002", "Sword", "5", "100", "RegularEquipment")));
        GameMgr.update(new ArrayList<>(Arrays.asList("4", "1002", "3003", "Triforce", "2147483646", "100", "RegularEquipment")));
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
        GameMgr.update(new ArrayList<>(Arrays.asList("7", "1001", "1101", "Candy", "1", "111")));
        GameMgr.update(new ArrayList<>(Arrays.asList("7", "1001", "1102", "Candy", "2", "111")));
        GameMgr.update(new ArrayList<>(Arrays.asList("7", "1001", "1103", "Candy", "3", "111")));
        assertEquals("Candy", GameMgr.getAdventurers().get(1001).getFoods().get(1103).getName());

        GameMgr.update(new ArrayList<>(Arrays.asList("8", "1001", "1103")));
        assertEquals(2, GameMgr.getAdventurers().get(1001).getFoods().size());

        GameMgr.update(new ArrayList<>(Arrays.asList("1", "1002", "Zelda")));
        GameMgr.update(new ArrayList<>(Arrays.asList("7", "1001", "1109", "Candy", "9", "111")));
        assertEquals(0, GameMgr.getAdventurers().get(1002).getFoods().size());
        assertEquals(3, GameMgr.getAdventurers().get(1001).getFoods().size());
    }

    @Test
    public void useBottleTest() {
        GameMgr.update(new ArrayList<>(Arrays.asList("1", "1001", "Link")));
        GameMgr.update(new ArrayList<>(Arrays.asList("1", "1002", "Zelda")));
        GameMgr.update(new ArrayList<>(Arrays.asList("2", "1001", "2001", "Potion", "10", "100", "RegularBottle")));
        GameMgr.update(new ArrayList<>(Arrays.asList("2", "1001", "2002", "Potion", "6", "100", "RegularBottle")));
        GameMgr.update(new ArrayList<>(Arrays.asList("10", "1001", "2001")));
        GameMgr.getAdventurers().get(1001).enhanceLevel(10);  // max: 3 bottles
        GameMgr.update(new ArrayList<>(Arrays.asList("10", "1001", "2002")));
        GameMgr.update(new ArrayList<>(Arrays.asList("10", "1001", "2002")));  // Multiple add!

        assertEquals(
                2, GameMgr.getAdventurers().get(1001).getBackpack().getBottles().get("Potion").size()
        );

        GameMgr.update(new ArrayList<>(Arrays.asList("2", "1002", "2004", "Potion", "5", "100", "RegularBottle")));
        GameMgr.update(new ArrayList<>(Arrays.asList("10", "1002", "2004")));
        assertEquals(
                2, GameMgr.getAdventurers().get(1001).getBackpack().getBottles().get("Potion").size()
        );

        GameMgr.update(new ArrayList<>(Arrays.asList("2", "1001", "2003", "Potion", "5", "100", "RegularBottle")));
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

        GameMgr.update(new ArrayList<>(Arrays.asList("2", "1002", "2005", "Potion", "55", "100", "RegularBottle")));
        GameMgr.update(new ArrayList<>(Arrays.asList("10", "1002", "2005")));  // Fail to add
        GameMgr.update(new ArrayList<>(Arrays.asList("12", "1002", "Potion")));  // Will use 2004(0)
        assertEquals(505, GameMgr.getAdventurers().get(1002).getPower());

        GameMgr.update(new ArrayList<>(Arrays.asList("12", "1002", "Potion")));  // Fail to use
        assertEquals(505, GameMgr.getAdventurers().get(1002).getPower());

        // Not existed bottles
        GameMgr.update(new ArrayList<>(Arrays.asList("12", "1002", "Heal")));
        assertEquals(505, GameMgr.getAdventurers().get(1002).getPower());
    }

    @Test
    public void useEquipmentTest() {
        GameMgr.update(new ArrayList<>(Arrays.asList("1", "1001", "Link")));
        GameMgr.update(new ArrayList<>(Arrays.asList("4", "1001", "1011", "Sword", "11", "100", "RegularEquipment")));
        GameMgr.update(new ArrayList<>(Arrays.asList("4", "1001", "1012", "Sword", "12", "100", "RegularEquipment")));
        GameMgr.update(new ArrayList<>(Arrays.asList("4", "1001", "1022", "Shield", "12", "100", "RegularEquipment")));

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
        GameMgr.update(new ArrayList<>(Arrays.asList("7", "1001", "1101", "Candy", "1", "100", "RegularEquipment")));
        GameMgr.update(new ArrayList<>(Arrays.asList("7", "1002", "1102", "Candy", "2", "100", "RegularEquipment")));
        GameMgr.update(new ArrayList<>(Arrays.asList("7", "1002", "1112", "Milk", "12", "100", "RegularEquipment")));
        GameMgr.update(new ArrayList<>(Arrays.asList("7", "1001", "1103", "Candy", "3", "100", "RegularEquipment")));

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

        // Not existed food name
        GameMgr.update(new ArrayList<>(Arrays.asList("13", "1002", "Cake")));
        // The food that was eaten up
        GameMgr.update(new ArrayList<>(Arrays.asList("13", "1002", "Milk")));
        assertEquals(13, GameMgr.getAdventurers().get(1002).getLevel());
    }

    @Test
    public void comprehensiveTest3_1() {
        GameMgr.update(new ArrayList<>(Arrays.asList("1", "1001", "Link")));
        GameMgr.update(new ArrayList<>(Arrays.asList("1", "1002", "Zelda")));
        GameMgr.update(new ArrayList<>(Arrays.asList("4", "1001", "1101", "Sword", "100", "111", "RegularEquipment")));
        GameMgr.update(new ArrayList<>(Arrays.asList("4", "1002", "1102", "Shield", "99", "111", "RegularEquipment")));

        // Fetch the equipment
        GameMgr.update(new ArrayList<>(Arrays.asList("9", "1002", "1102")));
        assertNull(GameMgr.getAdventurers().get(1001).getBackpack().getEquipments().get("Sword"));
        assertNull(GameMgr.getAdventurers().get(1001).getBackpack().getEquipments().get("Shield"));
        assertNotNull(GameMgr.getAdventurers().get(1002).getBackpack().getEquipments().get("Shield"));

        // Drop the equipment directly
        GameMgr.update(new ArrayList<>(Arrays.asList("5", "1002", "1102")));
        assertNull(GameMgr.getAdventurers().get(1002).getBackpack().getEquipments().get("Shield"));

        // Fetch more bottles
        GameMgr.update(new ArrayList<>(Arrays.asList("2", "1001", "2001", "Potion", "10", "100", "RegularBottle")));
        GameMgr.update(new ArrayList<>(Arrays.asList("2", "1001", "2002", "Potion", "6", "100", "RegularBottle")));
        GameMgr.update(new ArrayList<>(Arrays.asList("2", "1001", "2003", "Potion", "2", "100", "RegularBottle")));
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
        GameMgr.update(new ArrayList<>(Arrays.asList("7", "1001", "3001", "Candy", "11", "100", "100", "RegularEquipment")));
        GameMgr.update(new ArrayList<>(Arrays.asList("7", "1001", "3002", "Candy", "22", "100", "100", "RegularEquipment")));
        GameMgr.update(new ArrayList<>(Arrays.asList("7", "1001", "3003", "Candy", "33", "100", "100", "RegularEquipment")));
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

    @Test
    public void comprehensiveTest4_1() {
        // Add adventurers
        GameMgr.update(new ArrayList<>(Arrays.asList("1", "1001", "Adv1")));
        GameMgr.update(new ArrayList<>(Arrays.asList("1", "1002", "Adv2")));
        GameMgr.update(new ArrayList<>(Arrays.asList("1", "1003", "Adv3")));

        // Add bottles
        GameMgr.update(new ArrayList<>(Arrays.asList("2", "1002", "1101", "Bot1", "100", "100", "RegularBottle")));
        GameMgr.update(new ArrayList<>(Arrays.asList("2", "1003", "1102", "Bot2", "200", "100", "RegularBottle")));
        GameMgr.update(new ArrayList<>(Arrays.asList("2", "1003", "1103", "Bot3", "300", "100", "RegularBottle")));

        // Fetch bottles
        GameMgr.update(new ArrayList<>(Arrays.asList("10", "1003", "1102")));

        // Add equipments
        GameMgr.update(new ArrayList<>(Arrays.asList("4", "1002", "1201", "Equ1", "10", "100", "RegularEquipment")));
        GameMgr.update(new ArrayList<>(Arrays.asList("4", "1002", "1202", "Equ2", "20", "100", "RegularEquipment")));

        // Fetch equipments
        GameMgr.update(new ArrayList<>(Arrays.asList("9", "1002", "1201")));

        // Do Attack
        GameMgr.update(new ArrayList<>(Arrays.asList("14", "2", "5", "Adv2", "Adv3")));
        GameMgr.dispatchLog("2000/01-Adv2-Bot3");  // Fail to use
        GameMgr.dispatchLog("2000/01-Adv3-Bot2");  // Power: 500 -> 700
        GameMgr.dispatchLog("2000/01-Adv4-Bot2");  // Not exist
        GameMgr.dispatchLog("2000/02-Adv2@#-Equ2");  // Fail to use
        assertEquals(700, GameMgr.getAdventurers().get(1003).getPower());
        GameMgr.dispatchLog("2000/02-Adv2@#-Equ1");  // Do harm: 10
        assertEquals(690, GameMgr.getAdventurers().get(1003).getPower());
        GameMgr.dispatchLog("2000/02-Adv2@adv1-Equ2");  // Fail to attack
        assertEquals(500, GameMgr.getAdventurers().get(1001).getPower());
        GameMgr.clearFightMode();

        // Coverage
        GameMgr.update(new ArrayList<>(Arrays.asList("15", "2000/01")));  // Exist
        GameMgr.update(new ArrayList<>(Arrays.asList("15", "2001/01")));  // Non-exist
        GameMgr.update(new ArrayList<>(Arrays.asList("16", "1001")));  // Non-exist
        GameMgr.update(new ArrayList<>(Arrays.asList("16", "1002")));  // Exist
        GameMgr.update(new ArrayList<>(Arrays.asList("17", "1003")));  // Exist
        GameMgr.update(new ArrayList<>(Arrays.asList("17", "1002")));  // Non-exist

        // Equipment star-up
        GameMgr.update(new ArrayList<>(Arrays.asList("6", "1002", "1201")));  // Star: 11

        // Fetch & eat food
        GameMgr.update(new ArrayList<>(Arrays.asList("7", "1002", "1301", "Food1", "3", "111")));
        GameMgr.update(new ArrayList<>(Arrays.asList("11", "1002", "1301")));
        GameMgr.update(new ArrayList<>(Arrays.asList("13", "1002", "Food1")));  // Level: 4
        assertEquals(4, GameMgr.getAdventurers().get(1002).getLevel());

        // Do attack
        GameMgr.update(new ArrayList<>(Arrays.asList("14", "3", "2", "Adv2", "Adv3", "Adv1")));
        GameMgr.dispatchLog("2002/02-Adv2@#-Equ1");  // Harm: 44
        GameMgr.dispatchLog("2002/02-Adv2@Adv1-Equ1");  // Harm: 44
        GameMgr.dispatchLog("2002/02-Adv2@Adv1-Equ");  // Not exist
        GameMgr.dispatchLog("2002/02-Adv4@Adv1-Equ1");  // Not exist
        GameMgr.dispatchLog("2002/02-Adv4@#-Equ1");  // Not exist
        assertEquals(412, GameMgr.getAdventurers().get(1001).getPower());
        assertEquals(646, GameMgr.getAdventurers().get(1003).getPower());
    }

    @Test
    public void comprehensiveTest_6_commodity() {
        // Add adventurers
        GameMgr.update(new ArrayList<>(Arrays.asList("1", "1001", "Adv1")));
        GameMgr.update(new ArrayList<>(Arrays.asList("1", "1002", "Adv2")));

        GameMgr.update(new ArrayList<>(Arrays.asList("2", "1001", "1101", "Bot01", "100", "101", "RegularBottle")));
        GameMgr.update(new ArrayList<>(Arrays.asList("2", "1001", "1102", "Bot02", "100", "102", "ReinforcedBottle", "0.3")));
        GameMgr.update(new ArrayList<>(Arrays.asList("2", "1001", "1103", "Bot03", "100", "103", "RecoverBottle", "0.8")));

        GameMgr.update(new ArrayList<>(Arrays.asList("4", "1001", "1201", "Equ01", "100", "101", "RegularEquipment")));
        GameMgr.update(new ArrayList<>(Arrays.asList("4", "1001", "1202", "Equ02", "100", "102", "EpicEquipment", "0.3")));
        GameMgr.update(new ArrayList<>(Arrays.asList("4", "1001", "1203", "Equ03", "100", "103", "CritEquipment", "80")));

        GameMgr.update(new ArrayList<>(Arrays.asList("18", "1001", "1002")));
        GameMgr.update(new ArrayList<>(Arrays.asList("19", "1001")));
        GameMgr.update(new ArrayList<>(Arrays.asList("20", "1002")));
        GameMgr.update(new ArrayList<>(Arrays.asList("21", "1001", "1103")));
    }

    @Test
    public void buyThingTest() {
        SingletonShop.clear();
        GameMgr.update(new ArrayList<>(Arrays.asList("1", "1001", "Adv1")));
        GameMgr.update(new ArrayList<>(Arrays.asList("1", "1002", "Adv2")));

        GameMgr.update(new ArrayList<>(Arrays.asList("2", "1001", "1101", "Bot01", "101", "101", "RegularBottle")));
        GameMgr.update(new ArrayList<>(Arrays.asList("2", "1001", "1102", "Bot02", "101", "102", "ReinforcedBottle", "0.3")));
        GameMgr.update(new ArrayList<>(Arrays.asList("2", "1001", "1103", "Bot03", "100", "103", "RecoverBottle", "0.8")));

        GameMgr.update(new ArrayList<>(Arrays.asList("4", "1001", "1201", "Equ01", "110", "101", "RegularEquipment")));
        GameMgr.update(new ArrayList<>(Arrays.asList("4", "1001", "1202", "Equ02", "120", "102", "EpicEquipment", "0.3")));
        GameMgr.update(new ArrayList<>(Arrays.asList("4", "1001", "1203", "Equ03", "110", "103", "CritEquipment", "80")));

        GameMgr.update(new ArrayList<>(Arrays.asList("10", "1001", "1101")));
        GameMgr.update(new ArrayList<>(Arrays.asList("3", "1001", "1101")));
        GameMgr.update(new ArrayList<>(Arrays.asList("10", "1001", "1102")));
        GameMgr.update(new ArrayList<>(Arrays.asList("10", "1001", "1102")));
        GameMgr.update(new ArrayList<>(Arrays.asList("10", "1001", "1103")));
        GameMgr.update(new ArrayList<>(Arrays.asList("22", "1001")));

        GameMgr.update(new ArrayList<>(Arrays.asList("23", "1001", "2101", "BotBuy", "RegularBottle")));
        GameMgr.update(new ArrayList<>(Arrays.asList("23", "1002", "2102", "BotBuy", "RegularBottle")));

        assertEquals(100, GameMgr.getAdventurers().get(1001).getBottles().get(2101).getCapacity());
        assertEquals("RegularBottle", GameMgr.getAdventurers().get(1001).getBottles().get(2101).getBelonging());

        GameMgr.update(new ArrayList<>(Arrays.asList("9", "1001", "1201")));
        GameMgr.update(new ArrayList<>(Arrays.asList("9", "1001", "1202")));
        GameMgr.update(new ArrayList<>(Arrays.asList("9", "1001", "1203")));

        GameMgr.update(new ArrayList<>(Arrays.asList("7", "1001", "1301", "Food", "211", "100")));
        GameMgr.update(new ArrayList<>(Arrays.asList("8", "1001", "1301")));
        GameMgr.update(new ArrayList<>(Arrays.asList("22", "1001")));

        assertEquals(1, GameMgr.getAdventurers().get(1001).countCommodity());

        GameMgr.update(new ArrayList<>(Arrays.asList("23", "1001", "3101", "EquBuy", "EpicEquipment", "0.123")));
        assertEquals(113, GameMgr.getAdventurers().get(1001).getEquipments().get(3101).getStar());

    }
}