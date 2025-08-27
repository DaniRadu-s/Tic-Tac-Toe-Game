package app.model;

public class GameDTO {
    private String alias;
    private long duration;
    private int score;

    public GameDTO(String alias, long date, int score) {
        this.alias = alias;
        this.duration = date;
        this.score = score;
    }

    public String getAlias() {
        return alias;
    }

    public int getScore() {
        return score;
    }

    public long getDuration() {
        return duration;
    }
}

