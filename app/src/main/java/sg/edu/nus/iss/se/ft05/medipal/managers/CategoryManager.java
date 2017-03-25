package sg.edu.nus.iss.se.ft05.medipal.managers;

import android.content.Context;
import android.database.Cursor;

import sg.edu.nus.iss.se.ft05.medipal.dao.CategoryDAO;
import sg.edu.nus.iss.se.ft05.medipal.dao.CategoryDAOImpl;
import sg.edu.nus.iss.se.ft05.medipal.dao.DBHelper;
import sg.edu.nus.iss.se.ft05.medipal.domain.Category;

/**
 * Created by ethi on 10/03/17.
 */

public class CategoryManager {

    public static final String[] safeCategoryCodes = {"CHR", "INC", "COM"};
    private static CategoryDAO categoryAll;
    private CategoryDAO categoryDAO;

    public Category getCategory() {

        return category;
    }

    public void setCategory(Category category) {

        this.category = category;
    }

    private Category category;


    public CategoryManager(String categoryName, String code, String description, Boolean remind) {

        category = new Category(categoryName, code, description, remind);
    }

    public CategoryManager() {

    }

    public static Cursor findAll(Context context) {

        categoryAll = new CategoryDAOImpl(context);
        return categoryAll.findAll();
    }

    public Category findById(Context context, int id) {

        categoryDAO = new CategoryDAOImpl(context);
        category = categoryDAO.findByField(DBHelper.CATEGORY_KEY_ID, id);
        return category;
    }

    public Category findByName(Context context, String name) {

        categoryDAO = new CategoryDAOImpl(context);
         return categoryDAO.findByField(DBHelper.CATEGORY_KEY_CATEGORY, name);
    }

    public Category findByCode(Context context, String code) {

        categoryDAO = new CategoryDAOImpl(context);
        return categoryDAO.findByField(DBHelper.CATEGORY_KEY_CODE, code);
    }


    public long save(Context context) {

        categoryDAO = new CategoryDAOImpl(context);
        return categoryDAO.insert(category);
    }

    public int update(Context context) {

        categoryDAO = new CategoryDAOImpl(context);
        return categoryDAO.update(category);
    }

    public int delete(Context context) {

        categoryDAO = new CategoryDAOImpl(context);
        return categoryDAO.delete(category.getId());
    }


    public int updateReminder(Context context, boolean isChecked) {

        categoryDAO = new CategoryDAOImpl(context);
        category.setRemind(isChecked);
        return categoryDAO.update(category);
    }

    public static Cursor fetchAllCategoriesWithId(Context context) {

        categoryAll = new CategoryDAOImpl(context);
        return categoryAll.fetchAllCategoriesWithId();
    }
}
