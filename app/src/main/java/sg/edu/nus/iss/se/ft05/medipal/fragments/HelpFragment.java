package sg.edu.nus.iss.se.ft05.medipal.fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sg.edu.nus.iss.se.ft05.medipal.R;
import sg.edu.nus.iss.se.ft05.medipal.activities.MainActivity;

public class HelpFragment extends Fragment {

    public HelpFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setVisibility(View.GONE);

        FloatingActionButton fabSOS = (FloatingActionButton) getActivity().findViewById(R.id.fabSOS);
        fabSOS.setVisibility(View.GONE);

        FloatingActionButton fabEmail = (FloatingActionButton) getActivity().findViewById(R.id.fabEmail);
        fabEmail.setVisibility(View.GONE);

        getActivity().setTitle("Help");

        //hide the share button
        setHasOptionsMenu(true);
        getActivity().invalidateOptionsMenu();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.help_fragment, container, false);
    }
}
