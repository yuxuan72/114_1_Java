package com.streaming.interfaces;

import java.time.Duration;

public interface WatchHistoryTrackable {
    void recordProgress(Duration watchedDuration);
    double getWatchProgress(); // percent 0..100
    boolean isCompleted();
    java.time.Duration getLastWatchedPosition();
}