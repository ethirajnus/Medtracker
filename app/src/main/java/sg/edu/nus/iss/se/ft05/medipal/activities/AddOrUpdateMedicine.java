package sg.edu.nus.iss.se.ft05.medipal.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import sg.edu.nus.iss.se.ft05.medipal.Category;
import sg.edu.nus.iss.se.ft05.medipal.Medicine;
import sg.edu.nus.iss.se.ft05.medipal.R;
import sg.edu.nus.iss.se.ft05.medipal.Reminder;
import sg.edu.nus.iss.se.ft05.medipal.dao.DBHelper;
import sg.edu.nus.iss.se.ft05.medipal.fragments.MedicineFragment;


public class AddOrUpdateMedicine extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {


    private EditText name, description, quantity, consumeQuality, threshold, expirefactor, dateIssued,frequency,startTime,interval;
    private CheckBox reminder;
    private Spinner dosage,category;
    DatePickerDialog datePickerDialog;
    Calendar dateCalendar,timeCalendar;
    private Medicine medicine;
    private Button saveButton;
    private HashMap<String, Integer> categoriesMap;
    private Context context;

    private static final SimpleDateFormat formatter = new SimpleDateFormat(
            "yyyy-MM-dd", Locale.ENGLISH);


    private static final HashMap<String, Integer> DOSAGE_HASH_MAP = createDosageHashMap();
    static final HashMap<Integer,String > DOSAGE_REVERSE_HASH_MAP = createDosageReverseHashMap();
    private TimePickerDialog timePickerDialog;
    private Reminder reminderMedicine;

    private static HashMap<Integer,String> createDosageReverseHashMap() {
        HashMap<Integer,String> result = new HashMap<Integer, String>();
        for (Map.Entry<String, Integer> entry : DOSAGE_HASH_MAP.entrySet()) {
            result.put(entry.getValue(),entry.getKey());
        }
        return  result;
    }

    private ArrayList<String> categoryList,dosageList;
    private static HashMap<String, Integer> createDosageHashMap() {
        HashMap<String,Integer> result = new HashMap<String, Integer>();
        result.put("pills",1);
        result.put("cc",2);
        result.put("ml",3);
        result.put("gr",4);
        result.put("mg",5);
        result.put("drops",6);
        result.put("pieces",7);
        result.put("puffs",8);
        result.put("units",9);
        result.put("teaspoon",10);
        result.put("tablespoon",11);
        result.put("patch",12);
        result.put("mcg",13);
        result.put("I",14);
        result.put("meq",15);
        result.put("spray",16);
        return result;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        context = getApplicationContext();
        findViewsById();
        setListeners();
        populateDropDownList();
        Bundle b = getIntent().getExtras();
        if (b != null && b.getString("action").equalsIgnoreCase("edit")) {
            updateSaveButton();
            updateMedicineValues(b.getInt("id"));
            setTitle("Edit Medicine");
        } else {
            setTitle("New Medicine");
        }

    }

    private void populateDropDownList() {
        Cursor mCursor = Category.fetchAllCategoriesWithId(context);
        categoryList = new ArrayList<String>();
        categoriesMap = new HashMap<String, Integer>();
        while(mCursor.moveToNext()) {
            int id = mCursor.getInt(mCursor.getColumnIndex(DBHelper.CATEGORY_KEY_ID));
            String categoryName = mCursor.getString(mCursor.getColumnIndex(DBHelper.CATEGORY_KEY_CATEGORY));
            categoryList.add(categoryName); //add the item
            categoriesMap.put(categoryName,id);
        }

        ArrayAdapter<String> categoryDataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoryList);

        // Drop down layout style - list view with radio button
        categoryDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        category.setAdapter(categoryDataAdapter);

