public class Episode extends Content implements Playable, WatchHistoryTrackable {
    private Series parentSeries;
    private int episodeNumber;

    @Override
    public PlaybackSession play(User user) throws IllegalAccessException {
        return Platform.getInstance().requestPlayback(user, this);
    }
}