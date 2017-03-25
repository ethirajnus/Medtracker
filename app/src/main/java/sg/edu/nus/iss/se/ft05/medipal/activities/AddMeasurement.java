package sg.edu.nus.iss.se.ft05.medipal.activities;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import java.util.Date;

import sg.edu.nus.iss.se.ft05.medipal.Measurement;
import sg.edu.nus.iss.se.ft05.medipal.R;
import sg.edu.nus.iss.se.ft05.medipal.fragments.MeasurementFragment;
import static sg.edu.nus.iss.se.ft05.medipal.constants.Constants.*;


public class AddMeasurement extends AppCompatActivity implements View.OnClickListener {

    Button saveButton;
    EditText systolic, diastolic, pulse, temperature, weight, measuredOn;

    private Measurement measurement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_measurement);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        findViewsById();
        setListeners();

        setTitle("New Measurement");
    }

    /*private void updateCategoryValues(int id) {
        category = Category.findById(getApplicationContext(),id);
        name.setText(category.getCategoryName());
        code.setText(category.getCode());
        description.setText(category.getDescription());
        reminder.setChecked(category.getRemind());
        name.setTag(id);
    }

    private void updateSaveButton() {
        saveButton.setTag("update");
        saveButton.setText("Update");
    }*/

    private void setListeners() {
        saveButton.setOnClickListener(this);
    }

    private void findViewsById() {
        //id = (EditText) findViewById(R.id.measurementId);
        systolic = (EditText) findViewById(R.id.measurementSystolic);
        diastolic = (EditText) findViewById(R.id.measurementDiastolic);
        pulse = (EditText) findViewById(R.id.measurementPulse);
        temperature = (EditText) findViewById(R.id.measurementTemperature);
        weight = (EditText) findViewById(R.id.measurementWeight);
        saveButton = (Button) findViewById(R.id.saveMeasurement);
        saveButton.setTag("New");
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.saveMeasurement:
                saveMeasurement();
                break;

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void saveMeasurement() {
        String temp = systolic.getText().toString();
        int measurementSystolic = Integer.parseInt(temp);
        temp = diastolic.getText().toString();
        int measurementDiastolic = Integer.parseInt(temp);
        temp = pulse.getText().toString();
        int measurementPulse = Integer.parseInt(temp);
        temp = temperature.getText().toString();
        int measurementTemperature = Integer.parseInt(temp);
        temp = weight.getText().toString();
        int measurementWeight = Integer.parseInt(temp);
        String measurementMeasuredOn = new SimpleDateFormat(DATE_FORMAT).format(new Date());
        Measurement measurement = new Measurement(measurementSystolic, measurementDiastolic, measurementPulse, measurementTemperature, measurementWeight, measurementMeasuredOn);

        //  if(saveButton.getTag().toString().equalsIgnoreCase("New")){

        if (measurement.save(getApplicationContext()) == -1) {
            Toast.makeText(getApplicationContext(), "Measurement was not inserted properly,Please try again later", Toast.LENGTH_SHORT).show();
        } else {
            navigateToMainActivity();

        }
        //}
        /*else {
            category.setRemind(categoryReminder);
            category.setCategoryName(categoryName);
            category.setCode(categoryCode);
            category.setDescription(categoryDescription);
            if(category.update(getApplicationContext())== -1){
                Toast.makeText(getApplicationContext(), "Category was not updated properly,Please try again later", Toast.LENGTH_SHORT).show();
            }
            else {
                navigateToMainAcitivity();
            }
        }*/
    }

    public void navigateToMainActivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        MainActivity.currentFragment = MeasurementFragment.class.getName();
        startActivity(intent);
    }
}
