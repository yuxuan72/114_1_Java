package streaming;

public abstract class Advertisement {
    protected String adId;
    protected int durationSeconds;

    public Advertisement(String adId, int durationSeconds) {
        this.adId = adId;
        this.durationSeconds = durationSeconds;
    }
}