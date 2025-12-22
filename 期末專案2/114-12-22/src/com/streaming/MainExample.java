package com.streaming;

import com.streaming.model.*;
import java.time.Duration;
import java.util.Set;

public class MainExample {
    public static void main(String[] args) throws Exception {
        Platform platform = new Platform();

        User user = new User("u1","a@b.com", 20, Region.TW, SubscriptionPlan.FreeTier);
        user.setLoggedIn(true);
        Profile p = new Profile("p1","小明");
        user.addProfile(p);
        platform.registerUser(user);

        Movie movie = new Movie("m1","示範電影", AgeRating.PG13, Set.of(Region.TW, Region.US), SubscriptionRequirement.ANY, Duration.ofMinutes(90));
        platform.registerContent(movie);

        try {
            PlaybackSession session = platform.startPlayback("m1", user);
            System.out.println("開始播放 sessionId=" + session.getSessionId() + " ads=" + session.getScheduledAds());
            // 模擬觀看 10 分鐘
            session.seek(Duration.ofMinutes(10));
            platform.endPlayback(session.getSessionId(), user);
        } catch (IllegalAccessException | IllegalStateException ex) {
            System.err.println("播放被拒絕: " + ex.getMessage());
        }
    }
}