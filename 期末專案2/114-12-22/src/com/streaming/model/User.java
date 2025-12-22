package com.streaming.model;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * User = 帳號（包含訂閱方案與多個 Profile）；
 * activeStreams 的管理在此模擬（同步區塊）。實務請使用分散式儲存/鎖。
 */
public class User {
    private final String id;
    private final String email;
    private final int age;
    private final Region region;
    private SubscriptionPlan subscriptionPlan;
    private final List<Profile> profiles = new ArrayList<>();
    private final AtomicInteger activeStreams = new AtomicInteger(0);
    private volatile boolean loggedIn = false;

    public User(String id, String email, int age, Region region, SubscriptionPlan plan) {
        this.id = id; this.email = email; this.age = age; this.region = region; this.subscriptionPlan = plan;
    }

    public String getId() { return id; }

    public void addProfile(Profile p) { profiles.add(p); }
    public List<Profile> getProfiles() { return Collections.unmodifiableList(profiles); }
    public Profile getProfile() {
        return profiles.isEmpty() ? null : profiles.get(0); // default
    }

    public boolean isLoggedIn() { return loggedIn; }
    public void setLoggedIn(boolean v) { loggedIn = v; }

    public int getAge() { return age; }
    public Region getRegion() { return region; }
    public SubscriptionPlan getSubscriptionPlan() { return subscriptionPlan; }
    public void setSubscriptionPlan(SubscriptionPlan p) { subscriptionPlan = p; }

    /**
     * 嘗試取得一個播放 slot（帳號層級）。若已達上限回傳 false。
     * 實務：如果分散式、需配合 Redis INCR/DECR 或 lease locking。
     */
    public synchronized boolean tryAcquireStreamSlot(int maxAllowed) {
        if (activeStreams.get() >= maxAllowed) return false;
        activeStreams.incrementAndGet();
        return true;
    }

    public synchronized void releaseStreamSlot() {
        int cur = activeStreams.decrementAndGet();
        if (cur < 0) activeStreams.set(0);
    }
}