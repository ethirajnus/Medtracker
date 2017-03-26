package sg.edu.nus.iss.se.ft05.medipal.fragments;

import android.content.Context;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sg.edu.nus.iss.se.ft05.medipal.managers.CategoryManager;
import sg.edu.nus.iss.se.ft05.medipal.R;
import sg.edu.nus.iss.se.ft05.medipal.activities.AddOrUpdateCategory;
import sg.edu.nus.iss.se.ft05.medipal.activities.MainActivity;
import sg.edu.nus.iss.se.ft05.medipal.adapters.CategoryListAdapter;
import static sg.edu.nus.iss.se.ft05.medipal.constants.Constants.*;

/**
 * A placeholder fragment containing a simple view.
 */
public class CategoryFragment extends Fragment {

    private CategoryListAdapter mAdapter;
    private Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.category_fragment, container, false);

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setVisibility(View.VISIBLE);

        FloatingActionButton fabSOS = (FloatingActionButton) getActivity().findViewById(R.id.fabSOS);
        fabSOS.setVisibility(View.GONE);

        ((MainActivity)getActivity()).setFloatingActionButtonAction(AddOrUpdateCategory.class);
        RecyclerView categoryRecyclerView;
        context = getActivity().getApplicationContext();

        //hide the share button
        setHasOptionsMenu(true);
        getActivity().invalidateOptionsMenu();

        categoryRecyclerView = (RecyclerView) view.findViewById(R.id.all_categories_list_view);
        // Set layout for the RecyclerView, because it's a list we are using the linear layout
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(context));

        // Get all guest info from the database and save in a cursor
        Cursor cursor = CategoryManager.findAll(context);

        // Create an adapter for that cursor to display the data
        mAdapter = new CategoryListAdapter(context, cursor);

        // Link the adapter to the RecyclerView
        categoryRecyclerView.setAdapter(mAdapter);

        getActivity().setTitle(CATEGORY);

        return view;

    }

}
