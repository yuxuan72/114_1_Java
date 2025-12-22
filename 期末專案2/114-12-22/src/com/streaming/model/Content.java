package com.streaming.model;

import com.streaming.interfaces.*;
import java.time.Duration;
import java.util.*;

import java.util.*;

public abstract class Content implements Playable, Categorizable, Restrictable {

    protected String title;
    protected Duration duration;
    protected AgeRating ageRating;
    protected List<Region> allowedRegions = new ArrayList<>();
    protected List<Category> categories = new ArrayList<>();

    public abstract boolean isPremiumContent();

    @Override
    public PlaybackSession play(User user) {

        if (!user.isLoggedIn()) {
            throw new IllegalStateException("User must be logged in");
        }

        if (user.getAge() < ageRating.getMinAge()) {
            throw new IllegalAccessError(
                    "Content is rated " + ageRating +
                            ", user must be " + ageRating.getMinAge() + " or older"
            );
        }

        if (!allowedRegions.contains(user.getRegion())) {
            throw new IllegalAccessError("Content is not available in your region");
        }

        Subscription plan = user.getSubscription();

        if (isPremiumContent() && !plan.canWatchPremium()) {
            throw new IllegalStateException("Upgrade required");
        }

        if (!user.canStartNewStream()) {
            throw new IllegalStateException(
                    "Maximum " + plan.getMaxDevices() + " simultaneous streams allowed"
            );
        }

        Duration last = user.getLastWatchPosition(this);
        PlaybackSession session = new PlaybackSession(user, this, last);

        if (!plan.isAdFree()) {
            session.scheduleAds();
        }

        user.registerSession(session);
        return session;
    }

    public void addCategory(Category category) {
        categories.add(category);
    }

    public List<Category> getCategories() {
        return categories;
    }

    public AgeRating getAgeRating() {
        return ageRating;
    }

    public boolean isAccessibleBy(User user) {
        return user.getAge() >= ageRating.getMinAge();
    }
}
