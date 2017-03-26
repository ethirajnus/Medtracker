package sg.edu.nus.iss.se.ft05.medipal.managers;

import android.content.Context;
import android.database.Cursor;

import sg.edu.nus.iss.se.ft05.medipal.dao.CategoryDAO;
import sg.edu.nus.iss.se.ft05.medipal.dao.CategoryDAOImpl;
import sg.edu.nus.iss.se.ft05.medipal.dao.DBHelper;
import sg.edu.nus.iss.se.ft05.medipal.domain.Category;



/**
 * Class for Category Manager
 * Created by ethi on 10/03/17.
 */
public class CategoryManager {

    public static final String[] safeCategoryCodes = {"CHR", "INC", "COM"};
    private static CategoryDAO categoryAll;
    private CategoryDAO categoryDAO;

    /**
     * Getter for Category
     *
     * @return category
     */
    public Category getCategory() {

        return category;
    }

    /**
     * Setter for Category
     * @param category
     */
    public void setCategory(Category category) {

        this.category = category;
    }

    private Category category;


    public CategoryManager(String categoryName, String code, String description, Boolean remind) {

        category = new Category(categoryName, code, description, remind);
    }

    public CategoryManager() {

    }

    /**
     * Method for finding all category
     * @param context
     * @return
     */
    public static Cursor findAll(Context context) {

        categoryAll = new CategoryDAOImpl(context);
        return categoryAll.findAll();
    }

    /**
     * Method for find category by id
     * @param context
     * @param id
     * @return
     */
    public Category findById(Context context, int id) {

        categoryDAO = new CategoryDAOImpl(context);
        category = categoryDAO.findByField(DBHelper.CATEGORY_KEY_ID, id);
        return category;
    }

    /**
     * Method for find category by name
     * @param context
     * @param name
     * @return
     */
    public Category findByName(Context context, String name) {

        categoryDAO = new CategoryDAOImpl(context);
         return categoryDAO.findByField(DBHelper.CATEGORY_KEY_CATEGORY, name);
    }

    /**
     * Method for find category by code
     * @param context
     * @param code
     * @return
     */
    public Category findByCode(Context context, String code) {

        categoryDAO = new CategoryDAOImpl(context);
        return categoryDAO.findByField(DBHelper.CATEGORY_KEY_CODE, code);
    }

    /**
     * SAve Category
     * @param context
     * @return
     */
    public long save(Context context) {

        categoryDAO = new CategoryDAOImpl(context);
        return categoryDAO.insert(category);
    }

    /**
     * Update Category
     * @param context
     * @return
     */
    public int update(Context context) {

        categoryDAO = new CategoryDAOImpl(context);
        return categoryDAO.update(category);
    }

    /**
     * Delete category
     * @param context
     * @return
     */
    public int delete(Context context) {

        categoryDAO = new CategoryDAOImpl(context);
        return categoryDAO.delete(category.getId());
    }

    /**
     * Update reminder
     * @param context
     * @param isChecked
     * @return
     */
    public int updateReminder(Context context, boolean isChecked) {

        categoryDAO = new CategoryDAOImpl(context);
        category.setRemind(isChecked);
        return categoryDAO.update(category);
    }

    /**
     *
     * @param context
     * @return
     */
    public static Cursor fetchAllCategoriesWithId(Context context) {

        categoryAll = new CategoryDAOImpl(context);
        return categoryAll.fetchAllCategoriesWithId();
    }
}
