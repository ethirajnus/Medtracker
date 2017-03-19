package sg.edu.nus.iss.se.ft05.medipal.dao;

import android.animation.ObjectAnimator;
import android.database.Cursor;

import sg.edu.nus.iss.se.ft05.medipal.model.Category;

/**
 * Created by ethi on 10/03/17.
 */

public interface CategoryDAO {
    int delete(int id);
    Cursor findAll();
    Category findByField(String column, Object value);
    long insert(Category category);
    int update(Category category);
    Cursor fetchAllCategoriesWithId();
}
