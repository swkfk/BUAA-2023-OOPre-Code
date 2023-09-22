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

    public void obtainBottle(int botID, String botName, int capacity) {
        bottles.put(botID, new Bottle(botID, botName, capacity));
    }

    public void dropBottle(int botID) {
        String name = bottles.get(botID).getName();
        bottles.remove(botID);
        System.out.println(bottles.size() + " " + name);
    }

    public void obtainEquipment(int equId, String equName, int equStar) {
        equipments.put(equId, new Equipment(equId, equName, equStar));
    }

    public void dropEquipment(int equID) {
        String name = equipments.get(equID).getName();
        equipments.remove(equID);
        System.out.println(equipments.size() + " " + name);
    }

    public void enhanceEquipment(int equID) {
        Equipment equipment = equipments.get(equID);
        equipment.enhanceStar();
        System.out.println(equipment.getName() + " " + equipment.getStar());
    }

    public void obtainFood(int foodID, String foodName, int foodEnergy) {
        foods.put(foodID, new Food(foodID, foodName, foodEnergy));
    }

    public void dropFood(int foodId) {
        String name = foods.get(foodId).getName();
        foods.remove(foodId);
        System.out.println(foods.size() + " " + name);
    }
}
