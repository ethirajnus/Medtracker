package sg.edu.nus.iss.se.ft05.medipal.fragments;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import sg.edu.nus.iss.se.ft05.medipal.R;
import sg.edu.nus.iss.se.ft05.medipal.adapters.AppointmentListAdapter;
import sg.edu.nus.iss.se.ft05.medipal.model.Appointment;


public class DefaultFragment extends Fragment {
    private AppointmentListAdapter appointmentListAdapter;
    private Context context;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView appointmentRecyclerView;
    private Cursor cursor;
    private String date;
    private List<Appointment> appointments;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_default,container,false);
        context=getActivity().getApplicationContext();
        linearLayoutManager=new LinearLayoutManager(context);
        appointmentRecyclerView=(RecyclerView) view.findViewById(R.id.default_view);
        appointmentRecyclerView.setLayoutManager(linearLayoutManager);
        Calendar calendar=Calendar.getInstance();
        Date d=calendar.getTime();
        date=new SimpleDateFormat("dd-MM-yyyy").format(d);
        cursor=Appointment.filterDate(context,date);
        appointmentListAdapter=new AppointmentListAdapter(context,cursor);
        appointmentRecyclerView.setAdapter(appointmentListAdapter);
        return view;
    }


}
