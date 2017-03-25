package sg.edu.nus.iss.se.ft05.medipal.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sg.edu.nus.iss.se.ft05.medipal.R;

import static sg.edu.nus.iss.se.ft05.medipal.constants.Constants.REPORT;

/**
 * Created by ethi on 24/03/17.
 */

public class ReportFragment extends Fragment {

    public ReportFragment() {
        // Required empty public constructor
    }

	@Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
    	View view = inflater.inflate(R.layout.report_fragment, container, false);
        getActivity().setTitle(REPORT);
        return view;
    	
    }
}
