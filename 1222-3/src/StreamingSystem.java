import java.util.*;

// --- 1. 廣告系統階層 (含時間點邏輯) ---
abstract class Advertisement {
    String content;
    int timestampMinute; // 廣告顯示的時間點（分鐘）

    public Advertisement(String content, int minute) {
        this.content = content;
        this.timestampMinute = minute;
    }
    public abstract void display();
}

class PreRollAd extends Advertisement {
    public PreRollAd(String content) { super(content, 0); }
    @Override
    public void display() {
        System.out.println("🎬 [片頭廣告] (00:00) 正在播放: " + content + " - 廣告結束後開始正片");
    }
}

class MidRollAd extends Advertisement {
    public MidRollAd(String content, int minute) { super(content, minute); }
    @Override
    public void display() {
        System.out.println("⏳ [中插廣告] (" + timestampMinute + ":00) 節目插播: " + content);
    }
}

class BannerAd extends Advertisement {
    public BannerAd(String content, int minute) { super(content, minute); }
    @Override
    public void display() {
        System.out.println("🖼️ [橫幅廣告] (" + timestampMinute + ":00) 螢幕下方顯示: " + content);
    }
}

// --- 2. 基礎枚舉與使用者類別 ---
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
    Set<String> completedEpisodes = new HashSet<>();

    public User(String n, int a, String r, boolean p) {
        name = n; age = a; region = r; isPremium = p;
    }
}

// --- 3. 播放會話紀錄 (整合廣告排程過濾) ---
class PlaybackSession {
    String title;
    long startPosition;
    boolean adsScheduled = false;
    List<Advertisement> adSchedule = new ArrayList<>();

    public PlaybackSession(String title, long pos) {
        this.title = title;
        this.startPosition = pos;
    }

    public void scheduleAds() {
        this.adsScheduled = true;
        // 預設廣告排程
        adSchedule.add(new PreRollAd("2025 全新影集預告"));
        adSchedule.add(new BannerAd("訂閱 Premium 享無廣告體驗", 1));
        adSchedule.add(new MidRollAd("品牌贊助中斷廣告", 5));
    }

    public void start() {
        System.out.println("✅ 播放啟動成功: [" + title + "]");
        System.out.println("   - 從上次位置繼續: " + startPosition + " 秒");

        if (adsScheduled) {
            System.out.println("   - 播放模式: 免費含廣告 (系統已根據進度過濾廣告)");
            long startMinute = startPosition / 60;
            for (Advertisement ad : adSchedule) {
                // 只播放當前進度之後的廣告
                if (ad.timestampMinute >= startMinute) {
                    ad.display();
                }
            }
        } else {
            System.out.println("   - 播放模式: ✨ Premium 純淨模式");
        }
    }
}

// --- 4. 內容與影集階層 ---
abstract class Content {
    String title;
    AgeRating rating;
    List<String> allowedRegions;
    boolean isPremiumContent;

    public Content(String t, AgeRating r, List<String> reg, boolean p) {
        title = t; rating = r; allowedRegions = reg; isPremiumContent = p;
    }

    public PlaybackSession play(User user) throws Exception {
        if (user == null) throw new IllegalStateException("User must be logged in");
        if (user.age < rating.minAge) throw new IllegalAccessException("Age limit error: Content is " + rating);
        if (!allowedRegions.contains(user.region)) throw new IllegalAccessException("Region error: Not available in " + user.region);
        if (this.isPremiumContent && !user.isPremium) throw new IllegalAccessException("Premium upgrade required");

        int limit = user.isPremium ? 4 : 1;
        if (user.activeStreams >= limit) throw new IllegalStateException("Max simultaneous streams reached");

        long lastPos = user.history.getOrDefault(this.title, 0L);
        PlaybackSession session = new PlaybackSession(this.title, lastPos);

        if (!user.isPremium) session.scheduleAds();

        user.activeStreams++;
        session.start();
        return session;
    }
}

class Movie extends Content {
    public Movie(String t, AgeRating r, List<String> reg, boolean p) { super(t, r, reg, p); }
}

class Episode {
    int sNum, eNum;
    String title;
    public Episode(int s, int e, String t) { sNum = s; eNum = e; title = t; }
    @Override
    public String toString() { return "第 " + sNum + " 季 第 " + eNum + " 集: " + title; }
}

class Series extends Content {
    private List<List<Episode>> seasons;
    public Series(String t, AgeRating r, List<String> reg, List<List<Episode>> data) {
        super(t, r, reg, true);
        this.seasons = data;
    }

