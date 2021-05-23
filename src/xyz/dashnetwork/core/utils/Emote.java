package xyz.dashnetwork.core.utils;

public enum Emote {

    SPACER(null, '\u0001'),
    HANYUU_HOH("HanyuuHoh", '\u0002'),
    HANYUU_NANI("HanyuuNani", '\u0003'),
    HANYUU_OHNO("HanyuuOhNo", '\u0004'),
    HANYUU_SMUG("HanyuuSmug", '\u0005'),
    MII_XD("MiiXD", '\u0006'),
    RENA_AH("RenaAh", '\u0007'),
    RENA_HAU("RenaHau", '\u0008'),
    RENA_POG("RenaPog", '\u0009'),
    RENA_THINK("RenaThink", '\u000B'),
    RENA_WHY("RenaWhy", '\u000C'),
    RIKA_DONE("RikaDone", '\u000E'),
    RIKA_FLEX("RikaFlex", '\u000F'),
    RIKA_FLUSHED("RikaFlushed", '\u0010'),
    RIKA_GUN("RikaGun", '\u0011'),
    RIKA_HOH("RikaHoh", '\u0012'),
    RIKA_JUICE("RikaJuice", '\u0013'),
    RIKA_MEW("RikaMew", '\u0014'),
    RIKA_POUT("RikaPout", '\u0015'),
    SATOKO_GIGGLE("SatokoGiggle", '\u0016'),
    SATOKO_WUT("SatokoWut", '\u0017'),
    SHII_PHANTOM("ShiiPhantom", '\u0018');

    private String name;
    private char unicode;

    Emote(String name, char unicode) {
        this.name = name;
        this.unicode = unicode;
    }

    public String getName() {
        return name;
    }

    public char getUnicode() {
        return unicode;
    }

}
