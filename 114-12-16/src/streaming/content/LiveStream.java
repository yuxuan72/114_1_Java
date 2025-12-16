package streaming.content;

import streaming.Content;

import java.time.Duration;

public abstract class LiveStream extends Content {
    public LiveStream(String id, String title) {
        super(id, title, Duration.ZERO);
    }
}