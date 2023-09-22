import java.util.HashMap;
import java.util.PriorityQueue;

public class Backpack {
    private HashMap<String, Integer> equipments;
    private HashMap<String, PriorityQueue<Integer>> bottles;
    private HashMap<String, PriorityQueue<Integer>> foods;
    private int maxBottles;

    public Backpack(int level) {
        equipments = new HashMap<>();
        bottles = new HashMap<>();
        foods = new HashMap<>();
        maxBottles = levelToCapacity(level);
    }

    public void tryAddEquipment(int equId, String name) {
        equipments.put(name, equId);
    }

    public void tryAddBottle(int botId, String name) {
        if (bottles.containsKey(name)) {
            if (bottles.get(name).size() < maxBottles) {
                bottles.get(name).add(botId);
            }
        } else {
            bottles.put(name, new PriorityQueue<>());
            bottles.get(name).add(botId);
        }
    }

    public void tryAddFood(int foodId, String name) {
        if (!foods.containsKey(name)) {
            foods.put(name, new PriorityQueue<>());
        }
        foods.get(name).add(foodId);
    }

    public void updateMaxBottles(int level) {
        maxBottles = levelToCapacity(level);
    }

    public static int levelToCapacity(int level) {
        return level / 5 + 1;
    }
}
