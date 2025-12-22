public interface Restrictable {
    AgeRating getAgeRating();
    ArrayList<String> getRegionRestrictions();
    boolean isAccessibleBy(User user);
}