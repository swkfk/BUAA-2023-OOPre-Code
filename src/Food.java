public class Food {
    private final int id;
    private final String name;
    private final int energy;
    private final long price;

    public Food(int id, String name, int energy, long price) {
        this.id = id;
        this.energy = energy;
        this.name = name;
        this.price = price;
    }

    public int getEnergy() {
        return energy;
    }

    public String getName() {
        return name;
    }
}
