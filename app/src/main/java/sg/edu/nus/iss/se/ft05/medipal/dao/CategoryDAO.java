package sg.edu.nus.iss.se.ft05.medipal.dao;

import android.database.Cursor;

import java.util.List;

import sg.edu.nus.iss.se.ft05.medipal.Category;

/**
 * Created by ethi on 10/03/17.
 */

public interface CategoryDAO {
    int delete(int id);
    Cursor findAll();
    Category findById(int id);
    long insert(Category category);
    int update(Category category);
    Cursor fetchAllCategoriesWithId();
}
