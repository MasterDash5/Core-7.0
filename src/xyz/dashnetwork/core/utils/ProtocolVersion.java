package xyz.dashnetwork.core.utils;

public enum ProtocolVersion {

    UNKNOWN(-1),
    v1_4_7(51),
    v1_5_1(60),
    v1_5_2(61),
    v1_6_1(73),
    v1_6_2(74),
    v1_6_4(78),
    v1_7_5(4),
    v1_7_10(5),
    v1_8(47),
    v1_9(107),
    v1_9_1(108),
    v1_9_2(109),
    v1_9_4(110),
    v1_10(210),
    v1_11(315),
    v1_11_2(316),
    v1_12(335),
    v1_12_1(338),
    v1_12_2(340),
    v1_13(393),
    v1_13_1(401),
    v1_13_2(404),
    v1_14(477),
    v1_14_1(480),
    v1_14_2(485),
    v1_14_3(490),
    v1_14_4(498),
    v1_15(573),
    v1_15_1(575),
    v1_15_2(578),
    v1_16(735),
    v1_16_1(736),
    v1_16_2(751),
    v1_16_3(753),
    v1_16_5(754);

    private int id;

    ProtocolVersion(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return toString().replace("v", "").replace("_", ".");
    }

    public static ProtocolVersion fromId(int id) {
        for (ProtocolVersion version : values())
            if (version.getId() == id)
                return version;
        return UNKNOWN;
    }

}
