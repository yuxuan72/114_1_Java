import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        // 1. 初始化平台與基本資料
        Platform platform = Platform.getInstance();
        Category action = new Category("Action");
        Category scifi = new Category("Sci-Fi");

        // 2. 建立訂閱方案
        Subscription premium = new PremiumPlan();
        Subscription free = new FreeTier();

        // 3. 建立使用者與 Profile (假設使用者 15 歲，在台灣)
        User user = new User("user@example.com", premium, "Taiwan");
        Profile kidProfile = new Profile("Junior", 15);
        user.addProfile(kidProfile);
        user.setLoggedIn(true);

        // 4. 建立內容：一部限制級電影 (R-rated)
        Movie adultMovie = new Movie("John Wick", AgeRating.R); // R 級通常需要 18+
        adultMovie.addCategory(action);
        adultMovie.setRegions(new ArrayList<>(Arrays.asList("Taiwan", "USA")));

        // 5. 建立內容：影集架構
        Series strangerThings = new Series("Stranger Things", AgeRating.PG13);
        Episode ep1 = new Episode("Chapter One: The Vanishing of Will Byers", strangerThings);
        // ... 假設已經把 ep1 加入 strangerThings 的清單中

        System.out.println("--- 開始影音平台模擬測試 ---");

        // 測試場景 A：挑戰年齡限制
        try {
            System.out.println("\n[場景 A] 使用者 (15歲) 嘗試觀看限制級電影: " + adultMovie.title);
            PlaybackSession session = adultMovie.play(user);
        } catch (IllegalAccessException e) {
            System.err.println("播放失敗: " + e.getMessage());
        }

        // 測試場景 B：正常播放影集單集 (符合年齡)
        try {
            System.out.println("\n[場景 B] 使用者嘗試觀看影集: " + ep1.title);
            PlaybackSession session = ep1.