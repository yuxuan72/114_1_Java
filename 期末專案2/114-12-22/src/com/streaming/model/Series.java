package com.streaming.model;

import java.util.*;

/**
 * Series 含多個 Season；提供 getNextEpisode(current, skipWatched)
 */
public class Series extends Content {
    private final List<Season> seasons = new ArrayList<>();

    public Series(String id, String title, AgeRating rating, Set<Region> allowedRegions, SubscriptionRequirement req) {
        super(id, title, rating, allowedRegions, req);
    }

    public void addSeason(Season s) { seasons.add(s); }

    public List<Season> getSeasons() { return Collections.unmodifiableList(seasons); }

    /**
     * 取得 next episode；若 skipWatched = true 則會跳過 profile 已標示完成的集數
     */
    public Episode getNextEpisode(Episode current, Profile profile, boolean skipWatched) {
        if (current == null) return null;
        // 找 seasonal index & episode index
        int sIdx = -1;
        for (int i = 0; i < seasons.size(); i++) {
            if (seasons.get(i).getIndex() == current.getSeasonNumber()) {
                sIdx = i; break;
            }
        }
        if (sIdx == -1) return null;
        Season season = seasons.get(sIdx);
        List<Episode> eps = season.getEpisodes();
        // find within same season
        for (int i = 0; i < eps.size(); i++) {
            Episode e = eps.get(i);
            if (e.getEpisodeNumber() == current.getEpisodeNumber()) {
                // if last in season, try next season
                if (i + 1 < eps.size()) {
                    Episode candidate = eps.get(i + 1);
                    if (skipWatched && profile != null && profile.isEpisodeMarkedCompleted(candidate)) {
                        // find next non-completed in this season
                        int j = i + 2;
                        while (j < eps.size()) {
                            Episode c2 = eps.get(j);
                            if (!(skipWatched && profile.isEpisodeMarkedCompleted(c2))) return c2;
                            j++;
                        }
                        // fallthrough to next season
                    } else {
                        return candidate;
                    }
                }
                // next season
                for (int ns = sIdx + 1; ns < seasons.size(); ns++) {
                    List<Episode> nextEps = seasons.get(ns).getEpisodes();
                    if (!nextEps.isEmpty()) {
                        Episode candidate = nextEps.get(0);
                        if (skipWatched && profile != null && profile.isEpisodeMarkedCompleted(candidate)) {
                            // find first not completed in subsequent seasons
                            int k = 0;
                            while (k < nextEps.size()) {
                                Episode c3 = nextEps.get(k);
                                if (!profile.isEpisodeMarkedCompleted(c3)) return c3;
                                k++;
                            }
                            // else continue to next season
                            continue;
                        } else {
                            return candidate;
                        }
                    }
                }
                return null; // no next season
            }
        }
        return null;
    }

    // convenience overload
    public Episode getNextEpisode(Episode current) {
        return getNextEpisode(current, null, false);
    }
}