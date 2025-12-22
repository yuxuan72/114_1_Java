package ClassStructure.Content;

abstract class Content implements Restrictable, Playable {
    protected String title;
    protected AgeRating rating;
    protected List<Region> allowedRegions;
    protected boolean isPremium;

    // 實作權限檢查邏輯
    public boolean isAccessibleBy(User user) {
        if (user.getAge() < getMinAge(this.rating)) return false;
        if (!allowedRegions.contains(user.getCurrentRegion())) return false;
        return true;
    }

    private int getMinAge(AgeRating r) {
        return switch(r) { case PG -> 12; case R -> 18; default -> 0; };
    }
}

class Movie extends Content {
    public PlaybackSession play(User user) throws IllegalAccessException {
        // 詳見後續邏輯實作
        return new PlaybackSession(this, user);
    }
}