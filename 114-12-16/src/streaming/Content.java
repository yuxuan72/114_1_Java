package streaming;

import streaming.Core.Category;
import streaming.Interfaces.*;

import javax.swing.plaf.synth.Region;
import java.time.Duration;
import java.util.ArrayList;

public abstract class Content implements Playable,
        Categorizable,
        Recommendable,
        Restrictable,
        WatchHistoryTrackable {

    protected String contentId;
    protected String title;
    protected Duration duration;
    protected ArrayList<Category> categories;
    protected AgeRating ageRating;
    protected ArrayList<Region> regionRestrictions;
    protected Duration lastWatchedPosition;

    protected Content(String contentId, String title, Duration duration) {
        if (contentId == null || contentId.isEmpty())
            throw new IllegalArgumentException("contentId cannot be null or empty");
        if (title == null || title.isEmpty())
            throw new IllegalArgumentException("title cannot be null or empty");

        this.contentId = contentId;
        this.title = title;
        this.duration = duration;
        this.categories = new ArrayList<>();
        this.regionRestrictions = new ArrayList<>();
        this.lastWatchedPosition = Duration.ZERO;
    }

    public String getTitle() {
        return title;
    }
}