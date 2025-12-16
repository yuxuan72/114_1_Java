package streaming.Interfaces;

import streaming.Core.Category;

import java.util.ArrayList;

public interface Categorizable {
    void addCategory(Category category);
    ArrayList<Category> getCategories();
    boolean matchesCategory(Category category);
}