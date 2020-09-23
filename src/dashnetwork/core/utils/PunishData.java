package dashnetwork.core.utils;

public class PunishData {

    private Long expire;
    private String banner;
    private String reason;

    // Default constructor for Yaml
    public PunishData() {}

    public PunishData(Long expire, String banner, String reason) {
        this.expire = expire;
        this.banner = banner;
        this.reason = reason;
    }

    public Long getExpire() {
        return expire;
    }

    public void setExpire(Long expire) {
        this.expire = expire;
    }

    public boolean isPermanent() {
        return expire == null;
    }

    public boolean isExpired() {
        return !isPermanent() && expire < System.currentTimeMillis();
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "Long: " + expire + " Banner: " + banner + " Reason: " + reason;
    }

}
