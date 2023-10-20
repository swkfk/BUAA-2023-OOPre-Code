public abstract class LoggerBase {
    private final String date;

    private final String mainAdvName;
    private final String mainObjName;

    public String getDate() {
        return date;
    }

    public String getMainAdvName() {
        return mainAdvName;
    }

    public String getMainObjName() {
        return mainObjName;
    }

    public LoggerBase(String date, String mainAdvName, String mainObjName) {
        this.date = date;
        this.mainObjName = mainObjName;
        this.mainAdvName = mainAdvName;
    }

    @Override
    public abstract String toString();
}
