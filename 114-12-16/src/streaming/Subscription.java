package streaming;

public abstract class Subscription {
    protected String name;
    protected String maxResolution;
    protected int maxDevices;
    protected boolean hasAds;

    protected Subscription(String name, String maxResolution,
                           int maxDevices, boolean hasAds) {
        this.name = name;
        this.maxResolution = maxResolution;
        this.maxDevices = maxDevices;
        this.hasAds = hasAds;
    }

    public abstract Object getPlanName();
}