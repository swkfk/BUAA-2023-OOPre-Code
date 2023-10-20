public class EquipmentCrit extends Equipment {
    private final int critical;

    public EquipmentCrit(int id, String name, int star, long price,  int critical) {
        super(id, name, star, price);
        this.critical = critical;
    }

    @Override
    public int getDamage(int level) {
        return getStar() * level + critical;
    }
}
