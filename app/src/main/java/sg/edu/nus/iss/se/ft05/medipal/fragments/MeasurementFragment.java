package sg.edu.nus.iss.se.ft05.medipal.fragments;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sg.edu.nus.iss.se.ft05.medipal.Category;
import sg.edu.nus.iss.se.ft05.medipal.Measurement;
import sg.edu.nus.iss.se.ft05.medipal.R;
import sg.edu.nus.iss.se.ft05.medipal.activities.AddMeasurement;
import sg.edu.nus.iss.se.ft05.medipal.activities.AddOrUpdateCategory;
import sg.edu.nus.iss.se.ft05.medipal.activities.MainActivity;
import sg.edu.nus.iss.se.ft05.medipal.adapters.CategoryListAdapter;
import sg.edu.nus.iss.se.ft05.medipal.adapters.MeasurementListAdapter;


public class MeasurementFragment extends Fragment {

    private MeasurementListAdapter mAdapter;
    private Context context;
        public MeasurementFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.measurement_fragment, container, false);
        ((MainActivity)getActivity()).setFloatingActionButtonAction(AddMeasurement.class);
        RecyclerView measurementRecyclerView;
        context = getActivity().getApplicationContext();
        measurementRecyclerView = (RecyclerView) view.findViewById(R.id.all_measurement_list_view);
        // Set layout for the RecyclerView, because it's a list we are using the linear layout
        measurementRecyclerView.setLayoutManager(new LinearLayoutManager(context));

        // Get all guest info from the database and save in a cursor
        Cursor cursor = Measurement.findAll(context);

        // Create an adapter for that cursor to display the data
        mAdapter = new MeasurementListAdapter(context,cursor);

        // Link the adapter to the RecyclerView
        measurementRecyclerView.setAdapter(mAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            // Override onMove and simply return false inside
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                //do nothing, we only care about swiping
                return false;
            }

            // Override onSwiped
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                // Inside, get the viewHolder's itemView's tag and store in a long variable id
                //get the id of the item being swiped
                int id = (int) viewHolder.itemView.getTag();
                // call removeGuest and pass through that id
                //remove from DB
                Measurement measurement = Measurement.findById(context, id);
                measurement.delete(context);
                // call swapCursor on mAdapter passing in getAllGuests() as the argument
                //update the list
                mAdapter.swapCursor(Measurement.findAll(context));
            }

            //attach the ItemTouchHelper to the waitlistRecyclerView
        }).attachToRecyclerView(measurementRecyclerView);

        getActivity().setTitle("Measurements");

        // Inflate the layout for this fragment
        return view;

    }
}
