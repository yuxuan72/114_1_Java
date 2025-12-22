import java.util.List;

interface Playable {
    PlaybackSession play(User user) throws Exception;
}

interface Restrictable {
    int getMinAge();
    List<String> getAllowedRegions();
}