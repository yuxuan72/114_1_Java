package com.streaming.interfaces;

import com.streaming.model.PlaybackSession;
import com.streaming.model.User;

public interface Playable {
    PlaybackSession play(User user);
    void pause();
    void resume();
}
