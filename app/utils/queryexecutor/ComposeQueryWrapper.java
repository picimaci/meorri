package utils.queryexecutor;

public class ComposeQueryWrapper {
    public String key;
    public String where;
    public String order;

    public ComposeQueryWrapper(String key, String where, String order) {
        this.key = key;
        this.where = where;
        this.order = order;
    }
}
