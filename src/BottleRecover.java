public class BottleRecover extends Bottle {
    private final double ratio;

    public BottleRecover(int id, String name, int capacity, long price, double ratio) {
        super(id, name, capacity, price);
        this.ratio = ratio;
    }

    @Override
    public int getRecovery(int power) {
        if (getCapacity() > 0) {
            return (int) (ratio * power);
        } else {
            return 0;
        }
    }

    @Override
    public String getBelonging() {
        return "RecoverBottle";
    }
}
