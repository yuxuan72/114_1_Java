package com.streaming.model;

import java.util.ArrayList;
import java.util.List;

public class Season {
    private final String id;
    private final int index;
    private final List<Episode> episodes = new ArrayList<>();

    public Season(String id, int index) {
        this.id = id; this.index = index;
    }

    public void addEpisode(Episode e) {
        episodes.add(e);
    }

    public List<Episode> getEpisodes() { return episodes; }
    public int getIndex() { return index; }
}