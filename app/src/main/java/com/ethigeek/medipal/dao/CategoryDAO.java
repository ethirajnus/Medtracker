package com.ethigeek.medipal.dao;

import android.database.Cursor;

import com.ethigeek.medipal.domain.Category;




/**
 * Interface for category database operations
 * @author Ethiraj Srinivasan
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
