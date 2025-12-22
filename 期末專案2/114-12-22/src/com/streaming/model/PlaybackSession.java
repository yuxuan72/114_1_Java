package com.streaming.model;

import java.time.Duration;
import java.util.*;

/**
 * PlaybackSession 表示一次播放階段。簡化：包含廣告排程方法與結束時同步進度回寫。
 */
public class PlaybackSession {
    private final String sessionId;
    private final Content content;
    private final Profile profile;
    private Duration position;
    private final SubscriptionPlan plan;
    private final List<String> scheduledAds = new ArrayList<>();
    private boolean active = true;

    public PlaybackSession(String sessionId, Content content, Profile profile, Duration resumePosition, SubscriptionPlan plan) {
        this.sessionId = sessionId;
        this.content = content;
        this.profile = profile;
        this.position = resumePosition != null ? resumePosition : Duration.ZERO;
        this.plan = plan;
    }

    public String getSessionId() { return sessionId; }
    public Content getContent() { return content; }
    public Duration getPosition() { return position; }

    public void seek(Duration p) { this.position = p; }

    public void scheduleAds() {
        // 簡化：為 FreeTier 排 PreRoll + MidRoll 樣例
        scheduledAds.add("PreRollAd");
        if (content instanceof Movie || content instanceof Episode) {
            scheduledAds.add("MidRollAd@25%");
            scheduledAds.add("MidRollAd@50%");
        }
    }

    public List<String> getScheduledAds() { return Collections.unmodifiableList(scheduledAds); }

    public void stopAndPersist() {
        // 停止 session，回寫 profile 與 content 的 progress
        active = false;
        profile.setLastPosition(content.id, position);
        boolean completed = false;
        // 假設若 position >= duration -> completed
        if (content instanceof Movie) {
            Movie m = (Movie) content;
            if (!m.getDuration().isZero() && position.compareTo(m.getDuration()) >= 0) completed = true;
        }
        if (content instanceof Episode) {
            Episode e = (Episode) content;
            if (!e.getDuration().isZero() && position.compareTo(e.getDuration()) >= 0) completed = true;
        }
        if (completed) profile.markCompleted(content);
        content.updateProfilePosition(profile, position, completed);
        // 釋放帳號的 stream slot（需 access user instance）
        // 實務：PlaybackSession 應持有 reference 到 User 或由 Platform 管理 sessions -> 這裡只示意
    }
}