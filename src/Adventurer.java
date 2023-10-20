import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;

public class Adventurer {
    private final int id;

    private final String name;

    private final HashMap<Integer, Bottle> bottles;
    private final HashMap<Integer, Equipment> equipments;
    private final HashMap<Integer, Food> foods;
    private final Backpack backpack;

    private final ArrayList<LoggerBase> loggerAttacker;
    private final ArrayList<LoggerBase> loggerAttackee;

    private int level;
    private int power;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

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
        this.loggerAttacker = new ArrayList<>();
        this.loggerAttackee = new ArrayList<>();
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
        backpack.dropBottle(name, botID);
        if (isPrint) {
            System.out.println(bottles.size() + " " + name);
        }
    }

    public void fetchBottle(int botID) {
        String name = bottles.get(botID).getName();
        backpack.tryAddBottle(botID, name);
    }

    public boolean checkBottle(String botName) {
        return backpack.getBottleId(botName) != -1;
    }

    public void obtainEquipment(int equId, String equName, int equStar) {
        equipments.put(equId, new Equipment(equId, equName, equStar));
    }

    public void dropEquipment(int equID) {
        String name = equipments.get(equID).getName();
        equipments.remove(equID);
        backpack.dropEquipment(name, equID);
        System.out.println(equipments.size() + " " + name);
    }

    public void fetchEquipment(int equID) {
        String name = equipments.get(equID).getName();
        backpack.tryAddEquipment(equID, name);
    }

    public boolean checkEquipment(String equName) {
        return backpack.getEquId(equName) != -1;
    }

    public void enhanceEquipment(int equID) {
        Equipment equipment = equipments.get(equID);
        equipment.enhanceStar();
        System.out.println(equipment.getName() + " " + equipment.getStar());
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
        backpack.dropFood(name, foodID);
        if (isPrint) {
            System.out.println(foods.size() + " " + name);
        }
    }

    public void fetchFood(int foodID) {
        String name = foods.get(foodID).getName();
        backpack.tryAddFood(foodID, name);
    }

    public void useBottle(String name) {
        int botId = backpack.getBottleId(name);
        if (botId == -1) {
            // Nothing to use
            System.out.println("fail to use " + name);
        } else {
            useBottleCore(botId);
        }
    }

    public void useBottleInFight(String name) {
        int botId = backpack.getBottleId(name);  // assert .. != -1
        useBottleCore(botId);
    }

    private void useBottleCore(int botId) {
        int bottleCapacity = bottles.get(botId).clearCapacity();
        this.enhancePower(bottleCapacity);
        if (bottleCapacity == 0) {
            dropBottle(botId, false);  // from the inventory & backpack
        }
        System.out.println(botId + " " + getPower());
    }

    public void doAttack(LoggerBase logger) {
        loggerAttacker.add(logger);
    }

    public int beAttacked(int damage, LoggerBase logger) {
        loggerAttackee.add(logger);
        power -= damage;
        return power;
    }

    public int useEquipmentInFight(String equName) {
        int equId = backpack.getEquId(equName);
        int equStar = equipments.get(equId).getStar();
        return level * equStar;
    }

    public void enhancePower(int powerUp) {
        power += powerUp;
    }

    public void useFood(String name) {
        int foodId = backpack.getFoodId(name);
        if (foodId == -1) {
            System.out.println("fail to eat " + name);
        } else {
            int foodEnergy = foods.get(foodId).getEnergy();
            this.enhanceLevel(foodEnergy);
            dropFood(foodId, false);
            // backpack.useFood(name);
            System.out.println(foodId + " " + getLevel());
        }
    }

    public void enhanceLevel(int up) {
        level += up;
        backpack.updateMaxBottles(level);
    }

    public void queryLoggerAttacker() {
        if (loggerAttacker.isEmpty()) {
            System.out.println("No Matched Log");
            return;
        }
        loggerAttacker.forEach(System.out::println);
    }

    public void queryLoggerAttackee() {
        if (loggerAttackee.isEmpty()) {
            System.out.println("No Matched Log");
            return;
        }
        loggerAttackee.forEach(System.out::println);
    }
}
