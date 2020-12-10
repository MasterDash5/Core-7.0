package dashnetwork.core.utils;

public class Username {

    private String name;
    private long changedAt;

    public Username(String name, long changedAt) {
        this.name = name;
        this.changedAt = changedAt;
    }

    public String getName() {
        return name;
    }

    public long getChangedAt() {
        return changedAt;
    }

}
