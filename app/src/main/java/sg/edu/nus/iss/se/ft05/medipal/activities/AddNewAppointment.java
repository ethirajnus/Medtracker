package sg.edu.nus.iss.se.ft05.medipal.activities;

import android.content.Context;

import android.content.DialogInterface;

import android.os.AsyncTask;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import sg.edu.nus.iss.se.ft05.medipal.Util.ReminderUtils;
import sg.edu.nus.iss.se.ft05.medipal.constants.Constants;
import sg.edu.nus.iss.se.ft05.medipal.managers.AppointmentManager;
import sg.edu.nus.iss.se.ft05.medipal.R;
import sg.edu.nus.iss.se.ft05.medipal.fragments.AppointmentFragment;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.text.ParseException;
import java.util.Calendar;

import android.widget.DatePicker;
import android.app.DatePickerDialog;
import android.widget.TimePicker;
import android.app.TimePickerDialog;
import static sg.edu.nus.iss.se.ft05.medipal.constants.Constants.*;
import java.text.SimpleDateFormat;

import android.text.InputType;
import android.widget.Toast;

import java.util.Date;

public class AddNewAppointment extends AppCompatActivity implements View.OnClickListener {
    EditText date, time, clinic, test, pre_test;
    private int mHour, mMinute;
    private SimpleDateFormat dateFormatter;
    private DatePickerDialog fromDatePickerDialog;
    private TimePickerDialog timePickerDialog;
    Context context;
    boolean flag=true;//To ensure that all input fields are valid
    private static final String BLANK_DATE_MESSAGE="Appointment date required";
    private static final String WRONG_TIME="Please choose a slot at least one hour from now";
    private static final String BLANK_TIME_MESSAGE="Appointment time required";
    private static final String BLANK_CLINIC_MESSAGE="Clinic Required";
    private static final String BLANK_TEST_MESSAGE="Test Required";
    private static final String WRONG_DATE="Date cannot be before today";
    AppointmentManager appointmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_appointment);
        context = getApplicationContext();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        dateFormatter = new SimpleDateFormat(DATE_FORMAT);

        findViewsById();

        try {
            setDateTimeField();
        } catch (ParseException e) {
            e.printStackTrace();
        }
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

    private void setDateTimeField() throws java.text.ParseException {
        date.setOnClickListener(this);
        final Calendar now = Calendar.getInstance();
        final Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                date.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH)) {

            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                //Ensure that date chosen is before today's date
                if (year < now.get(Calendar.YEAR))

                    view.updateDate(newCalendar
                            .get(Calendar.YEAR), newCalendar
                            .get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

                if (monthOfYear < now.get(Calendar.MONTH) && year == now.get(Calendar.YEAR))
                    view.updateDate(newCalendar
                            .get(Calendar.YEAR), newCalendar
                            .get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

                if (dayOfMonth < now.get(Calendar.DAY_OF_MONTH) && year == now.get(Calendar.YEAR) &&
                        monthOfYear == now.get(Calendar.MONTH))
                    view.updateDate(newCalendar
                            .get(Calendar.YEAR), newCalendar
                            .get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
            }

        };
        time.setOnClickListener(this);
        timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                        Date today=Calendar.getInstance().getTime(),appointment_date=Calendar.getInstance().getTime();
                        //ensure that appointment time is at least one hour from the current time
                        String hour , minutes ;
                        try {
                            today=new SimpleDateFormat(DATE_FORMAT).
                                    parse(new SimpleDateFormat(DATE_FORMAT).format(Calendar.getInstance().getTime()));
                            appointment_date=new SimpleDateFormat(DATE_FORMAT).parse(date.getText().toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        if (hourOfDay < 10)
                            hour = "0" + hourOfDay;
                        else
                            hour =""+ hourOfDay;
                        if (minute < 10)
                            minutes = "0" + minute;
                        else
                            minutes =""+ minute;


                        if(today.equals(appointment_date)) {
                            if ((newCalendar.get(Calendar.HOUR_OF_DAY) + 1) < hourOfDay) {
                                time.setError(null);
                                time.setText(hour + ":" + minutes);
                                flag = true;
                            } else if ((newCalendar.get(Calendar.HOUR_OF_DAY) + 1) == hourOfDay && (newCalendar.get(Calendar.MINUTE)) < minute) {
                                time.setError(null);
                                time.setText(hour + ":" + minutes);
                                flag = true;
                            } else {
                                time.setError(WRONG_TIME);
                            }
                        }
                        else
                        {time.setText(hour+":"+minutes);}

                    }
                }, mHour, mMinute, false)  ;


    }

    public void createAppointment(View view) throws java.text.ParseException
    {

        date=(EditText) findViewById(R.id.new_appointment_date);
        time=(EditText) findViewById(R.id.new_appointment_time);
        clinic=(EditText) findViewById(R.id.new_appointment_clinic);
        test=(EditText) findViewById(R.id.new_appointment_test);
        pre_test=(EditText) findViewById(R.id.new_appointment_pre_test);
        String adate="",atime="",aclinic="",atest="",apre_test="";
        Calendar calendar=Calendar.getInstance();
        Date d1=calendar.getTime(),d2=calendar.getTime();
        String secondDate=new SimpleDateFormat(DATE_FORMAT).format(d2);
        adate=date.getText().toString();
        atime=time.getText().toString();
        aclinic=clinic.getText().toString();
        atest=test.getText().toString();
        apre_test=pre_test.getText().toString();
        flag=true;

        if (clinic.getText().toString().length() == 0) {
            clinic.setError(BLANK_CLINIC_MESSAGE);
            flag = false;
        }
        if (time.getText().toString().length() == 0) {
            time.setError(BLANK_TIME_MESSAGE);
            flag = false;
        }
        if (date.getText().toString().length() == 0) {
            date.setError(BLANK_DATE_MESSAGE);
            flag = false;
        }
        if (test.getText().toString().length() == 0) {
            test.setError(BLANK_TEST_MESSAGE);
            flag = false;
        }
        if(date.getText().toString().length()!=0) {
            d1=new SimpleDateFormat(DATE_FORMAT).parse(adate);
        }
        d2=new SimpleDateFormat(DATE_FORMAT).parse(secondDate);
        if(d1.before(d2))
        {
            date.setError(WRONG_DATE);
            flag = false;
        }
        else if (AppointmentManager.exists(context, adate,atime)) {
            AlertDialog.Builder warningDialog = new AlertDialog.Builder(this);
            warningDialog.setTitle(Constants.TITLE_WARNING);
            warningDialog.setMessage("You already have an appointment at this slot");
            warningDialog.setPositiveButton(Constants.OK_BUTTON, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface alert, int button) {
                    alert.dismiss();
                }
            });
            warningDialog.show();
            flag = false;
        }
        if (flag == true) {

            //All input fields are valid
            appointmentManager = new AppointmentManager(adate, atime, aclinic, atest, apre_test);
            Log.v("date",adate);
            new SaveAppointment().execute();
        }
    }

    private class SaveAppointment extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            return appointmentManager.save(context)==-1;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if(result){
                Toast.makeText(context, APPOINTMENT_NOT_SAVED, Toast.LENGTH_SHORT).show();
            } else {
                ReminderUtils.syncAppointmentReminder(context);
                navigateToMainAcitivity();
            }
        }
    }

    public void navigateToMainAcitivity() {
        Intent intent = new Intent(context, MainActivity.class);
        MainActivity.currentFragment = AppointmentFragment.class.getName();
        startActivity(intent);
        finish();
    }

}