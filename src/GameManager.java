import java.util.ArrayList;
import java.util.HashMap;

public class GameManager {
    private HashMap<Integer, Adventurer> adventurers;

    public GameManager() {
        adventurers = new HashMap<>();
    }

    public void update(ArrayList<String> input) {
        int type = Integer.parseInt(input.get(Constants.OP_IDX_TYPE));
        int advID = Integer.parseInt(input.get(Constants.OP_IDX_ADV_ID));

        Adventurer adventurer = adventurers.get(advID);
        switch (type) {
            case Constants.ADD_ADVENTURER: {
                adventurers.put(advID, new Adventurer(advID, input.get(Constants.OP_IDX_ADV_NAME)));
                break;
            }
            case Constants.OBTAIN_BOTTLE: {
                adventurer.obtainBottle(
                        Integer.parseInt(input.get(Constants.OP_IDX_OBJ_ID)),
                        input.get(Constants.OP_IDX_OBJ_NAME),
                        Integer.parseInt(input.get(Constants.OP_IDX_BOT_CAP))
                );
                break;
            }
            case Constants.DROP_BOTTLE: {
                adventurer.dropBottle(Integer.parseInt(input.get(Constants.OP_IDX_OBJ_ID)));
                break;
            }
            case Constants.OBTAIN_EQUIPMENT: {
                adventurer.obtainEquipment(
                        Integer.parseInt(input.get(Constants.OP_IDX_OBJ_ID)),
                        input.get(Constants.OP_IDX_OBJ_NAME),
                        Integer.parseInt(input.get(Constants.OP_IDX_EQU_STAR))
                );
                break;
            }
            case Constants.DROP_EQUIPMENT: {
                adventurer.dropEquipment(Integer.parseInt(input.get(Constants.OP_IDX_OBJ_ID)));
                break;
            }
            case Constants.STAR_UP_EQUIPMENT: {
                adventurer.enhanceEquipment(Integer.parseInt(input.get(Constants.OP_IDX_OBJ_ID)));
                break;
            }
            default: {
            }
        }
    }
}
