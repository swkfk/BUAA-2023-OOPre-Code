import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FoodTest {

    private Food food;

    @Before
    public void buildFood() {
        food = new Food(1000, "Candy", 12, 312);
    }

    @Test
    public void getEnergy() {
        assertEquals(12, food.getEnergy());
    }

    @Test
    public void getName() {
        assertEquals("Candy", food.getName());
    }
}