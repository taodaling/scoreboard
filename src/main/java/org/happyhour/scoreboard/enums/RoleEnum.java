package org.happyhour.scoreboard.enums;

public enum RoleEnum {

    // --阿瓦隆
    LOYAL_SERVANT(0x0000, "忠臣"),
    MERLIN(0x0001, "梅林"),
    PERCIVAL(0x0002, "派西维尔"),

    MINION_OF_MORDRED(0x0100, "狼"),
    ASSASSIN(0x0101, "刺客"),
    MORGANA(0x0102, "莫甘娜"),
    MORDRED(0x0103, "莫德雷德"),
    OBERON(0x0104, "奥博伦"),
    // --阿瓦隆

    // --狼人杀
    VILLAGER(0x1000, "村民"),

    POLICE_CHIEF(0x1100, "警长"),
    PREDICTOR(0x1101, "预言家"),
    WITCH(0x1102, "女巫"),
    HUNTER(0x1103, "猎人"),
    FOOL(0x1104, "白痴"),
    GUARD(0x1105, "守卫"),
    KNIGHT(0x1106, "骑士"),
    GRAVE_GUARD(0x1107, "守墓人"),


    WEREWOLF(0x1200, "狼人"),
    WOLF_KING(0x1201, "狼王"),
    WHITE_WOLF_KING(0x1202, "白狼王"),
    WOLF_BEAUTY(0x1203, "狼美人"),
    SNOW_WOLF(0x1204, "雪狼"),
    GARGOYLE(0x1205, "石像鬼"),
    GHOST_RIDER(0x1206, "恶灵骑士");
    // --狼人杀

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
