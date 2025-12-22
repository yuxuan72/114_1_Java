package com.streaming;

import com.streaming.model.*;
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
        PlaybackSession s = c.play(user);
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
}