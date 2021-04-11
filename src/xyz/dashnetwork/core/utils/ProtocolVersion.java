package xyz.dashnetwork.core.utils;

public enum ProtocolVersion {

    UNKNOWN(-1, false),
    v1_4_7(51, true),
    v1_5_1(60, true),
    v1_5_2(61, true),
    v1_6_1(73, true),
    v1_6_2(74, true),
    v1_6_4(78, true),
    v1_7_5(4, false),
    v1_7_10(5, false),
    v1_8(47, false),
    v1_9(107, false),
    v1_9_1(108, false),
    v1_9_2(109, false),
    v1_9_4(110, false),
    v1_10(210, false),
    v1_11(315, false),
    v1_11_2(316, false),
    v1_12(335, false),
    v1_12_1(338, false),
    v1_12_2(340, false),
    v1_13(393, false),
    v1_13_1(401, false),
    v1_13_2(404, false),
    v1_14(477, false),
    v1_14_1(480, false),
    v1_14_2(485, false),
    v1_14_3(490, false),
    v1_14_4(498, false),
    v1_15(573, false),
    v1_15_1(575, false),
    v1_15_2(578, false),
    v1_16(735, false),
    v1_16_1(736, false),
    v1_16_2(751, false),
    v1_16_3(753, false),
    v1_16_5(754, false);

    private int id;
    private boolean legacy;

    ProtocolVersion(int id, boolean legacy) {
        this.id = id;
        this.legacy = legacy;
    }

    public int getId() {
        return id;
    }

    public boolean isLegacy() {
        return legacy;
    }

    public String getName() {
        return name().replace("v", "").replace("_", ".");
    }

    public static ProtocolVersion fromId(int id) {
        for (ProtocolVersion version : values())
            if (version.getId() == id)
                return version;
        return UNKNOWN;
    }

    public static ProtocolVersion fromString(String string) {
        for (ProtocolVersion version : values())
            if (string.startsWith(version.getName()))
                return version;
        return UNKNOWN;
    }

    public boolean isOlderThan(ProtocolVersion version) {
        return getId() < version.getId() || (!version.isLegacy() && isLegacy());
    }

    public boolean isOlderThanOrEqual(ProtocolVersion version) {
        return getId() == version.getId() || isOlderThan(version);
    }

    public boolean isNewerThan(ProtocolVersion version) {
        return getId() > version.getId() || (version.isLegacy() && !isLegacy());
    }

    public boolean isNewerThanOrEqual(ProtocolVersion version) {
        return getId() == version.getId() || isNewerThan(version);
    }

}
