package streaming.content;

import streaming.Content;

import java.time.Duration;

public abstract class Episode extends Content {
    private int season;
    private int episodeNumber;
    private Series series;

    public Episode(String id, String title, Duration duration,
                   Series series, int season, int episodeNumber) {
        super(id, title, duration);
        this.series = series;
        this.season = season;
        this.episodeNumber = episodeNumber;
    }
}