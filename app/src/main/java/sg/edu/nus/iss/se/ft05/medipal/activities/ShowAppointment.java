package sg.edu.nus.iss.se.ft05.medipal.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import sg.edu.nus.iss.se.ft05.medipal.model.Appointment;
import sg.edu.nus.iss.se.ft05.medipal.R;

public class ShowAppointment extends AppCompatActivity {
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_appointment);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("APPOINTMENT INFO");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        Intent intent=getIntent();
        Bundle b=intent.getExtras();
        long l=b.getLong("id");

        TextView date,time,clinic,test,pre_test;
        date=(TextView) findViewById(R.id.appointment_date);
        time=(TextView) findViewById(R.id.appointment_time);
        clinic=(TextView) findViewById(R.id.appointment_clinic);
        test=(TextView) findViewById(R.id.appointment_test);
        pre_test=(TextView) findViewById(R.id.appointment_pre_test);
        context=getApplicationContext();
        Appointment appointment=Appointment.findById(context,l);
        date.setText(appointment.getDate());
        time.setText(appointment.getTime());
        clinic.setText(appointment.getClinic());
        test.setText(appointment.getTest());
        pre_test.setText(appointment.getPreTest());
    }

}
