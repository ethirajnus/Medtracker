package sg.edu.nus.iss.se.ft05.medipal.activities;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import sg.edu.nus.iss.se.ft05.medipal.Util.ReminderUtils;
import sg.edu.nus.iss.se.ft05.medipal.model.Appointment;
import sg.edu.nus.iss.se.ft05.medipal.R;
import sg.edu.nus.iss.se.ft05.medipal.fragments.AppointmentFragment;
import sg.edu.nus.iss.se.ft05.medipal.model.Reminder;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.Calendar;

import android.widget.DatePicker;
import android.app.DatePickerDialog;
import android.widget.TimePicker;
import android.app.TimePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;

import java.text.SimpleDateFormat;

import android.text.InputType;

import java.util.Date;
import java.util.Locale;

public class AddNewAppointment extends AppCompatActivity implements View.OnClickListener {

    EditText date, time, clinic, test, pre_test;
    private int mHour, mMinute;
    private SimpleDateFormat dateFormatter;
    private SimpleDateFormat timeFormatter;
    private DatePickerDialog fromDatePickerDialog;
    private TimePickerDialog timePickerDialog;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_appointment);

        context = getApplicationContext();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("NEW APPOINTMENT");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy");


        findViewsById();

        setDateTimeField();

    }

    @Override
    public void onClick(View view) {
        if (view == date)
            fromDatePickerDialog.show();
        else if (view == time)
            timePickerDialog.show();


    }

    private void findViewsById() {
        date = (EditText) findViewById(R.id.new_appointment_date);
        date.setInputType(InputType.TYPE_NULL);
        date.requestFocus();
        time = (EditText) findViewById(R.id.new_appointment_time);
        time.setInputType(InputType.TYPE_NULL);

    }

    private void setDateTimeField() {
        date.setOnClickListener(this);
        final Calendar now = Calendar.getInstance();
        final Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                date.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    public void createAppointment(View view) throws java.text.ParseException {
        date = (EditText) findViewById(R.id.new_appointment_date);
        time = (EditText) findViewById(R.id.new_appointment_time);
        clinic = (EditText) findViewById(R.id.new_appointment_clinic);
        test = (EditText) findViewById(R.id.new_appointment_test);
        pre_test = (EditText) findViewById(R.id.new_appointment_pre_test);
        String adate = "", atime = "", aclinic = "", atest = "", apre_test = "";
        Calendar calendar = Calendar.getInstance();
        Date d2 = calendar.getTime();

        String secondDate = new SimpleDateFormat("dd-MM-yyyy").format(d2);

        boolean flag = true;
        adate = date.getText().toString();
        atime = time.getText().toString();
        aclinic = clinic.getText().toString();
        atest = test.getText().toString();
        apre_test = pre_test.getText().toString();

        Date d1 = new SimpleDateFormat("dd-MM-yyyy").parse(adate);
        d2 = new SimpleDateFormat("dd-MM-yyyy").parse(secondDate);

        if (d1.before(d2)) {
            date.setError("Date cannot be before today");
            flag = false;
        }
        if (clinic.getText().toString().length() == 0) {
            clinic.setError("Clinic name required");
            flag = false;
        }
        if (test.getText().toString().length() == 0) {
            test.setError("Test name required");
            flag = false;
        }
        if (flag == true) {


            Appointment appointment = new Appointment(adate, atime, aclinic, atest, apre_test);
            Log.v("date",adate);
            appointment.save(context);
            ReminderUtils.syncAppointmentReminder(context);

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            MainActivity.currentFragment = AppointmentFragment.class.getName();
            startActivity(intent);
        }
    }

}
