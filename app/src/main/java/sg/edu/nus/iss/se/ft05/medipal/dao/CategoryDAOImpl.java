package sg.edu.nus.iss.se.ft05.medipal.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import sg.edu.nus.iss.se.ft05.medipal.Category;

/**
 * Created by ethi on 10/03/17.
 */

public class CategoryDAOImpl extends DBHelper implements CategoryDAO {


    private static final String LOG = "CategoryDAOImpl";

    public CategoryDAOImpl(Context context) {
        super(context);
    }

    @Override
    public int delete(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result =  db.delete(TABLE_CATEGORY, CATEGORY_KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
        return result;
    }

    @Override
    public Cursor findAll() {
        List<Category> categories = new ArrayList<Category>();

        String selectQuery = "SELECT  * FROM " + TABLE_CATEGORY;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor;


    }

    @Override
    public Category findById(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_CATEGORY + " WHERE "
                + CATEGORY_KEY_ID + " = " + id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Category category = new Category();
        category.setId(c.getInt(c.getColumnIndex(CATEGORY_KEY_ID)));
        category.setCategoryName((c.getString(c.getColumnIndex(CATEGORY_KEY_CATEGORY))));
        category.setCode(c.getString(c.getColumnIndex(CATEGORY_KEY_CODE)));
        category.setDescription(c.getString(c.getColumnIndex(CATEGORY_KEY_DESCRIPTION)));
        category.setRemind(c.getInt(c.getColumnIndex(CATEGORY_KEY_REMIND)) == 1);
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
        long result = db.insert(TABLE_CATEGORY, null, values);
        return  result;
    }

    @Override
    public int update(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CATEGORY_KEY_CATEGORY, category.getCategoryName());
        values.put(CATEGORY_KEY_CODE, category.getCode());
        values.put(CATEGORY_KEY_DESCRIPTION, category.getDescription());
        values.put(CATEGORY_KEY_REMIND, category.getRemind());
        // updating row
        int result = db.update(TABLE_CATEGORY, values, CATEGORY_KEY_ID + " = ?",
                new String[] { String.valueOf(category.getId()) });
        return result;
    }

}
