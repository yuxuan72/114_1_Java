public class Movie extends Content implements Playable, WatchHistoryTrackable {
    @Override
    public PlaybackSession play(User user) throws IllegalAccessException {
        return Platform.getInstance().requestPlayback(user, this);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void seek(Duration position) {

    }

    @Override
    public AgeRating getAgeRating() {
        return null;
    }

    @Override
    public ArrayList<String> getRegionRestrictions() {
        return null;
    }

    @Override
    public void recordProgress(Duration watchedDuration) {

    }

    @Override
    public double getWatchProgress() {
        return 0;
    }

    @Override
    public boolean isCompleted() {
        return false;
    }

    @Override
    public Duration getLastWatchedPosition() {
        return null;
    }
    // 實作其餘方法...
}