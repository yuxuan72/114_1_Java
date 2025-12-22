public class PlaybackSession {
    private User user;
    private Content content;
    private Duration currentPos;

    public PlaybackSession(User user, Content content) {
        this.user = user;
        this.content = content;
        // 自動載入上次觀看位置
        this.currentPos = user.getWatchHistory(content).getLastPosition();
    }

    public void updateProgress(Duration newPos) {
        this.currentPos = newPos;
        // 即時同步回使用者觀看紀錄
        user.saveHistory(content, newPos);
    }
}