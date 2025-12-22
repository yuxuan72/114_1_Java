package com.streaming.model;

public enum SubscriptionPlan {
    FreeTier(false, 1, "Free"),
    Basic(true, 1, "Basic"),
    Standard(true, 2, "Standard"),
    Premium(true, 4, "Premium");

    private final boolean supportsPremium;
    private final int maxSimultaneousStreams;
    private final String displayName;

    SubscriptionPlan(boolean supportsPremium, int maxSimultaneousStreams, String displayName) {
        this.supportsPremium = supportsPremium;
        this.maxSimultaneousStreams = maxSimultaneousStreams;
        this.displayName = displayName;
    }

    public boolean supportsPremiumContent() { return supportsPremium; }
    public int getMaxSimultaneousStreams() { return maxSimultaneousStreams; }
    public String getName() { return displayName; }
}

