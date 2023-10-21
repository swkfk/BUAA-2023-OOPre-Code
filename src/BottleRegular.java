public class BottleRegular extends Bottle {
    public BottleRegular(int id, String name, int capacity, long price) {
        super(id, name, capacity, price);
    }

    @Override
    public String getBelonging() {
        return "RegularBottle";
    }
}
