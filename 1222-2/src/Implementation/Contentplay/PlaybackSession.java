package Implementation.Contentplay;
public PlaybackSession play(User user) throws IllegalAccessException {
    // 1. 驗證登入
    if (user == null) throw new IllegalStateException("User must be logged in");

    // 2. 驗證年齡
    if (user.getAge() < getMinAge(this.rating)) {
        throw new IllegalAccessException("Content is rated " + this.rating + ", user must be older.");
    }

    // 3. 驗證地區
    if (!this.allowedRegions.contains(user.getRegion())) {
        throw new IllegalAccessException("Content is not available in your region");
    }

    // 4. 驗證訂閱方案與裝置數
    Subscription plan = user.getSubscription();
    if (this.isPremium && !plan.canWatchPremiumContent()) {
        throw new IllegalAccessException("Upgrade to Premium to watch this content");
    }

    if (user.getActiveSessionsCount() >= plan.getMaxDevices()) {
        throw new IllegalStateException("Maximum " + plan.getMaxDevices() + " simultaneous streams allowed");
    }

    // 5. 建立 Session 並恢復進度
    Duration lastPos = user.getWatchHistory().getProgress(this);
    PlaybackSession session = new PlaybackSession(this, user, lastPos);

    if (plan instanceof FreeTier) {
        session.scheduleAds(); // 免費版排入廣告
    }

    return session;
}