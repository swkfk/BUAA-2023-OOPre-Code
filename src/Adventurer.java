import javafx.util.Pair;

import java.util.HashMap;

public class Adventurer {
    private int id;
    private String name;
    private HashMap<Integer, Bottle> bottles;
    private HashMap<Integer, Equipment> equipments;
    private HashMap<Integer, Food> foods;
    private Backpack backpack;
    private int level;
    private int power;

    public HashMap<Integer, Equipment> getEquipments() {
        return equipments;
    }

    public HashMap<Integer, Bottle> getBottles() {
        return bottles;
    }

    public HashMap<Integer, Food> getFoods() {
        return foods;
    }

    public Backpack getBackpack() {
        return backpack;
    }

    public int getPower() {
        return power;
    }

    public int getLevel() {
        return level;
    }

    public Adventurer(int id, String name) {
        this.id = id;
        this.name = name;
        this.level = 1;
        this.power = 500;
        this.bottles = new HashMap<>();
        this.equipments = new HashMap<>();
        this.foods = new HashMap<>();
        this.backpack = new Backpack(this.level);
    }

    public void obtainBottle(Pair<String, Pair<Integer, Integer>> args) {
        obtainBottle(args.getValue().getKey(), args.getKey(), args.getValue().getValue());
    }

    public void obtainBottle(int botID, String botName, int capacity) {
        bottles.put(botID, new Bottle(botID, botName, capacity));
    }

    public void dropBottle(int botID) {
        dropBottle(botID, true);
    }

    public void dropBottle(int botID, boolean isPrint) {
        String name = bottles.get(botID).getName();
        bottles.remove(botID);
        if (isPrint) {
            System.out.println(bottles.size() + " " + name);
        }
    }

    public void fetchBottle(int botID) {
        String name = bottles.get(botID).getName();
        backpack.tryAddBottle(botID, name);
    }

    public void obtainEquipment(Pair<String, Pair<Integer, Integer>> args) {
        obtainEquipment(args.getValue().getKey(), args.getKey(), args.getValue().getValue());
    }

    public void obtainEquipment(int equId, String equName, int equStar) {
        equipments.put(equId, new Equipment(equId, equName, equStar));
    }

    public void dropEquipment(int equID) {
        String name = equipments.get(equID).getName();
        equipments.remove(equID);
        System.out.println(equipments.size() + " " + name);
    }

    public void fetchEquipment(int equID) {
        String name = equipments.get(equID).getName();
        backpack.tryAddEquipment(equID, name);
    }

    public void enhanceEquipment(int equID) {
        Equipment equipment = equipments.get(equID);
        equipment.enhanceStar();
        System.out.println(equipment.getName() + " " + equipment.getStar());
    }

    public void obtainFood(Pair<String, Pair<Integer, Integer>> args) {
        obtainFood(args.getValue().getKey(), args.getKey(), args.getValue().getValue());
    }

    public void obtainFood(int foodID, String foodName, int foodEnergy) {
        foods.put(foodID, new Food(foodID, foodName, foodEnergy));
    }

    public void dropFood(int foodID) {
        dropFood(foodID, true);
    }

    public void dropFood(int foodID, boolean isPrint) {
        String name = foods.get(foodID).getName();
        foods.remove(foodID);
        if (isPrint) {
            System.out.println(foods.size() + " " + name);
        }
    }

    public void fetchFood(int foodID) {
        String name = foods.get(foodID).getName();
        backpack.tryAddFood(foodID, name);
    }

    public Pair<Integer, Integer> useBottle(String name) {
        int botId = backpack.getBottleId(name);
        if (botId == -1) {
            // Nothing to use
            return new Pair<>(-1, 0);
        } else {
            // use: botId, name
            int bottleCapacity = bottles.get(botId).getCapacity();
            if (bottleCapacity == 0) {
                // remove it!
                dropBottle(botId, false);  // from the inventory
                backpack.useBottle(name);  // from the backpack
                return new Pair<>(0, botId);
            } else {
                return new Pair<>(bottles.get(botId).clearCapacity(), botId);
            }
        }
    }

    public void enhancePower(int powerUp) {
        power += powerUp;
    }

    public Pair<Integer, Integer> useFood(String name) {
        int foodId = backpack.getFoodId(name);
        if (foodId == -1) {
            return new Pair<>(-1, 0);
        } else {
            int foodEnergy = foods.get(foodId).getEnergy();
            dropFood(foodId, false);
            backpack.useFood(name);
            return new Pair<>(foodEnergy, foodId);
        }
    }

    public void enhanceLevel(int up) {
        level += up;
        backpack.updateMaxBottles(level);
    }
}
