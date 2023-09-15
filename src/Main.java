import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ArrayList<ArrayList<String>> inputInfo = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine().trim());
        for (int i = 0; i < n; ++i) {
            String nextLine = scanner.nextLine();
            String[] strings = nextLine.trim().split(" +");
            inputInfo.add(new ArrayList<>(Arrays.asList(strings)));
        }

        GameManager mgr = new GameManager();
        for (ArrayList<String> info : inputInfo) {
            mgr.update(info);
        }
    }
}
