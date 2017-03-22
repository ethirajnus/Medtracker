package sg.edu.nus.iss.se.ft05.medipal.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import sg.edu.nus.iss.se.ft05.medipal.constants.Constants;
import sg.edu.nus.iss.se.ft05.medipal.model.Category;
import sg.edu.nus.iss.se.ft05.medipal.model.Medicine;
import sg.edu.nus.iss.se.ft05.medipal.R;
import sg.edu.nus.iss.se.ft05.medipal.model.Reminder;
import sg.edu.nus.iss.se.ft05.medipal.Util.ReminderUtils;
import sg.edu.nus.iss.se.ft05.medipal.dao.DBHelper;
import sg.edu.nus.iss.se.ft05.medipal.fragments.MedicineFragment;

import static sg.edu.nus.iss.se.ft05.medipal.constants.Constants.*;


public class AddOrUpdateMedicine extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {


    private EditText name, description, quantity, consumeQuantity, threshold, expirefactor, dateIssued, frequency, startTime, interval;
    private CheckBox reminder;
    private Spinner dosage, category;
    DatePickerDialog datePickerDialog;
    Calendar dateCalendar;
    private Medicine medicine;
    private Button saveButton;
    private Context context;

    private static final SimpleDateFormat formatter = new SimpleDateFormat(
            DATE_FORMAT, Locale.ENGLISH);


    private static final Map<String, Integer> DOSAGE_HASH_MAP = createDosageHashMap();
    static final Map<Integer, String> DOSAGE_REVERSE_HASH_MAP = createDosageReverseHashMap();
    private TimePickerDialog timePickerDialog;
    private Reminder reminderMedicine;
    private Map<String, Integer> categoriesMap;

    private static Map<Integer, String> createDosageReverseHashMap() {
        Map<Integer, String> result = new HashMap();
        for (Map.Entry<String, Integer> entry : DOSAGE_HASH_MAP.entrySet()) {
            result.put(entry.getValue(), entry.getKey());
        }
        return result;
    }

