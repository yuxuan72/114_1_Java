package com.streaming.model;

import com.streaming.interfaces.*;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 抽象 Content 類別，實作共用的 play(User) 判斷流程與欄位。
 */
public abstract class Content implements Playable, Categorizable, Recommendable, Restrictable, WatchHistoryTrackable {
    protected final String id;
    protected final String title;
    protected final AgeRating ageRating;
    protected final Set<Region> allowedRegions;
    protected final SubscriptionRequirement requiredSubscription; // e.g., PREMIUM_ONLY / ANY
    protected final List<Category> categories = new ArrayList<>();

    // per-profile watch progress map: profileId -> lastPosition
    protected final Map<String, Duration> progressByProfile = new ConcurrentHashMap<>();
    protected final Set<String> completedByProfile = ConcurrentHashMap.newKeySet();

    public Content(String id, String title, AgeRating ageRating, Set<Region> allowedRegions, SubscriptionRequirement requiredSubscription) {
        this.id = id; this.title = title; this.ageRating = ageRating;
        this.allowedRegions = new HashSet<>(allowedRegions);
        this.requiredSubscription = requiredSubscription;
    }

    public String getId() { return id; }

    // ---- Restrictable ----
    @Override
    public AgeRating getAgeRating() { return ageRating; }

    @Override
    public List<Region> getRegionRestrictions() { return new ArrayList<>(allowedRegions); }

    @Override
    public boolean isAccessibleBy(User user) {
        if (user == null || !user.isLoggedIn()) return false;
        if (user.getRegion() == null || !allowedRegions.contains(user.getRegion())) return false;
        if (user.getProfile() == null) return false;
        // subscription check
        if (requiredSubscription == SubscriptionRequirement.PREMIUM_ONLY) {
            return user.getSubscriptionPlan() != SubscriptionPlan.FreeTier;
        }
        return true;
    }

    // ---- Categorizable ----
    @Override
    public void addCategory(Category category) { categories.add(category); }
    @Override
    public List<Category> getCategories() { return Collections.unmodifiableList(categories); }
    @Override
    public boolean matchesCategory(Category category) { return categories.contains(category); }

    // ---- Recommendable (簡化示範) ----
    @Override
    public List<Content> getSimilarContent() { return Collections.emptyList(); }
    @Override
    public double getRecommendationScore(User user) { return 0.0; }

    // ---- WatchHistoryTrackable ----
    @Override
    public void recordProgress(Duration watchedDuration) {
        if (watchedDuration == null) return;
        // profile required to write precise key; in practise include profile id
        // 示例中不直接更新 profile 以避免耦合，應透過 WatchHistory service
    }

    @Override
    public double getWatchProgress() {
        // 需要 content 長度資訊；留 0 做示範
        return 0.0;
    }

    @Override
    public boolean isCompleted() {
        return false;
    }

    @Override
    public Duration getLastWatchedPosition() {
        return Duration.ZERO;
    }

    // ---- Playable: implement core play flow according to規格 ----
    @Override
    public PlaybackSession play(User user) throws IllegalAccessException {
        // 1. 驗證使用者已登入
        if (user == null || !user.isLoggedIn()) {
            throw new IllegalStateException("User must be logged in");
        }
        // 2. 年齡分級
        int userAge = user.getAge();
        if (userAge < ageRating.getMinimumAge()) {
            throw new IllegalAccessException(String.format("Content is rated %s, user must be %d or older", ageRating.name(), ageRating.getMinimumAge()));
        }
        // 3. 地區限制
        if (user.getRegion() == null || !allowedRegions.contains(user.getRegion())) {
            throw new IllegalAccessException("Content is not available in your region");
        }
        // 4. 訂閱方案是否支援
        if (requiredSubscription == SubscriptionRequirement.PREMIUM_ONLY && user.getSubscriptionPlan() == SubscriptionPlan.FreeTier) {
            throw new IllegalAccessException("Your subscription does not allow access to this content");
        }
        // 5. 同時觀看裝置數檢查 (帳號層級)
        int max = user.getSubscriptionPlan().getMaxSimultaneousStreams();
        boolean allowed = user.tryAcquireStreamSlot(max);
        if (!allowed) {
            throw new IllegalStateException(String.format("Maximum %d simultaneous streams allowed", max));
        }

        // 6. 建立 PlaybackSession（若有觀看紀錄，從上次位置繼續）
        Duration lastPos = getProfileLastPosition(user.getProfile());
        PlaybackSession session = new PlaybackSession(UUID.randomUUID().toString(), this, user.getProfile(), lastPos, user.getSubscriptionPlan());

        // 7. FreeTier：排程廣告（示意）
        if (user.getSubscriptionPlan() == SubscriptionPlan.FreeTier) {
            session.scheduleAds(); // 在 PlaybackSession 內會建立 PreRoll / MidRoll slot 等
        }

        // 在實務上要把 session 寫入 DB / cache，並註冊在 user active sessions 中 (user.tryAcquireStreamSlot 已處理)
        return session;
    }

    protected Duration getProfileLastPosition(Profile profile) {
        if (profile == null) return Duration.ZERO;
        return progressByProfile.getOrDefault(profile.getId(), Duration.ZERO);
    }

    // 用來由外部更新 progress （例如 PlaybackSession 結束時呼叫）
    public void updateProfilePosition(Profile profile, Duration position, boolean completed) {
        if (profile == null) return;
        if (position == null) position = Duration.ZERO;
        progressByProfile.put(profile.getId(), position);
        if (completed) completedByProfile.add(profile.getId());
    }

    // 可由子類別覆寫
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void seek(Duration position) {}
}