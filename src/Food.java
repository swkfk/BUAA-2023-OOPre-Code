public class Food {
    private int id;
    private String name;
    private int energy;

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
