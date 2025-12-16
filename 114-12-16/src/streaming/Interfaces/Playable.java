package streaming.Interfaces;

import streaming.Core.User;
import streaming.Core.WatchHistory;

public interface Playable {
    WatchHistory.PlaybackSession play(User user);
    void pause();
    void resume();
    void seek(java.time.Duration position);
}
