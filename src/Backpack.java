import java.util.HashMap;
import java.util.PriorityQueue;

public class Backpack {
    private final HashMap<String, Integer> equipments;
    private final HashMap<String, PriorityQueue<Integer>> bottles;
    private final HashMap<String, PriorityQueue<Integer>> foods;
    private int maxBottles;

    public Backpack(int level) {
        equipments = new HashMap<>();
        bottles = new HashMap<>();
        foods = new HashMap<>();
        maxBottles = levelToCapacity(level);
    }

    public HashMap<String, PriorityQueue<Integer>> getBottles() {
        return bottles;
    }

    public HashMap<String, Integer> getEquipments() {
        return equipments;
    }

    public HashMap<String, PriorityQueue<Integer>> getFoods() {
        return foods;
    }

    public void tryAddEquipment(int equId, String name) {
        equipments.put(name, equId);
    }

    public void tryAddBottle(int botId, String name) {
        if (bottles.containsKey(name)) {
            if (bottles.get(name).size() < maxBottles && !bottles.get(name).contains(botId)) {
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
        if (!foods.get(name).contains(foodId)) {
            foods.get(name).add(foodId);
        }
    }

    public void updateMaxBottles(int level) {
        maxBottles = levelToCapacity(level);
    }

    public int getBottleId(String name) {
        if (!bottles.containsKey(name) || bottles.get(name).isEmpty()) {
            return -1;
        } else {
            return bottles.get(name).peek();  // Maybe there will be a fake warning for NPE.
        }
    }

    public int getFoodId(String name) {
        if (!foods.containsKey(name) || foods.get(name).isEmpty()) {
            return -1;
        } else {
            return foods.get(name).peek();
        }
    }

    public int getEquId(String name) {
        return equipments.getOrDefault(name, -1);
    }

    public void useBottle(String name) {
        bottles.get(name).poll();
    }

    public void dropBottle(String name, int botID) {
        if (bottles.containsKey(name)) {
            bottles.get(name).remove(botID);
        }
    }

    public void useFood(String name) {
        foods.get(name).poll();
    }

    public void dropFood(String name, int foodID) {
        if (foods.containsKey(name)) {
            foods.get(name).remove(foodID);
        }
    }

    public void dropEquipment(String name, int equID) {
        equipments.remove(name, equID);
    }

    public static int levelToCapacity(int level) {
        return level / 5 + 1;
    }
}
