import java.util.ArrayList;
import java.util.HashMap;

public class GameManager {
    private HashMap<Integer, Adventurer> adventurers;

    public GameManager() {
        adventurers = new HashMap<>();
    }

    public void update(ArrayList<String> input) {
        int type = Integer.parseInt(input.get(0));
        int advID = Integer.parseInt(input.get(1));

        Adventurer adventurer = adventurers.get(advID);
        switch (type) {
            // Add an adventurer
            case 1: {
                adventurers.put(advID, new Adventurer(advID, input.get(2)));
                break;
            }
            // Give a bottle to an adventurer
            case 2: {
                adventurer.obtainBottle(
                        Integer.parseInt(input.get(2)),
                        input.get(3),
                        Integer.parseInt(input.get(4))
                );
                break;
            }
            // Remove a bottle from an adventurer
            case 3: {
                adventurer.dropBottle(Integer.parseInt(input.get(2)));
                break;
            }
            // Give equipment to an adventurer
            case 4: {
                adventurer.obtainEquipment(
                        Integer.parseInt(input.get(2)),
                        input.get(3),
                        Integer.parseInt(input.get(4))
                );
                break;
            }
            // Remove equipment from an adventurer
            case 5: {
                adventurer.dropEquipment(Integer.parseInt(input.get(2)));
                break;
            }
            // Increase the star
            case 6: {
                adventurer.enhanceEquipment(Integer.parseInt(input.get(2)));
                break;
            }
            default: {
            }
        }
    }
}
