public class EquipmentRegular extends Equipment {
    public EquipmentRegular(int id, String name, int star, long price) {
        super(id, name, star, price);
    }

    @Override
    public String getBelonging() {
        return "RegularEquipment";
    }
}
