public class LoggerBottles extends LoggerBase {
    public LoggerBottles(String date, String mainAdvName, String mainObjName) {
        super(date, mainAdvName, mainObjName);
    }

    @Override
    public String toString() {
        return String.format("%s %s used %s", getDate(), getMainAdvName(), getMainObjName());
    }
}
