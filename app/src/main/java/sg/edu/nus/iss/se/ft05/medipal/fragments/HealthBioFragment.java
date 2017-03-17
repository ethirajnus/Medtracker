package sg.edu.nus.iss.se.ft05.medipal.fragments;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sg.edu.nus.iss.se.ft05.medipal.R;
import sg.edu.nus.iss.se.ft05.medipal.activities.AddOrUpdateHealthBioActivity;
import sg.edu.nus.iss.se.ft05.medipal.activities.MainActivity;
import sg.edu.nus.iss.se.ft05.medipal.adapters.HealthBioListAdapter;
import sg.edu.nus.iss.se.ft05.medipal.constants.Constants;
import sg.edu.nus.iss.se.ft05.medipal.model.HealthBio;

/**
 * @author Moushumi Seal
 *
 */

public class HealthBioFragment extends Fragment {

    private HealthBioListAdapter mAdapter;
    private Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Set view for Health Bio fragment
        View view = inflater.inflate(R.layout.healthbio_fragment, container, false);

        // Set floating action button
        ((MainActivity)getActivity()).setFloatingActionButtonAction(AddOrUpdateHealthBioActivity.class);

        // retrieving context
        context = getActivity().getApplicationContext();

        RecyclerView healthBioRecyclerView = (RecyclerView) view.findViewById(R.id.all_healthbio_list_view);

        healthBioRecyclerView.setLayoutManager(new LinearLayoutManager(context));

        // Get health bio from the database and save in a cursor
        Cursor cursor = HealthBio.findAll(context);

        // Create an adapter for that cursor to display the data
        mAdapter = new HealthBioListAdapter(context, cursor);

        // Link the adapter to the RecyclerView
        healthBioRecyclerView.setAdapter(mAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                //do nothing, we only care about swiping
                return false;
            }
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                //get the id of the item being swiped
                int id = (int) viewHolder.itemView.getTag();
                //remove from DB
                HealthBio healthBio = HealthBio.findById(context, id);
                healthBio.delete(context);
                //update the list
                mAdapter.swapCursor(HealthBio.findAll(context));
            }

            //attach the ItemTouchHelper to the healthBioRecyclerView
        }).attachToRecyclerView(healthBioRecyclerView);
        getActivity().setTitle(Constants.TITLE_VIEW_HEALTHBIO);

        return view;

    }
}
