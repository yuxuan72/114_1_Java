package com.streaming.model;

import java.time.Duration;
import java.util.Set;

public class Movie extends Content {
    private final Duration duration;

    public Movie(String id, String title, AgeRating ageRating, Set<Region> allowedRegions, SubscriptionRequirement req, Duration duration) {
        super(id, title, ageRating, allowedRegions, req);
        this.duration = duration;
    }

    public Duration getDuration() { return duration; }
}