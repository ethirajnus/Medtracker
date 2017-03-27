package sg.edu.nus.iss.se.ft05.medipal.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import sg.edu.nus.iss.se.ft05.medipal.domain.Appointment;
import sg.edu.nus.iss.se.ft05.medipal.managers.AppointmentManager;
import sg.edu.nus.iss.se.ft05.medipal.R;

import static sg.edu.nus.iss.se.ft05.medipal.constants.Constants.ID;

/**
 * Class to show appointment details
 */
public class ShowAppointment extends AppCompatActivity {
    private Context context;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_appointment);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int id = bundle.getInt(ID);

        EditText date, time, clinic, description;
        Button button;
        date = (EditText) findViewById(R.id.new_appointment_date);
        time = (EditText) findViewById(R.id.new_appointment_time);
        clinic = (EditText) findViewById(R.id.new_appointment_clinic);
        description = (EditText) findViewById(R.id.new_appointment_description);
        button = (Button) findViewById(R.id.button);
        button.setVisibility(View.GONE);

        context = getApplicationContext();

        AppointmentManager appointmentManager = new AppointmentManager();
        Appointment appointment = appointmentManager.findById(context, id);

        date.setText(appointment.getDate());
        time.setText(appointment.getTime());
        clinic.setText(appointment.getClinic());
        description.setText(appointment.getDescription());

        date.setEnabled(false);
        time.setEnabled(false);
        clinic.setEnabled(false);
        description.setEnabled(false);
    }
}
