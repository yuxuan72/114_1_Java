package streaming.content;
import streaming.Content;

import java.time.Duration;
import java.util.ArrayList;

public abstract class Series extends Content {
    private ArrayList<Episode> episodes;

    public Series(String id, String title) {
        super(id, title, Duration.ZERO);
        this.episodes = new ArrayList<>();
    }

    public void addEpisode(Episode episode) {
        episodes.add(episode);
    }
}