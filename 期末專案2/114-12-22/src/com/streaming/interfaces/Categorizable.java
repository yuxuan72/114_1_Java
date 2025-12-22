package com.streaming.interfaces;

import com.streaming.model.Category;
import java.util.List;



public interface Categorizable {
    void addCategory(Category category);
    List<Category> getCategories();
}
