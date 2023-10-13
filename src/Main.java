import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GameManager mgr = new GameManager();

        int n = Integer.parseInt(scanner.nextLine().trim());
        for (int i = 0; i < n; ++i) {
            String[] strings = scanner.nextLine().trim().split(" +");
            int requireLogCount = mgr.update(new ArrayList<>(Arrays.asList(strings)));
            for (int j = 0; j < requireLogCount; ++j) {
                mgr.dispatchLog(scanner.nextLine());
            }
        }
    }
}
