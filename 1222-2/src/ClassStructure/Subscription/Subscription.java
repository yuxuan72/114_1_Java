package ClassStructure.Subscription;

abstract class Subscription {
    protected int maxDevices;
    protected String quality;
    public abstract boolean canWatchPremiumContent();
    public int getMaxDevices() { return maxDevices; }
}

class FreeTier extends Subscription {
    public FreeTier() { this.maxDevices = 1; this.quality = "SD"; }
    public boolean canWatchPremiumContent() { return false; }
}

class PremiumPlan extends Subscription {
    public PremiumPlan() { this.maxDevices = 4; this.quality = "4K"; }
    public boolean canWatchPremiumContent() { return true; }
}