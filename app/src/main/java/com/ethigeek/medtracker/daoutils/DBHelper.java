package com.ethigeek.medtracker.daoutils;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Connection;

import java.util.ArrayList;

import com.ethigeek.medtracker.domain.Category;


/**
 * Created by ethiraj srinivasan on 10/03/17.
 * Updated by Ashish Katre on 13-03-17.
 */

public class DBHelper extends SQLiteOpenHelper {

    protected static final String TABLE_CATEGORY = "categories";

    protected static final String TABLE_APPOINTMENT = "appointments";
    protected static final String TABLE_MEDICINE = "medicines";
    protected static final String TABLE_REMINDER = "reminders";
    protected static final String TABLE_MEASUREMENT = "measurement";
    protected static final String TABLE_CONSUMPTION = "consumption";


    private static final String DATABASE_NAME = "medtracker";

    private static final String DATABASE_COMMAND_DROP = "DROP TABLE IF EXISTS ";
    private static final String DATABASE_COMMAND_CREATE = "CREATE TABLE ";
    private static final String DATABASE_COMMAND_LEFT_BRACKET = "(";
    private static final String DATABASE_COMMAND_INTEGER = " INTEGER";
    private static final String DATABASE_COMMAND_INTEGER_COMMA = " INTEGER,";
    private static final String DATABASE_COMMAND_PRIMARY_KEY = " PRIMARY KEY,";
    private static final String DATABASE_COMMAND_TEXT = " TEXT,";
    private static final String DATABASE_COMMAND_RIGHT_BRACKET = ")";

    // ICE CONTACTS
    protected static final String TABLE_ICE_CONTACTS = "icecontacts";
    public static final String ICE_CONTACTS_KEY_ID = "id";
    public static final String ICE_CONTACTS_KEY_NAME = "name";
    public static final String ICE_CONTACTS_KEY_DESC = "description";
    public static final String ICE_CONTACTS_KEY_PHONE = "phone";
    public static final String ICE_CONTACTS_KEY_TYPE = "type";
    public static final String ICE_CONTACTS_KEY_PRIORITY = "priority";


    private static final int DATABASE_VERSION = 1;
    public static final String CATEGORY_KEY_ID = "id";
    public static final String CATEGORY_KEY_CATEGORY = "category";
    public static final String CATEGORY_KEY_CODE = "code";
    public static final String CATEGORY_KEY_DESCRIPTION = "description";
    public static final String CATEGORY_KEY_REMIND = "remind";


    public static final String APPOINTMENT_KEY_ID = "id";
    public static final String APPOINTMENT_KEY_APPOINTMENT_DATE = "date";
    public static final String APPOINTMENT_KEY_APPOINTMENT_TIME = "time";
    public static final String APPOINTMENT_KEY_APPOINTMENT_CLINIC = "clinic";
    public static final String APPOINTMENT_KEY_APPOINTMENT_DESCRIPTION = "description";



    public static final String CONSUMPTION_KEY_ID = "id";
    public static final String CONSUMPTION_KEY_MEDICINEID = "medicineId";
    public static final String CONSUMPTION_KEY_QUANTITY = "quantity";
    public static final String CONSUMPTION_KEY_DATE = "date";
    public static final String CONSUMPTION_KEY_TIME = "time";

    public static final String MEDICINE_KEY_ID = "id";
    public static final String MEDICINE_KEY_MEDICINE = "medicine";
    public static final String MEDICINE_KEY_DESCRIPTION = "description";
    public static final String MEDICINE_KEY_CATID = "catId";
    public static final String MEDICINE_KEY_REMINDERID = "reminderId";
    public static final String MEDICINE_KEY_REMIND = "remind";
    public static final String MEDICINE_KEY_QUANTITY = "quantity";
    public static final String MEDICINE_KEY_DOSAGE = "dosage";
    public static final String MEDICINE_KEY_CONSUME_QUALITY = "consumeQuantity";
    public static final String MEDICINE_KEY_THRESHOLD = "threshold";
    public static final String MEDICINE_KEY_DATE_ISSUED = "dateIssued";
    public static final String MEDICINE_KEY_EXPIRE_FACTOR = "expireFactor";
    public static final String REMINDER_KEY_ID = "id";
    public static final String REMINDER_KEY_STARTTIME = "startTime";
    public static final String REMINDER_KEY_FREQUENCY = "frequency";

    public static final String MEASUREMENT_KEY_ID = "id";
    public static final String MEASUREMENT_KEY_SYSTOLIC = "systolic";
    public static final String MEASUREMENT_KEY_DIASTOLIC = "diastolic";
    public static final String MEASUREMENT_KEY_PULSE = "pulse";
    public static final String MEASUREMENT_KEY_TEMPERATURE = "temperature";
    public static final String MEASUREMENT_KEY_WEIGHT = "weight";
    public static final String MEASUREMENT_KEY_MEASURED_ON = "measuredOn";


