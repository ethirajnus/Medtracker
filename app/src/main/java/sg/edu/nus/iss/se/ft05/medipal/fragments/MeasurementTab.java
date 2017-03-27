package sg.edu.nus.iss.se.ft05.medipal.fragments;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import sg.edu.nus.iss.se.ft05.medipal.constants.Constants;
import sg.edu.nus.iss.se.ft05.medipal.domain.Measurement;
import sg.edu.nus.iss.se.ft05.medipal.managers.MeasurementManager;
import sg.edu.nus.iss.se.ft05.medipal.R;

/**
 * Class for Measurement tab
 * @author Moushumi Seal
 */
public class MeasurementTab extends Fragment {
    //private Context context;
    private TextView tv_weight, tv_pulse, tv_bloodPressure, diastolic, tv_temperature;
    int recent_weight,recent_systolic,recent_diastolic,recent_pulse,recent_temperature;

    public MeasurementTab() {
        // Required empty public constructor
    }

    /**
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    /**
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        MeasurementManager measurementManager = new MeasurementManager();
        Measurement measurement = measurementManager.findLatest(getContext());

        View view = inflater.inflate(R.layout.fragment_measurement_tab, container, false);

        tv_weight = (TextView) view.findViewById(R.id.weight);
        tv_pulse = (TextView) view.findViewById(R.id.pulse);
        tv_bloodPressure = (TextView) view.findViewById(R.id.bloodPressure);
        tv_temperature = (TextView) view.findViewById(R.id.temperature);

        int systolic, diastolic, pulse, weight;
        String temperature;
        systolic = measurement.getSystolic();
        diastolic = measurement.getDiastolic();
        pulse = measurement.getPulse();
        weight = measurement.getWeight();
        temperature = String.valueOf(measurement.getTemperature());

        if(systolic == 0 && diastolic == 0){
            tv_bloodPressure.setText((formatText(Constants.BLOOD_PRESSURE," - ")));
        } else {
            tv_bloodPressure.setText((formatText(Constants.BLOOD_PRESSURE, systolic
                            + "/"
                            + diastolic
                            + Constants.BLOOD_PRESSURE_UNIT)));
        }
        if(pulse == 0){
            tv_pulse.setText((formatText(Constants.PULSE," - ")));
        } else {
            tv_pulse.setText(formatText(Constants.PULSE, pulse + Constants.PULSE_UNIT));
        }
        if(temperature.matches(Constants.PATTERN_ZERO)){
            tv_temperature.setText(formatText(Constants.TEMPERATURE, " - "));
        } else {
            tv_temperature.setText(formatText(Constants.TEMPERATURE, temperature + Constants.TEMPERATURE_UNIT));
        }
        if(weight == 0){
            tv_weight.setText(formatText(Constants.WEIGHT, " - "));
        } else {
            tv_weight.setText(formatText(Constants.WEIGHT, weight + Constants.WEIGHT_UNIT));
        }
        return view;
    }
    private SpannableString formatText(String boldText, String normalText){
        SpannableString str = new SpannableString(boldText + normalText);
        str.setSpan(new StyleSpan(Typeface.BOLD), 0, boldText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return  str;
    }

}
