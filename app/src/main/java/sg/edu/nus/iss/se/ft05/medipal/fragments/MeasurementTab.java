package sg.edu.nus.iss.se.ft05.medipal.fragments;

import android.database.Cursor;
import android.icu.util.Measure;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import sg.edu.nus.iss.se.ft05.medipal.domain.Measurement;
import sg.edu.nus.iss.se.ft05.medipal.managers.MeasurementManager;
import sg.edu.nus.iss.se.ft05.medipal.R;


public class MeasurementTab extends Fragment {
    //private Context context;
    private TextView weight, pulse, systolic, diastolic, temperature;
    int recent_weight,recent_systolic,recent_diastolic,recent_pulse,recent_temperature;

    public MeasurementTab() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        MeasurementManager measurementManager = new MeasurementManager();
        Measurement measurement=measurementManager.findLatest(getContext());

        View view = inflater.inflate(R.layout.fragment_measurement_tab, container, false);
        weight = (TextView) view.findViewById(R.id.weight);
        weight.setText("" + measurement.getWeight());
        pulse = (TextView) view.findViewById(R.id.pulse);
        pulse.setText("" + measurement.getPulse());
        systolic = (TextView) view.findViewById(R.id.systolic);
        systolic.setText("" + measurement.getSystolic());
        diastolic = (TextView) view.findViewById(R.id.diastolic);
        diastolic.setText("" + measurement.getDiastolic());
        temperature = (TextView) view.findViewById(R.id.temperature);
        temperature.setText("" + measurement.getTemperature());



        return view;
    }


}
