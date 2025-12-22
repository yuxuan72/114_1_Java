package com.streaming.interfaces;

import com.streaming.model.PlaybackSession;
import com.streaming.model.User;

public interface Playable {
    PlaybackSession play(User user) throws IllegalAccessException;
    void pause();
    void resume();
    void seek(java.time.Duration position);
}