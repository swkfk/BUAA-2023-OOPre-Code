import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

public class Adventurer implements ICommodity {
    private final int id;

    private final String name;

    private final HashMap<Integer, Bottle> bottles;
    private final HashMap<Integer, Equipment> equipments;
    private final HashMap<Integer, Food> foods;
    private final Backpack backpack;
    private final HashSet<Adventurer> employees;

    private final ArrayList<LoggerBase> loggerAttacker;
    private final ArrayList<LoggerBase> loggerAttackee;

    private int level;
    private int power;
    private int powerBefore;
    private long money;

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

    public HashSet<Adventurer> getEmployees() {
        return employees;
    }

    public Adventurer(int id, String name) {
        this.id = id;
        this.name = name;
        this.level = 1;
        this.power = 500;
        this.money = 0;
        this.bottles = new HashMap<>();
        this.equipments = new HashMap<>();
        this.foods = new HashMap<>();
        this.employees = new HashSet<>();
        this.backpack = new Backpack(this.level);
        this.loggerAttacker = new ArrayList<>();
        this.loggerAttackee = new ArrayList<>();
    }

    public void obtainBottle(int botID, Bottle bottle) {
        bottles.put(botID, bottle);
    }

    public void dropBottle(int botID) {
        dropBottle(botID, true);
    }

    public void dropBottle(int botID, boolean isSold) {
        Bottle toBeDropped = bottles.get(botID);
        String name = toBeDropped.getName();
        bottles.remove(botID, toBeDropped);
        backpack.dropBottle(name, botID);
        if (isSold) {
            System.out.println(bottles.size() + " " + name);
            money += SingletonShop.getInstance().stockBottle(toBeDropped);
        }
    }

    public void fetchBottle(int botID) {
        String name = bottles.get(botID).getName();
        backpack.tryAddBottle(botID, name);
    }

    public boolean checkBottle(String botName) {
        return backpack.getBottleId(botName) != -1;
    }

    public void obtainEquipment(int equId, Equipment equipment) {
        equipments.put(equId, equipment);
    }

