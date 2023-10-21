public class EquipmentEpic extends Equipment {
    private final double ratio;

    public EquipmentEpic(int id, String name, int star, long price, double ratio) {
        super(id, name, star, price);
        this.ratio = ratio;
    }

    @Override
    public int getDamage(int victimPower) {
        return (int) (ratio * victimPower);
    }

    @Override
    public String getBelonging() {
        return "EpicEquipment";
    }
}
