package sg.edu.nus.iss.se.ft05.medipal.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import sg.edu.nus.iss.se.ft05.medipal.managers.Measurement;
import sg.edu.nus.iss.se.ft05.medipal.R;

public class DisplayMeasurement extends AppCompatActivity {

    public static final String MEASUREMENT_ID = "id";
    private TextView id,systolic,diastolic,pulse,temperature,weight,measuredOn;
    private Measurement measurement;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_measurement);
        setTitle("Measurement Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Bundle bundle = getIntent().getExtras();
        int id = bundle.getInt(MEASUREMENT_ID);
        context=getApplicationContext();

        findViewsById();

        measurement=Measurement.findById(context,id);

        this.id.setText         ("Id : "+id);
        this.systolic.setText   ("Systolic : "+measurement.getSystolic()+" mm Hg");
        this.diastolic.setText  ("Diastolic : "+measurement.getDiastolic()+" mm Hg");
        this.pulse.setText      ("Pulse : "+measurement.getPulse()+" bpm");
        this.temperature.setText("Temperature : "+measurement.getTemperature()+" C");
        this.weight.setText     ("Weight : "+measurement.getWeight()+" Kg");
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

}