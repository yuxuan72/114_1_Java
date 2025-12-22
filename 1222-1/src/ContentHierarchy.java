import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

abstract class Content implements Playable, Restrictable {
    protected String title;
    protected int minAge;
    protected List<String> allowedRegions;

    // 修正：補上建構子，讓 Movie/Series 可以呼叫
    public Content(String title, int minAge, List<String> allowedRegions) {
        this.title = title;
        this.minAge = minAge;
        this.allowedRegions = allowedRegions;
    }

    @Override
    public PlaybackSession play(User user) throws Exception {
        if (user == null) throw new IllegalStateException("User must be logged in");

        // 驗證年齡
        Profile current = user.getCurrentProfile();
        if (current != null && current.getAge() < this.minAge) {
            throw new Exception("Content is rated " + minAge + ", user is too young");
        }

        // 驗證地區
        if (!allowedRegions.contains(user.getRegion())) {
            throw new Exception("Content is not available in your region");
        }

        // 驗證裝置數
        if (user.getActiveSessionsCount() >= user.getPlan().getMaxDevices()) {
            throw new IllegalStateException("Maximum streams allowed");
        }

        return new PlaybackSession(this, user);
    }

    @Override public int getMinAge() { return minAge; }
    @Override public List<String> getAllowedRegions() { return allowedRegions; }
}

class Movie extends Content {
    private Duration duration;
    public Movie(String title, int minAge, List<String> regions) {
        super(title, minAge, regions); // 修正：呼叫父類別建構子
    }
}

class Season {
    private List<Episode> episodes = new ArrayList<>();
}

class Series extends Content {
    private List<Season> seasons = new ArrayList<>();
    public Series(String title, int minAge, List<String> regions) {
        super(title, minAge, regions);
    }
}

class Episode extends Content {
    public Episode(String title, int minAge, List<String> regions) {
        super(title, minAge, regions);
    }
}