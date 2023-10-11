public class LoggerNormalAttack extends LoggerBase {
    private String subAdvName;

    public String getSubAdvName() {
        return subAdvName;
    }

    public LoggerNormalAttack(String date, String mainAdvName, String mainObjName, String subAdvName) {
        super(date, mainAdvName, mainObjName);
        this.subAdvName = subAdvName;
    }

    @Override
    public String toString() {
        return String.format("%s %s attacked %s with %s", getDate(), getMainAdvName(), getSubAdvName(), getMainObjName());
    }
}
