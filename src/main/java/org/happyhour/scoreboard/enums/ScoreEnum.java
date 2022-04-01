package org.happyhour.scoreboard.enums;

public enum ScoreEnum {

    LOSE(-1, "输"),
    ABSENT(0, "未参加"),
    WIN(1, "赢"),
    MVP(2, "MVP");

    public int id;
    public String name;

    ScoreEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static ScoreEnum of(Integer id) {
        if (id == null) {
            return null;
        }
        for (ScoreEnum tmp : ScoreEnum.values()) {
            if (tmp.id == id.intValue()) {
                return tmp;
            }
        }
        return null;
    }
}
