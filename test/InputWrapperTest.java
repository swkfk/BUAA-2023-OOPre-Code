import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class InputWrapperTest {
    @Test
    public void testMain() {
        InputWrapper wrapper = new InputWrapper(new ArrayList<>(Arrays.asList("string", "10", "2147483648", "3.14159")));
        assertEquals("string", wrapper.get(0));
        assertEquals(10, wrapper.getInt(1));
        assertEquals(2147483648L, wrapper.getLong(2));
        assertEquals(3.14159, wrapper.getDouble(3), 0.000000001);
    }
}