    private List<String> categoryList, dosageList;

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
        if (b != null && b.getString(ACTION).equalsIgnoreCase(EDIT)) {
            updateSaveButton();
            updateMedicineValues(b.getInt(ID));
            setTitle(EDIT_MEDICINE);
        } else {
            setTitle(NEW_MEDICINE);
        }

    }

    private void populateDropDownList() {
        Cursor mCursor = Category.fetchAllCategoriesWithId(context);
        categoryList = new ArrayList<>();
        categoriesMap = new HashMap<>();
        while (mCursor.moveToNext()) {
            int id = mCursor.getInt(mCursor.getColumnIndex(DBHelper.CATEGORY_KEY_ID));
            String categoryName = mCursor.getString(mCursor.getColumnIndex(DBHelper.CATEGORY_KEY_CATEGORY));
            categoryList.add(categoryName); //add the item
            categoriesMap.put(categoryName, id);
        }

        ArrayAdapter<String> categoryDataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoryList);

        // Drop down layout style - list view with radio button
        categoryDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        category.setAdapter(categoryDataAdapter);

        dosageList = new ArrayList<>(DOSAGE_HASH_MAP.keySet());
        ArrayAdapter<String> dosageDataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dosageList);
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
        category.setSelection(categoryList.indexOf(Category.findById(context, medicine.getCategoryId()).getCategoryName()));
        reminder.setChecked(medicine.getRemind());
        quantity.setText(String.valueOf(medicine.getQuantity()));
        dosage.setSelection(dosageList.indexOf(DOSAGE_REVERSE_HASH_MAP.get(medicine.getDosage())));
        consumeQuantity.setText(String.valueOf(medicine.getConsumeQuantity()));
        threshold.setText(String.valueOf(medicine.getThreshold()));
        dateIssued.setText(medicine.getDateIssued());
        expirefactor.setText(String.valueOf(medicine.getExpireFactor()));
        frequency.setText(String.valueOf(reminderMedicine.getFrequency()));
        startTime.setText(reminderMedicine.getStartTime());
        interval.setText(String.valueOf(reminderMedicine.getInterval()));
        name.setTag(id);
    }

    private void updateSaveButton() {
        saveButton.setTag(UPDATE);
        saveButton.setText(UPDATE);
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
        expirefactor = (EditText) findViewById(R.id.medicineExpireFactor);
        frequency = (EditText) findViewById(R.id.reminderFrequency);
        startTime = (EditText) findViewById(R.id.reminderStartTime);
        interval = (EditText) findViewById(R.id.reminderInterval);
        saveButton = (Button) findViewById(R.id.saveMedicine);
        saveButton.setTag(NEW);
    }

    private void setListeners() {
        dateIssued.setOnClickListener(this);
        startTime.setOnClickListener(this);
        Calendar newCalendar = Calendar.getInstance();
        timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                startTime.setText(hourOfDay + COLON + minute);
            }
        },
                newCalendar.get(Calendar.HOUR_OF_DAY),
                newCalendar.get(Calendar.MINUTE), true);
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
        dateIssued.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()!=0)
                    dateIssued.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        startTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()!=0)
                    startTime.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.medicineDateIssued:
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
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
        boolean isValidFormat = checkFormat();
        if (!isValidFormat) {
            return;
        }
        String medicineName = name.getText().toString();
        String medicineDescription = description.getText().toString();
        int medicineCategory = categoriesMap.get(category.getSelectedItem());
        Boolean medicineRemind = reminder.isChecked();
        int medicineQuantity = Integer.parseInt(quantity.getText().toString());
        int medicineDosage = DOSAGE_HASH_MAP.get(dosage.getSelectedItem());
        int medicineConsumeQuantity = Integer.parseInt(consumeQuantity.getText().toString());
        int medicineThreshold = Integer.parseInt(threshold.getText().toString());
        String medicineDateIssued = dateIssued.getText().toString();
        int medicinceExpireFactor = Integer.parseInt(expirefactor.getText().toString());
        int reminderFrequency = Integer.parseInt(frequency.getText().toString());
        String reminderStartTime = startTime.getText().toString();
        int reminderInterval = Integer.parseInt(interval.getText().toString());

        if (saveButton.getTag().toString().equalsIgnoreCase(NEW)) {
            reminderMedicine = new Reminder(reminderFrequency, reminderStartTime, reminderInterval);
            int medicineReminderId = (int) reminderMedicine.save(context);
            medicine = new Medicine(medicineName, medicineDescription, medicineCategory, medicineReminderId, medicineRemind, medicineQuantity, medicineDosage, medicineConsumeQuantity, medicineThreshold, medicineDateIssued, medicinceExpireFactor);
            if (isValid()) {
                if (medicine.save(context) == -1) {
                    Toast.makeText(context, MEDICINE_NOT_SAVED, Toast.LENGTH_SHORT).show();
                } else {
                    ReminderUtils.syncMedicineReminder(context);
                    navigateToMainAcitivity();
                }
            }
        } else {
            medicine.setName(medicineName);
            medicine.setDescription(medicineDescription);
            medicine.setCategoryId(medicineCategory);
            medicine.setRemind(medicineRemind);
            medicine.setQuantity(medicineQuantity);
            medicine.setDosage(medicineDosage);
            medicine.setConsumeQuantity(medicineConsumeQuantity);
            medicine.setThreshold(medicineThreshold);
            medicine.setDateIssued(medicineDateIssued);
            medicine.setExpireFactor(medicinceExpireFactor);
            reminderMedicine.setFrequency(reminderFrequency);
            reminderMedicine.setStartTime(reminderStartTime);
            reminderMedicine.setInterval(reminderInterval);
            reminderMedicine.update(context);
            if (isValid()) {
                if (medicine.update(context) == -1) {
                    Toast.makeText(context, MEDICINE_NOT_UPDATED, Toast.LENGTH_SHORT).show();
                } else {
                    ReminderUtils.syncMedicineReminder(context);
                    navigateToMainAcitivity();
                }
            }


        }
    }

    private boolean checkFormat() {

        boolean isValid = true;
        if (name.getText().toString().isEmpty()) {
            name.setError("Please provide medicine name!");
            name.requestFocus();
            isValid = false;
        } else if (description.getText().toString().isEmpty()) {
            description.setError("Please enter description!");
            description.requestFocus();
            isValid = false;
        } else if (quantity.getText().toString().isEmpty()) {
            quantity.setError("Please enter quantity");
            quantity.requestFocus();
            isValid = false;
        } else if (consumeQuantity.getText().toString().isEmpty()) {
            consumeQuantity.setError("Please enter consume quantity");
            consumeQuantity.requestFocus();
            isValid = false;
        } else if (threshold.getText().toString().isEmpty()) {
            threshold.setError("Please enter expire factor");
            threshold.requestFocus();
            isValid = false;
        } else if (dateIssued.getText().toString().isEmpty()) {
            dateIssued.setError("Please enter date Issued");
            dateIssued.requestFocus();
            isValid = false;
        } else if (expirefactor.getText().toString().isEmpty()) {
            expirefactor.setError("Please enter threshold");
            expirefactor.requestFocus();
            isValid = false;
        } else if (frequency.getText().toString().isEmpty()) {
            frequency.setError("Please enter frequency");
            frequency.requestFocus();
            isValid = false;
        } else if (startTime.getText().toString().isEmpty()) {
            startTime.setError("Please enter start time");
            startTime.requestFocus();
            isValid = false;
        } else if (interval.getText().toString().isEmpty()) {
            interval.setError("Please enter interval");
            interval.requestFocus();
            isValid = false;
        }
        return isValid;
    }

    private boolean isValid() {
        boolean isValid = true;
        if (medicine.getConsumeQuantity() > medicine.getQuantity()) {
            consumeQuantity.setError("Consume Quantity should be less than Quantity");
            consumeQuantity.requestFocus();
            isValid = false;
        } else if (medicine.getThreshold() > medicine.getQuantity()) {
            threshold.setError("Threshold should be less than Quantity");
            threshold.requestFocus();
            isValid = false;
        }
        else if (medicine.getExpireFactor() > 24 ) {
            expirefactor.setError("Expire Factor should be less than 25");
            expirefactor.requestFocus();
            isValid = false;
        }
        else if (medicine.getCategory(context).getRemind() == true && medicine.getRemind() == false ){
            AlertDialog.Builder warningDialog = new AlertDialog.Builder(this);
            warningDialog.setTitle(Constants.TITLE_WARNING);
            warningDialog.setMessage("Reminder cannot be true off for this category of medicine");
            warningDialog.setPositiveButton(Constants.OK_BUTTON, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface alert, int button) {
                    alert.dismiss();
                }
            });
            warningDialog.show();
            isValid = false;
        }
        else{
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY,23);
            calendar.set(Calendar.MINUTE,59);
            calendar.set(Calendar.SECOND,59);
            long fullDayTime = calendar.getTimeInMillis();
            String time[] = startTime.getText().toString().split(":");
            int hour = Integer.parseInt(time[0]);
            int minute = Integer.parseInt(time[1]);
            calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);
            int frequencyReminder = Integer.parseInt(frequency.getText().toString());
            int intervalReminder = Integer.parseInt(interval.getText().toString());
            long maxTimeForReminder = calendar.getTimeInMillis() + (MINUTE * frequencyReminder * intervalReminder);
            if(maxTimeForReminder>fullDayTime){
                frequency.setError("Input proper combination of Frequency,Start Time and Interval");
                frequency.requestFocus();
                isValid = false;
            }

        }
        return isValid;
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