    public static final String REMINDER_KEY_INTERVAL = "interval";


    // Table Create Statements
    private static final String CREATE_TABLE_CATEGORY = "CREATE TABLE "
            + TABLE_CATEGORY + "(" + CATEGORY_KEY_ID + " INTEGER PRIMARY KEY," + CATEGORY_KEY_CATEGORY
            + " TEXT UNIQUE," + CATEGORY_KEY_CODE + " TEXT UNIQUE," + CATEGORY_KEY_DESCRIPTION
            + " TEXT," + CATEGORY_KEY_REMIND + " INTEGER DEFAULT 0" + ")";

    private static final String CREATE_TABLE_CONSUMPTION = "CREATE TABLE "
            + TABLE_CONSUMPTION + "(" + CONSUMPTION_KEY_ID + " INTEGER PRIMARY KEY," + CONSUMPTION_KEY_MEDICINEID
            + " INTEGER," + CONSUMPTION_KEY_QUANTITY
            + " INTEGER," + CONSUMPTION_KEY_DATE + " TEXT," + CONSUMPTION_KEY_TIME
            + " TEXT" + ")";


    // Create table ICE ICEContactsManager
    private static final String CREATE_TABLE_ICE_CONTACTS = DATABASE_COMMAND_CREATE
            + TABLE_ICE_CONTACTS + DATABASE_COMMAND_LEFT_BRACKET + ICE_CONTACTS_KEY_ID + DATABASE_COMMAND_INTEGER + DATABASE_COMMAND_PRIMARY_KEY + ICE_CONTACTS_KEY_NAME
            + DATABASE_COMMAND_TEXT + ICE_CONTACTS_KEY_DESC + DATABASE_COMMAND_TEXT + ICE_CONTACTS_KEY_PHONE
            + DATABASE_COMMAND_INTEGER_COMMA + ICE_CONTACTS_KEY_TYPE + DATABASE_COMMAND_TEXT + ICE_CONTACTS_KEY_PRIORITY + DATABASE_COMMAND_INTEGER + DATABASE_COMMAND_RIGHT_BRACKET;


    private static final String CREATE_TABLE_APPOINTMENT = "CREATE TABLE "
            + TABLE_APPOINTMENT + "(" + APPOINTMENT_KEY_ID + " INTEGER PRIMARY KEY," + APPOINTMENT_KEY_APPOINTMENT_DATE
            + " TEXT," + APPOINTMENT_KEY_APPOINTMENT_TIME + " TEXT," + APPOINTMENT_KEY_APPOINTMENT_CLINIC
            + " TEXT," +  APPOINTMENT_KEY_APPOINTMENT_DESCRIPTION + " TEXT" + ")";

    private static final String CREATE_TABLE_MEDICINE = "CREATE TABLE "
            + TABLE_MEDICINE + "(" + MEDICINE_KEY_ID + " INTEGER PRIMARY KEY," + MEDICINE_KEY_MEDICINE
            + " TEXT," + MEDICINE_KEY_DESCRIPTION + " TEXT," + MEDICINE_KEY_CATID + " INTEGER,"
            + MEDICINE_KEY_REMINDERID + " INTEGER," + MEDICINE_KEY_REMIND
            + " INTEGER DEFAULT 0," + MEDICINE_KEY_QUANTITY + " INTEGER," + MEDICINE_KEY_DOSAGE + " INTEGER,"
            + MEDICINE_KEY_CONSUME_QUALITY + " INTEGER," + MEDICINE_KEY_THRESHOLD + " INTEGER," + MEDICINE_KEY_DATE_ISSUED + " TEXT," +
            MEDICINE_KEY_EXPIRE_FACTOR + " INTEGER" + ")";


    private static final String CREATE_TABLE_REMINDER = "CREATE TABLE "
            + TABLE_REMINDER + "(" + REMINDER_KEY_ID + " INTEGER PRIMARY KEY," + REMINDER_KEY_FREQUENCY
            + " INTEGER," + REMINDER_KEY_STARTTIME + " TEXT," + REMINDER_KEY_INTERVAL
            + " INTEGER" + ")";

    private static final String CREATE_TABLE_MEASUREMENT = "CREATE TABLE "
            + TABLE_MEASUREMENT + "(" + MEASUREMENT_KEY_ID + " INTEGER PRIMARY KEY," + MEASUREMENT_KEY_SYSTOLIC
            + " INTEGER," + MEASUREMENT_KEY_DIASTOLIC + " INTEGER," + MEASUREMENT_KEY_PULSE + " INTEGER,"
            + MEASUREMENT_KEY_TEMPERATURE + " REAL," + MEASUREMENT_KEY_WEIGHT + " INTEGER,"
            + MEASUREMENT_KEY_MEASURED_ON + " TEXT" + ")";


