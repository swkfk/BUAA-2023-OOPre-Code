public class Bottle {
    private int id;
    private String name;

    private int capacity;

    public Bottle(int id, String name, int capacity) {
        this.capacity = capacity;
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public int clearCapacity() {
        int ret = capacity;
        capacity = 0;
        return ret;
    }
}
