package com.ethigeek.medtracker.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ethigeek.medtracker.daoutils.DBHelper;
import com.ethigeek.medtracker.domain.Category;

import static com.ethigeek.medtracker.daoutils.DbConstants.*;


/**
 * Implementation class for category database operations
 * @author Ethiraj Srinivasan
 */
public class CategoryDAOImpl extends DBHelper implements CategoryDAO {

    public CategoryDAOImpl(Context context) {
        super(context);
    }

    /**
     * Delete operation
     * @param id
     * @return
     */
    @Override
    public int delete(int id) {

        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_CATEGORY, CATEGORY_KEY_ID + DATABASE_COMMAND_SYMBOL,
                new String[]{String.valueOf(id)});
    }

    /**
     * Select all operation
     * @return
     */
    @Override
    public Cursor findAll() {

        String selectQuery = DATABASE_COMMAND_SELECT_ALL + TABLE_CATEGORY;
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(selectQuery, null);


    }

    /**
     *
     * @param column
     * @param value
     * @return
     */
    @Override
    public Category findByField(String column, Object value) {

        SQLiteDatabase db = this.getReadableDatabase();
        if (column.contentEquals(CATEGORY_KEY_CATEGORY) || column.contentEquals(CATEGORY_KEY_CODE)) {
            value = DATABASE_COMMAND_SINGLE_QUOTE + value + DATABASE_COMMAND_SINGLE_QUOTE;
        }

        String selectQuery = DATABASE_COMMAND_SELECT_ALL + TABLE_CATEGORY + DATABASE_COMMAND_SELECT_WHERE
                + column + DATABASE_COMMAND_SYMBOL_EQUAL + value;
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null) {
            c.moveToFirst();
        }

        Category category = new Category();

        if (c.getCount() > 0) {

            category.setId(c.getInt(c.getColumnIndex(CATEGORY_KEY_ID)));
            category.setCategoryName((c.getString(c.getColumnIndex(CATEGORY_KEY_CATEGORY))));
            category.setCode(c.getString(c.getColumnIndex(CATEGORY_KEY_CODE)));
            category.setDescription(c.getString(c.getColumnIndex(CATEGORY_KEY_DESCRIPTION)));
            category.setRemind(c.getInt(c.getColumnIndex(CATEGORY_KEY_REMIND)) == 1);
        }

        db.close();

        return category;
    }

    /*
     * Creating a category
     */
    @Override
    public long insert(Category category) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(CATEGORY_KEY_CATEGORY, category.getCategoryName());
        values.put(CATEGORY_KEY_CODE, category.getCode());
        values.put(CATEGORY_KEY_DESCRIPTION, category.getDescription());
        values.put(CATEGORY_KEY_REMIND, category.getRemind());

        // insert row
        return db.insert(TABLE_CATEGORY, null, values);
    }

    /**
     * Update operation
     * @param category
     * @return
     */
    @Override
    public int update(Category category) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(CATEGORY_KEY_CATEGORY, category.getCategoryName());
        values.put(CATEGORY_KEY_CODE, category.getCode());
        values.put(CATEGORY_KEY_DESCRIPTION, category.getDescription());
        values.put(CATEGORY_KEY_REMIND, category.getRemind());
        // updating row
        return db.update(TABLE_CATEGORY, values, CATEGORY_KEY_ID + DATABASE_COMMAND_SYMBOL,
                new String[]{String.valueOf(category.getId())});
    }

    /**
     *
     * @return
     */
    public Cursor fetchAllCategoriesWithId() {

        String selectQuery = DATABASE_COMMAND_SELECT_ID_CATEGORY + TABLE_CATEGORY;
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(selectQuery, null);
    }
}
