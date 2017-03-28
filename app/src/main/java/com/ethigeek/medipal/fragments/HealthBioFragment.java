package com.ethigeek.medipal.fragments;

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

import com.ethigeek.medipal.R;
import com.ethigeek.medipal.activities.AddOrUpdateHealthBioActivity;
import com.ethigeek.medipal.activities.MainActivity;
import com.ethigeek.medipal.adapters.HealthBioListAdapter;
import com.ethigeek.medipal.utils.Constants;
import com.ethigeek.medipal.managers.HealthBioManager;


/**
 * Class for Health bio fragment operations
 *
 * @author Moushumi Seal
 */
public class HealthBioFragment extends Fragment {

    private HealthBioListAdapter mAdapter;
    private Context context;
    private HealthBioManager healthBioManager;
    private RecyclerView healthBioRecyclerView;
    private TextView tv_noHealthbio;

    /**
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Set view for Health Bio fragment
        View view = inflater.inflate(R.layout.healthbio_fragment, container, false);

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setVisibility(View.VISIBLE);

        FloatingActionButton fabSOS = (FloatingActionButton) getActivity().findViewById(R.id.fabSOS);
        TextView tvSOS = (TextView) getActivity().findViewById(R.id.tv_sos);
        fabSOS.setVisibility(View.GONE);
        tvSOS.setVisibility(View.GONE);

        // Set floating action button
        ((MainActivity) getActivity()).setFloatingActionButtonAction(AddOrUpdateHealthBioActivity.class);

        // retrieving context
        context = getActivity().getApplicationContext();

        healthBioRecyclerView = (RecyclerView) view.findViewById(R.id.all_healthbio_list_view);
        tv_noHealthbio = (TextView) view.findViewById(R.id.tv_noHealthbio);

        healthBioRecyclerView.setLayoutManager(new LinearLayoutManager(context));

        // Get health bio from the database and save in a cursor
        Cursor cursor = HealthBioManager.findAll(context);


        // Create an adapter for that cursor to display the data
        mAdapter = new HealthBioListAdapter(context, getActivity(), cursor,healthBioRecyclerView,tv_noHealthbio);

        checkForEmptyList();
        // Link the adapter to the RecyclerView
        healthBioRecyclerView.setAdapter(mAdapter);

        //hide the share button
        setHasOptionsMenu(true);
        getActivity().invalidateOptionsMenu();

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
                healthBioManager = new HealthBioManager();
                healthBioManager.findById(context, id);
                mAdapter.swapCursor(HealthBioManager.findAll(context));

                AlertDialog.Builder warningDialog = new AlertDialog.Builder(getActivity(), R.style.AppTheme_Dialog);
                warningDialog.setTitle(Constants.TITLE_WARNING);
                warningDialog.setMessage(R.string.warning_delete);
                warningDialog.setPositiveButton(Constants.BUTTON_YES, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface alert, int which) {
                        //remove from DB
                        new DeleteHealthBio().execute();
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

            //attach the ItemTouchHelper to the healthBioRecyclerView
        }).attachToRecyclerView(healthBioRecyclerView);
        getActivity().setTitle(Constants.TITLE_VIEW_HEALTHBIO);

        return view;

    }

    private class DeleteHealthBio extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            return healthBioManager.delete(context) == -1;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            Toast.makeText(context, R.string.delete_success, Toast.LENGTH_SHORT).show();
            mAdapter.swapCursor(HealthBioManager.findAll(context));
            checkForEmptyList();
        }
    }

    private void checkForEmptyList() {
        if (mAdapter != null) {
            tv_noHealthbio.setVisibility((mAdapter.getItemCount() == 0) ? View.VISIBLE : View.GONE);
            healthBioRecyclerView.setVisibility((mAdapter.getItemCount() == 0) ? View.GONE : View.VISIBLE);
        }
    }
}
