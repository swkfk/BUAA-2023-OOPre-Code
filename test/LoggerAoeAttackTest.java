import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LoggerAoeAttackTest {
    private LoggerAoeAttack loggerAoeAttack;

    @Before
    public void setUp() {
        loggerAoeAttack = new LoggerAoeAttack("2023/12", "Link", "Sword");
    }

    @Test
    public void getDate() {
        assertEquals("2023/12", loggerAoeAttack.getDate());
    }

    @Test
    public void getMainAdvName() {
        assertEquals("Link", loggerAoeAttack.getMainAdvName());
    }

    @Test
    public void getMainObjName() {
        assertEquals("Sword", loggerAoeAttack.getMainObjName());
    }

    @Test
    public void testToString() {
        assertEquals("2023/12 Link AOE-attacked with Sword", loggerAoeAttack.toString());
    }
}