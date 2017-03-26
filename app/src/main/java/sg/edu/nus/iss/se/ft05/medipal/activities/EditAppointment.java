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
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import sg.edu.nus.iss.se.ft05.medipal.Util.ReminderUtils;
import sg.edu.nus.iss.se.ft05.medipal.domain.Appointment;
import sg.edu.nus.iss.se.ft05.medipal.managers.AppointmentManager;
import sg.edu.nus.iss.se.ft05.medipal.R;
import sg.edu.nus.iss.se.ft05.medipal.fragments.AppointmentFragment;

import static sg.edu.nus.iss.se.ft05.medipal.constants.Constants.*;


/**
 *
 */
public class EditAppointment extends AppCompatActivity implements View.OnClickListener {


    private int mHour, mMinute, id;
    private SimpleDateFormat dateFormatter;
    private DatePickerDialog fromDatePickerDialog;
    private TimePickerDialog timePickerDialog;
    private EditText date, time, clinic, test, pre_test;
    AppointmentManager appointmentManager;
    private Context context;
    private boolean flag = true; //flag is to ensure that all fields have been filled properly
    private static final String BLANK_DATE_MESSAGE = "Appointment date required";
    private static final String WRONG_TIME = "Please choose a slot at least one hour from now";
    private static final String BLANK_TIME_MESSAGE = "Appointment time required";
    private static final String BLANK_CLINIC_MESSAGE = "Clinic Required";
    private static final String BLANK_TEST_MESSAGE = "Test Required";
    private static final String WRONG_DATE = "Date cannot be before today";

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_appointment);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        context = getApplicationContext();

        dateFormatter = new SimpleDateFormat(DATE_FORMAT);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        id = bundle.getInt("id");

        appointmentManager = new AppointmentManager();
        Appointment appointment = appointmentManager.findById(getApplicationContext(), id);

        date = (EditText) findViewById(R.id.appointment_date);
        time = (EditText) findViewById(R.id.appointment_time);
        clinic = (EditText) findViewById(R.id.appointment_clinic);
        test = (EditText) findViewById(R.id.appointment_test);
        pre_test = (EditText) findViewById(R.id.appointment_pre_test);


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

        if (view == date)
            fromDatePickerDialog.show();

        else if (view == time)
            timePickerDialog.show();

    }

    private void findViewsById() {

        date = (EditText) findViewById(R.id.appointment_date);
        date.setInputType(InputType.TYPE_NULL);
        date.requestFocus();
        time = (EditText) findViewById(R.id.appointment_time);
        time.setInputType(InputType.TYPE_NULL);
    }

    private void setDateTimeField() {

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

                        String hour, minutes;
                        if (hourOfDay < 10)
                            hour = "0" + hourOfDay;
                        else
                            hour = "" + hourOfDay;
                        if (minute < 10)
                            minutes = "0" + minute;
                        else
                            minutes = "" + minute;

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
                }, mHour, mMinute, false);


    }

    public void editAppointment(View view) throws java.text.ParseException {

        boolean flag = true;
        Calendar calendar = Calendar.getInstance();

        if (clinic.getText().toString().length() == 0) {
            clinic.setError(BLANK_CLINIC_MESSAGE);
            flag = false;
        }

        if (time.getText().toString().length() == 0) {
            clinic.setError(BLANK_TIME_MESSAGE);
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

        Date d2 = calendar.getTime();
        String secondDate = new SimpleDateFormat(DATE_FORMAT).format(d2);
        Date d1 = new SimpleDateFormat(DATE_FORMAT).parse(date.getText().toString());
        d2 = new SimpleDateFormat(DATE_FORMAT).parse(secondDate);

        if (d1.before(d2)) {
            date.setError(WRONG_DATE);
            flag = false;
        }

        if (flag) {

            Appointment appointment = new Appointment();

            //All input fields are valid
            appointment.setDate(date.getText().toString());
            appointment.setTime(time.getText().toString());
            appointment.setClinic(clinic.getText().toString());
            appointment.setTest(test.getText().toString());
            appointment.setPreTest(pre_test.getText().toString());
            appointment.setId(id);

            appointmentManager.setAppointment(appointment);

            if(appointmentManager.save(context) == -1)
            {
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
