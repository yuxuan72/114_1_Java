package com.streaming.model;

// java
public abstract class Advertisement {
    private final String id;
    public Advertisement(String id) { this.id = id; }
    public String getId() { return id; }
}

// java
class PreRollAd extends Advertisement {
    public PreRollAd(String id) { super(id); }
}

// java
class MidRollAd extends Advertisement {
    public MidRollAd(String id) { super(id); }
}

// java
class BannerAd extends Advertisement {
    public BannerAd(String id) { super(id); }
}
