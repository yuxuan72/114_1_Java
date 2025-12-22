public interface Playable {
    PlaybackSession play(User user) throws IllegalAccessException, IllegalStateException;
    void pause();
    void resume();
    void seek(Duration position);
}