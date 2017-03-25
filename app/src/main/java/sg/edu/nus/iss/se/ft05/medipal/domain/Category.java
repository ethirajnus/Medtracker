package sg.edu.nus.iss.se.ft05.medipal.domain;

/**
 * Created by e0146812 on 3/25/2017.
 */
public class Category {

    private int id;
    private String categoryName, code, description;
    private Boolean remind;

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

    public Category(String categoryName, String code, String description, Boolean remind) {

        this.categoryName = categoryName;
        this.code = code;
        this.description = description;
        this.remind = remind;
    }

    public Category() {

    }
}
