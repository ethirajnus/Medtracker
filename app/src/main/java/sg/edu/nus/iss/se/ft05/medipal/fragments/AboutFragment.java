package sg.edu.nus.iss.se.ft05.medipal.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sg.edu.nus.iss.se.ft05.medipal.R;

/**
 * Class for About the application
 * @author Moushumi Seal
 */
public class AboutFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setVisibility(View.GONE);

        FloatingActionButton fabSOS = (FloatingActionButton) getActivity().findViewById(R.id.fabSOS);
        fabSOS.setVisibility(View.GONE);

        getActivity().setTitle(R.string.action_about);

        //hide the share button
        setHasOptionsMenu(true);
        getActivity().invalidateOptionsMenu();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about, container, false);
    }
}
