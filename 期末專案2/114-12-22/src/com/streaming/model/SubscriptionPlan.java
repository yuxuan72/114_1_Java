package com.streaming.model;

public enum SubscriptionPlan {
    FreeTier(1, "SD", true),
    BasicPlan(1, "HD", false),
    StandardPlan(2, "FHD", false),
    PremiumPlan(4, "4K", false);

    private final int maxSimultaneousStreams;
    private final String maxQuality;
    private final boolean hasAds;

    SubscriptionPlan(int maxSimultaneousStreams, String maxQuality, boolean hasAds) {
        this.maxSimultaneousStreams = maxSimultaneousStreams;
        this.maxQuality = maxQuality;
        this.hasAds = hasAds;
    }

    public int getMaxSimultaneousStreams() {
        return maxSimultaneousStreams;
    }

    public String getMaxQuality() {
        return maxQuality;
    }

    public boolean hasAds() {
        return hasAds;
    }
}