        dosageList = new ArrayList<String>(DOSAGE_HASH_MAP.keySet());
        ArrayAdapter<String> dosageDataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dosageList);
        // Drop down layout style - list view with radio button
        dosageDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        dosage.setAdapter(dosageDataAdapter);

    }

    private void updateMedicineValues(int id) {
        medicine = Medicine.findById(context, id);
        reminderMedicine = Reminder.findById(context, medicine.getReminderId());
        name.setText(medicine.getName());
        description.setText(medicine.getDescription());
        category.setSelection(categoryList.indexOf(Category.findById(context,medicine.getCategoryId()).getCategoryName()));
        reminder.setChecked(medicine.getRemind());
        quantity.setText(String.valueOf(medicine.getQuantity()));
        dosage.setSelection(dosageList.indexOf(DOSAGE_REVERSE_HASH_MAP.get(medicine.getDosage())));
        consumeQuality.setText(String.valueOf(medicine.getConsumeQuality()));
        threshold.setText(String.valueOf(medicine.getThreshold()));
        dateIssued.setText(medicine.getDateIssued());
        expirefactor.setText(String.valueOf(medicine.getExpireFactor()));
        frequency.setText(String.valueOf(reminderMedicine.getFrequency()));
        startTime.setText(reminderMedicine.getStartTime());
        interval.setText(String.valueOf(reminderMedicine.getInterval()));
        name.setTag(id);
    }

    private void updateSaveButton() {
        saveButton.setTag("update");
        saveButton.setText("Update");
    }

    private void findViewsById() {
        name = (EditText) findViewById(R.id.medicineName);
        description = (EditText) findViewById(R.id.medicineDescription);
        category = (Spinner) findViewById(R.id.medicineCategory);
        reminder = (CheckBox) findViewById(R.id.medicineReminder);
        quantity = (EditText) findViewById(R.id.medicineQuantity);
        dosage = (Spinner) findViewById(R.id.medicineDosage);
        consumeQuality = (EditText) findViewById(R.id.medicineConsumeQuantity);
        threshold = (EditText) findViewById(R.id.medicineThreshold);
        dateIssued = (EditText) findViewById(R.id.medicineDateIssued);
        expirefactor = (EditText) findViewById(R.id.medicineExpireFactor);
        frequency = (EditText) findViewById(R.id.reminderFrequency);
        startTime = (EditText) findViewById(R.id.reminderStartTime);
        interval = (EditText) findViewById(R.id.reminderInterval);
        saveButton = (Button) findViewById(R.id.saveMedicine);
        saveButton.setTag("New");
    }

    private void setListeners() {
        dateIssued.setOnClickListener(this);
        startTime.setOnClickListener(this);
        Calendar newCalendar = Calendar.getInstance();
        timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener(){
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                startTime.setText( hourOfDay + ":" + minute);
            }
        },
                newCalendar.get(Calendar.HOUR_OF_DAY),
                newCalendar.get(Calendar.MINUTE),true);
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                dateCalendar = Calendar.getInstance();
                dateCalendar.set(year, monthOfYear, dayOfMonth);
                dateIssued.setText(formatter.format(dateCalendar.getTime()));
            }
        },
                newCalendar.get(Calendar.YEAR),
                newCalendar.get(Calendar.MONTH),
                newCalendar.get(Calendar.DAY_OF_MONTH));
        saveButton.setOnClickListener(this);
        category.setOnItemSelectedListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.medicineDateIssued:
                datePickerDialog.show();
                break;
            case R.id.reminderStartTime:
                timePickerDialog.show();
                break;
            case R.id.saveMedicine:
                saveMedicine();
                break;
        }
    }

    private void saveMedicine() {
        String medicineName = name.getText().toString();
        String medicineDescription = description.getText().toString();
        int medicineCategory = categoriesMap.get(category.getSelectedItem());
        Boolean medicineRemind = reminder.isChecked();
        int medicinceQuantity = Integer.parseInt(quantity.getText().toString());
        int medicinceDosage = DOSAGE_HASH_MAP.get(dosage.getSelectedItem());
        int medicinceConsumeQuality = Integer.parseInt(consumeQuality.getText().toString());
        int medicinceThreshold = Integer.parseInt(threshold.getText().toString());
        String medicinceDateIssued = dateIssued.getText().toString();
        int medicinceExpireFactor = Integer.parseInt(expirefactor.getText().toString());
        int reminderFrequency = Integer.parseInt(frequency.getText().toString());
        String reminderStartTime = startTime.getText().toString();
        Log.v("reminderStartTime", reminderStartTime);
        int reminderInterval = Integer.parseInt(interval.getText().toString());

        if (saveButton.getTag().toString().equalsIgnoreCase("New")) {
            Reminder reminder = new Reminder(reminderFrequency,reminderStartTime,reminderInterval);
            int medicineReminderId = (int) reminder.save(context);
            Log.v("medicine reminder id ",String.valueOf(medicineReminderId));
            Medicine medicine = new Medicine(medicineName, medicineDescription, medicineCategory, medicineReminderId, medicineRemind, medicinceQuantity, medicinceDosage, medicinceConsumeQuality, medicinceThreshold, medicinceDateIssued, medicinceExpireFactor);
            if (medicine.save(context) == -1 ) {
                Toast.makeText(context, "Medicine was not inserted properly,Please try again later", Toast.LENGTH_SHORT).show();
            } else {
                navigateToMainAcitivity();
            }
        } else {
            medicine.setName(medicineName);
            medicine.setDescription(medicineDescription);
            medicine.setCategoryId(medicineCategory);
            medicine.setRemind(medicineRemind);
            medicine.setQuantity(medicinceQuantity);
            medicine.setDosage(medicinceDosage);
            medicine.setConsumeQuality(medicinceConsumeQuality);
            medicine.setThreshold(medicinceThreshold);
            medicine.setDateIssued(medicinceDateIssued);
            medicine.setExpireFactor(medicinceExpireFactor);
            reminderMedicine.setFrequency(reminderFrequency);
            reminderMedicine.setStartTime(reminderStartTime);
            reminderMedicine.setInterval(reminderInterval);
            reminderMedicine.update(context);
            if (medicine.update(context) == -1) {
                Toast.makeText(context, "Medicine was not updated properly,Please try again later", Toast.LENGTH_SHORT).show();
            } else {
                navigateToMainAcitivity();
            }

        }
    }

    public void navigateToMainAcitivity() {
        Intent intent = new Intent(context, MainActivity.class);
        MainActivity.currentFragment = MedicineFragment.class.getName();
        startActivity(intent);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
