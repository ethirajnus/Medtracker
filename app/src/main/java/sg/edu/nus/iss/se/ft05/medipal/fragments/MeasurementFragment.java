package sg.edu.nus.iss.se.ft05.medipal.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import sg.edu.nus.iss.se.ft05.medipal.constants.Constants;
import sg.edu.nus.iss.se.ft05.medipal.managers.MeasurementManager;
import sg.edu.nus.iss.se.ft05.medipal.R;
import sg.edu.nus.iss.se.ft05.medipal.activities.AddMeasurement;
import sg.edu.nus.iss.se.ft05.medipal.activities.MainActivity;
import sg.edu.nus.iss.se.ft05.medipal.adapters.MeasurementListAdapter;

/**
 * Class for Measurement fragment operations
 */
public class MeasurementFragment extends Fragment {

    private MeasurementListAdapter mAdapter;
    private Context context;
    private MeasurementManager measurementManager;
    private RecyclerView measurementRecyclerView;
    private TextView noMeasurement;

    public MeasurementFragment() {
        // Required empty public constructor
    }

    /**
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.measurement_fragment, container, false);

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setVisibility(View.VISIBLE);

        FloatingActionButton fabSOS = (FloatingActionButton) getActivity().findViewById(R.id.fabSOS);
        fabSOS.setVisibility(View.GONE);

        ((MainActivity) getActivity()).setFloatingActionButtonAction(AddMeasurement.class);

        context = getActivity().getApplicationContext();
        measurementRecyclerView = (RecyclerView) view.findViewById(R.id.all_measurement_list_view);
        // Set layout for the RecyclerView, because it's a list we are using the linear layout
        measurementRecyclerView.setLayoutManager(new LinearLayoutManager(context));

        noMeasurement = (TextView) view.findViewById(R.id.tv_noMeasurement);

        // Get all guest info from the database and save in a cursor
        Cursor cursor = MeasurementManager.findAll(context);

        // Create an adapter for that cursor to display the data
        mAdapter = new MeasurementListAdapter(context, cursor);

        // Link the adapter to the RecyclerView
        measurementRecyclerView.setAdapter(mAdapter);
        checkForEmptyList();

        //hide the share button
        setHasOptionsMenu(true);
        getActivity().invalidateOptionsMenu();

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
                mAdapter.swapCursor(MeasurementManager.findAll(context));
                // Inside, get the viewHolder's itemView's tag and store in a long variable id
                //get the id of the item being swiped
                int id = (int) viewHolder.itemView.getTag();
                measurementManager = new MeasurementManager();
                measurementManager.findById(context, id);
                AlertDialog.Builder warningDialog = new AlertDialog.Builder(getActivity(),R.style.AppTheme_Dialog);
                warningDialog.setTitle(Constants.TITLE_WARNING);
                warningDialog.setMessage(R.string.warning_delete);
                warningDialog.setPositiveButton(Constants.BUTTON_YES, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface alert, int which) {
                        //remove from DB
                        new DeleteMeasurement().execute();
                        alert.dismiss();
                    }
                });
                warningDialog.setNegativeButton(Constants.BUTTON_NO, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface alert, int which) {
                        alert.dismiss();
                    }
                });
                warningDialog.show();
            }

            //attach the ItemTouchHelper to the waitlistRecyclerView
        }).attachToRecyclerView(measurementRecyclerView);

        getActivity().setTitle("Measurements");

        // Inflate the layout for this fragment
        return view;

    }
    private class DeleteMeasurement extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            return measurementManager.delete(context)==-1;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            Toast.makeText(context, R.string.delete_success, Toast.LENGTH_SHORT).show();
            mAdapter.swapCursor(MeasurementManager.findAll(context));
            checkForEmptyList();
        }
    }
    private void checkForEmptyList(){
        if(mAdapter != null ){
            noMeasurement.setVisibility((mAdapter.getItemCount() == 0)? View.VISIBLE : View.GONE);
            measurementRecyclerView.setVisibility((mAdapter.getItemCount() == 0)? View.GONE : View.VISIBLE);
        }
    }
}
