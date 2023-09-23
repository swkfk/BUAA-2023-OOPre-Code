import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;

public class GameManager {
    private HashMap<Integer, Adventurer> adventurers;

    public GameManager() {
        adventurers = new HashMap<>();
    }

    public HashMap<Integer, Adventurer> getAdventurers() {
        return adventurers;
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
            case Constants.OBTAIN_BOTTLE:
            case Constants.OBTAIN_EQUIPMENT:
            case Constants.OBTAIN_FOOD: {
                update(adventurer, type, argParserObtain(input));
                break;
            }
            case Constants.DROP_BOTTLE:
            case Constants.DROP_EQUIPMENT:
            case Constants.STAR_UP_EQUIPMENT:
            case Constants.DROP_FOOD:
            case Constants.FETCH_BOTTLE:
            case Constants.FETCH_EQUIPMENT:
            case Constants.FETCH_FOOD: {
                update(adventurer, type, argParserSingleID(input));
                break;
            }
            case Constants.USE_BOTTLE:
            case Constants.USE_FOOD: {
                update(adventurer, type, argParserSingleName(input));
                break;
            }
            default: {
            }
        }
    }

    private void update(Adventurer adv, int type, Pair<String, Pair<Integer, Integer>> args) {
        switch (type) {
            case Constants.OBTAIN_BOTTLE: {
                adv.obtainBottle(args);
                break;
            }
            case Constants.OBTAIN_EQUIPMENT: {
                adv.obtainEquipment(args);
                break;
            }
            case Constants.OBTAIN_FOOD: {
                adv.obtainFood(args);
                break;
            }
            default: {
            }
        }
    }

    private void update(Adventurer adv, int type, int arg) {
        switch (type) {
            case Constants.DROP_BOTTLE: {
                adv.dropBottle(arg);
                break;
            }
            case Constants.DROP_EQUIPMENT: {
                adv.dropEquipment(arg);
                break;
            }
            case Constants.STAR_UP_EQUIPMENT: {
                adv.enhanceEquipment(arg);
                break;
            }
            case Constants.DROP_FOOD: {
                adv.dropFood(arg);
                break;
            }
            case Constants.FETCH_BOTTLE: {
                adv.fetchBottle(arg);
                break;
            }
            case Constants.FETCH_EQUIPMENT: {
                adv.fetchEquipment(arg);
                break;
            }
            case Constants.FETCH_FOOD: {
                adv.fetchFood(arg);
                break;
            }
            default: {
            }
        }
    }

    private void update(Adventurer adv, int type, String arg) {
        switch (type) {
            case Constants.USE_BOTTLE: {
                adv.useBottle(arg);
                break;
            }
            case Constants.USE_FOOD: {
                adv.useFood(arg);
                break;
            }
            default: {
            }
        }
    }

    private Pair<String, Pair<Integer, Integer>> argParserObtain(ArrayList<String> input) {
        return new Pair<>(
                input.get(Constants.OP_IDX_OBJ_NAME),
                new Pair<>(
                        Integer.parseInt(input.get(Constants.OP_IDX_OBJ_ID)),
                        Integer.parseInt(input.get(Constants.OP_IDX_OBJ_ATTR))
                )
        );
    }

    private int argParserSingleID(ArrayList<String> input) {
        return Integer.parseInt(input.get(Constants.OP_IDX_OBJ_ID));
    }

    private String argParserSingleName(ArrayList<String> input) {
        return input.get(Constants.OP_IDX_USE_OBJ_NAME);
    }
}