    Connection connection = null;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        db.execSQL(CREATE_TABLE_CATEGORY);
        db.execSQL(CREATE_TABLE_APPOINTMENT);
        db.execSQL(CREATE_TABLE_MEDICINE);
        db.execSQL(CREATE_TABLE_REMINDER);

        db.execSQL(CREATE_TABLE_MEASUREMENT);
        db.execSQL(CREATE_TABLE_CONSUMPTION);

        insertDefaultValues(db);
        db.execSQL(getCreateTableHealthBioQuery());
        db.execSQL(CREATE_TABLE_ICE_CONTACTS);
        db.execSQL(getCreateTablePersonalBioQuery());

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(DATABASE_COMMAND_DROP + TABLE_CATEGORY);

        db.execSQL(DATABASE_COMMAND_DROP + TABLE_APPOINTMENT);

        db.execSQL(DATABASE_COMMAND_DROP + TABLE_MEDICINE);
        db.execSQL(DATABASE_COMMAND_DROP + TABLE_REMINDER);
        db.execSQL(DATABASE_COMMAND_DROP + TABLE_CONSUMPTION);

        db.execSQL(DATABASE_COMMAND_DROP + TABLE_MEASUREMENT);

        db.execSQL(DATABASE_COMMAND_DROP + TABLE_ICE_CONTACTS);
        db.execSQL(DATABASE_COMMAND_DROP + DbConstants.TABLE_HEALTH_BIO);
        db.execSQL(DATABASE_COMMAND_DROP + DbConstants.TABLE_PERSONAL_BIO);
        onCreate(db);
    }


    public static void insertDefaultValues(SQLiteDatabase db) {

        ArrayList<Category> categorList = new ArrayList();

        categorList.add(new Category("Supplement", "SUP", "Supplement type of medicines", true));
        categorList.add(new Category("Chronic", "CHR", "Chronice type of medicines", true));
        categorList.add(new Category("Incidental", "INC", "Incidental type of medicines", true));
        categorList.add(new Category("Complete Course", "COM", "Complete type of medicines", true));
        categorList.add(new Category("Self Apply", "SEL", "Self Apply type of medicines", true));

        for (Category category : categorList) {

            ContentValues values = new ContentValues();
            values.put(CATEGORY_KEY_CATEGORY, category.getCategoryName());
            values.put(CATEGORY_KEY_CODE, category.getCode());
            values.put(CATEGORY_KEY_DESCRIPTION, category.getDescription());
            values.put(CATEGORY_KEY_REMIND, category.getRemind());
            // insert row
            db.insert(TABLE_CATEGORY, null, values);
        }
    }

    //Creating HealthBio table
    private String getCreateTableHealthBioQuery() {

        final StringBuilder CREATE_TABLE_HEALTHBIO = new StringBuilder()
                .append("CREATE TABLE ")
                .append(DbConstants.TABLE_HEALTH_BIO)
                .append(" (")
                .append(DbConstants.HEALTH_BIO_KEY_ID)
                .append(" INTEGER PRIMARY KEY,")
                .append(DbConstants.HEALTH_BIO_KEY_CONDITION)
                .append(" TEXT,")
                .append(DbConstants.HEALTH_BIO_KEY_CONDITION_TYPE)
                .append(" TEXT,")
                .append(DbConstants.HEALTH_BIO_KEY_START_DATE)
                .append(" TEXT")
                .append(")");

        return CREATE_TABLE_HEALTHBIO.toString();
    }

    //Creating PersonalBio table
    private String getCreateTablePersonalBioQuery() {

        final StringBuilder CREATE_TABLE_PERSONALBIO = new StringBuilder()
                .append("CREATE TABLE ")
                .append(DbConstants.TABLE_PERSONAL_BIO)
                .append(" (")
                .append(DbConstants.PERSONAL_BIO_KEY_ID)
                .append(" INTEGER PRIMARY KEY, ")
                .append(DbConstants.PERSONAL_BIO_KEY_NAME)
                .append(" TEXT, ")
                .append(DbConstants.PERSONAL_BIO_KEY_DOB)
                .append(" TEXT, ")
                .append(DbConstants.PERSONAL_BIO_KEY_IDNO)
                .append(" TEXT, ")
                .append(DbConstants.PERSONAL_BIO_KEY_ADDRESS)
                .append(" TEXT, ")
                .append(DbConstants.PERSONAL_BIO_KEY_POSTAL_CODE)
                .append(" TEXT, ")
                .append(DbConstants.PERSONAL_BIO_KEY_HEIGHT)
                .append(" INTEGER, ")
                .append(DbConstants.PERSONAL_BIO_KEY_BLOOD_TYPE)
                .append(" TEXT")
                .append(")");

        return CREATE_TABLE_PERSONALBIO.toString();
    }

}
