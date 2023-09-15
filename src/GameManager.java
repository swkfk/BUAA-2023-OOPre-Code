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
            case OperatorLiterals.ADD_ADVENTURER: {
                adventurers.put(advID, new Adventurer(advID, input.get(2)));
                break;
            }
            case OperatorLiterals.OBTAIN_BOTTLE: {
                adventurer.obtainBottle(
                        Integer.parseInt(input.get(2)),
                        input.get(3),
                        Integer.parseInt(input.get(4))
                );
                break;
            }
            case OperatorLiterals.DROP_BOTTLE: {
                adventurer.dropBottle(Integer.parseInt(input.get(2)));
                break;
            }
            case OperatorLiterals.OBTAIN_EQUIPMENT: {
                adventurer.obtainEquipment(
                        Integer.parseInt(input.get(2)),
                        input.get(3),
                        Integer.parseInt(input.get(4))
                );
                break;
            }
            case OperatorLiterals.DROP_EQUIPMENT: {
                adventurer.dropEquipment(Integer.parseInt(input.get(2)));
                break;
            }
            case OperatorLiterals.STAR_UP_EQUIPMENT: {
                adventurer.enhanceEquipment(Integer.parseInt(input.get(2)));
                break;
            }
            default: {
            }
        }
    }
}
