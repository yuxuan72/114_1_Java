public abstract class Subscription {
    protected int maxDevices;
    protected String resolution;
    public abstract boolean hasAds();
    public int getMaxDevices() { return maxDevices; }
}
// 具體方案實作
public class PremiumPlan extends Subscription {
    public PremiumPlan() { this.maxDevices = 4; this.resolution = "4K"; }
    @Override public boolean hasAds() { return false; }
}

public class FreeTier extends Subscription {
    public FreeTier() { this.maxDevices = 1; this.resolution = "720p"; }
    @Override public boolean hasAds() { return true; }
}