package sg.edu.nus.iss.se.ft05.medipal.domain;


/**
 * Domain class for Medicine
 * @author Ethiraj Srinivasan
 */
public class Medicine {

    //Variables
    private int id, categoryId, reminderId, quantity, dosage, consumeQuantity, threshold, expireFactor;
    private boolean remind;
    private String dateIssued;
    private String name, description;

    //Getters and Setters
    public void setId(int id) {
        this.id = id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getReminderId() {
        return reminderId;
    }

    public void setReminderId(int reminderId) {
        this.reminderId = reminderId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getDosage() {
        return dosage;
    }

    public void setDosage(int dosage) {
        this.dosage = dosage;
    }

    public int getConsumeQuantity() {
        return consumeQuantity;
    }

    public void setConsumeQuantity(int consumeQuality) {
        this.consumeQuantity = consumeQuality;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public int getExpireFactor() {
        return expireFactor;
    }

    public void setExpireFactor(int expireFactor) {
        this.expireFactor = expireFactor;
    }

    public boolean getRemind() {
        return remind;
    }

    public void setRemind(boolean remind) {
        this.remind = remind;
    }

    public String getDateIssued() {
        return dateIssued;
    }

    public void setDateIssued(String dateIssued) {
        this.dateIssued = dateIssued;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public Medicine() {


    }

    public Medicine(String name, String description, int categoryId, int reminderId, boolean remind, int quantity, int dosage, int consumeQuantity, int threshold, String dateIssued, int expireFactor) {

        this.categoryId = categoryId;
        this.reminderId = reminderId;
        this.quantity = quantity;
        this.dosage = dosage;
        this.consumeQuantity = consumeQuantity;
        this.threshold = threshold;
        this.expireFactor = expireFactor;
        this.remind = remind;
        this.dateIssued = dateIssued;
        this.name = name;
        this.description = description;
    }


}
