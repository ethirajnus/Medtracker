package sg.edu.nus.iss.se.ft05.medipal.model;

import android.content.Context;
import android.database.Cursor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sg.edu.nus.iss.se.ft05.medipal.dao.CategoryDAOImpl;
import sg.edu.nus.iss.se.ft05.medipal.dao.MedicineDAO;
import sg.edu.nus.iss.se.ft05.medipal.dao.MedicineDAOImpl;

import static sg.edu.nus.iss.se.ft05.medipal.dao.DBHelper.MEDICINE_KEY_ID;
import static sg.edu.nus.iss.se.ft05.medipal.dao.DBHelper.MEDICINE_KEY_REMINDERID;


/**
 * Created by ethi on 12/03/17.
 */

public class Medicine {
    private static MedicineDAO medicineAll;
    private MedicineDAO medicineDAO;
    private int id,categoryId,reminderId,quantity,dosage,consumeQuantity,threshold,expireFactor;
    private Boolean remind;
    private String dateIssued;
    private String name,description;

    public Medicine(String name, String description, int categoryId, int reminderId, Boolean remind, int quantity, int dosage, int consumeQuantity, int threshold, String dateIssued, int expireFactor) {
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

    public static Map<Integer,Integer> listAllMedicine(Context context){
        Cursor cursor = Medicine.findAll(context);
        Map<Integer,Integer > medicineHashMap = new HashMap();
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            medicineHashMap.put(cursor.getInt(cursor.getColumnIndex(MEDICINE_KEY_ID)),cursor.getInt(cursor.getColumnIndex(MEDICINE_KEY_REMINDERID)));
        }
        return  medicineHashMap;
    }


    public int getId() {
        return id;
    }

    public int updateReminder(Context context,boolean isChecked) {
        medicineDAO = new MedicineDAOImpl(context);
        setRemind(isChecked);
        return medicineDAO.update(this);

    }

    public Category getCategory(Context context){
        return Category.findById(context,getCategoryId());
    }

    public static Cursor fetchAllMedicinesWithId(Context context) {
        medicineAll = new  MedicineDAOImpl(context);
        return medicineAll.fetchAllMedicinesWithId();
    }

    public Reminder getReminder(Context context) {
        return Reminder.findById(context,getReminderId());
    }

    public List<Consumption> consumptions(Context context){
       return Consumption.findByMedicineID(context, getId());

    }
}
