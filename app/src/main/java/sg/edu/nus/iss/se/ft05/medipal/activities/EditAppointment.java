package sg.edu.nus.iss.se.ft05.medipal.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import sg.edu.nus.iss.se.ft05.medipal.Appointment;
import sg.edu.nus.iss.se.ft05.medipal.R;
import sg.edu.nus.iss.se.ft05.medipal.fragments.AppointmentFragment;

public class EditAppointment extends AppCompatActivity implements View.OnClickListener {

    EditText date1,time1;
    private int mHour,mMinute;
    private SimpleDateFormat dateFormatter;
    private DatePickerDialog fromDatePickerDialog;
    private TimePickerDialog timePickerDialog;
    EditText date,time,clinic,test,pre_test;
    Appointment appointment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_appointment);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("APPOINTMENT INFO");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
        Intent intent=getIntent();
        Bundle b=intent.getExtras();
        long l=b.getLong("id");
        appointment=Appointment.findById(getApplicationContext(),l);

        date=(EditText) findViewById(R.id.appointment_date);
        time=(EditText) findViewById(R.id.appointment_time);
        clinic=(EditText) findViewById(R.id.appointment_clinic);
        test=(EditText) findViewById(R.id.appointment_test);
        pre_test=(EditText) findViewById(R.id.appointment_pre_test);


        date.setText(appointment.getDate());
        time.setText(appointment.getTime());
        clinic.setText(appointment.getClinic());
        test.setText(appointment.getTest());
        pre_test.setText(appointment.getPreTest());


        findViewsById();

        setDateTimeField();

    }
    @Override
    public void onClick(View view) {
        if(view==date)
            fromDatePickerDialog.show();
        else if(view==time)
            timePickerDialog.show();

    }
    private void findViewsById() {
        date = (EditText) findViewById(R.id.appointment_date);
        date.setInputType(InputType.TYPE_NULL);
        date.requestFocus();
        time=(EditText) findViewById(R.id.appointment_time);
        time.setInputType(InputType.TYPE_NULL);

    }

    private void setDateTimeField() {
        date.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                date.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        time.setOnClickListener(this);
        timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        String hour="",minutes="";
                        if(hourOfDay<10)
                            hour="0"+hourOfDay;
                        else
                            hour+=hourOfDay;
                        if(minute<10)
                            minutes="0"+minute;
                        else
                            minutes+=minute;

                        time.setText(hour + ":" + minutes);
                    }
                }, mHour, mMinute, false);


    }

    public void editAppointment(View view) throws java.text.ParseException
    {
        boolean flag=true;
        Calendar calendar=Calendar.getInstance();
        Date d2=calendar.getTime();
        String secondDate=new SimpleDateFormat("dd-MM-yyyy").format(d2);
        Date d1=new SimpleDateFormat("dd-MM-yyyy").parse(date.getText().toString());
        d2=new SimpleDateFormat("dd-MM-yyyy").parse(secondDate);
        if(d1.before(d2))
        {
            date.setError("Date cannot be before today");
            flag=false;
        }
        if(clinic.getText().toString().length()==0)
        {clinic.setError("Clinic name required");
            flag=false;}
        if(test.getText().toString().length()==0)
        {test.setError("Test name required");
            flag=false;}
        if(flag==true) {
            appointment.setDate(date.getText().toString());
            appointment.setTime(time.getText().toString());
            appointment.setClinic(clinic.getText().toString());
            appointment.setTest(test.getText().toString());
            appointment.setPreTest(pre_test.getText().toString());
            appointment.update(getApplicationContext());
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            MainActivity.currentFragment = AppointmentFragment.class.getName();
            startActivity(intent);
        }
    }
}
