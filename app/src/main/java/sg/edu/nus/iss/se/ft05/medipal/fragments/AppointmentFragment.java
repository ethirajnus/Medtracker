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

import sg.edu.nus.iss.se.ft05.medipal.Appointment;
import sg.edu.nus.iss.se.ft05.medipal.R;
import sg.edu.nus.iss.se.ft05.medipal.activities.AddNewAppointment;
import sg.edu.nus.iss.se.ft05.medipal.activities.MainActivity;
import sg.edu.nus.iss.se.ft05.medipal.adapters.AppointmentListAdapter;
import sg.edu.nus.iss.se.ft05.medipal.model.Medicine;

/**
 * Created by ethi on 08/03/17.
 */

public class AppointmentFragment extends Fragment {
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    private AppointmentListAdapter mAdapter;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.appointment_fragment, container, false);
        ((MainActivity)getActivity()).setFloatingActionButtonAction(AddNewAppointment.class);
        context=getActivity().getApplicationContext();
        recyclerView=(RecyclerView) view.findViewById(R.id.all_appointments_list_view);
        linearLayoutManager=new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        Cursor cursor = Appointment.findAll(context);
        mAdapter = new AppointmentListAdapter(context,cursor);
        recyclerView.setAdapter(mAdapter);
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            // COMPLETED (4) Override onMove and simply return false inside
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                //do nothing, we only care about swiping
                return false;
            }

            // COMPLETED (5) Override onSwiped
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                long id= (long) viewHolder.itemView.getTag();
                Appointment appointment = Appointment.findById(context, id);
                appointment.delete(context);
                //update the list
                mAdapter.swapCursor(Appointment.findAll(context));
            }

            //COMPLETED (11) attach the ItemTouchHelper to the waitlistRecyclerView
        }).attachToRecyclerView(recyclerView);

        return view;
    }

}
