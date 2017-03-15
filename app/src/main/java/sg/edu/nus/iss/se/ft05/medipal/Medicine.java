package sg.edu.nus.iss.se.ft05.medipal;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.Date;

import sg.edu.nus.iss.se.ft05.medipal.dao.CategoryDAOImpl;
import sg.edu.nus.iss.se.ft05.medipal.dao.MedicineDAO;
import sg.edu.nus.iss.se.ft05.medipal.dao.MedicineDAOImpl;



/**
 * Created by ethi on 12/03/17.
 */

public class Medicine {
    private static MedicineDAO medicineAll;
    private MedicineDAO medicineDAO;
    private int id,categoryId,reminderId,quantity,dosage,consumeQuality,threshold,expireFactor;
    private Boolean remind;
    private String dateIssued;
    private String name,description;

    public Medicine(String name, String description, int categoryId, int reminderId, Boolean remind, int quantity, int dosage, int consumeQuality, int threshold, String dateIssued, int expireFactor) {
        this.categoryId = categoryId;
        this.reminderId = reminderId;
        this.quantity = quantity;
        this.dosage = dosage;
        this.consumeQuality = consumeQuality;
        this.threshold = threshold;
        this.expireFactor = expireFactor;
        this.remind = remind;
        this.dateIssued = dateIssued;
        this.name = name;
        this.description = description;
    }

    public Medicine() {

    }

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

    public int getConsumeQuality() {
        return consumeQuality;
    }

    public void setConsumeQuality(int consumeQuality) {
        this.consumeQuality = consumeQuality;
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

    public Boolean getRemind() {
        return remind;
    }

    public void setRemind(Boolean remind) {
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



    public static Cursor findAll(Context context) {
        medicineAll = new MedicineDAOImpl(context);
        Cursor cursor = medicineAll.findAll();
        Log.v("medicine find all",String.valueOf(cursor.getCount()));
        return cursor;
    }

    public static Medicine findById(Context context, int id) {
        medicineAll = new MedicineDAOImpl(context);
        return  medicineAll.findById(id);
    }

    public int delete(Context context) {
        medicineDAO = new MedicineDAOImpl(context);
        return medicineDAO.delete(this.getId());
    }
    public long save(Context context){
        medicineDAO = new MedicineDAOImpl(context);
        return medicineDAO.insert(this);
    }

    public int update(Context context){
        medicineDAO = new MedicineDAOImpl(context);
        return medicineDAO.update(this);
    }

    public int getId() {
        return id;
    }
}
