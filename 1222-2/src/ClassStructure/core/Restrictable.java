package ClassStructure.core;
// 觀看限制介面
interface Restrictable {
    AgeRating getAgeRating();
    boolean isAccessibleBy(User user);
}

// 可播放介面
interface Playable {
    PlaybackSession play(User user) throws IllegalAccessException;
}

enum AgeRating { G, PG, R, NC17 }
enum Region { TAIWAN, USA, JAPAN, GLOBAL }