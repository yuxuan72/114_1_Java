package streaming.content;

import streaming.Content;

import java.time.Duration;

public abstract class Documentary extends Content {
    public Documentary(String id, String title, Duration duration) {
        super(id, title, duration);
    }
}