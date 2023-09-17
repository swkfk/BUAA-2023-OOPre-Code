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
}