package sg.edu.nus.iss.se.ft05.medipal.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import sg.edu.nus.iss.se.ft05.medipal.Measurement;
import sg.edu.nus.iss.se.ft05.medipal.R;
import sg.edu.nus.iss.se.ft05.medipal.dao.MeasurementDAO;
import sg.edu.nus.iss.se.ft05.medipal.fragments.CategoryFragment;
import sg.edu.nus.iss.se.ft05.medipal.fragments.MeasurementFragment;

public class DisplayMeasurement extends AppCompatActivity {

    public static final String MEASUREMENT_ID = "id";
    TextView id,systolic,diastolic,pulse,temperature,weight,measuredOn;
    Measurement measurement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_measurement);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Measurement Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        /*toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToMainAcitivity();
            }
        });*/

        Bundle bundle = getIntent().getExtras();
        int id = bundle.getInt(MEASUREMENT_ID);

        //Toast.makeText(getApplicationContext(), "Selected Id:"+id, Toast.LENGTH_SHORT).show();
        findViewsById();

        measurement=Measurement.findById(getApplicationContext(),id);

        this.id.setText         ("Id : "+id);
        this.systolic.setText   ("Systolic : "+measurement.getSystolic());
        this.diastolic.setText  ("Diastolic : "+measurement.getDiastolic());
        this.pulse.setText      ("Pulse : "+measurement.getPulse());
        this.temperature.setText("Temperature : "+measurement.getTemperature());
        this.weight.setText     ("Weight : "+measurement.getWeight());
        this.measuredOn.setText ("MeasuredOn : "+measurement.getMeasuredOn());


    }
    private void findViewsById() {
        this.id = (TextView) findViewById(R.id.measurementId);
        systolic = (TextView) findViewById(R.id.measurementSystolic);
        diastolic = (TextView) findViewById(R.id.measurementDiastolic);
        pulse = (TextView) findViewById(R.id.measurementPulse);
        temperature = (TextView) findViewById(R.id.measurementTemperature);
        weight = (TextView) findViewById(R.id.measurementWeight);
        measuredOn = (TextView) findViewById(R.id.measurementMeasuredOn);
    }

    /*public void navigateToMainAcitivity(){
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        MainActivity.currentFragment= MeasurementFragment.class.getName();
        startActivity(intent);
    }*/
}