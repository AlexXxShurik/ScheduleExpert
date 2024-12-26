package database;

public class PatternsData {
    private int id;
    private String pattern;

    public PatternsData(int id, String pattern) {
        this.id = id;
        this.pattern = pattern;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }
}
