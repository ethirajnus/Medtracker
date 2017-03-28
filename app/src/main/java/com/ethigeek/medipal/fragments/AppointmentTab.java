package com.ethigeek.medipal.fragments;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.ethigeek.medipal.R;
import com.ethigeek.medipal.adapters.AppointmentListAdapter;
import com.ethigeek.medipal.managers.AppointmentManager;

/**
 * Class for Appointment tab
 *
 * @author Dhruv Mandan Gopal
 */
public class AppointmentTab extends Fragment {
    private Context context;
    private TextView textView;
    private RecyclerView recyclerView;
    private AppointmentListAdapter mAdapter;
    private String date;
    private List<AppointmentManager> appointmentManagers;
    private static final String APPOINTMENTS = "Today's Appointments";
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private String content = "";
    private TextView noAppointments;


    public AppointmentTab() {
        // Required empty public constructor
    }

    /**
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_appointment_tab, container, false);
        context = getActivity().getApplicationContext();

        Calendar calendar = Calendar.getInstance();
        Date d = calendar.getTime();
        date = new SimpleDateFormat(DATE_FORMAT).format(d);
        context = getActivity().getApplicationContext();
        recyclerView = (RecyclerView) view.findViewById(R.id.all_appointments_list_view);
        noAppointments = (TextView) view.findViewById(R.id.tv_noAppointments);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        Cursor cursor = AppointmentManager.filterDate(context, date);
        mAdapter = new AppointmentListAdapter(context, getActivity(), cursor, recyclerView, noAppointments);
        recyclerView.setAdapter(mAdapter);

        if (mAdapter != null) {
            noAppointments.setVisibility((mAdapter.getItemCount() == 0) ? View.VISIBLE : View.GONE);
            recyclerView.setVisibility((mAdapter.getItemCount() == 0) ? View.GONE : View.VISIBLE);
        }


        return view;
    }


}
