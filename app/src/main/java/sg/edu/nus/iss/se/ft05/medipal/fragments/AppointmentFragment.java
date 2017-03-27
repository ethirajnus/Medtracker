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

import sg.edu.nus.iss.se.ft05.medipal.Util.ReminderUtils;
import sg.edu.nus.iss.se.ft05.medipal.constants.Constants;
import sg.edu.nus.iss.se.ft05.medipal.managers.AppointmentManager;
import sg.edu.nus.iss.se.ft05.medipal.R;
import sg.edu.nus.iss.se.ft05.medipal.activities.AddNewAppointment;
import sg.edu.nus.iss.se.ft05.medipal.activities.MainActivity;
import sg.edu.nus.iss.se.ft05.medipal.adapters.AppointmentListAdapter;


/**
 * Class for Appointment fragement
 *
 * @author Dhruv Mandan Gopal
 */
public class AppointmentFragment extends Fragment {

    private RecyclerView recyclerView;
    private TextView noAppointments;
    private LinearLayoutManager linearLayoutManager;
    AppointmentManager appointmentManager;
    private AppointmentListAdapter mAdapter;
    private Context context;
    private static final String TITLE = "Appointments";

    /**
     * method for processing when creating view
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.appointment_fragment, container, false);

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setVisibility(View.VISIBLE);

        FloatingActionButton fabSOS = (FloatingActionButton) getActivity().findViewById(R.id.fabSOS);
        fabSOS.setVisibility(View.GONE);

        ((MainActivity) getActivity()).setFloatingActionButtonAction(AddNewAppointment.class);
        context = getActivity().getApplicationContext();
        recyclerView = (RecyclerView) view.findViewById(R.id.all_appointments_list_view);
        noAppointments = (TextView) view.findViewById(R.id.tv_noAppointments);
        linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        Cursor cursor = AppointmentManager.findAll(context);
        mAdapter = new AppointmentListAdapter(context, cursor, recyclerView, noAppointments);
        recyclerView.setAdapter(mAdapter);

        checkForEmptyList();

        //hide the share button
        setHasOptionsMenu(true);
        getActivity().invalidateOptionsMenu();

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            //  Override onMove and simply return false inside
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                //do nothing, we only care about swiping
                return false;
            }

            //  Override onSwiped
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {

                int id = (int) viewHolder.itemView.getTag();
                appointmentManager = new AppointmentManager();
                appointmentManager.findById(context, id);
                mAdapter.swapCursor(AppointmentManager.findAll(context));
                AlertDialog.Builder warningDialog = new AlertDialog.Builder(getActivity(), R.style.AppTheme_Dialog);
                warningDialog.setTitle(Constants.TITLE_WARNING);
                warningDialog.setMessage(R.string.warning_delete);
                warningDialog.setPositiveButton(Constants.BUTTON_YES, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface alert, int which) {
                        //remove from DB
                        new DeleteAppointment().execute();
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
            // attach the ItemTouchHelper to the waitlistRecyclerView
        }).attachToRecyclerView(recyclerView);


        getActivity().setTitle(TITLE);

        return view;
    }

    private class DeleteAppointment extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            return appointmentManager.delete(context) == -1;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            ReminderUtils.syncAppointmentReminder(context);
            mAdapter.swapCursor(AppointmentManager.findAll(context));
            Toast.makeText(context, R.string.delete_success, Toast.LENGTH_SHORT).show();
            checkForEmptyList();
        }
    }

    private void checkForEmptyList() {
        if (mAdapter != null) {
            noAppointments.setVisibility((mAdapter.getItemCount() == 0) ? View.VISIBLE : View.GONE);
            recyclerView.setVisibility((mAdapter.getItemCount() == 0) ? View.GONE : View.VISIBLE);
        }
    }
}
