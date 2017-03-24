package sg.edu.nus.iss.se.ft05.medipal.model;

import android.content.Context;
import android.database.Cursor;

import sg.edu.nus.iss.se.ft05.medipal.dao.PersonalBioDAO;
import sg.edu.nus.iss.se.ft05.medipal.dao.PersonalBioDAOImpl;

/**
 * @author Moushumi Seal
 */

public class PersonalBio {
    private int id;
    private String name;
    private String dob;
    private String idNo;
    private String address;
    private String postalCode;
    private String height;
    private String bloodType;

    private PersonalBioDAO personalBioDAO;
    private static PersonalBioDAO personalBioDAOAll;

    public PersonalBio(String name, String dob,
                       String idNo, String address, String postalCode,
                       String height, String bloodType) {
        this.name = name;
        this.dob = dob;
        this.idNo = idNo;
        this.address = address;
        this.postalCode = postalCode;
        this.height = height;
        this.bloodType = bloodType;
    }

    public PersonalBio() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public static Cursor findAll(Context context){
        personalBioDAOAll = new PersonalBioDAOImpl(context);
        return personalBioDAOAll.findAll();
    }

    public static PersonalBio findById(Context context, int id){
        personalBioDAOAll = new PersonalBioDAOImpl(context);
        return  personalBioDAOAll.findById(id);
    }

    public long save(Context context){
        personalBioDAO = new PersonalBioDAOImpl(context);
        return personalBioDAO.insert(this);
    }

    public int update(Context context){
        personalBioDAO = new PersonalBioDAOImpl(context);
        return personalBioDAO.update(this);
    }

    public int delete(Context context){
        personalBioDAO = new PersonalBioDAOImpl(context);
        return personalBioDAO.delete(this.getId());
    }

}
