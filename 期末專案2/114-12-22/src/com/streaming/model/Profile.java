package com.streaming.model;

import java.util.*;
import java.time.Duration;

/**
 * Profile 包含 per-profile watch history 與對 episode 已完成標記。
 */
public class Profile {
    private final String id;
    private final String name;
    private final Map<String, Duration> lastPositions = new HashMap<>(); // contentId -> pos
    private final Set<String> completedContent = new HashSet<>();

    public Profile(String id, String name) { this.id = id; this.name = name; }

    public String getId() { return id; }
    public String getName() { return name; }

    public Duration getLastPosition(String contentId) {
        return lastPositions.getOrDefault(contentId, Duration.ZERO);
    }

    public void setLastPosition(String contentId, Duration pos) {
        lastPositions.put(contentId, pos);
    }

    public boolean isEpisodeMarkedCompleted(Episode e) {
        if (e == null) return false;
        return completedContent.contains(e.id);
    }

    public void markCompleted(Content c) {
        completedContent.add(c.id);
    }
}