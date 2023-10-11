public class LoggerAoeAttack extends LoggerBase {
    public LoggerAoeAttack(String date, String mainAdvName, String mainObjName) {
        super(date, mainAdvName, mainObjName);
    }

    @Override
    public String toString() {
        return String.format(
                "%s %s AOE-attacked with %s",
                getDate(), getMainAdvName(), getMainObjName()
        );
    }
}
