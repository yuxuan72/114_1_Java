import java.util.*;

// --- 基礎枚舉與介面 ---
enum AgeRating { G, PG13, R }
enum Region { TAIWAN, USA, JAPAN }

interface Restrictable {
    boolean isAccessibleBy(User user);
}

// --- 核心類別 ---
abstract class Content implements Restrictable {
    protected String title;
    protected AgeRating rating;
    protected List<Region> allowedRegions;
    protected boolean isPremium;

    public Content(String title, AgeRating rating, List<Region> regions, boolean isPremium) {
        this.title = title;
        this.rating = rating;
        this.allowedRegions = regions;
        this.isPremium = isPremium;
    }

    public String getTitle() { return title; }

    @Override
    public boolean isAccessibleBy(User user) {
        // 地區檢查
        if (!allowedRegions.contains(user.region)) {
            System.out.println("❌ 地區限制: " + title + " 在您所在的地區無法觀看。");
            return false;
        }
        // 年齡檢查
        int minAge = (rating == AgeRating.R) ? 18 : (rating == AgeRating.PG13 ? 13 : 0);
        if (user.age < minAge) {
            System.out.println("❌ 年齡限制: 此內容分級為 " + rating + "，您未滿 " + minAge + " 歲。");
            return false;
        }
        return true;
    }
}

class Movie extends Content {
    public Movie(String title, AgeRating rating, List<Region> regions, boolean isPremium) {
        super(title, rating, regions, isPremium);
    }
}

// --- 訂閱系統 ---
abstract class Subscription {
    public abstract int getMaxDevices();
    public abstract boolean canWatchPremium();
}

class FreeTier extends Subscription {
    public int getMaxDevices() { return 1; }
    public boolean canWatchPremium() { return false; }
}

class PremiumPlan extends Subscription {
    public int getMaxDevices() { return 4; }
    public boolean canWatchPremium() { return true; }
}

// --- 使用者類別 ---
class User {
    String name;
    int age;
    Region region;
    Subscription subscription;
    int activeSessions = 0; // 模擬當前正在觀看的裝置數

    public User(String name, int age, Region region, Subscription sub) {
        this.name = name;
        this.age = age;