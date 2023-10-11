public abstract class LoggerBase {
    private String date;

    private String mainAdvName;
    private String mainObjName;

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
