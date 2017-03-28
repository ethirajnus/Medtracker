package com.ethigeek.medtracker.domain;



/**
 * Domain class for category
 * @author Ethiraj Srinivasan
 */
public class Category {

    //variables
    private int id;
    private String categoryName, code, description;
    private boolean remind;

    //Getters and Setters
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

    public void setRemind(boolean remind) {
        this.remind = remind;
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

    public boolean getRemind() {
        return remind;
    }

    public int getId() {
        return id;
    }

    public Category(String categoryName, String code, String description, boolean remind) {

        this.categoryName = categoryName;
        this.code = code;
        this.description = description;
        this.remind = remind;
    }

    public Category() {

    }
}