    public Episode getNextEpisode(Episode current, User user) {
        Episode nextCandidate = calculateRawNext(current);
        while (nextCandidate != null && user.completedEpisodes.contains(nextCandidate.title)) {
            System.out.println("   (跳過已看完集數: " + nextCandidate.title + ")");
            nextCandidate = calculateRawNext(nextCandidate);
        }
        return nextCandidate;
    }

    private Episode calculateRawNext(Episode current) {
        int sIdx = current.sNum - 1;
        int eIdx = current.eNum - 1;
        if (eIdx + 1 < seasons.get(sIdx).size()) return seasons.get(sIdx).get(eIdx + 1);
        else if (sIdx + 1 < seasons.size()) return seasons.get(sIdx + 1).get(0);
        return null;
    }
}

// --- 5. 執行與測試系統 ---
public class StreamingSystem {
    private static final String CORRECT_PASSWORD = "0000";
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // 初始化測試數據
        Movie movieR = new Movie("奧本海默", AgeRating.R, Arrays.asList("TW"), true);
        Movie movieG = new Movie("免費短片", AgeRating.G, Arrays.asList("TW", "US"), false);

        List<List<Episode>> seriesData = Arrays.asList(
                Arrays.asList(new Episode(1,1,"序章"), new Episode(1,2,"中轉站"), new Episode(1,3,"第一季終")),
                Arrays.asList(new Episode(2,1,"新世界"), new Episode(2,2,"大結局"))
        );
        Series series = new Series("進擊的 Java", AgeRating.PG, Arrays.asList("TW"), seriesData);

        // --- 階段 1: 登入 ---
        System.out.println(">>> [ 階段 1: 登入驗證 ]");
        System.out.print("請輸入登入密碼: ");
        if (!CORRECT_PASSWORD.equals(scanner.nextLine())) {
            System.out.println("❌ 登入失敗：User must be logged in");
            return;
        }
        System.out.println("🔓 登入成功！");

        // --- 階段 2: 年齡 ---
        System.out.println("\n>>> [ 階段 2: 年齡限制驗證 ]");
        System.out.print("請輸入您的年齡: ");
        int inputAge = Integer.parseInt(scanner.nextLine());
        User sessionUser = new User("測試員", inputAge, "UNKNOWN", false);

        if (sessionUser.age < movieR.rating.minAge) {
            System.out.println("❌ 權限錯誤: Content is rated " + movieR.rating + ", user must be " + movieR.rating.minAge + " or older");
            return;
        }

        // --- 階段 3: 地區 (手動驗證) ---
        System.out.println("\n>>> [ 階段 3: 地區限制驗證 ]");
        System.out.print("請輸入您所在的地區: ");
        String inputRegion = scanner.nextLine().trim();
        if (!inputRegion.equalsIgnoreCase("Taiwan")) {
            System.out.println("❌ 地區限制錯誤: Content is not available in your region");
            return;
        }
        sessionUser.region = "TW";
        System.out.println("✅ 地區驗證成功！");

        // --- 階段 4: 廣告時間點與播放測試 ---
        runStage("階段 4: 廣告時間點測試 (從第 2 分鐘開始觀看免費內容)", () -> {
            sessionUser.isPremium = false;
            sessionUser.activeStreams = 0;
            sessionUser.history.put("免費短片", 125L); // 2 分 5 秒
            movieG.play(sessionUser);
        });

        // --- 階段 5: 方案與裝置限制測試 ---
        runStage("階段 5: 方案限制驗證 (嘗試播放 Premium 內容)", () -> {
            sessionUser.activeStreams = 0;
            movieR.play(sessionUser); // 若 sessionUser.isPremium 為 false 會在此處報錯
        });

        // --- 階段 6: 影集跳轉與權限測試 ---
        runStage("階段 6: 影集跳轉與權限驗證", () -> {
            sessionUser.isPremium = true; // 升級 Premium
            sessionUser.activeStreams = 0;
            sessionUser.completedEpisodes.add("中轉站");
            sessionUser.completedEpisodes.add("第一季終");

            series.play(sessionUser);

            Episode current = seriesData.get(0).get(0); // S1E1
            System.out.println("當前播放完畢: " + current);
            Episode next = series.getNextEpisode(current, sessionUser);
            System.out.println(">>> 系統自動撥放下一集 -> " + next);
        });

        System.out.println("\n🎉 所有系統流程整合測試完成！");
    }

    private static void runStage(String label, TestAction action) {
        System.out.println("\n>>> [ " + label + " ]");
        try {
            action.run();
        } catch (Exception e) {
            System.out.println("❌ 攔截預期錯誤: " + e.getMessage());
        }
    }

    interface TestAction { void run() throws Exception; }
}