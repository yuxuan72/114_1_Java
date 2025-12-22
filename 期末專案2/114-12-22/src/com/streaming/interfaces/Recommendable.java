package com.streaming.interfaces;

import com.streaming.model.User;
import com.streaming.model.Content;
import java.util.List;

public interface Recommendable {
    List<Content> getSimilarContent();
    double getRecommendationScore(User user);
}