package streaming.Interfaces;

import streaming.Content;
import streaming.Core.User;

import java.util.ArrayList;

public interface Recommendable {
    ArrayList<Content> getSimilarContent();
    double getRecommendationScore(User user);
}