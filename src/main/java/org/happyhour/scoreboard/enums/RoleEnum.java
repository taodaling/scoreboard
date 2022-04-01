package org.happyhour.scoreboard.enums;

public enum RoleEnum {

    ASSASSIN(-3, "刺客"),
    MOGANNA(-2, "莫甘娜"),
    BADMAN(-1, "狼"),
    ABSENT(0, "未参加"),
    GOODMAN(1, "平民"),
    MEILIN(2, "梅林"),
    PAI(3, "派西维尔");

    public int id;
    public String name;

    RoleEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static RoleEnum of(Integer id) {
        if (id == null) {
            return null;
        }
        for (RoleEnum tmp : RoleEnum.values()) {
            if (tmp.id == id.intValue()) {
                return tmp;
            }
        }
        return null;
    }

    public boolean isGood(int id) {
        return id > 0;
    }
}
