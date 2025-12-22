package com.streaming.model;
public enum AgeRating {
    G(0), PG(7), PG13(13), R(18);

    private int minAge;
    AgeRating(int age) { this.minAge = age; }
    public int getMinAge() { return minAge; }
}
