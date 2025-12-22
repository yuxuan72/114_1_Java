import java.time.Duration;

// 介面定義
interface Playable {
    PlaybackSession play(User user) throws Exception;
}

interface Restrictable {
    int getMinAge();
    java.util.List<String> getAllowedRegions();
}

// 核心功能類別
class PlaybackSession {
    private Content content;
    private User user;

    public PlaybackSession(Content content, User user) {
        this.content = content;
        this.user = user;
    }
}