    public void dropEquipment(int equID, boolean isSold) {
        Equipment toBeDropped = equipments.get(equID);
        String name = toBeDropped.getName();
        equipments.remove(equID, toBeDropped);
        backpack.dropEquipment(name, equID);
        if (isSold) {
            System.out.println(equipments.size() + " " + name);
            money += SingletonShop.getInstance().stockEquipment(toBeDropped);
        }
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

    public void obtainFood(int foodID, Food food) {
        foods.put(foodID, food);
    }

    public void dropFood(int foodID) {
        dropFood(foodID, true);
    }

    public void dropFood(int foodID, boolean isSold) {
        Food toBeDropped = foods.get(foodID);
        String name = toBeDropped.getName();
        foods.remove(foodID, toBeDropped);
        backpack.dropFood(name, foodID);
        if (isSold) {
            System.out.println(foods.size() + " " + name);
            money += SingletonShop.getInstance().stockFood(toBeDropped);
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
        Bottle bottle = bottles.get(botId);
        int recovery = bottle.getRecovery(power);
        int capacity = bottle.getCapacity();
        bottle.clearCapacity();
        this.enhancePower(recovery);
        if (capacity == 0) {
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

    public int useEquipmentInFight(String equName, int victimPower) {
        int equId = backpack.getEquId(equName);
        Equipment equipment = equipments.get(equId);
        if (equipment instanceof EquipmentEpic) {
            return equipments.get(equId).getDamage(victimPower);
        } else {
            return equipments.get(equId).getDamage(level);
        }
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

    public void hireAdventurer(Adventurer employee) {
        employees.add(employee);
    }

    public String queryCommodityBelongingById(int id) {
        if (null != bottles.get(id)) {
            return bottles.get(id).getBelonging();
        }
        if (null != equipments.get(id)) {
            return equipments.get(id).getBelonging();
        }
        if (null != foods.get(id)) {
            return foods.get(id).getBelonging();
        }
        for (Adventurer adv : employees) {
            if (adv.getId() == id) {
                return adv.getBelonging();
            }
        }
        return null;
    }

    @Override
    public String getBelonging() {
        return "Adventurer";
    }

    @Override
    public long getCommodity() {
        final long[] res = {0};
        // 如果只是 long 的话，传给 lambda 是值传递，因此要求 final 修饰，以确保程序员知晓
        bottles.values().forEach(o -> res[0] += o.getCommodity());
        equipments.values().forEach(o -> res[0] += o.getCommodity());
        foods.values().forEach(o -> res[0] += o.getCommodity());
        employees.forEach((o) -> res[0] += o.getCommodity());
        res[0] += this.money;
        return res[0];
    }

    @Override
    public int getAttribute() {
        return 0;
    }

    public int countCommodity() {
        return bottles.size() + equipments.size() + foods.size() + employees.size();
    }

    public long maxCommodity() {
        final long[] res = {0};
        bottles.values().forEach(o -> res[0] = Math.max(res[0], o.getCommodity()));
        equipments.values().forEach(o -> res[0] = Math.max(res[0], o.getCommodity()));
        foods.values().forEach(o -> res[0] = Math.max(res[0], o.getCommodity()));
        employees.forEach(o -> res[0] = Math.max(res[0], o.getCommodity()));
        return res[0];
    }

    public void sellAll() {
        long moneyEarned = 0;
        HashSet<Integer> toBeRemovedBottle = new HashSet<>();
        HashSet<Integer> toBeRemovedEquipment = new HashSet<>();
        HashSet<Integer> toBeRemovedFood = new HashSet<>();
        for (PriorityQueue<Integer> botQueue: backpack.getBottles().values()) {
            for (Integer botId : botQueue) {
                moneyEarned += SingletonShop.getInstance().stockBottle(bottles.get(botId));
                toBeRemovedBottle.add(botId);
            }
        }
        for (Integer equId: backpack.getEquipments().values()) {
            moneyEarned += SingletonShop.getInstance().stockEquipment(equipments.get(equId));
            toBeRemovedEquipment.add(equId);
        }
        for (PriorityQueue<Integer> foodQueue : backpack.getFoods().values()) {
            for (Integer foodId : foodQueue) {
                moneyEarned += SingletonShop.getInstance().stockFood(foods.get(foodId));
                toBeRemovedFood.add(foodId);
            }
        }
        // `isSold` is false in that the sold action happened when adding the money
        toBeRemovedBottle.forEach(id -> dropBottle(id, false));
        toBeRemovedEquipment.forEach(id -> dropEquipment(id, false));
        toBeRemovedFood.forEach(id -> dropFood(id, false));
        this.money += moneyEarned;
        System.out.println(this.name + " emptied the backpack " + moneyEarned);
    }

    public void buyThing(int id, String name, String type, String other) {
        boolean bought = false;
        long price;
        if (type.contains("Bottle")) {
            Bottle toBuy = SingletonShop.getInstance().sellBottle(id, name, type, other);
            if (toBuy == null) {
                return;
            }
            price = toBuy.getCommodity();
            if (price <= this.money) {
                this.money -= price;
                this.obtainBottle(id, toBuy);
                bought = true;
            }
        } else if (type.contains("Equipment")) {
            Equipment toBuy = SingletonShop.getInstance().sellEquipment(id, name, type, other);
            if (toBuy == null) {
                return;
            }
            price = toBuy.getCommodity();
            if (price <= this.money) {
                this.money -= price;
                this.obtainEquipment(id, toBuy);
                bought = true;
            }
        } else {
            Food toBuy = SingletonShop.getInstance().sellFood(id, name, type, other);
            if (toBuy == null) {
                return;
            }
            price = toBuy.getCommodity();
            if (price <= this.money) {
                this.money -= price;
                this.obtainFood(id, toBuy);
                bought = true;
            }
        }
        if (bought) {
            System.out.println("successfully buy " + name + " for " + price);
        } else {
            System.out.println("failed to buy " + name + " for " + price);
        }
    }

    public long helpEmployer(int powerLoss) {
        long moneyNeeded = (long)10000 * powerLoss;
        long moneyGiveOut = Math.min(moneyNeeded, this.money);
        this.money -= moneyGiveOut;
        return moneyGiveOut;
    }

    public void enterFightMode() {
        this.powerBefore = power;
    }

    public void leaveFightMode() {
        if (power <= powerBefore / 2) {
            int powerLoss = powerBefore - power;
            employees.forEach(adventurer -> this.money += adventurer.helpEmployer(powerLoss));
        }
    }

    public long getMoney() {
        return money;
    }
}
