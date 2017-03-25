package sg.edu.nus.iss.se.ft05.medipal.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import sg.edu.nus.iss.se.ft05.medipal.managers.Measurement;
import sg.edu.nus.iss.se.ft05.medipal.R;




public class MeasurementTab extends Fragment {
    //private Context context;
    private TextView weight,pulse,systolic,diastolic,temperature;

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

        View view=inflater.inflate(R.layout.fragment_measurement_tab,container,false);
        weight=(TextView) view.findViewById(R.id.weight);
        weight.setText(""+Measurement.getRecent_weight());
        pulse=(TextView) view.findViewById(R.id.pulse);
        pulse.setText(""+Measurement.getRecent_pulse());
        systolic=(TextView) view.findViewById(R.id.systolic);
        systolic.setText(""+Measurement.getRecentSystolic());
        diastolic=(TextView) view.findViewById(R.id.diastolic);
        diastolic.setText(""+Measurement.getRecent_diastolic());
        temperature=(TextView) view.findViewById(R.id.temperature);
        temperature.setText(""+Measurement.getRecent_temperature());

        return view;
    }


}
