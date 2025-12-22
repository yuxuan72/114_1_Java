// java
package com.streaming;

import com.streaming.model.*;
import java.time.Duration;

public class MainExample {
    public static void main(String[] args) {
        // 使用 singleton API（如果你的 Platform 是 singleton）
        Platform platform = Platform.getInstance();

        // 建立一個使用者（對應舊版 User(String id, Subscription) 的簽章）
        User user = new User("u1", new FreeTier());
        user.setLoggedIn(true);

        // 取得或建立預設 profile（符合先前範例的 helper）
        Profile profile = user.getOrCreateDefaultProfile();

        // 建立內容（符合先前 Content 子類的建構子）
        Movie movie = new Movie("m1", "示範電影", AgeRating.PG13, Duration.ofMinutes(90));
        // 設定可播放地區（使用 Content 提供的區域集合 accessor）
        movie.getRegionRestrictions().add(Region.TW);

        try {
            // 直接呼叫 Content.play(User)，讓 Content 負責檢查權限並回傳 PlaybackSession
            PlaybackSession session = movie.play(user);
            System.out.println("開始播放 sessionId=" + session.getId() + " ads=" + session.getScheduledAds());

            // 模擬觀看 10 分鐘
            session.seek(Duration.ofMinutes(10));

            // 結束播放（若你的 Platform 有 unregister API，請改用該 API）
            platform.unregisterSession(user, session);
        } catch (IllegalAccessException | IllegalStateException ex) {
            System.err.println("播放被拒絕: " + ex.getMessage());
        }
    }
}
