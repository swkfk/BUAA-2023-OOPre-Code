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

    public String get(int idx) {
        return input.get(idx);
    }

    public List<String> subList(int start) {
        return input.subList(start, input.size());
    }

}
