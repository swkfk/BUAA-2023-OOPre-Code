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
            if (strings[0].contains("-")) {
                --i;
            }
        }

        GameManager mgr = new GameManager();
        int restLogCount = 0;
        for (ArrayList<String> info : inputInfo) {
            if (restLogCount == 0) {
                mgr.clearFightMode();
                restLogCount += mgr.update(info);
            } else {
                mgr.dispatchLog(info.get(0));
                restLogCount -= 1;
            }
        }
    }
}
