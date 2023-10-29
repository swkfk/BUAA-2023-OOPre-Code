import java.util.ArrayList;
import java.util.List;

public class InputWrapper {
    private final ArrayList<String> input;

    public InputWrapper(ArrayList<String> input) {
        this.input = input;
    }

    public int getInt(int idx) {
        return Integer.parseInt(input.get(idx));
    }

    public long getLong(int idx) {
        return Long.parseLong(input.get(idx));
    }

    public double getDouble(int idx) {
        return Double.parseDouble(input.get(idx));
    }

    public String get(int idx) {
        try {
            return input.get(idx);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    public List<String> subList(int start) {
        return input.subList(start, input.size());
    }
}
