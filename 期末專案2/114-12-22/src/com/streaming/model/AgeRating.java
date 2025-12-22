package com.streaming.model;

public enum AgeRating {
    G(0), PG(10), PG13(13), R(17), MA18(18);

    private final int minimumAge;

    AgeRating(int minimumAge) {
        this.minimumAge = minimumAge;
    }

    public int getMinimumAge() {
        return minimumAge;
    }
}