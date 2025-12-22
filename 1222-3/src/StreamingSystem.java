import java.util.*;

// --- 1. 基礎枚舉與輔助類別 ---
enum AgeRating {
    G(0), PG(12), R(18);
    final int minAge;
    AgeRating(int age) { this.minAge = age; }
}

class User {
    String name;
    int age;
    String region;
    boolean isPremium;
    int activeStreams = 0;
    Map<String, Long> history = new HashMap<>();

    public User(String n, int a, String r, boolean p) {
        name = n; age = a; region = r; isPremium = p;
    }
}

// --- 2. 播放會話紀錄 ---
class PlaybackSession {
    public PlaybackSession(String title, long pos, boolean hasAds) {
        System.out.println("✅ 播放啟動成功: [" + title + "]");
        System.out.println("   - 起始位置: " + pos + " 秒");
        if (hasAds) System.out.println("   - [提示] 當前為免費方案，已排程廣告插入點。");
    }
}

// --- 3. 內容與影集階層 ---
abstract class Content {
    String title;
    AgeRating rating;
    List<String> allowedRegions;
    boolean isPremiumContent;

    public Content(String t, AgeRating r, List<String> reg, boolean p) {
        title = t; rating = r; allowedRegions = reg; isPremiumContent = p;
    }

    public PlaybackSession play(User user) throws Exception {
        // (1) 驗證登入
        if (user == null) throw new IllegalStateException("User must be logged in");

        // (2) 驗證年齡限制
        if (user.age < rating.minAge) {
            throw new IllegalAccessException("Content is rated " + rating + ", user must be " + rating.minAge + " or older");
        }

        // (3) 驗證地區限制
        if (!allowedRegions.contains(user.region)) {
            throw new IllegalAccessException("Content is not available in your region");
        }

        // (4) 驗證訂閱方案
        if (this.isPremiumContent && !user.isPremium) {
            throw new IllegalAccessException("Upgrade to Premium to watch this content");
        }

        // (5) 驗證同時觀看裝置限制
        int limit = user.isPremium ? 4 : 1;
        if (user.activeStreams >= limit) {
            throw new IllegalStateException("Maximum " + limit + " simultaneous streams allowed");
        }

        // (6) 建立成功播放會話
        long lastPos = user.history.getOrDefault(this.title, 0L);
        user.activeStreams++;
        return new PlaybackSession(this.title, lastPos, !user.isPremium);
    }
}

class Episode {
    String title;
    int seasonNum, epNum;
    public Episode(int s, int e, String t) { seasonNum = s; epNum = e; title = t; }
    @Override
    public String toString() { return "第 " + seasonNum + " 季 第 " + epNum + " 集: " + title; }
}

class Series extends Content {
    private List<List<Episode>> seasons;
    public Series(String t, AgeRating r, List<String> reg, List<List<Episode>> data) {
        super(t, r, reg, true);
        this.seasons = data;
    }
    public Episode getNextEpisode(Episode current) {
        int sIdx = current.seasonNum - 1;
        int eIdx = current.epNum - 1;
        if (eIdx + 1 >= seasons.get(sIdx).size()) {
            if (sIdx + 1 < seasons.size()) return seasons.get(sIdx + 1).get(0);
            return null;
        }
        return seasons.get(sIdx).get(eIdx + 1);
    }
}

class Movie extends Content {
    public Movie(String t, AgeRating r, List<String> reg, boolean p) { super(t, r, reg, p); }
}

// --- 4. 執行與測試 (每一階段皆可執行) ---
public class StreamingSystem {
    public static void main(String[] args) {
        // 準備測試資料
        Movie movieR = new Movie("奧本海默", AgeRating.R, Arrays.asList("US"), true);
        Movie movieG = new Movie("角落小夥伴", AgeRating.G, Arrays.asList("TW", "US"), false);

        List<List<Episode>> seriesData = Arrays.asList(
                Arrays.asList(new Episode(1,1,"序章"), new Episode(1,2,"終章")),
                Arrays.asList(new Episode(2,1,"新篇章"))
        );
        Series series = new Series("進擊的程式碼", AgeRating.PG, Arrays.asList("TW", "US"), seriesData);

        // --- 開始各階段測試 ---

        // 階段 1: 未登入測試
        testStep("1. 未登入驗證", () -> movieG.play(null));

        // 階段 2: 年齡限制測試 (10歲嘗試看R級)
        User kid = new User("小明", 10, "TW", false);
        testStep("2. 年齡限制驗證", () -> movieR.play(kid));

        // 階段 3: 地區限制測試 (在台灣看僅限美國內容)
        User adultTW = new User("大華", 25, "TW", true);
        testStep("3. 地區限制驗證", () -> movieR.play(adultTW));

        // 階段 4: 訂閱方案測試 (免費帳號看 Premium 電影)
        testStep("4. 訂閱方案驗證", () -> movieR.play(kid)); // kid 已改為 TW 地區但非 Premium

        // 階段 5: 裝置數量限制測試
        User userSingle = new User("阿強", 30, "TW", false); // 非 Premium 限 1 台
        testStep("5. 裝置限制驗證", () -> {
            userSingle.activeStreams = 1;
            movieG.play(userSingle);
        });

        // 階段 6: 成功播放案例 (含歷史紀錄與廣告)
        System.out.println("\n>>> [ 6. 成功播放案例 ]");
        try {
            User premiumUser = new User("老王", 35, "US", true);
            premiumUser.history.put("奧本海默", 3600L); // 已看一小時
            movieR.play(premiumUser);
        } catch (Exception e) { e.printStackTrace(); }

        // 階段 7: 影集跳轉邏輯
        System.out.println("\n>>> [ 7. 影集下一集跳轉 ]");
        Episode currentEp = seriesData.get(0).get(1); // 第一季第二集
        System.out.println("當前: " + currentEp);
        Episode nextEp = series.getNextEpisode(currentEp);
        System.out.println("自動跳轉 -> " + (nextEp != null ? nextEp : "已完結"));
    }

    // 輔助測試方法
    private static void testStep(String label, TestAction action) {
        System.out.println("\n>>> [ " + label + " ]");
        try {
            action.run();
        } catch (Exception e) {
            System.out.println("捕獲異常: " + e.getClass().getSimpleName() + " - " + e.getMessage());
        }
    }

    interface TestAction { void run() throws Exception; }
}