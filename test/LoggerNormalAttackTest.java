import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LoggerNormalAttackTest {
    private LoggerNormalAttack loggerNormalAttack;

    @Before
    public void setUp() {
        loggerNormalAttack = new LoggerNormalAttack("2023/10", "Link", "Sword", "Monster");
    }

    @Test
    public void getSubAdvName() {
        assertEquals("Monster", loggerNormalAttack.getSubAdvName());
    }

    @Test
    public void testToString() {
        assertEquals("2023/10 Link attacked Monster with Sword", loggerNormalAttack.toString());
    }
}