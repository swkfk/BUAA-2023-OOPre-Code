public class BottleReinforced extends Bottle {
    private final double ratio;

    public BottleReinforced(int id, String name, int capacity, long price, double ratio) {
        super(id, name, capacity, price);
        this.ratio = ratio;
    }

    @Override
    public int getRecovery(int ignored) {
        return (int) ((1 + ratio) * getCapacity());
    }

    @Override
    public String getBelonging() {
        return "ReinforcedBottle";
    }
}
