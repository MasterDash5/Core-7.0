package dashnetwork.core.utils;

public class PunishData {

    private Long expire;
    private String banner;
    private String reason;

    public PunishData(Long expire, String banner, String reason) {
        this.expire = expire;
        this.banner = banner;
        this.reason = reason;
    }

    public Long getExpire() {
        return expire;
    }

    public boolean isPermanent() {
        return expire == null;
    }

    // UUID
    public String getBanner() {
        return banner;
    }

    public String getReason() {
        return reason;
    }

}
