package com.streaming.interfaces;

import com.streaming.model.AgeRating;
import com.streaming.model.Region;
import com.streaming.model.User;
import java.util.List;

public interface Restrictable {
    AgeRating getAgeRating();
    List<Region> getRegionRestrictions();
    boolean isAccessibleBy(User user);
}