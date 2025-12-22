public class Platform {
    private static Platform instance;
    private Platform() {}

    public static Platform getInstance() {
        if (instance == null) instance = new Platform();
        return instance;
    }

    public PlaybackSession requestPlayback(User user, Content content) throws IllegalAccessException {
        // 權限判斷邏輯
        if (!content.isAccessibleBy(user)) {
            throw new IllegalAccessException("Age or Region restriction");
        }

        if (getActiveStreams(user) >= user.getSubscription().getMaxDevices()) {
            throw new IllegalStateException("Max devices reached");
        }

        return new PlaybackSession(user, content);
    }

    private int getActiveStreams(User user) { /* 查詢活躍 Session */ return 0; }
}