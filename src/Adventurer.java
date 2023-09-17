import java.util.HashMap;

public class Adventurer {
    private int id;
    private String name;

    private HashMap<Integer, Bottle> bottles;

    private HashMap<Integer, Equipment> equipments;

    public HashMap<Integer, Equipment> getEquipments() {
        return equipments;
    }

    public HashMap<Integer, Bottle> getBottles() {
        return bottles;
    }

    public Adventurer(int id, String name) {
        this.id = id;
        this.name = name;
        this.bottles = new HashMap<>();
        this.equipments = new HashMap<>();
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
}
