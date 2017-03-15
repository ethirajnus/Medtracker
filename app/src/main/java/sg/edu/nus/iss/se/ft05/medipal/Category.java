package sg.edu.nus.iss.se.ft05.medipal;

import android.content.Context;
import android.database.Cursor;

import sg.edu.nus.iss.se.ft05.medipal.dao.CategoryDAO;
import sg.edu.nus.iss.se.ft05.medipal.dao.CategoryDAOImpl;

/**
 * Created by ethi on 10/03/17.
 */

public class Category {

    private static CategoryDAO categoryAll;
    private int id;
    private String categoryName,code,description;
    private Boolean remind;
    private CategoryDAO categoryDAO;


    public Category(String categoryName, String code, String description, Boolean remind) {
        this.categoryName = categoryName;
        this.code = code;
        this.description = description;
        this.remind = remind;
    }

    public Category() {

    }

    public static Cursor findAll(Context context){
        categoryAll = new CategoryDAOImpl(context);
        return categoryAll.findAll();
    }

    public static Category findById(Context context, int id){
        categoryAll = new CategoryDAOImpl(context);
        return  categoryAll.findById(id);
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRemind(Boolean remind) {
        this.remind = remind;
    }

    public long save(Context context){
        categoryDAO = new CategoryDAOImpl(context);
        return categoryDAO.insert(this);
    }

    public int update(Context context){
        categoryDAO = new CategoryDAOImpl(context);
        return categoryDAO.update(this);
    }

    public int delete(Context context){
        categoryDAO = new CategoryDAOImpl(context);
        return categoryDAO.delete(this.getId());
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getRemind() {
        return remind;
    }

    public int getId() {
        return id;
    }

    public int updateReminder(Context context,boolean isChecked) {
        categoryDAO = new CategoryDAOImpl(context);
        setRemind(isChecked);
        return categoryDAO.update(this);

    }

    public static  Cursor fetchAllCategoriesWithId(Context context){
        categoryAll = new CategoryDAOImpl(context);
        return categoryAll.fetchAllCategoriesWithId();
    }
}
