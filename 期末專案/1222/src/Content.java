public abstract class Content implements Categorizable, Recommendable, Restrictable {
    protected String id;
    protected String title;
    protected AgeRating ageRating;
    protected ArrayList<String> regions;
    protected ArrayList<Category> categories;

    @Override
    public boolean isAccessibleBy(User user) {
        return user.getAge() >= ageRating.getMinAge() && regions.contains(user.getRegion());
    }
}