package sg.edu.nus.iss.se.ft05.medipal.dao;

import android.database.Cursor;

import sg.edu.nus.iss.se.ft05.medipal.domain.Category;


/**
 * Created by ethi on 10/03/17.
 */
public interface CategoryDAO {

    public int delete(int id);

    public Cursor findAll();

    public Category findByField(String column, Object value);

    public long insert(Category categoryManager);

    public int update(Category categoryManager);

    public Cursor fetchAllCategoriesWithId();
}
