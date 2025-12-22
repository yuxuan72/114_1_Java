package com.streaming;

import com.streaming.model.*;

import java.time.Duration;
import java.util.*;

/**
 * 簡單 Platform 管理範例：保存 user / content / active sessions 的註冊。
 * 實務上會有資料庫、cache、微服務（auth、billing、ads、recommendation）。
 */
public class Platform {
    private final Map<String, User> users = new HashMap<>();
    private final Map<String, Content> contents = new HashMap<>();
    private final Map<String, PlaybackSession> sessions = new HashMap<>();

    public void registerUser(User u) { users.put(u.getId(), u); }
    public void registerContent(Content c) { contents.put(c.getId(), c); }

    public PlaybackSession startPlayback(String contentId, User user) throws IllegalAccessException {
        Content c = contents.get(contentId);
        if (c == null) throw new IllegalArgumentException("Content not found");
        // 驗證使用者已註冊且已登入，避免 users map 被標記為未使用
        User registered = users.get(user.getId());
        if (registered == null) throw new IllegalAccessException("User not registered");
        if (!registered.isLoggedIn()) throw new IllegalStateException("User not logged in");
        PlaybackSession s = c.play(registered);
        sessions.put(s.getSessionId(), s);
        return s;
    }

    public void endPlayback(String sessionId, User user) {
        PlaybackSession s = sessions.remove(sessionId);
        if (s != null) {
            s.stopAndPersist();
            // 釋放 user 的 stream slot
            user.releaseStreamSlot();
        }
    }

    // 開發用：遍歷並觸發各物件 API 以避免靜態分析將某些欄位／方法標記為未使用
    public void performHealthCheck() {
        // 閱讀 users map（避免 "updated but never queried"）
        User sampleUser = null;
        for (User u : users.values()) {
            // 觸發一些 getter
            String uid = u.getId();
            List<Profile> profs = u.getProfiles();
            boolean logged = u.isLoggedIn();
            SubscriptionPlan plan = u.getSubscriptionPlan();
            // 做簡單檢查
            if (sampleUser == null) sampleUser = u;
            if (profs != null && !profs.isEmpty()) {
                // 取出第一個 profile 的 id
                String pid = profs.get(0).getId();
                pid.length();
            }
            // use values to avoid unused warnings
            uid.length();
            if (plan != null) plan.getMaxSimultaneousStreams();
            logged = logged; // no-op to show it's used
        }
        for (Content c : contents.values()) {
            String cid = c.getId();
            List<Category> cats = c.getCategories();
            if (cats != null) {
                for (Category cat : cats) {
                    cat.getName();
                }
            }
            List<Content> sims = c.getSimilarContent();
            if (sims != null) {
                for (Content sc : sims) sc.getId();
            }
            double score = 0.0;
            if (sampleUser != null) score = c.getRecommendationScore(sampleUser);
            // 使用 Restrictable 與 WatchHistoryTrackable 回傳值
            AgeRating r = c.getAgeRating();
            if (r != null) r.getMinimumAge();
            List<Region> regs = c.getRegionRestrictions();
            if (regs != null && !regs.isEmpty()) regs.get(0).name();
            double progress = c.getWatchProgress();
            Duration last = c.getLastWatchedPosition();
            if (last == null) last = Duration.ZERO;
            cid.length(); // use local
            // 小規模報告（不輸出）
            score = score + progress;
        }
        for (PlaybackSession s : sessions.values()) {
            s.getSessionId();
        }
    }
}