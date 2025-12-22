public interface WatchHistoryTrackable {
    void recordProgress(Duration watchedDuration);
    double getWatchProgress();
    boolean isCompleted();
    Duration getLastWatchedPosition();
}