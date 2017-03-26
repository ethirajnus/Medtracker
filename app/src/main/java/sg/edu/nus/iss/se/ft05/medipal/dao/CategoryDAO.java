package sg.edu.nus.iss.se.ft05.medipal.dao;

import android.database.Cursor;

import sg.edu.nus.iss.se.ft05.medipal.domain.Category;


/**
 * Created by ethi on 10/03/17.
 */

/**
 * Interface for catergory database operations
 */
public interface CategoryDAO {
    /**
     * Delete operation
     * @param id
     * @return
     */
    public int delete(int id);

    /**
     * Select all function
     * @return
     */
    public Cursor findAll();

    /**
     * Find by field
     * @param column
     * @param value
     * @return
     */
    public Category findByField(String column, Object value);

    /**
     * Insert operaton
     * @param categoryManager
     * @return
     */
    public long insert(Category categoryManager);

    /**
     * Update operation
     * @param categoryManager
     * @return
     */
    public int update(Category categoryManager);

    /**
     * Fetch
     * @return
     */
    public Cursor fetchAllCategoriesWithId();
}
