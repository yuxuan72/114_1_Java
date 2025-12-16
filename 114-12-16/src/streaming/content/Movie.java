package streaming.content;

import streaming.Content;

import java.time.Duration;

public abstract class Movie extends Content {
    public Movie(String id, String title, Duration duration) {
        super(id, title, duration);
    }
}