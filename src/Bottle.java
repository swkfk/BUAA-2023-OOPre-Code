public class Bottle implements ICommodity {
    private final int id;
    private final String name;
    private int capacity;
    private final int volumn;
    private final long price;

    public Bottle(int id, String name, int capacity, long price) {
        this.volumn = capacity;
        this.capacity = capacity;
        this.name = name;
        this.id = id;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void clearCapacity() {
        capacity = 0;
    }

    public int getRecovery(int ignored) {
        return capacity;
    }

    @Override
    public String getBelonging() {
        return null;
    }

    @Override
    public long getCommodity() {
        return price;
    }

    @Override
    public int getAttribute() {
        return volumn;
    }
}
