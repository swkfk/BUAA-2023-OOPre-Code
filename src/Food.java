public class Food {
    private final int id;
    private final String name;
    private final int energy;

    public Food(int id, String name, int energy) {
        this.id = id;
        this.energy = energy;
        this.name = name;
    }

    public int getEnergy() {
        return energy;
    }

    public String getName() {
        return name;
    }
}
