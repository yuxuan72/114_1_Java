package streaming;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 簡單示範程式：執行後會在標準輸出列印平台內容。
 *
 * 編譯與執行：
 * javac streaming/StreamingPlatform.java streaming/Main.java
 * java -cp . streaming.Main
 */
public class Main {
    public static void main(String[] args) {
        StreamingPlatform platform = new StreamingPlatform("MyStream");

        // 建立影片並加入平台
        StreamingPlatform.Video v1 = new StreamingPlatform.Video("v001", "A Great Movie", "Drama", true);
        StreamingPlatform.Video v2 = new StreamingPlatform.Video("v002", "Comedy Night", "Comedy", false);
        platform.addVideo(v1);
        platform.addVideo(v2);

        // 建立使用者並加入平台
        StreamingPlatform.User u1 = new StreamingPlatform.User("user01");
        u1.setDisplayName("Alice");
        platform.addUser(u1);

        // 範例查詢與輸出
        System.out.println("Find v001: " + platform.findVideoById("v001"));
        System.out.println("Available videos: " + platform.getAvailableVideos());
        System.out.println("Search 'comedy': " + platform.findVideosByKeyword("comedy"));
        System.out.println("All users: " + platform.getAllUsers());
    }
}


/**
 * 簡單版 StreamingPlatform：基本的新增與查詢功能。
 */
class StreamingPlatform {
    private String name;
    private final List<Video> videos = new ArrayList<>();
    private final List<User> users = new ArrayList<>();

    public StreamingPlatform(String name) {
        if (name == null || name.isEmpty()) throw new IllegalArgumentException("name required");
        this.name = name;
    }

    public void addVideo(Video v) {
        Objects.requireNonNull(v, "video cannot be null");
        if (findVideoById(v.getVideoId()) != null) throw new IllegalArgumentException("duplicate videoId");
        videos.add(v);
    }

    public Video findVideoById(String videoId) {
        if (videoId == null) return null;
        for (Video v : videos) if (videoId.equals(v.getVideoId())) return v;
        return null;
    }

    public List<Video> findVideosByKeyword(String keyword) {
        if (keyword == null || keyword.isEmpty()) return Collections.emptyList();
        String k = keyword.toLowerCase();
        List<Video> out = new ArrayList<>();
        for (Video v : videos) {
            String t = v.getTitle() == null ? "" : v.getTitle().toLowerCase();
            String c = v.getCategory() == null ? "" : v.getCategory().toLowerCase();
            if (t.contains(k) || c.contains(k)) out.add(v);
        }
        return out;
    }

    public List<Video> getAvailableVideos() {
        List<Video> out = new ArrayList<>();
        for (Video v : videos) if (v.isAvailable()) out.add(v);
        return out;
    }

    public List<Video> getAllVideos() { return Collections.unmodifiableList(videos); }

    public void addUser(User u) {
        Objects.requireNonNull(u, "user cannot be null");
        if (findUserById(u.getUserId()) != null) throw new IllegalArgumentException("duplicate userId");
        users.add(u);
    }

    public User findUserById(String userId) {
        if (userId == null) return null;
        for (User u : users) if (userId.equals(u.getUserId())) return u;
        return null;
    }

    public List<User> getAllUsers() { return Collections.unmodifiableList(users); }

    // 簡單 User / Video 類別（可另拆檔）
    public static class Video {
        private final String videoId;
        private String title;
        private String category;
        private boolean available;

        public Video(String videoId, String title, String category, boolean available) {
            if (videoId == null || videoId.isEmpty()) throw new IllegalArgumentException("videoId required");
            this.videoId = videoId;
            this.title = title;
            this.category = category;
            this.available = available;
        }

        public String getVideoId() { return videoId; }
        public String getTitle() { return title; }
        public String getCategory() { return category; }
        public boolean isAvailable() { return available; }
        public void setAvailable(boolean av) { this.available = av; }

        @Override
        public String toString() {
            return "Video{" +
                    "videoId='" + videoId + '\'' +
                    ", title='" + title + '\'' +
                    ", category='" + category + '\'' +
                    ", available=" + available +
                    '}';
        }
    }

    public static class User {
        private final String userId;
        private String displayName;

        public User(String userId) {
            if (userId == null || userId.isEmpty()) throw new IllegalArgumentException("userId required");
            this.userId = userId;
        }

        public String getUserId() { return userId; }
        public String getDisplayName() { return displayName; }
        public void setDisplayName(String n) { this.displayName = n; }

        @Override
        public String toString() {
            return "User{" +
                    "userId='" + userId + '\'' +
                    ", displayName='" + displayName + '\'' +
                    '}';
        }
    }
}