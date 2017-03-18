package sg.edu.nus.iss.se.ft05.medipal.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by ethi on 10/03/17.
 */

public class DBHelper extends SQLiteOpenHelper {

    protected static final String TABLE_CATEGORY = "categories";
    protected static final String TABLE_APPOINTMENT="appointments";
    private static final String DATABASE_NAME = "medipal";
    private static final int DATABASE_VERSION = 1;

    public static final String CATEGORY_KEY_ID = "id";
    public static final String CATEGORY_KEY_CATEGORY = "category";
    public static final String CATEGORY_KEY_CODE = "code";
    public static final String CATEGORY_KEY_DESCRIPTION = "description";
    public static final String CATEGORY_KEY_REMIND = "remind";

    public static final String APPOINTMENT_KEY_ID="id";
    public static final String APPOINTMENT_KEY_APPOINTMENT_DATE="date";
    public static final String APPOINTMENT_KEY_APPOINTMENT_TIME="time";
    public static final String APPOINTMENT_KEY_APPOINTMENT_CLINIC="clinic";
    public static final String APPOINTMENT_KEY_APPOINTMENT_TEST="test";
    public static final String APPOINTMENT_KEY_APPOINTMENT_PRE_TEST="pre_test";
    // Table Create Statements
    // Todo table create statement
    private static final String CREATE_TABLE_CATEGORY = "CREATE TABLE "
            + TABLE_CATEGORY + "(" + CATEGORY_KEY_ID + " INTEGER PRIMARY KEY," + CATEGORY_KEY_CATEGORY
            + " TEXT," + CATEGORY_KEY_CODE + " TEXT," + CATEGORY_KEY_DESCRIPTION
            + " TEXT," + CATEGORY_KEY_REMIND + " INTEGER DEFAULT 0"+")";
    private static final String CREATE_TABLE_APPOINTMENT = "CREATE TABLE "
            + TABLE_APPOINTMENT + "(" + APPOINTMENT_KEY_ID + " INTEGER PRIMARY KEY," + APPOINTMENT_KEY_APPOINTMENT_DATE
            + " TEXT," + APPOINTMENT_KEY_APPOINTMENT_TIME + " TEXT," + APPOINTMENT_KEY_APPOINTMENT_CLINIC
            + " TEXT," + APPOINTMENT_KEY_APPOINTMENT_TEST + " TEXT,"+ APPOINTMENT_KEY_APPOINTMENT_PRE_TEST+ " TEXT"+")";
    Connection connection = null;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        db.execSQL(CREATE_TABLE_CATEGORY);
        db.execSQL(CREATE_TABLE_APPOINTMENT);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_APPOINTMENT);
        onCreate(db);
    }

    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }
}
