package sg.edu.nus.iss.se.ft05.medipal.activities;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sg.edu.nus.iss.se.ft05.medipal.dao.DBHelper;
import sg.edu.nus.iss.se.ft05.medipal.domain.Medicine;
import sg.edu.nus.iss.se.ft05.medipal.domain.Reminder;
import sg.edu.nus.iss.se.ft05.medipal.managers.CategoryManager;
import sg.edu.nus.iss.se.ft05.medipal.managers.MedicineManager;
import sg.edu.nus.iss.se.ft05.medipal.R;
import sg.edu.nus.iss.se.ft05.medipal.managers.ReminderManager;

import static sg.edu.nus.iss.se.ft05.medipal.activities.AddOrUpdateMedicine.DOSAGE_REVERSE_HASH_MAP;
import static sg.edu.nus.iss.se.ft05.medipal.constants.Constants.MEDICINE;

/**
 * class to show medicine details
 * @author Ethiraj Srinivasan
 */
public class ShowMedicine extends AppCompatActivity {

    private EditText name, description, quantity, consumeQuantity, threshold, expireFactor, dateIssued, frequency, startTime, interval;
    private CheckBox reminder;
    private Spinner dosage, category;
    private Button saveButton;
    private Context context;
    private static final Map<String, Integer> DOSAGE_HASH_MAP = createDosageHashMap();
    private List<String> categoryList, dosageList;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        setTitle(MEDICINE);
        context = getApplicationContext();
        Bundle b = getIntent().getExtras();
        MedicineManager medicineManager = new MedicineManager();
        Medicine medicine = medicineManager.findById(context, b.getInt("id"));
        ReminderManager reminderManager = new ReminderManager();
        Reminder reminder = reminderManager.findById(context, medicine.getReminderId());
        findViewsById();
        updateValues(medicineManager, reminder);
        disableFields();
    }

    private static Map<String, Integer> createDosageHashMap() {
        Map<String, Integer> result = new HashMap();
        result.put("pills", 1);
        result.put("cc", 2);
        result.put("ml", 3);
        result.put("gr", 4);
        result.put("mg", 5);
        result.put("drops", 6);
        result.put("pieces", 7);
        result.put("puffs", 8);
        result.put("units", 9);
        result.put("teaspoon", 10);
        result.put("tablespoon", 11);
        result.put("patch", 12);
        result.put("mcg", 13);
        result.put("I", 14);
        result.put("meq", 15);
        result.put("spray", 16);
        return result;
    }

    /**
     *
     * @param medicineManager
     * @param reminderMedicine
     */
    private void updateValues(MedicineManager medicineManager, Reminder reminderMedicine) {

        Cursor mCursor = CategoryManager.fetchAllCategoriesWithId(context);
        categoryList = new ArrayList<>();
        while (mCursor.moveToNext()) {
            int id = mCursor.getInt(mCursor.getColumnIndex(DBHelper.CATEGORY_KEY_ID));
            String categoryName = mCursor.getString(mCursor.getColumnIndex(DBHelper.CATEGORY_KEY_CATEGORY));
            categoryList.add(categoryName); //add the item
        }

        ArrayAdapter<String> categoryDataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoryList);

        // Drop down layout style
        categoryDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        category.setAdapter(categoryDataAdapter);

        dosageList = new ArrayList<>(DOSAGE_HASH_MAP.keySet());
        ArrayAdapter<String> dosageDataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dosageList);
        // Drop down layout style - list view with radio button
        dosageDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        dosage.setAdapter(dosageDataAdapter);

        Medicine medicine = medicineManager.getMedicine();
        name.setText(medicine.getName());
        description.setText(medicine.getDescription());
        category.setSelection(categoryList.indexOf(new CategoryManager().findById(getApplicationContext(),
                        medicine.getCategoryId()).getCategoryName()));
        reminder.setChecked(medicine.getRemind());
        quantity.setText(String.valueOf(medicine.getQuantity()));
        dosage.setSelection(dosageList.indexOf(DOSAGE_REVERSE_HASH_MAP.get(medicine.getDosage())));
        consumeQuantity.setText(String.valueOf(medicine.getConsumeQuantity()));
        threshold.setText(String.valueOf(medicine.getThreshold()));
        dateIssued.setText(medicine.getDateIssued());
        expireFactor.setText(String.valueOf(medicine.getExpireFactor()));
        frequency.setText(String.valueOf(reminderMedicine.getFrequency()));
        startTime.setText(reminderMedicine.getStartTime());
        interval.setText(String.valueOf(reminderMedicine.getInterval()));
        saveButton.setVisibility(View.INVISIBLE);
    }


    private void findViewsById() {
        name = (EditText) findViewById(R.id.medicineName);
        description = (EditText) findViewById(R.id.medicineDescription);
        category = (Spinner) findViewById(R.id.medicineCategory);
        reminder = (CheckBox) findViewById(R.id.medicineReminder);
        quantity = (EditText) findViewById(R.id.medicineQuantity);
        dosage = (Spinner) findViewById(R.id.medicineDosage);
        consumeQuantity = (EditText) findViewById(R.id.medicineConsumeQuantity);
        threshold = (EditText) findViewById(R.id.medicineThreshold);
        dateIssued = (EditText) findViewById(R.id.medicineDateIssued);
        expireFactor = (EditText) findViewById(R.id.medicineExpireFactor);
        frequency = (EditText) findViewById(R.id.reminderFrequency);
        startTime = (EditText) findViewById(R.id.reminderStartTime);
        interval = (EditText) findViewById(R.id.reminderInterval);
        saveButton = (Button) findViewById(R.id.saveMedicine);
    }

    private void disableFields(){
        name.setEnabled(false);
        description.setEnabled(false);
        category.setEnabled(false);
        reminder.setEnabled(false);
        quantity.setEnabled(false);
        dosage.setEnabled(false);
        consumeQuantity.setEnabled(false);
        threshold.setEnabled(false);
        dateIssued.setEnabled(false);
        expireFactor.setEnabled(false);
        frequency.setEnabled(false);
        startTime.setEnabled(false);
        interval.setEnabled(false);
    }
}
