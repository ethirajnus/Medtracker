package sg.edu.nus.iss.se.ft05.medipal.dao;

import android.database.Cursor;

import java.util.List;

import sg.edu.nus.iss.se.ft05.medipal.Category;

/**
 * Created by ethi on 10/03/17.
 */

public interface CategoryDAO {
    public int delete(long id);
    public Cursor findAll();
    public Category findById(long id);
    public long insert(Category category);
    public int update(Category category);
}
