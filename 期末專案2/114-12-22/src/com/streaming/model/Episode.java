package com.streaming.model;

import java.time.Duration;
import java.util.Set;

public class Episode extends Content {
    private final int seasonNumber;
    private final int episodeNumber;
    private final Duration duration;
    private final Series parentSeries;

    public Episode(String id, String title, AgeRating rating, Set<Region> allowedRegions, SubscriptionRequirement req,
                   Series parentSeries, int seasonNumber, int episodeNumber, Duration duration) {
        super(id, title, rating, allowedRegions, req);
        this.parentSeries = parentSeries;
        this.seasonNumber = seasonNumber;
        this.episodeNumber = episodeNumber;
        this.duration = duration;
    }

    public Series getParentSeries() { return parentSeries; }
    public int getSeasonNumber() { return seasonNumber; }
    public int getEpisodeNumber() { return episodeNumber; }
    public Duration getDuration() { return duration; }
}