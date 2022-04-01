package org.happyhour.scoreboard.enums;

public enum RoleEnum {

    LOYAL_SERVANT(0x0000, "忠臣"),
    MERLIN(0x0001, "梅林"),
    PERCIVAL(0x0002, "派西维尔"),

    MINION_OF_MORDRED(0x0100, "狼"),
    ASSASSIN(0x0101, "刺客"),
    MORGANA(0x0102, "莫甘娜"),
    MORDRED(0x0103, "莫德雷德"),
    OBERON(0x0104, "奥博伦");

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
}
