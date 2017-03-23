package sg.edu.nus.iss.se.ft05.medipal.fragments;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import sg.edu.nus.iss.se.ft05.medipal.Measurement;
import sg.edu.nus.iss.se.ft05.medipal.R;
import sg.edu.nus.iss.se.ft05.medipal.model.Consumption;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MeasurementTab.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MeasurementTab#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MeasurementTab extends Fragment {
    private Context context;
    private TextView textView;
    private Cursor cursor;
    private String date;
    private List<Measurement> measurements;
    private static final String MEASUREMENTS="Your Measurements";
    private static final String DATE_FORMAT="dd-MM-yyyy";
    private String content="";

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
        content+=MEASUREMENTS;
        textView=(TextView) view.findViewById(R.id.measurementText);
        textView.setText(content);
        return inflater.inflate(R.layout.fragment_measurement_tab, container, false);
    }


}
