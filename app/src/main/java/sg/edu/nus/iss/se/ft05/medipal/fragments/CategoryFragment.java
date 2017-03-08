package sg.edu.nus.iss.se.ft05.medipal.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sg.edu.nus.iss.se.ft05.medipal.R;
import sg.edu.nus.iss.se.ft05.medipal.activities.AddCategory;
import sg.edu.nus.iss.se.ft05.medipal.activities.AddMedicine;
import sg.edu.nus.iss.se.ft05.medipal.activities.MainActivity;


/**
 * A placeholder fragment containing a simple view.
 */
public class CategoryFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.category_fragment, container, false);
        ((MainActivity)getActivity()).setFloatingActionButtonAction(AddCategory.class);
        return view;
    }
}
