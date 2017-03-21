package sg.edu.nus.iss.se.ft05.medipal.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import sg.edu.nus.iss.se.ft05.medipal.R;
import sg.edu.nus.iss.se.ft05.medipal.Util.ReminderUtils;
import sg.edu.nus.iss.se.ft05.medipal.dao.DBHelper;
import sg.edu.nus.iss.se.ft05.medipal.fragments.ConsumptionFragment;
import sg.edu.nus.iss.se.ft05.medipal.model.Category;
import sg.edu.nus.iss.se.ft05.medipal.model.Consumption;


import static sg.edu.nus.iss.se.ft05.medipal.constants.Constants.ACTION;
import static sg.edu.nus.iss.se.ft05.medipal.constants.Constants.COLON;
import static sg.edu.nus.iss.se.ft05.medipal.constants.Constants.DATE_FORMAT;
import static sg.edu.nus.iss.se.ft05.medipal.constants.Constants.EDIT;
import static sg.edu.nus.iss.se.ft05.medipal.constants.Constants.EDIT_MEDICINE;
import static sg.edu.nus.iss.se.ft05.medipal.constants.Constants.ID;
import static sg.edu.nus.iss.se.ft05.medipal.constants.Constants.MEDICINE_NOT_SAVED;
import static sg.edu.nus.iss.se.ft05.medipal.constants.Constants.MEDICINE_NOT_UPDATED;
import static sg.edu.nus.iss.se.ft05.medipal.constants.Constants.NEW;
import static sg.edu.nus.iss.se.ft05.medipal.constants.Constants.NEW_MEDICINE;
import static sg.edu.nus.iss.se.ft05.medipal.constants.Constants.UPDATE;

public class AddConsumption extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private EditText quantity, date, time;
    private Spinner medicineid;
    private Button saveButton;
    private Context context;

    private static final SimpleDateFormat formatter = new SimpleDateFormat(
            DATE_FORMAT, Locale.ENGLISH);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_consumption);
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
            setTitle(EDIT_CONSUMPTION);
        } else {
            setTitle(NEW_CONSUMPTION);
        }
    }

    private void updateConsumptionValues(int id) {
        medicine = Medicine.findById(context, id);
        quantity.setText(String.valueOf(consumption.getQuantity()));
        date.setText(consumption.getDate());
        Time.setText(consumption.getTime());
    }

    private void updateSaveButton() {
        saveButton.setTag(UPDATE);
        saveButton.setText(UPDATE);
    }

    private void findViewsById() {
        medicineid = (spinner) findViewById(R.id.consumptionmedicineid);
        quantity = (EditText) findViewById(R.id.consumptionQuantity);
        date = (EditText) findViewById(R.id.consumptionDate);
        Time = (EditText) findViewById(R.id.consumptionTime);
        saveButton = (Button) findViewById(R.id.saveconsumption);
        saveButton.setTag(NEW);
    }

    private void setListeners() {
        date.setOnClickListener(this);
        Time.setOnClickListener(this);
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

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.consumptionDate:
                datePickerDialog.show();
                break;
            case R.id.consumptionTime:
                timePickerDialog.show();
                break;
            case R.id.saveconsumption:
                saveconsumption();
                break;
        }
    }

    private void saveconsumption() {
        boolean isValidFormat = checkFormat();
        if (!isValidFormat) {
            return;
        }
        int medicineid = medicinesmap. (medicineid.getSelectedItem());
        int Quantity = Integer.parseInt(quantity.getText().toString());
        String Date = date.getText().toString();
        String Time = Time.getText().toString();

        if (saveButton.getTag().toString().equalsIgnoreCase(NEW)) {
            consumption = new Consumption(consumptionmedicineid, consumptionQuantity, consumptionDate);
            if (isValid()) {
                if (consumption.save(context) == -1) {
                    Toast.makeText(context, CONSUMPTION_NOT_SAVED, Toast.LENGTH_SHORT).show();
                } else {
                    navigateToMainAcitivity();
                }
            }
        } else {
            consumption.setMedicineId(consumptionMedicineID);
            consumption.setQuantity(consumptionQuantity);
            consumption.setDate(consumptionDate);
            consumption.setTime(consumptionTime);
            if (isValid()) {
                if (consumption.update(context) == -1) {
                    Toast.makeText(context, CONSUMPTION_NOT_UPDATED, Toast.LENGTH_SHORT).show();
                } else {
                    navigateToMainAcitivity();
                }
            }


        }
    }

    private boolean checkFormat() {

        boolean isValid = true;
        if (quantity.getText().toString().isEmpty()) {
            quantity.setError("Please enter quantity");
            quantity.requestFocus();
            isValid = false;
        } else if (date.getText().toString().isEmpty()) {
            date.setError("Please enter date ");
            isValid = false;
        } else if (Time.getText().toString().isEmpty()) {
            Time.setError("Please enter time");
            isValid = false;
        }
        return isValid;
    }

    public void navigateToMainAcitivity() {
        Intent intent = new Intent(context, MainActivity.class);
        MainActivity.currentFragment = ConsumptionFragment.class.getName();
        startActivity(intent);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

}



