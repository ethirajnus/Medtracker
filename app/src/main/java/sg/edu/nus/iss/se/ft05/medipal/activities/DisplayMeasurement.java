package sg.edu.nus.iss.se.ft05.medipal.activities;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.TextView;

import sg.edu.nus.iss.se.ft05.medipal.domain.Measurement;
import sg.edu.nus.iss.se.ft05.medipal.managers.MeasurementManager;
import sg.edu.nus.iss.se.ft05.medipal.R;

/**
 * Class to Display Measurement
 */
public class DisplayMeasurement extends AppCompatActivity {

    public static final String MEASUREMENT_ID = "id";
    private TextView systolic, diastolic, pulse, temperature, weight, measuredOn;

    private Context context;

    /**
     * Method to run while creating UI for addition
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_display_measurement);
        setTitle("Measurement Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Bundle bundle = getIntent().getExtras();
        int id = bundle.getInt(MEASUREMENT_ID);
        context = getApplicationContext();

        findViewsById();
        MeasurementManager measurementManager = new MeasurementManager();
        Measurement measurement = measurementManager.findById(context, id);

        if(measurement.getSystolic() == 0){
            systolic.setText(formatText("Systolic : "," - "));
            //systolic.setVisibility(View.GONE);
        } else {
            systolic.setVisibility(View.VISIBLE);
            systolic.setText(formatText("Systolic : ",measurement.getSystolic() + " mm Hg"));
        }
        if(measurement.getDiastolic() == 0){
            diastolic.setText(formatText("Diastolic : "," - "));
            //diastolic.setVisibility(View.GONE);
        } else {
            diastolic.setVisibility(View.VISIBLE);
            diastolic.setText(formatText("Diastolic : ",measurement.getDiastolic() + " mm Hg"));
        }
        if(measurement.getPulse() == 0){
            pulse.setText(formatText("Pulse : "," - "));
            //pulse.setVisibility(View.GONE);
        } else {
            pulse.setVisibility(View.VISIBLE);
            pulse.setText(formatText("Pulse : ", measurement.getPulse() + " bpm"));
        }
        if(measurement.getTemperature() == 0){
            temperature.setText(formatText("Temperature : ", " - "));
            //temperature.setVisibility(View.GONE);
        } else {
            temperature.setVisibility(View.VISIBLE);
            temperature.setText(formatText("Temperature : ", measurement.getTemperature() + " C"));
        }
        if(measurement.getWeight() == 0){
            weight.setText(formatText("Weight : ", " - "));
            //weight.setVisibility(View.GONE);
        } else {
            weight.setVisibility(View.VISIBLE);
            weight.setText(formatText("Weight : ", measurement.getWeight() + " Kg"));
        }

        measuredOn.setText(formatText("Measured On : ", measurement.getMeasuredOn()));
    }

    /**
     * view
     */
    private void findViewsById() {
        systolic = (TextView) findViewById(R.id.measurementSystolic);
        diastolic = (TextView) findViewById(R.id.measurementDiastolic);
        pulse = (TextView) findViewById(R.id.measurementPulse);
        temperature = (TextView) findViewById(R.id.measurementTemperature);
        weight = (TextView) findViewById(R.id.measurementWeight);
        measuredOn = (TextView) findViewById(R.id.measurementMeasuredOn);
    }

    private SpannableString formatText(String boldText, String normalText){
        SpannableString str = new SpannableString(boldText + normalText);
        str.setSpan(new StyleSpan(Typeface.BOLD), 0, boldText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return  str;
    }

}