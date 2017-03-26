package sg.edu.nus.iss.se.ft05.medipal.activities;

import android.content.DialogInterface;
import android.content.Intent;

import java.text.SimpleDateFormat;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import java.util.Calendar;
import java.util.Date;

import sg.edu.nus.iss.se.ft05.medipal.constants.Constants;
import sg.edu.nus.iss.se.ft05.medipal.managers.MeasurementManager;
import sg.edu.nus.iss.se.ft05.medipal.R;
import sg.edu.nus.iss.se.ft05.medipal.fragments.MeasurementFragment;

import static sg.edu.nus.iss.se.ft05.medipal.constants.Constants.*;


public class AddMeasurement extends AppCompatActivity implements View.OnClickListener {

    Button saveButton;
    EditText systolic, diastolic, pulse, temperature, weight, measuredOn;

    private MeasurementManager measurementManager;

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

        if(isValid()) {
            setEmptyFieldsToZero();
            Date d = Calendar.getInstance().getTime();
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
            String measurementMeasuredOn = new SimpleDateFormat(DATE_FORMAT).format(d);

            MeasurementManager measurementManager = new MeasurementManager(measurementSystolic, measurementDiastolic, measurementPulse, measurementTemperature, measurementWeight, measurementMeasuredOn);

            if (measurementManager.save(getApplicationContext()) == -1) {

                Toast.makeText(getApplicationContext(), "Measurement was not inserted properly,Please try again later", Toast.LENGTH_SHORT).show();

            } else {

                navigateToMainActivity();

            }
        }
    }

    @Override
    public void onBackPressed() {
        navigateToMainActivity();
    }

    public void navigateToMainActivity() {

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        MainActivity.currentFragment = MeasurementFragment.class.getName();
        startActivity(intent);
        finish();
    }

    private boolean isValid(){
        boolean isValid  = true;
        if(TextUtils.isEmpty(systolic.getText().toString().trim())
                && TextUtils.isEmpty(diastolic.getText().toString().trim())
                && TextUtils.isEmpty(pulse.getText().toString().trim())
                && TextUtils.isEmpty(temperature.getText().toString().trim())
                && TextUtils.isEmpty(weight.getText().toString().trim())) {
            isValid = false;
            AlertDialog.Builder warningDialog = new AlertDialog.Builder(this);
            warningDialog.setTitle(Constants.TITLE_WARNING);
            warningDialog.setMessage(R.string.warning_atleastOne);
            warningDialog.setPositiveButton(Constants.OK_BUTTON, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface alert, int which) {
                    alert.dismiss();
                }
            });
            warningDialog.show();
        }else if(TextUtils.isEmpty(systolic.getText().toString().trim())
                && !TextUtils.isEmpty(diastolic.getText().toString().trim())){
            systolic.setError(Constants.EMPTY_SYSTOLIC);
            systolic.requestFocus();
            isValid = false;
        } else if(!TextUtils.isEmpty(systolic.getText().toString().trim())
                && TextUtils.isEmpty(diastolic.getText().toString().trim())){
            diastolic.setError(Constants.EMPTY_DIASTOLIC);
            diastolic.requestFocus();
            isValid = false;
        }
        if(systolic.getText().toString().trim().matches(Constants.PATTERN_ZERO)){
            systolic.setError(Constants.INVALID_SYSTOLIC);
            systolic.requestFocus();
            isValid = false;
        }else if(diastolic.getText().toString().trim().matches(Constants.PATTERN_ZERO)){
            diastolic.setError(Constants.INVALID_DIASTOLIC);
            diastolic.requestFocus();
            isValid = false;
        }else if(pulse.getText().toString().trim().matches(Constants.PATTERN_ZERO)){
            pulse.setError(Constants.INVALID_PULSE);
            pulse.requestFocus();
            isValid = false;
        }else if(temperature.getText().toString().trim().matches(Constants.PATTERN_ZERO)){
            temperature.setError(Constants.INVALID_TEMPERATURE);
            temperature.requestFocus();
            isValid = false;
        }else if(weight.getText().toString().trim().matches(Constants.PATTERN_ZERO)){
            weight.setError(Constants.INVALID_WEIGHT);
            weight.requestFocus();
            isValid = false;
        }
        return isValid;
    }

    private void setEmptyFieldsToZero(){
        if(TextUtils.isEmpty(systolic.getText().toString().trim()))
            systolic.setText(String.valueOf(0));
        if(TextUtils.isEmpty(diastolic.getText().toString().trim()))
            diastolic.setText(String.valueOf(0));
        if(TextUtils.isEmpty(pulse.getText().toString().trim()))
            pulse.setText(String.valueOf(0));
        if(TextUtils.isEmpty(temperature.getText().toString().trim()))
            temperature.setText(String.valueOf(0));
        if(TextUtils.isEmpty(weight.getText().toString().trim()))
            weight.setText(String.valueOf(0));
    }
}
