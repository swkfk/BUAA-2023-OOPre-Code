import java.util.ArrayList;

public class SingletonShop {
    private static SingletonShop instance = null;
    private final TradeLog botLog;
    private final TradeLog equLog;
    private final TradeLog foodLog;

    private SingletonShop() {
        botLog = new TradeLog();
        equLog = new TradeLog();
        foodLog = new TradeLog();
    }

    public static SingletonShop getInstance() {
        if (instance == null) {
            instance = new SingletonShop();
        }
        return instance;
    }

    public long stockBottle(Bottle bottle) {
        botLog.update(bottle);
        return bottle.getCommodity();
    }

    public long stockEquipment(Equipment equipment) {
        equLog.update(equipment);
        return equipment.getCommodity();
    }

    public long stockFood(Food food) {
        foodLog.update(food);
        return food.getCommodity();
    }

    public Bottle sellBottle(int id, String name, String type, String other) {
        if (type.equals("RegularBottle")) {
            return new BottleRegular(id, name, botLog.getAttribute(), botLog.getPrice());
        }
        if (type.equals("RecoverBottle")) {
            return new BottleRecover(
                    id, name, botLog.getAttribute(), botLog.getPrice(), Double.parseDouble(other)
            );
        }
        if (type.equals("ReinforcedBottle")) {
            return new BottleReinforced(
                    id, name, botLog.getAttribute(), botLog.getPrice(), Double.parseDouble(other)
            );
        }
        return null;
    }

    public Equipment sellEquipment(int id, String name, String type, String other) {
        if (type.equals("RegularEquipment")) {
            return new EquipmentRegular(id, name, equLog.getAttribute(), equLog.getPrice());
        }
        if (type.equals("CritEquipment")) {
            return new EquipmentCrit(
                    id, name, equLog.getAttribute(), equLog.getPrice(), Integer.parseInt(other)
            );
        }
        if (type.equals("EpicEquipment")) {
            return new EquipmentEpic(
                    id, name, equLog.getAttribute(), equLog.getPrice(), Double.parseDouble(other)
            );
        }
        return null;
    }

    public Food sellFood(int id, String name, String type, String other) {
        if (type.equals("Food")) {
            return new Food(id, name, foodLog.getAttribute(), foodLog.getPrice());
        }
        return null;
    }

    private static class TradeLog {
        private final ArrayList<ICommodity> commodities;

        public TradeLog() {
            commodities = new ArrayList<>();
        }

        public void update(ICommodity commodity) {
            commodities.add(commodity);
        }

        public long getPrice() {
            long sum = 0;
            for (ICommodity commodity : commodities) {
                sum += commodity.getCommodity();
            }
            return sum / commodities.size();
        }

        public int getAttribute() {
            long sum = 0;
            for (ICommodity commodity : commodities) {
                sum += commodity.getAttribute();
            }
            return (int) (sum / commodities.size());
        }
    }
}
